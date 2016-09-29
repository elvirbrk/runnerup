
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
import org.runnerup.workout.Sport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Content values wrapper for the {@code location} table.
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class SportEntity extends AbstractTypeEntity {

    public SportEntity() {
        super();
    }

    public SportEntity(Cursor c) {
        super();
        try {
            toContentValues(c);
        } catch (Exception e) {
            Log.e(Constants.LOG, e.getMessage());
        }
    }

    public void setName(String value) {
        values().put(Constants.DB.SPORT.NAME, value);
    }

    public String getName() {
        if (values().containsKey(Constants.DB.SPORT.NAME)) {
            return values().getAsString(Constants.DB.SPORT.NAME);
        }
        return null;
    }

    public void setFavorite(Integer value) {
        values().put(Constants.DB.SPORT.FAVORITE, value);
    }

    public Integer getFavorite() {
        if (values().containsKey(Constants.DB.SPORT.FAVORITE)) {
            return values().getAsInteger(Constants.DB.SPORT.FAVORITE);
        }
        return null;
    }

    @Override
    protected List<String> getValidColumns() {
        List<String> columns = new ArrayList<String>();
        columns.add(Constants.DB.PRIMARY_KEY);
        columns.add(Constants.DB.SPORT.NAME);
        columns.add(Constants.DB.SPORT.FAVORITE);

        return columns;
    }

    @Override
    protected String getTableName() {
        return Constants.DB.SPORT.TABLE;
    }

    @Override
    protected String getNullColumnHack() {
        return null;
    }

    public static List<AbstractTypeEntity> getAll(SQLiteDatabase db){
        List<AbstractTypeEntity> list = new ArrayList<AbstractTypeEntity>();
        for (AbstractEntity a : getAll(db, new SportEntity())) {
            list.add((SportEntity)a);
        }

        Collections.sort(list, new Comparator<AbstractTypeEntity>() {
            @Override
            public int compare(AbstractTypeEntity a, AbstractTypeEntity b) {
                if (((SportEntity)a).getFavorite().compareTo(((SportEntity)b).getFavorite()) != 0) {
                    return ((SportEntity)b).getFavorite().compareTo(((SportEntity)a).getFavorite());
                } else {
                    return a.getName().compareTo(b.getName());
                }
            }

        });

        return list;
    }


}
