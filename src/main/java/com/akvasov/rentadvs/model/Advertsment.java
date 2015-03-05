package com.akvasov.rentadvs.model;

import com.akvasov.rentadvs.utils.mongo.Mapper;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by alex on 21.06.14.
 */
public class Advertsment {

    private String id = null;
    private String userId = null;
    private String text = null;
    private Date date = null;
    private Integer type = null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }



    public String getId() {
        if (id == null){
            id = generateId();
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String generateId(){
        return StringUtils.leftPad(userId, 4, "0") + date.toString() + " " + hashCode();
    }

    public Object marshal() throws IllegalAccessException {
        return Mapper.unmarshal(this);
    }

    public static Advertsment unMarshal(Object o){
        return Mapper.marshal(Advertsment.class, o);
    }

    @Override
    public String toString() {
        return "Advertsment{" +
                "userId='" + userId + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Advertsment that = (Advertsment) o;

        if (!date.equals(that.date)) return false;
        if (!id.equals(that.id)) return false;
        if (!text.equals(that.text)) return false;
        if (!userId.equals(that.userId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
