package com.akvasov.rentadvs.model;

import com.akvasov.rentadvs.utils.mongo.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 21.06.14.
 */
public class User {

    public enum UserType {
        FRIEND, FRIENDS_FRIEND, FRIENDS_FRIENDS_FRIEND, OTHER;

        public int toInt(){
            if (this == FRIEND) return 0;
            if (this == FRIENDS_FRIEND) return 1;
            if (this == FRIENDS_FRIENDS_FRIEND) return 2;
            return 3;
        }

        public static UserType fromInt(int i){
            if (i == 0) return FRIEND;
            if (i == 1) return FRIENDS_FRIEND;
            if (i == 2) return FRIENDS_FRIENDS_FRIEND;
            return OTHER;
        }
    }

    private List<Integer> friendsIds = new ArrayList<>();
    private String id = null;
    private String name = null;
    private int userType = UserType.OTHER.toInt();

    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<Integer> getFriendsIds() {
        return friendsIds;
    }

    public void setFriendsIds(List<Integer> friendsIds) {
        this.friendsIds = friendsIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getUserType() {
        return UserType.fromInt(userType);
    }

    public void setUserType(UserType userType) {
        this.userType = userType.toInt();
    }

    public Object marshal() throws IllegalAccessException {
        return Mapper.unmarshal(this);
    }

    public static User unMarshal(Object o){
        return Mapper.marshal(User.class, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userType != user.userType) return false;
        if (!id.equals(user.id)) return false;
        if (!name.equals(user.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = friendsIds != null ? friendsIds.hashCode() : 0;
        result = 31 * result + id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + userType;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{")
                .append("id=").append(id).append(", ")
                .append("name=").append(name).append(", ")
                .append("userType=").append(userType).append(", ")
                .append("friendsIds=").append(friendsIds).append(", ")
                .append("}");
        return sb.toString();
    }
}
