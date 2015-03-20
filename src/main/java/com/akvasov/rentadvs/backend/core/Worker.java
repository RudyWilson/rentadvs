package com.akvasov.rentadvs.backend.core;

import com.akvasov.rentadvs.backend.command.Command;
import com.akvasov.rentadvs.backend.command.GetAdvCommand;
import com.akvasov.rentadvs.backend.command.GetFriendsCommand;
import com.akvasov.rentadvs.backend.controller.PageController;
import com.akvasov.rentadvs.config.ServerConfig;
import com.akvasov.rentadvs.model.Advertsment;
import com.akvasov.rentadvs.model.User;
import org.aeonbits.owner.ConfigFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Created by akvasov on 17.07.14.
 */
public class Worker implements Callable<Worker.WorkerResult> {

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

    private PageController pageController;

    public PageController getPageController() {
        return pageController;
    }

    public void setPageController(PageController pageController) {
        this.pageController = pageController;
    }

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

                addTask(new GetFriendsCommand(this, getPageController(), u));
                addTask(new GetAdvCommand(this, getPageController(), u));
            }
        }
    }

    private static boolean isValidAdv(Advertsment adv){
        LOGGER.fine("Analize adv: " + adv);
        Date date = new Date();
        Calendar c_now = Calendar.getInstance();
        c_now.setTime(date);

        Calendar c = Calendar.getInstance();
        c.setTime(adv.getDate());

        c.add(Calendar.DAY_OF_MONTH, 7);
        boolean after = c.after(c_now);
        boolean textValid = TextAnalizator.getInstance().isValid(adv.getText());

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
