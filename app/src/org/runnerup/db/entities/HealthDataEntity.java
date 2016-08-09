
/**
 * Created by elvir.brkic on 09.08.2016..
 */
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
    public class HealthDataEntity extends AbstractEntity {

        private List<HealthDataValueEntity> values;

        public HealthDataEntity() {
            super();
            values = new ArrayList<HealthDataValueEntity>();
        }

        public HealthDataEntity(Cursor c) {
            super();
            try {
                toContentValues(c);
            } catch (Exception e) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }


        public void setTime(Integer value) {
            values().put(Constants.DB.HEALTH_DATA.TIME, value);
        }

        public Integer getTime() {
            if (values().containsKey(Constants.DB.HEALTH_DATA.TIME)) {
                return values().getAsInteger(Constants.DB.HEALTH_DATA.TIME);
            }
            return null;
        }

        public void setType(Long value) {
            values().put(Constants.DB.HEALTH_DATA.TYPE, value);
        }

        public Long getType() {
            if (values().containsKey(Constants.DB.HEALTH_DATA.TYPE)) {
                return values().getAsLong(Constants.DB.HEALTH_DATA.TYPE);
            }
            return null;
        }

        public void setComment(Double value) {
            values().put(Constants.DB.HEALTH_DATA.COMMENT, value);
        }

        public Double getComment() {
            if (values().containsKey(Constants.DB.HEALTH_DATA.COMMENT)) {
                return values().getAsDouble(Constants.DB.HEALTH_DATA.COMMENT);
            }
            return null;
        }


        @Override
        protected List<String> getValidColumns() {
            List<String> columns = new ArrayList<String>();
            columns.add(Constants.DB.PRIMARY_KEY);
            columns.add(Constants.DB.HEALTH_DATA.TIME);
            columns.add(Constants.DB.HEALTH_DATA.TYPE);
            columns.add(Constants.DB.HEALTH_DATA.COMMENT);
            return columns;
        }

        @Override
        protected String getTableName() {
            return Constants.DB.HEALTH_DATA.TABLE;
        }

        @Override
        protected String getNullColumnHack() {
            return null;
        }

        public void addValue(HealthDataValueEntity val) {
            if (val.getHealthData() != null && (this.getId() == null || !val.getHealthData().equals(this.getId()))) {
                throw new IllegalArgumentException("Foreign key of lap (" + val.getHealthData() +
                        ") doesn't match the activity primary key (" + this.getId() + ")");
            }

            if (val.getHealthData() == null && this.getId() != null) {
                val.setHealthData(this.getId());
            }

            getValues().add(val);
        }

        public void addValues(List<HealthDataValueEntity> vals) {
            for (HealthDataValueEntity val : vals) {
                this.addValue(val);
            }
        }

        public void putLaps(List<HealthDataValueEntity> vals) {
            this.getValues().clear();
            this.addValues(vals);
        }

        public List<HealthDataValueEntity> getValues() {
            return values;
        }
    }

