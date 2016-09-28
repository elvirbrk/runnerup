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

package org.runnerup.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.runnerup.util.Formatter;

import static java.lang.Boolean.TRUE;

@TargetApi(Build.VERSION_CODES.FROYO)
public class DecimalPicker extends LinearLayout {

    final NumberPicker wholePart;
    final TextView sepparator;
    final NumberPicker decimalPart;

    public DecimalPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        wholePart = new NumberPicker(context, attrs);
        LinearLayout unitStringLayout = new LinearLayout(context, attrs);
        unitStringLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        unitStringLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT));
        sepparator = new TextView(context, attrs);
        sepparator.setTextSize(25);

        //TODO: Use regional settings
        sepparator.setText(".");

        unitStringLayout.addView(sepparator);
        decimalPart = new NumberPicker(context, attrs);

        wholePart.setOrientation(VERTICAL);
        decimalPart.setDigits(2);
        decimalPart.setOrientation(VERTICAL);

        setOrientation(HORIZONTAL);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        addView(wholePart);
        addView(unitStringLayout);
        addView(decimalPart);

    }

    public void setRange (int min, int max, int decimals){
        wholePart.setRange(min, max, TRUE);
        decimalPart.setDigits(decimals);
        decimalPart.setRange(0, (int)Math.pow(10, decimals)-1, TRUE);
    }

    public double getValue() {
        double ret = 0;
        ret += (double)decimalPart.getValue()/10;
        ret += wholePart.getValue();
        return ret;
    }

    public void setValue(double s) {
        int h = (int)s;
        int f = (int)((s-h) * 10);
        wholePart.setValue((int) h);
        decimalPart.setValue((int) s);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        wholePart.setEnabled(enabled);
        decimalPart.setEnabled(enabled);
    }
}
