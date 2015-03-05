package com.akvasov.rentadvs.model;

import com.akvasov.rentadvs.db.DAO.DBDAOFactory;

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

    private Integer size;
    private List<User> users = new ArrayList<>();

    public UserListBean() throws UnknownHostException {
        users = DBDAOFactory.getFriendsDAO().loadAllUsers();
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
