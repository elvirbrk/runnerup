
/**
 * Created by elvir.brkic on 09.08.2016..
 */
/**
 * Created by elvir.brkic on 09.08.2016..
 */
/*
 * Copyright (C) 2013 jonas.oreland@gmail.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

    package org.runnerup.db.entities;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import org.runnerup.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Content values wrapper for the {@code location} table.
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class LastValueEntity extends AbstractEntity {

    public LastValueEntity() {
        super();
    }

    public LastValueEntity(Cursor c) {
        super();
        try {
            toContentValues(c);
        } catch (Exception e) {
            Log.e(Constants.LOG, e.getMessage());
        }
    }


    public void setTabName(String value) {
        values().put(Constants.DB.LAST_VALUES.TAB_NAME, value);
    }

    public String getTabName() {
        if (values().containsKey(Constants.DB.LAST_VALUES.TAB_NAME)) {
            return values().getAsString(Constants.DB.LAST_VALUES.TAB_NAME);
        }
        return null;
    }

    public void setField(String value) {
        values().put(Constants.DB.LAST_VALUES.FIELD, value);
    }

    public String getField() {
        if (values().containsKey(Constants.DB.LAST_VALUES.FIELD)) {
            return values().getAsString(Constants.DB.LAST_VALUES.FIELD);
        }
        return null;
    }

    public void setValue(String value) {
        values().put(Constants.DB.LAST_VALUES.VALUE, value);
    }

    public String getValue() {
        if (values().containsKey(Constants.DB.LAST_VALUES.VALUE)) {
            return values().getAsString(Constants.DB.LAST_VALUES.VALUE);
        }
        return null;
    }

    @Override
    protected List<String> getValidColumns() {
        List<String> columns = new ArrayList<String>();
        columns.add(Constants.DB.PRIMARY_KEY);
        columns.add(Constants.DB.LAST_VALUES.TAB_NAME);
        columns.add(Constants.DB.LAST_VALUES.FIELD);
        columns.add(Constants.DB.LAST_VALUES.VALUE);
        return columns;
    }

    @Override
    protected String getTableName() {
        return Constants.DB.LAST_VALUES.TABLE;
    }

    @Override
    protected String getNullColumnHack() {
        return null;
    }

    public long insertOrUpdate(SQLiteDatabase db) {
        if(getId() != null && getId() > 0){
            super.update(db);
        } else {
            super.insert(db);
        }
        return this.getId();
    }

    public void readByTabAndField(SQLiteDatabase DB, String tab, String field) {

        this.db = DB;
        this.setTabName(tab);
        this.setField(field);
        String cols[] = new String[getValidColumns().size()];
        getValidColumns().toArray(cols);
        Cursor cursor = DB.query(getTableName(), cols, " " + Constants.DB.LAST_VALUES.TAB_NAME+" = '"+tab+ "' and "+
                        Constants.DB.LAST_VALUES.FIELD +" = '"+ field+"'", null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                toContentValues(cursor);
            }
        } finally {
            cursor.close();
        }
    }
}
