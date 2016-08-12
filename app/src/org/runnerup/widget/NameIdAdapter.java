package org.runnerup.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.runnerup.db.entities.AbstractTypeEntity;

/**
 * Created by elvir.brkic on 12.08.2016..
 */
public class NameIdAdapter extends ArrayAdapter<AbstractTypeEntity> {

        // Your sent context
        private Context context;
        // Your custom values for the spinner (User)
        private AbstractTypeEntity[] values;

        public NameIdAdapter(Context context, int textViewResourceId,
                             AbstractTypeEntity[] values) {
            super(context, textViewResourceId, values);
            this.context = context;
            this.values = values;
        }

        public int getCount(){
            return values.length;
        }

        public AbstractTypeEntity getItem(int position){
            return values[position];
        }

        public long getItemId(int position){
            return position;
        }


        // And the "magic" goes here
        // This is for the "passive" state of the spinner
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
            //TextView label = new TextView(context);

            //label.setTextColor(Color.BLACK);
            // Then you can get the current item using the values array (Users array) and the current position
            // You can NOW reference each method you has created in your bean object (User class)
            //label.setText(values[position].getName());

            // And finally return your dynamic (or custom) view for each spinner item
            //return label;

            /*View v = super.getView(position, convertView, parent);
            // apply the style and sizes etc to the Text view from this view v
            // like ((TextView)v).setTextSize(...) etc
            ((TextView)v).setText(values[position].getName());

            return v;*/

            if (convertView == null) {
                LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflator.inflate(android.R.layout.simple_spinner_dropdown_item, parent,
                        false);
            }
            TextView ret = (TextView) convertView.findViewById(android.R.id.text1);
            ret.setText(values[position].getName());

            return convertView;

        }

        // And here is when the "chooser" is popped up
        // Normally is the same view, but you can customize it if you want
        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
          /*  View v = super.getView(position, convertView, parent);
            // apply the style and sizes etc to the Text view from this view v
            // like ((TextView)v).setTextSize(...) etc
            ((TextView)v).setText(values[position].getName());

            return v; */

            if (convertView == null) {
                LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflator.inflate(android.R.layout.simple_spinner_dropdown_item, parent,
                        false);
            }
            TextView ret = (TextView) convertView.findViewById(android.R.id.text1);
            ret.setText(values[position].getName());

            return convertView;

        }

}
