package com.akvasov.rentadvs.utils.mongo;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by alex on 12.08.14.
 */
public class Mapper {

    public static <T> T marshal(Class<T> clazz, Object o) {
        T result = null;
        try {
            result = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        DBObject dbo = (DBObject) o;

        for (String key : dbo.keySet()) {
            if (key.equals("_id")) continue;
            Field field;
            try {
                field = clazz.getDeclaredField(key);
                field.setAccessible(true);
                Class<?> type = field.getType();
                if (type.equals(String.class)) {
                    field.set(result, String.valueOf(dbo.get(key)));
                } else if (type.equals(int.class)) {
                    field.set(result, dbo.get(key));
                } else if (type.equals(Integer.class)) {
                    field.set(result, dbo.get(key));
                } else if (type.equals(Date.class)) {
                    field.set(result, dbo.get(key));
                } else if (type.equals(List.class) || type.equals(ArrayList.class)) {
                    List arrayList = new ArrayList<>();
                    BasicDBList lst = (BasicDBList) dbo.get(key);
                    ListIterator<Object> it = lst.listIterator();
                    while(it.hasNext()){
                        Object next = it.next();
                        if (next.getClass().equals(Integer.class)){
                            arrayList.add(next);
                        } else {
                            throw new RuntimeException("Unsupported type: " + it.next().getClass().getName());
                        }
                    }
                    field.set(result, arrayList);
                } else {
                    throw new RuntimeException("Unsupported type: " + dbo.get(key).getClass().getName());
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    public static Object unmarshal(Object obj) throws IllegalAccessException {
        BasicDBObject result = new BasicDBObject();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.getType().equals(String.class) || f.getType().equals(int.class) ||
                    f.getType().equals(Integer.class) || f.getType().equals(List.class) ||
            f.getType().equals(Date.class)) {
                Object o = f.get(obj);
                if (o != null) {
                    result.append(f.getName(), f.get(obj));
                }
            } else if (f.getType().equals(List.class)) {
                List o = (List) f.get(obj);
                if (o != null) {
                    result.put("$push", o.toArray());
                }
            } else {
                throw new RuntimeException("Unsupported type: " + f.getType().getName());
            }
        }
        return result;
    }


}
