package org.runnerup.db.entities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.runnerup.common.util.Constants;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elvir.brkic on 12.08.2016..
 */
public abstract class AbstractTypeEntity extends AbstractEntity {

    public abstract String getName();

    public AbstractTypeEntity() {
        super();

    }

    public AbstractTypeEntity(Cursor c) {
        super();
        try {
            toContentValues(c);
        } catch (Exception e) {
            Log.e(Constants.LOG, e.getMessage());
        }
    }

    public static List<AbstractTypeEntity> getAll(SQLiteDatabase db, AbstractTypeEntity inst) {

        String cols[] = new String[inst.getValidColumns().size()];
        inst.getValidColumns().toArray(cols);
        Cursor cursor = db.query(inst.getTableName(), cols, "" , null, null, null, null);

        List<AbstractTypeEntity> list = new ArrayList<AbstractTypeEntity>();
        try {
            if( cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                    AbstractTypeEntity ae = inst.getClass().getDeclaredConstructor(Cursor.class).newInstance(cursor);
                    ae.db = db;

                    list.add(ae);

                } while (cursor.moveToNext());

            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return list;
    }

        List<AbstractTypeEntity> list = new ArrayList<AbstractTypeEntity>();
        try {
            if( cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                    AbstractTypeEntity ae = inst.getClass().getDeclaredConstructor(Cursor.class).newInstance(cursor);
                    ae.db = db;

                    list.add(ae);

                } while (cursor.moveToNext());

            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return list;
    }

}
