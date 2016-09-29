
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
public class CaloriesEntity extends AbstractEntity {

    public CaloriesEntity() {
        super();
    }

    public CaloriesEntity(Cursor c) {
        super();
        try {
            toContentValues(c);
        } catch (Exception e) {
            Log.e(Constants.LOG, e.getMessage());
        }
    }


    public void setSportId(Integer value) {
        values().put(Constants.DB.CALORIES.SPORT_ID, value);
    }

    public Integer getSportId() {
        if (values().containsKey(Constants.DB.CALORIES.SPORT_ID)) {
            return values().getAsInteger(Constants.DB.CALORIES.SPORT_ID);
        }
        return null;
    }

    public void setCalories1(Integer value) {
        values().put(Constants.DB.CALORIES.CALORIES1, value);
    }

    public Integer getCalories1() {
        if (values().containsKey(Constants.DB.CALORIES.CALORIES1)) {
            return values().getAsInteger(Constants.DB.CALORIES.CALORIES1);
        }
        return null;
    }

    public void setCalories2(Integer value) {
        values().put(Constants.DB.CALORIES.CALORIES2, value);
    }

    public Integer getCalories2() {
        if (values().containsKey(Constants.DB.CALORIES.CALORIES2)) {
            return values().getAsInteger(Constants.DB.CALORIES.CALORIES2);
        }
        return null;
    }

    public void setWeight1(Integer value) {
        values().put(Constants.DB.CALORIES.WEIGHT1, value);
    }

    public Integer getWeight1() {
        if (values().containsKey(Constants.DB.CALORIES.WEIGHT1)) {
            return values().getAsInteger(Constants.DB.CALORIES.WEIGHT1);
        }
        return null;
    }

    public void setWeight2(Integer value) {
        values().put(Constants.DB.CALORIES.WEIGHT2, value);
    }

    public Integer getWeight2() {
        if (values().containsKey(Constants.DB.CALORIES.WEIGHT2)) {
            return values().getAsInteger(Constants.DB.CALORIES.WEIGHT2);
        }
        return null;
    }


    @Override
    protected List<String> getValidColumns() {
        List<String> columns = new ArrayList<String>();
        columns.add(Constants.DB.PRIMARY_KEY);
        columns.add(Constants.DB.CALORIES.SPORT_ID);
        columns.add(Constants.DB.CALORIES.WEIGHT1);
        columns.add(Constants.DB.CALORIES.WEIGHT2);
        columns.add(Constants.DB.CALORIES.CALORIES1);
        columns.add(Constants.DB.CALORIES.CALORIES2);

        return columns;
    }

    @Override
    protected String getTableName() {
        return Constants.DB.CALORIES.TABLE;
    }

    @Override
    protected String getNullColumnHack() {
        return null;
    }

    public int getCaloriesByWeightAndTime(int weight, int minutes){

        double k = (double)(getCalories2()-getCalories1())/(getWeight2()-getWeight1());

        double l = getCalories2() - k*getWeight2();

        double cal = (k*weight + l);

        double res = cal * minutes / 60;

        return (int)res;
    }


}
