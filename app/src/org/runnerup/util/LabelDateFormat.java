package org.runnerup.util;

/**
 * Created by elvir.brkic on 10.01.2017..
 */

public class LabelDateFormat {
    private String dateFormat;
    private int numOfLabels;

    public LabelDateFormat(String format, int numOfLabels)
    {
        this.dateFormat = format;
        this.numOfLabels = numOfLabels;
    }

    public String getDateFormat(){
        return dateFormat;
    }

    public int getNumOfLabels(){
        return numOfLabels;
    }
}
