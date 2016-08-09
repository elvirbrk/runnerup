
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
    import android.os.Build;
    import android.util.Log;

    import org.runnerup.common.util.Constants;

    import java.util.ArrayList;
    import java.util.List;

    /**
     * Content values wrapper for the {@code location} table.
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public class HealthDataValueEntity extends AbstractEntity {

        public HealthDataValueEntity() {
            super();
        }

        public HealthDataValueEntity(Cursor c) {
            super();
            try {
                toContentValues(c);
            } catch (Exception e) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }


        public void setHealthData(Long value) {
            values().put(Constants.DB.HEALTH_DATA_VALUES.HEALTH_DATA, value);
        }

        public Long getHealthData() {
            if (values().containsKey(Constants.DB.HEALTH_DATA_VALUES.HEALTH_DATA)) {
                return values().getAsLong(Constants.DB.HEALTH_DATA_VALUES.HEALTH_DATA);
            }
            return null;
        }

        public void setType(Long value) {
            values().put(Constants.DB.HEALTH_DATA_VALUES.TYPE, value);
        }

        public Long getType() {
            if (values().containsKey(Constants.DB.HEALTH_DATA_VALUES.TYPE)) {
                return values().getAsLong(Constants.DB.HEALTH_DATA_VALUES.TYPE);
            }
            return null;
        }

        public void setValue(Double value) {
            values().put(Constants.DB.HEALTH_DATA_VALUES.VALUE, value);
        }

        public Double getValue() {
            if (values().containsKey(Constants.DB.HEALTH_DATA_VALUES.VALUE)) {
                return values().getAsDouble(Constants.DB.HEALTH_DATA_VALUES.VALUE);
            }
            return null;
        }

        public void setUnit(Double value) {
            values().put(Constants.DB.HEALTH_DATA_VALUES.UNIT, value);
        }

        public Double getUnit() {
            if (values().containsKey(Constants.DB.HEALTH_DATA_VALUES.UNIT)) {
                return values().getAsDouble(Constants.DB.HEALTH_DATA_VALUES.UNIT);
            }
            return null;
        }



        @Override
        protected List<String> getValidColumns() {
            List<String> columns = new ArrayList<String>();
            columns.add(Constants.DB.PRIMARY_KEY);
            columns.add(Constants.DB.HEALTH_DATA_VALUES.HEALTH_DATA);
            columns.add(Constants.DB.HEALTH_DATA_VALUES.TYPE);
            columns.add(Constants.DB.HEALTH_DATA_VALUES.VALUE);
            columns.add(Constants.DB.HEALTH_DATA_VALUES.UNIT);
            columns.add(Constants.DB.HEALTH_DATA_VALUES.COMMENT);
            return columns;
        }

        @Override
        protected String getTableName() {
            return Constants.DB.HEALTH_DATA_VALUES.TABLE;
        }

        @Override
        protected String getNullColumnHack() {
            return null;
        }
    }
