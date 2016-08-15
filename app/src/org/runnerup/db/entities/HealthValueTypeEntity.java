
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
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import org.runnerup.common.util.Constants;
import org.runnerup.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Content values wrapper for the {@code location} table.
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class HealthValueTypeEntity extends AbstractTypeEntity {

    public HealthValueTypeEntity() {
        super();
    }

    public HealthValueTypeEntity(Cursor c) {
        super();
        try {
            toContentValues(c);
        } catch (Exception e) {
            Log.e(Constants.LOG, e.getMessage());
        }
    }


    public void setHealthTypeId(Long value) {
        values().put(Constants.DB.HEALTH_VALUE_TYPE.HEALTH_TYPE, value);
    }

    public Long getHealthTypeId() {
        if (values().containsKey(Constants.DB.HEALTH_VALUE_TYPE.HEALTH_TYPE)) {
            return values().getAsLong(Constants.DB.HEALTH_VALUE_TYPE.HEALTH_TYPE);
        }
        return null;
    }

    public void setName(String value) {
        values().put(Constants.DB.HEALTH_VALUE_TYPE.NAME, value);
    }

    public String getName() {
        if (values().containsKey(Constants.DB.HEALTH_VALUE_TYPE.NAME)) {
            return values().getAsString(Constants.DB.HEALTH_VALUE_TYPE.NAME);
        }
        return null;
    }

    public void setOrder(Long value) {
        values().put(Constants.DB.HEALTH_VALUE_TYPE.ORDER, value);
    }

    public Long getOrder() {
        if (values().containsKey(Constants.DB.HEALTH_VALUE_TYPE.ORDER)) {
            return values().getAsLong(Constants.DB.HEALTH_VALUE_TYPE.ORDER);
        }
        return null;
    }



    @Override
    protected List<String> getValidColumns() {
        List<String> columns = new ArrayList<String>();
        columns.add(Constants.DB.PRIMARY_KEY);
        columns.add(Constants.DB.HEALTH_VALUE_TYPE.HEALTH_TYPE);
        columns.add(Constants.DB.HEALTH_VALUE_TYPE.NAME);
        columns.add(Constants.DB.HEALTH_VALUE_TYPE.ORDER);
        return columns;
    }

    @Override
    protected String getTableName() {
        return Constants.DB.HEALTH_VALUE_TYPE.TABLE;
    }

    @Override
    protected String getNullColumnHack() {
        return null;
    }

    public static List<HealthValueTypeEntity> getAll(SQLiteDatabase db, int healthTypeId){
        List<HealthValueTypeEntity> list = new ArrayList<HealthValueTypeEntity>();
        for (AbstractTypeEntity a : getAll(db, new HealthValueTypeEntity())) {
            if (((HealthValueTypeEntity)a).getHealthTypeId() == healthTypeId)
            {
                list.add((HealthValueTypeEntity)a);
            }

        }

        return list;
    }
}
