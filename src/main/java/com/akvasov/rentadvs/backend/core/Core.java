package com.akvasov.rentadvs.backend.core;

import com.akvasov.rentadvs.backend.controller.PageController;
import com.akvasov.rentadvs.config.ServerConfig;
import com.akvasov.rentadvs.db.DAO.AdvDAO;
import com.akvasov.rentadvs.db.DAO.FriendsDAO;
import com.akvasov.rentadvs.model.Advertsment;
import com.akvasov.rentadvs.model.User;
import org.aeonbits.owner.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by akvasov on 17.07.14.
 */
@Component
@Scope(value = "singleton")
public class Core {

    private static final Logger LOGGER = Logger.getLogger(Core.class.getName());
    private static final ServerConfig config = ConfigFactory.create(ServerConfig.class);

    private List<User> users = new ArrayList<>();
    private List<Advertsment> advs = new ArrayList<>();
    ExecutorService singleThreadExecutor;

    private Boolean isWork = false;

    @Autowired
    private FriendsDAO friendsDAO;
    @Autowired
    private AdvDAO advDAO;
    @Autowired
    private PageController pageController;
    @Autowired
    private ApplicationContext ctx;

    private final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            LOGGER.log(Level.FINE, "Start core Thread");
            singleThreadExecutor = Executors.newSingleThreadExecutor();

            while (isWork) {
                LOGGER.fine("Try to create new Session");
                Worker worker = createNewSession();
                try {

                    Future<Worker.WorkerResult> result = singleThreadExecutor.submit(worker);
                    result.get();

                    System.out.println("Session succesfully");
                    LOGGER.fine("Sleep 1000");

                    postProcessing(result.get().getUsers(), result.get().getAdvs());

                    thread.sleep(config.schedulerSleepAfterWork());

                    isWork = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            singleThreadExecutor.shutdown();
            LOGGER.fine("Core stop");
        }
    });

    public void stop() {
        isWork = false;
    }

    @PostConstruct
    private void init() {
        users = friendsDAO.loadAllUsers();
    }

    public synchronized Boolean start() {
        if (isWork) return false;
        isWork = true;

        thread.start();
        return true;
    }

    private Worker createNewSession() {
        Worker worker = ctx.getBean(Worker.class);
        //worker.setPageController(pageController);
        worker.addUsers(users);
        return worker;
    }

    private void postProcessing(Collection<User> users, Collection<Advertsment> advs) {
        List<User> deleteUsers = new ArrayList<>();
        List<User> newUsers = new ArrayList<>();
        //Logging changes users
        for (User u : this.users) {
            if (!users.contains(u)) {
                LOGGER.info("Delete user: " + u);
                deleteUsers.add(u);
            }
        }
        for (User u : users) {
            if (!this.users.contains(u)) {
                LOGGER.info("Add user: " + u);
                newUsers.add(u);
            }
        }

        this.users.clear();
        this.users.addAll(users);
        //---
        //Logging changes advs
        List<Advertsment> newAdvs = new ArrayList<>();

        for (Advertsment a : advs) {
            if (!this.advs.contains(a)) {
                LOGGER.info("Add adv: " + a);
                newAdvs.add(a);
                this.advs.add(a);
            }
        }
        //---
        System.out.println("Users: " + users.size() + " " + users);
        System.out.println("Advs: " + advs.size() + " " + advs);

        friendsDAO.deleteUsers(deleteUsers);
        friendsDAO.addUsers(newUsers);

        advDAO.addAdvs(newAdvs);
    }

    public void waitUntilRunning() throws InterruptedException {
        thread.join();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Advertsment> getAdvs() {
        return advs;
    }

    public void setAdvs(List<Advertsment> advs) {
        this.advs = advs;
    }
}
