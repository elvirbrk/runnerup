package org.runnerup.util;

import org.runnerup.db.entities.HealthValueTypeEntity;
import org.runnerup.db.entities.UnitEntity;
import org.runnerup.widget.TitleSpinner;

/**
 * Created by elvir.brkic on 16.08.2016..
 */
public class HealthValueHelper {
    HealthValueTypeEntity hvt;
    TitleSpinner val;
    UnitEntity unit;

    public HealthValueHelper(HealthValueTypeEntity hvt, TitleSpinner value, UnitEntity unit){
        this.hvt = hvt;
        this.val = value;
        this.unit = unit;
    }

    public void setHVT(HealthValueTypeEntity h){
        hvt = h;
    }

    public HealthValueTypeEntity getHVT(){
        return hvt;
    }

    public void setValue(TitleSpinner v) {
        val = v;
    }

    public TitleSpinner getValue(){
        return val;
    }

    public void setUnit(UnitEntity u) {
        unit = u;
    }

    public UnitEntity getUnit (){
        return unit;
    }
}
