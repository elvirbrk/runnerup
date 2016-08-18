
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
    public class HealthValueEntity extends AbstractEntity {

        public HealthValueEntity() {
            super();
        }

        public HealthValueEntity(Cursor c) {
            super();
            try {
                toContentValues(c);
            } catch (Exception e) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }


        public void setHealthEntryId(Long value) {
            values().put(Constants.DB.HEALTH_VALUES.HEALTH_ENTRY, value);
        }

        public Long getHealthEntryId() {
            if (values().containsKey(Constants.DB.HEALTH_VALUES.HEALTH_ENTRY)) {
                return values().getAsLong(Constants.DB.HEALTH_VALUES.HEALTH_ENTRY);
            }
            return null;
        }

        public void setValueTypeId(Long value) {
            values().put(Constants.DB.HEALTH_VALUES.HEALTH_VALUE_TYPE, value);
        }

        public Long getValueTypeId() {
            if (values().containsKey(Constants.DB.HEALTH_VALUES.HEALTH_VALUE_TYPE)) {
                return values().getAsLong(Constants.DB.HEALTH_VALUES.HEALTH_VALUE_TYPE);
            }
            return null;
        }

        public HealthValueTypeEntity getValueType() {
            HealthValueTypeEntity ht = new HealthValueTypeEntity();
            ht.readByPrimaryKey(db, getValueTypeId());
            return ht;
        }

        public void setValue(Double value) {
            values().put(Constants.DB.HEALTH_VALUES.VALUE, value);
        }

        public Double getValue() {
            if (values().containsKey(Constants.DB.HEALTH_VALUES.VALUE)) {
                return values().getAsDouble(Constants.DB.HEALTH_VALUES.VALUE);
            }
            return null;
        }

        public void setUnitId(Long value) {
            values().put(Constants.DB.HEALTH_VALUES.UNIT, value);
        }

        public Long getUnitId() {
            if (values().containsKey(Constants.DB.HEALTH_VALUES.UNIT)) {
                return values().getAsLong(Constants.DB.HEALTH_VALUES.UNIT);
            }
            return null;
        }

        public UnitEntity getUnit() {
            UnitEntity ht = new UnitEntity();
            ht.readByPrimaryKey(db, getUnitId());
            return ht;
        }



        @Override
        protected List<String> getValidColumns() {
            List<String> columns = new ArrayList<String>();
            columns.add(Constants.DB.PRIMARY_KEY);
            columns.add(Constants.DB.HEALTH_VALUES.HEALTH_ENTRY);
            columns.add(Constants.DB.HEALTH_VALUES.HEALTH_VALUE_TYPE);
            columns.add(Constants.DB.HEALTH_VALUES.VALUE);
            columns.add(Constants.DB.HEALTH_VALUES.UNIT);
            return columns;
        }

        @Override
        protected String getTableName() {
            return Constants.DB.HEALTH_VALUES.TABLE;
        }

        @Override
        protected String getNullColumnHack() {
            return null;
        }

        public static List<HealthValueEntity> getAll(SQLiteDatabase db, int healthEntryId){
            List<HealthValueEntity> list = new ArrayList<HealthValueEntity>();
            for (AbstractEntity a : getAll(db, new HealthValueEntity())) {
                if (((HealthValueEntity)a).getHealthEntryId() == healthEntryId)
                {
                    list.add((HealthValueEntity)a);
                }

            }

            return list;
        }

    }
