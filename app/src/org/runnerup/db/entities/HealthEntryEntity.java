
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Content values wrapper for the {@code location} table.
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class HealthEntryEntity extends AbstractEntity {

    private List<HealthValueEntity> values;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");

    public HealthEntryEntity() {
        super();
        values = new ArrayList<HealthValueEntity>();
    }

    public HealthEntryEntity(Cursor c) {
        super();
        try {
            toContentValues(c);
        } catch (Exception e) {
            Log.e(Constants.LOG, e.getMessage());
        }
    }


    public void setHealthTypeId(Long value) {
        values().put(Constants.DB.HEALTH_ENTRY.HEALTH_TYPE, value);
    }

    public Long getHealthTypeId() {
        if (values().containsKey(Constants.DB.HEALTH_ENTRY.HEALTH_TYPE)) {
            return values().getAsLong(Constants.DB.HEALTH_ENTRY.HEALTH_TYPE);
        }
        return null;
    }

    public HealthTypeEntity getHealthType() {
        HealthTypeEntity ht = new HealthTypeEntity();
        ht.readByPrimaryKey(db, getHealthTypeId());
        return ht;
    }

//    private void setTime(Long value) {
//        values().put(Constants.DB.HEALTH_ENTRY.TIME, value);
//    }

    public void setTime(Date date) {

        values().put(Constants.DB.HEALTH_ENTRY.TIME, dateFormat.format(date));
    }

    public Date getTime() {
        if (values().containsKey(Constants.DB.HEALTH_ENTRY.TIME)) {
            try {
                return dateFormat.parse(values().getAsString(Constants.DB.HEALTH_ENTRY.TIME));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected List<String> getValidColumns() {
        List<String> columns = new ArrayList<String>();
        columns.add(Constants.DB.PRIMARY_KEY);
        columns.add(Constants.DB.HEALTH_ENTRY.HEALTH_TYPE);
        columns.add(Constants.DB.HEALTH_ENTRY.TIME);
        columns.add(Constants.DB.HEALTH_ENTRY.COMMENT);
        return columns;
    }

    @Override
    protected String getTableName() {
        return Constants.DB.HEALTH_ENTRY.TABLE;
    }

    @Override
    protected String getNullColumnHack() {
        return null;
    }

    public void addValue(HealthValueEntity val) {
        if (val.getHealthEntryId() != null && (this.getId() == null || !val.getHealthEntryId().equals(this.getId()))) {
            throw new IllegalArgumentException("Foreign key of lap (" + val.getHealthEntryId() +
                    ") doesn't match the activity primary key (" + this.getId() + ")");
        }

        if (val.getHealthEntryId() == null && this.getId() != null) {
            val.setHealthEntryId(this.getId());
        }

        getValues().add(val);
    }

    public void addValues(List<HealthValueEntity> vals) {
        for (HealthValueEntity v : vals) {
            this.addValue(v);
        }
    }

    public void putValues(List<HealthValueEntity> vals) {
        this.getValues().clear();
        this.addValues(vals);
    }

    public List<HealthValueEntity> getValues() {
        if (values == null) {
            values = HealthValueEntity.getAll(db,getId().intValue());
        }

        return values;
    }

    public static List<HealthEntryEntity> getAll(SQLiteDatabase db){
        List<AbstractEntity> l = AbstractEntity.getAll(db, new HealthEntryEntity());

        List<HealthEntryEntity> at = new ArrayList<>();

        for (AbstractEntity ae:l
                ) {
            if(ae instanceof HealthEntryEntity)
                at.add((HealthEntryEntity)ae);
        }

        return at;
    };
}
