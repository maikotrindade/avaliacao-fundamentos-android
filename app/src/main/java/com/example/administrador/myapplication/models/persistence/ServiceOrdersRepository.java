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
        String where = DatabaseContract.ID + " = ?";
        String[] args = {serviceOrder.getId().toString()};
        db.delete(DatabaseContract.SERVICE_ORDER_TABLE, where, args);
        db.close();
        helper.close();
    }

    public List<ServiceOrder> getAll() {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.SERVICE_ORDER_TABLE, DatabaseContract.COLUNS, null, null, null, null, DatabaseContract.DATE);
        List<ServiceOrder> serviceOrders = DatabaseContract.bindList(cursor);
        db.close();
        helper.close();
        return serviceOrders;
    }

    public User findUser(final String login, final String password) {
        String[] columns = {DatabaseContract.ID,
                DatabaseContract.USERNAME,
                DatabaseContract.PASSWORD
        };

        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase database = helper.getWritableDatabase();

        StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        query.append(columns);
        query.append(" FROM ");
        query.append(DatabaseContract.USER_TABLE);
        query.append(" WHERE ");
        query.append(DatabaseContract.USERNAME);
        query.append(" = ");
        query.append(login);
        query.append(" AND ");
        query.append(DatabaseContract.PASSWORD);
        query.append(" = ");
        query.append(password);

        Cursor cursor =  database.rawQuery(query.toString() , null);
        database.close();
        helper.close();
        return cursorToUser(cursor);
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setmId(cursor.getInt(0));
        user.setmUsername(cursor.getString(1));
        user.setmPassword(cursor.getString(2));
        return user;
    }

}