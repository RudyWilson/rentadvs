package com.akvasov.rentadvs.backend.core;

import com.akvasov.rentadvs.backend.command.Command;
import com.akvasov.rentadvs.backend.command.GetAdvCommand;
import com.akvasov.rentadvs.backend.command.GetFriendsCommand;
import com.akvasov.rentadvs.config.ServerConfig;
import com.akvasov.rentadvs.model.Advertsment;
import com.akvasov.rentadvs.model.User;
import org.aeonbits.owner.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Created by akvasov on 17.07.14.
 */
@Component
@Scope(value = "prototype")
public class Worker implements Callable<Worker.WorkerResult> {

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private TextAnalizator textAnalizator;

    public class WorkerResult {

        private final Collection<User> users;
        private final Collection<Advertsment> advs;

        public WorkerResult(Collection<User> users, Collection<Advertsment> advs) {
            this.users = users;
            this.advs = advs;
        }

        public Collection<User> getUsers() {
            return users;
        }

        public Collection<Advertsment> getAdvs() {
            return advs;
        }
    }

    private static final Logger LOGGER = Logger.getLogger(Worker.class.getName());
    private static final ServerConfig config =  ConfigFactory.create(ServerConfig.class);
    private Queue<Command> scheduler = new ConcurrentLinkedQueue<>();

    private Set<User> userSet = new HashSet();
    private Set<Advertsment> advertsmentSet = new HashSet();

    public synchronized void setFriends(User user, List<User> friends) {
        List<Integer> ids = new ArrayList<>();
        for (User u: friends){
            ids.add(Integer.valueOf(u.getId()));
        }
        user.setFriendsIds(ids);
    }

    public synchronized void addUsers(List<User> friends) {
        LOGGER.fine("Worker: Try to add userQueue with ids = " + friends);
        for (User u : friends) {
            if (!userSet.contains(u)) {
                LOGGER.fine("Worker: Add user with id = " + u.getId());
                userSet.add(u);

                addTask(ctx.getBean(GetFriendsCommand.class, this, u));
                addTask(ctx.getBean(GetAdvCommand.class, this, u));
            }
        }
    }

    private boolean isValidAdv(Advertsment adv){
        LOGGER.fine("Analize adv: " + adv);
        Date date = new Date();
        Calendar c_now = Calendar.getInstance();
        c_now.setTime(date);

        Calendar c = Calendar.getInstance();
        c.setTime(adv.getDate());

        c.add(Calendar.DAY_OF_MONTH, 7);
        boolean after = c.after(c_now);
        boolean textValid = textAnalizator.isValid(adv.getText());

        LOGGER.info("Adv: " + adv + " is " + after + " " + textValid);
        return after && textValid;
    }

    public synchronized void addAdvs(List<Advertsment> advs){
        LOGGER.fine("Worker: Try to add adv " + advs);
        for (Advertsment adv: advs){
            if (!advertsmentSet.contains(adv) && isValidAdv(adv)){
                LOGGER.fine("Worker: Add advertsment " + adv);
                advertsmentSet.add(adv);
            }
        }
    }

    public void addTask(Command task){
        scheduler.add(task);
    }

    public Boolean isEmpty(){
        return scheduler.isEmpty();
    }

    @Override
    public WorkerResult call() throws Exception {
        LOGGER.fine("Start worker ");
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        while (!isEmpty()){
            Command task = scheduler.remove();
            int attempts = 0;
            Future<Boolean> result;
            try {
                do{
                    result = executorService.schedule(task, config.schedulerPeriod(), TimeUnit.MILLISECONDS);
                    attempts++;
                } while (result.get() == false && attempts < 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        LOGGER.fine("Worker finish");
        return new WorkerResult(userSet, advertsmentSet);
    }
}
