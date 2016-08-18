/*
 * Copyright (C) 2012 - 2013 jonas.oreland@gmail.com
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

package org.runnerup.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.runnerup.R;
import org.runnerup.common.util.Constants;
import org.runnerup.db.ActivityCleaner;
import org.runnerup.db.DBHelper;
import org.runnerup.db.entities.AbstractTypeEntity;
import org.runnerup.db.entities.ActivityEntity;
import org.runnerup.db.entities.HealthEntryEntity;
import org.runnerup.db.entities.HealthTypeEntity;
import org.runnerup.db.entities.HealthValueEntity;
import org.runnerup.db.entities.HealthValueTypeEntity;
import org.runnerup.util.Formatter;
import org.runnerup.util.SimpleCursorLoader;
import org.runnerup.widget.TitleSpinner;
import org.runnerup.widget.WidgetUtil;
import org.runnerup.workout.Sport;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@TargetApi(Build.VERSION_CODES.FROYO)
public class HistoryActivity extends FragmentActivity implements Constants, OnItemClickListener,
        LoaderCallbacks<Cursor> {

    final static String TAB_WORKOUT = "workout";
    final static String TAB_HEALTH = "health";
    final static String TAB_GRAPH = "graph";

    SQLiteDatabase mDB = null;
    Formatter formatter = null;

    ListView listView = null;
    ExpandableListView listHealth = null;
    LinearLayout graphLayout = null;

    CursorAdapter cursorAdapter = null;
    HistoryListHealthAdapter listAdapter = null;
    TitleSpinner graphPeriod = null;

    TabHost tabHost = null;

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        listView = (ListView) findViewById(R.id.history_list);
        // get the listview
        listHealth = (ExpandableListView) findViewById(R.id.health_list);
        graphLayout = (LinearLayout) findViewById(R.id.history_graph);


        mDB = DBHelper.getReadableDatabase(this);
        formatter = new Formatter(this);
        listView.setDividerHeight(2);
        listView.setOnItemClickListener(this);
        cursorAdapter = new HistoryListAdapter(this, null);
        listView.setAdapter(cursorAdapter);

        tabHost = (TabHost) findViewById(R.id.tabhost_history);
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(TAB_WORKOUT);
        tabSpec.setIndicator(WidgetUtil.createHoloTabIndicator(this, getString(R.string.workout)));
        tabSpec.setContent(R.id.tab_workout);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(TAB_HEALTH);
        tabSpec.setIndicator(WidgetUtil.createHoloTabIndicator(this, getString(R.string.health)));
        tabSpec.setContent(R.id.tab_health);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(TAB_GRAPH);
        tabSpec.setIndicator(WidgetUtil.createHoloTabIndicator(this, getString(R.string.graph)));
        tabSpec.setContent(R.id.tab_graph);
        tabHost.addTab(tabSpec);

        tabHost.setOnTabChangedListener(onTabChangeListener);


        graphPeriod = (TitleSpinner) findViewById(R.id.graph_period);
        graphPeriod.setOnSelectedListener(onSetGraphPeriod);

        this.getSupportLoaderManager().initLoader(0, null, this);

        new ActivityCleaner().conditionalRecompute(mDB);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DBHelper.closeDB(mDB);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        String[] from = new String[] {
                "_id", DB.ACTIVITY.START_TIME,
                DB.ACTIVITY.DISTANCE, DB.ACTIVITY.TIME, DB.ACTIVITY.SPORT
        };

        return new SimpleCursorLoader(this, mDB, DB.ACTIVITY.TABLE, from, "deleted == 0", null,
                DB.ACTIVITY.START_TIME + " desc");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        cursorAdapter.swapCursor(arg1);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        cursorAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("ID", id);
        intent.putExtra("mode", "details");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        this.getSupportLoaderManager().restartLoader(0, null, this);
    }

    final TabHost.OnTabChangeListener onTabChangeListener = new TabHost.OnTabChangeListener() {

        @Override
        public void onTabChanged(String tabId) {
            if (tabId.contentEquals(TAB_WORKOUT))
                return;

            else if (tabId.contentEquals(TAB_HEALTH)) {
                // setting list adapter
                listHealth.setAdapter(prepareHealthData());
            } else if (tabId.contentEquals(TAB_GRAPH)) {
                loadGraph(getPeriodStart());
            }

            //updateView();
        }
    };

    private Date getPeriodStart(){
        String per = graphPeriod.getValue().toString();
        Calendar c = Calendar.getInstance();
        switch (per) {
            case "Last week":
                c.add(Calendar.DATE, -7);
                break;
            case "Last month":
                c.add(Calendar.MONTH, -1);
                break;
            case "Last 6 months":
                c.add(Calendar.MONTH, -6);
                break;
            case "Last year":
                c.add(Calendar.YEAR, -1);
                break;
            case "All":
                return null;
        }

        return c.getTime();
    };

    private void loadGraph(Date startDate) {

        graphLayout.removeViewsInLayout(0,graphLayout.getChildCount()-1);
        List<AbstractTypeEntity> hte = HealthTypeEntity.getAll(mDB);

        for (AbstractTypeEntity ate: hte
             ) {

            //LayoutInflater infalInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LayoutInflater infalInflater = LayoutInflater.from(this);
            GraphView gv = (GraphView) infalInflater.inflate(R.layout.history_graph_row, graphLayout, false);

//            final java.text.DateFormat dateTimeFormatter = android.text.format.DateFormat.getDateFormat(this);
//            CustomLabelFormatter clf = new CustomLabelFormatter() {
//                @Override
//                public String formatLabel(double v, boolean b) {
//                    if (b) {
//                        // transform number to time
//                        return dateTimeFormatter.format(new Date((long) v*1000));
//                    } else {
//                        return String.valueOf(v);
//                    }
//                }
//            };
//            gv.setCustomLabelFormatter(clf);



            gv.setTitle(ate.getName());

            List<HealthValueTypeEntity> hvtList = HealthValueTypeEntity.getAll(mDB, ate.getId().intValue());

            HashMap<Long, List<DataPoint>> graphData = new HashMap<>();
            for (HealthValueTypeEntity hv:hvtList
                    ) {
                graphData.put(hv.getId(), new ArrayList<DataPoint>());
            }

            List<HealthEntryEntity> heList = HealthEntryEntity.getAll(mDB, ate.getId(), startDate, new Date());

            for (HealthEntryEntity he: heList
                    ) {

                for (HealthValueEntity hv:he.getValues()
                        ) {
                    DataPoint gvd = new DataPoint(he.getTime(), hv.getValue());

                    graphData.get(hv.getValueTypeId()).add(gvd);
                }



            }

            for (HealthValueTypeEntity hv:hvtList
                    ) {
                List<DataPoint> gvd = graphData.get(hv.getId());
                LineGraphSeries series = new LineGraphSeries(gvd.toArray(new DataPoint[gvd.size()]));
                series.setColor(Color.GREEN);
                series.setDrawDataPoints(true);
                series.setDataPointsRadius(10);
                series.setThickness(8);

                gv.addSeries(series);

                // set date label formatter
                gv.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
                gv.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

                // set manual x bounds to have nice steps
                if (startDate != null) {
                    gv.getViewport().setMinX(startDate.getTime());
                    gv.getViewport().setMaxX((new Date()).getTime());
                    gv.getViewport().setXAxisBoundsManual(true);
                }

                gv.getViewport().setMinY(0);
                gv.getViewport().setMaxY(gv.getViewport().getMaxY(true));
                gv.getViewport().setYAxisBoundsManual(true);


                // as we use dates as labels, the human rounding to nice readable numbers
                // is not nessecary
                gv.getGridLabelRenderer().setHumanRounding(false);

            }


            //gv.getCustomLabelFormatter().formatLabel()

            graphLayout.addView(gv, graphLayout.getChildCount()-1);

        }

    }

    final TitleSpinner.OnSelectedListener onSetGraphPeriod = new TitleSpinner.OnSelectedListener() {

        @Override
        public void onSelected(Spinner spiner, int newValue) throws IllegalArgumentException {
            String pos = graphPeriod.getValue().toString();

            //loadHealthValueTypes(pos);


            loadGraph(getPeriodStart());

        }

    };

    private HistoryListHealthAdapter prepareHealthData() {

        listAdapter = new HistoryListHealthAdapter(this, getHealthData());

        return listAdapter;
    }

    private List<HealthEntryEntity> getHealthData() {
        return HealthEntryEntity.getAll(mDB);
    }


    class HistoryListAdapter extends CursorAdapter {
        final LayoutInflater inflater;

        public HistoryListAdapter(Context context, Cursor c) {
            super(context, c, true);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ActivityEntity ae = new ActivityEntity(cursor);
            int[] to = new int[] {
                    R.id.history_list_id,
                    R.id.history_list_start_time, R.id.history_list_distance,
                    R.id.history_list_time, R.id.history_list_pace, R.id.history_list_sport
            };

            Long id = ae.getId();
            Long st = ae.getStartTime();
            Float d = ae.getDistance();
            Long t = ae.getTime();
            Integer s = ae.getSport();

            {
                TextView tv = (TextView) view.findViewById(to[0]);
                tv.setText(Long.toString(id));
            }

            {
                TextView tv = (TextView) view.findViewById(to[1]);
                if (st != null) {
                    tv.setText(formatter.formatDateTime(st));
                } else {
                    tv.setText("");
                }
            }

            {
                TextView tv = (TextView) view.findViewById(to[2]);
                if (d != null) {
                    tv.setText(formatter.formatDistance(Formatter.Format.TXT_SHORT, d.longValue()));
                } else {
                    tv.setText("");
                }
            }

            {
                TextView tv = (TextView) view.findViewById(to[3]);
                if (t != null) {
                    tv.setText(formatter.formatElapsedTime(Formatter.Format.TXT_SHORT, t));
                } else {
                    tv.setText("");
                }
            }

            {
                TextView tv = (TextView) view.findViewById(to[4]);
                if (d != null && t != null && d != 0 && t != 0) {
                    tv.setText(formatter.formatPace(Formatter.Format.TXT_LONG, t / d));
                } else {
                    tv.setText("");
                }
            }

            {
                TextView tv = (TextView) view.findViewById(to[5]);

                if (s != null) {
                    tv.setText(Sport.textOf(getResources(), s));
                } else {
                    tv.setText(Sport.textOf(getResources(), DB.ACTIVITY.SPORT_RUNNING));
                }
            }
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return inflater.inflate(R.layout.history_row, parent, false);
        }

    }

    class HistoryListHealthAdapter extends BaseExpandableListAdapter {
        final LayoutInflater inflater;
        private Context _context;
        private List<HealthEntryEntity> _listEntry; // header titles
        // child data in format of header title, child title
        //private HashMap<String, List<String>> _listDataChild;

        public HistoryListHealthAdapter(Context context, List<HealthEntryEntity> listDataHeader) {
            super();
            inflater = LayoutInflater.from(context);
            this._context = context;
            this._listEntry = listDataHeader;
        }


        @Override
        public HealthValueEntity getChild(int groupPosition, int childPosititon) {
            return this._listEntry.get(groupPosition).getValues().get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final HealthValueEntity child = (HealthValueEntity) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.history_health_subrow, null);
            }

            TextView txtType = (TextView) convertView
                    .findViewById(R.id.health_history_list_value_type);
            txtType.setText(child.getValueType().getName());

            TextView txtValue = (TextView) convertView
                    .findViewById(R.id.health_history_list_value);
            txtValue.setText(child.getValue().toString());

            TextView txtUnit = (TextView) convertView
                    .findViewById(R.id.health_history_list_unit);
            txtUnit.setText(child.getUnit().getName());


            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listEntry.get(groupPosition).getValues().size();
        }

        @Override
        public HealthEntryEntity getGroup(int groupPosition) {
            return this._listEntry.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listEntry.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            HealthEntryEntity header = (HealthEntryEntity) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.history_health_row, null);
            }

            TextView lblTime = (TextView) convertView
                    .findViewById(R.id.health_history_list_time);
            lblTime.setTypeface(null, Typeface.BOLD);
            String s = DateFormat.getDateTimeInstance().format(header.getTime());
            lblTime.setText(s);

            TextView lblType = (TextView) convertView
                    .findViewById(R.id.health_history_list_type);
            lblType.setTypeface(null, Typeface.BOLD);
            lblType.setText(header.getHealthType().getName());

            listHealth.expandGroup(groupPosition);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

}
