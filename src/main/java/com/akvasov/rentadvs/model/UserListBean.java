package com.akvasov.rentadvs.model;

import com.akvasov.rentadvs.db.DAO.FriendsDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 25.08.14.
 */

@ManagedBean(name="usersList")
@RequestScoped
public class UserListBean implements Serializable {
    static final long serialVersionUID = 3L;

    @Autowired
    private FriendsDAO friendsDAO;

    private Integer size;
    private List<User> users = new ArrayList<>();

    public UserListBean() throws UnknownHostException {
        users = friendsDAO.loadAllUsers();
        size = users.size();
    }

    public Integer getSize() {
        return size;
    }

    public User getUserByIndex(Integer index){
        return users.get(index);
    }

    public List<User> getUsers() {
        return users;
    }
}
