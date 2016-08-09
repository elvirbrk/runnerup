
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
        import android.os.Build;
        import android.util.Log;

        import org.runnerup.common.util.Constants;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Content values wrapper for the {@code location} table.
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class HealthDataValueUnitEntity extends AbstractEntity {

    public HealthDataValueUnitEntity() {
        super();
    }

    public HealthDataValueUnitEntity(Cursor c) {
        super();
        try {
            toContentValues(c);
        } catch (Exception e) {
            Log.e(Constants.LOG, e.getMessage());
        }
    }


    public void setGroup(Integer value) {
        values().put(Constants.DB.HEALTH_DATA_VALUE_UNITS.GROUP, value);
    }

    public Integer getGroup() {
        if (values().containsKey(Constants.DB.HEALTH_DATA_VALUE_UNITS.GROUP)) {
            return values().getAsInteger(Constants.DB.HEALTH_DATA_VALUE_UNITS.GROUP);
        }
        return null;
    }

    public void setHealtDataType(Long value) {
        values().put(Constants.DB.HEALTH_DATA_VALUE_UNITS.HEALTH_DATA_TYPE, value);
    }

    public Long getHealtDataType() {
        if (values().containsKey(Constants.DB.HEALTH_DATA_VALUE_UNITS.HEALTH_DATA_TYPE)) {
            return values().getAsLong(Constants.DB.HEALTH_DATA_VALUE_UNITS.HEALTH_DATA_TYPE);
        }
        return null;
    }

    public void setName(Double value) {
        values().put(Constants.DB.HEALTH_DATA_VALUE_UNITS.NAME, value);
    }

    public Double getName() {
        if (values().containsKey(Constants.DB.HEALTH_DATA_VALUE_UNITS.NAME)) {
            return values().getAsDouble(Constants.DB.HEALTH_DATA_VALUE_UNITS.NAME);
        }
        return null;
    }



    @Override
    protected List<String> getValidColumns() {
        List<String> columns = new ArrayList<String>();
        columns.add(Constants.DB.PRIMARY_KEY);
        columns.add(Constants.DB.HEALTH_DATA_VALUE_UNITS.GROUP);
        columns.add(Constants.DB.HEALTH_DATA_VALUE_UNITS.HEALTH_DATA_TYPE);
        columns.add(Constants.DB.HEALTH_DATA_VALUE_UNITS.NAME);
        columns.add(Constants.DB.HEALTH_DATA_VALUE_UNITS.COMMENT);
        return columns;
    }

    @Override
    protected String getTableName() {
        return Constants.DB.HEALTH_DATA_VALUE_UNITS.TABLE;
    }

    @Override
    protected String getNullColumnHack() {
        return null;
    }
}
