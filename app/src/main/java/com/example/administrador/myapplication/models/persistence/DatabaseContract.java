package com.example.administrador.myapplication.models.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.administrador.myapplication.models.entities.ServiceOrder;
import com.example.administrador.myapplication.models.entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseContract {

    public static final String SERVICE_ORDER_TABLE = "service_order";
    public static final String ID = "id";
    public static final String CLIENT = "client";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String DATE = "date";
    public static final String VALUE = "value";
    public static final String PAID = "paid";
    public static final String DESCRIPTION = "description";
    public static final String ACTIVE = "active";
    public static final String CATEGORY = "category";
    public static final String[] SERVICE_ORDER_COLUMNS = {ID, CLIENT, PHONE, ADDRESS, DATE, VALUE, PAID, DESCRIPTION, ACTIVE, CATEGORY};

    public static final String USER_TABLE = "user";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String[] USER_COLUMNS = {ID, USERNAME, PASSWORD};

    public static String createTableServiceOrder() {
        final StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(SERVICE_ORDER_TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(CLIENT + " TEXT, ");
        sql.append(PHONE + " TEXT, ");
        sql.append(ADDRESS + " TEXT, ");
        sql.append(DATE + " INTEGER, ");
        sql.append(VALUE + " REAL, ");
        sql.append(PAID + " INTEGER, ");
        sql.append(DESCRIPTION + " TEXT, ");
        sql.append(ACTIVE + " INTEGER, ");
        sql.append(CATEGORY + " INTEGER ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static String createTableUser() {
        final StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(USER_TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(USERNAME + " TEXT, ");
        sql.append(PASSWORD + " TEXT ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static ContentValues getUserAdmin() {
        ContentValues content = new ContentValues();
        content.put(ID, 1);
        content.put(USERNAME, "admin");
        content.put(PASSWORD, "admin");
        return content;
    }

    public static ContentValues getContentValues(ServiceOrder serviceOrder) {
        ContentValues content = new ContentValues();
        content.put(ID, serviceOrder.getId());
        content.put(CLIENT, serviceOrder.getClient());
        content.put(PHONE, serviceOrder.getPhone());
        content.put(ADDRESS, serviceOrder.getAddress());
        content.put(DATE, serviceOrder.getDate().getTime());
        content.put(VALUE, serviceOrder.getValue());
        content.put(PAID, serviceOrder.isPaid() ? 1 : 0);
        content.put(DESCRIPTION, serviceOrder.getDescription());
        content.put(ACTIVE, serviceOrder.isActive() ? 1 : 0);
        content.put(CATEGORY, serviceOrder.getCategory());
        return content;
    }

    public static ServiceOrder bindServiceOrder(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            ServiceOrder serviceOrder = new ServiceOrder();
            serviceOrder.setId((cursor.getInt(cursor.getColumnIndex(ID))));
            serviceOrder.setClient(cursor.getString(cursor.getColumnIndex(CLIENT)));
            serviceOrder.setPhone(cursor.getString(cursor.getColumnIndex(PHONE)));
            serviceOrder.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
            serviceOrder.setDate(new Date(cursor.getInt(cursor.getColumnIndex(DATE))));
            serviceOrder.setValue(cursor.getLong(cursor.getColumnIndex(VALUE)));
            serviceOrder.setPaid(cursor.getInt(cursor.getColumnIndex(PAID)) == 1);
            serviceOrder.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
            serviceOrder.setActive(cursor.getInt(cursor.getColumnIndex(ACTIVE)) == 1);
            serviceOrder.setCategory(cursor.getInt(cursor.getColumnIndex(CATEGORY)));
            return serviceOrder;
        }
        return null;
    }

    public static List<ServiceOrder> bindServiceOrderList(Cursor cursor) {
        final List<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();
        while (cursor.moveToNext()) {
            serviceOrders.add(bindServiceOrder(cursor));
        }
        return serviceOrders;
    }

    public static User bindUser(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            User user = new User();
            user.setId((cursor.getInt(cursor.getColumnIndex(ID))));
            user.setUsername(cursor.getString(cursor.getColumnIndex(USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
            return user;
        }
        return null;
    }

}
