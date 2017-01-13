
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Content values wrapper for the {@code location} table.
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class SportIntensityEntity extends AbstractTypeEntity {

    public SportIntensityEntity() {
        super();
    }

    public SportIntensityEntity(Cursor c) {
        super();
        try {
            toContentValues(c);
        } catch (Exception e) {
            Log.e(Constants.LOG, e.getMessage());
        }
    }

    public void setSportId(Long value) {
        values().put(Constants.DB.SPORT_INTENSITY.SPORT_ID, value);
    }

    public Long getSportId() {
        if (values().containsKey(Constants.DB.SPORT_INTENSITY.SPORT_ID)) {
            return values().getAsLong(Constants.DB.SPORT_INTENSITY.SPORT_ID);
        }
        return null;
    }

    public void setName(String value) {
        values().put(Constants.DB.SPORT_INTENSITY.NAME, value);
    }

    public String getName() {
        if (values().containsKey(Constants.DB.SPORT_INTENSITY.NAME)) {
            return values().getAsString(Constants.DB.SPORT_INTENSITY.NAME);
        }
        return null;
    }

    public void setFavorite(Integer value) {
        values().put(Constants.DB.SPORT_INTENSITY.FAVORITE, value);
    }

    public Integer getFavorite() {
        if (values().containsKey(Constants.DB.SPORT_INTENSITY.FAVORITE)) {
            return values().getAsInteger(Constants.DB.SPORT_INTENSITY.FAVORITE);
        }
        return null;
    }

    public void setMinSpeed(Integer value) {
        values().put(Constants.DB.SPORT_INTENSITY.MIN_SPEED, value);
    }

    public Integer getMinSpeed() {
        if (values().containsKey(Constants.DB.SPORT_INTENSITY.MIN_SPEED)) {
            return values().getAsInteger(Constants.DB.SPORT_INTENSITY.MIN_SPEED);
        }
        return null;
    }

    public void setMaxSpeed(Integer value) {
        values().put(Constants.DB.SPORT_INTENSITY.MAX_SPEED, value);
    }

    public Integer getMaxSpeed() {
        if (values().containsKey(Constants.DB.SPORT_INTENSITY.MAX_SPEED)) {
            return values().getAsInteger(Constants.DB.SPORT_INTENSITY.MAX_SPEED);
        }
        return null;
    }

    @Override
    protected List<String> getValidColumns() {
        List<String> columns = new ArrayList<String>();
        columns.add(Constants.DB.PRIMARY_KEY);
        columns.add(Constants.DB.SPORT_INTENSITY.SPORT_ID);
        columns.add(Constants.DB.SPORT_INTENSITY.NAME);
        columns.add(Constants.DB.SPORT_INTENSITY.FAVORITE);
        columns.add(Constants.DB.SPORT_INTENSITY.MIN_SPEED);
        columns.add(Constants.DB.SPORT_INTENSITY.MAX_SPEED);

        return columns;
    }

    @Override
    protected String getTableName() {
        return Constants.DB.SPORT_INTENSITY.TABLE;
    }

    @Override
    protected String getNullColumnHack() {
        return null;
    }


    public static List<SportIntensityEntity> getAll(SQLiteDatabase db, int sportId){
        List<SportIntensityEntity> list = new ArrayList<SportIntensityEntity>();
        for (AbstractEntity a : getAll(db, new SportIntensityEntity())) {
            if (((SportIntensityEntity)a).getSportId() == sportId)
            {
                list.add((SportIntensityEntity)a);
            }

        }

        return list;
    }


}
