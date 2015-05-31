package com.example.administrador.myapplication.models.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrador.myapplication.models.entities.ServiceOrder;
import com.example.administrador.myapplication.models.entities.User;
import com.example.administrador.myapplication.util.AppUtil;

import java.util.List;

public final class ServiceOrdersRepository {

    private static class Singleton {
        public static final ServiceOrdersRepository INSTANCE = new ServiceOrdersRepository();
    }

    public ServiceOrdersRepository() {
        super();
    }

    public static ServiceOrdersRepository getInstance() {
        return Singleton.INSTANCE;
    }

    public void save(ServiceOrder serviceOrder) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        if (serviceOrder.getId() == null) {
            db.insert(DatabaseContract.SERVICE_ORDER_TABLE, null, DatabaseContract.getContentValues(serviceOrder));
        } else {
            String where = DatabaseContract.ID + " = ?";
            String[] args = {serviceOrder.getId().toString()};
            db.update(DatabaseContract.SERVICE_ORDER_TABLE, DatabaseContract.getContentValues(serviceOrder), where, args);
        }
        db.close();
        helper.close();
    }

    public void delete(ServiceOrder serviceOrder) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        serviceOrder.setActive(false);
        String where = DatabaseContract.ID + " = ?";
        String[] args = {serviceOrder.getId().toString()};
        db.update(DatabaseContract.SERVICE_ORDER_TABLE, DatabaseContract.getContentValues(serviceOrder), where, args);

        db.close();
        helper.close();
    }

    public List<ServiceOrder> getAll() {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.SERVICE_ORDER_TABLE, DatabaseContract.SERVICE_ORDER_COLUMNS, null, null, null, null, DatabaseContract.DATE);
        List<ServiceOrder> serviceOrders = DatabaseContract.bindServiceOrderList(cursor);
        db.close();
        helper.close();
        return serviceOrders;
    }


    public List<ServiceOrder> getAllByStatus(boolean status) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        String where = DatabaseContract.ACTIVE + " = ? ";
        String[] args = {status ? "1" : "0"};
        Cursor cursor = db.query(DatabaseContract.SERVICE_ORDER_TABLE, DatabaseContract.SERVICE_ORDER_COLUMNS, where, args, null, null, DatabaseContract.DATE);
        List<ServiceOrder> serviceOrders = DatabaseContract.bindServiceOrderList(cursor);
        db.close();
        helper.close();
        return serviceOrders;
    }

    public List<ServiceOrder> getAllByStatusAndPayment(boolean status, boolean payment) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        String where = DatabaseContract.ACTIVE + " = ? AND " + DatabaseContract.PAID + " = ? ";
        String[] args = {status ? "1" : "0", payment ? "1" : "0"};
        Cursor cursor = db.query(DatabaseContract.SERVICE_ORDER_TABLE, DatabaseContract.SERVICE_ORDER_COLUMNS, where, args, null, null, DatabaseContract.DATE);
        List<ServiceOrder> serviceOrders = DatabaseContract.bindServiceOrderList(cursor);
        db.close();
        helper.close();
        return serviceOrders;
    }

    public User findUser(final String username, final String password) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        String where = DatabaseContract.USERNAME + " = ? AND " + DatabaseContract.PASSWORD + " = ? ";
        String[] args = {username, password};
        Cursor cursor = db.query(DatabaseContract.USER_TABLE, DatabaseContract.USER_COLUMNS, where, args, null, null, DatabaseContract.USERNAME);
        User user = DatabaseContract.bindUser(cursor);
        db.close();
        helper.close();
        return user;
    }

}