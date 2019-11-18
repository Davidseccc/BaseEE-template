package cz.uhk.chemdb.model.chemdb.table;


import cz.uhk.chemdb.utils.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "meltingPoint")
public class MeltingPoint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Float temperatureFrom;
    private Float temperatureTo;
    private boolean oil;
    @OneToOne
    private Compound compound;

    public MeltingPoint() {

    }

    public MeltingPoint(String meltingPoint, Compound compound) {
        if (meltingPoint.equalsIgnoreCase("oil")) {
            oil = true;
        } else if (meltingPoint.startsWith(">")) {
            this.temperatureFrom = Float.parseFloat(meltingPoint.substring(meltingPoint.indexOf(">") + 1));
        } else if (meltingPoint.startsWith("<")) {
            this.temperatureTo = Float.parseFloat(meltingPoint.substring(meltingPoint.indexOf("<") + 1));
        } else if (meltingPoint.matches("\\d{1,4}(?>\\.|\\,)?\\d?(?>-{1}\\d{1,4}(?>\\.|\\,)?\\d?)?")) {
            this.temperatureFrom = Float.parseFloat(meltingPoint.substring(0, meltingPoint.indexOf("-") - 1));
            this.temperatureTo = Float.parseFloat(meltingPoint
                    .substring(meltingPoint.indexOf("-") + 1).replaceAll(",", "."));
        }
        this.compound = compound;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getTemperatureFrom() {
        return temperatureFrom;
    }

    public void setTemperatureFrom(float temperatureFrom) {
        this.temperatureFrom = temperatureFrom;
    }

    public float getTemperatureTo() {
        return temperatureTo;
    }

    public void setTemperatureTo(float temperatureTo) {
        this.temperatureTo = temperatureTo;
    }

    public boolean isOil() {
        return oil;
    }

    public void setOil(boolean oil) {
        this.oil = oil;
    }

    public Compound getCompound() {
        return compound;
    }

    public void setCompound(Compound compound) {
        this.compound = compound;
    }

    public boolean contains(String searchString) {
        Float f = Float.NaN;
        if (StringUtils.isNumeric(searchString)) {
            f = StringUtils.getNumber(searchString, Float.class).floatValue();
        }
        if (temperatureFrom != null && temperatureFrom == f) ;
        else if (temperatureFrom != null && temperatureFrom <= f) return true;
        else if (temperatureTo != null && temperatureTo >= f) return true;
        else if (searchString == "oil" && isOil()) return true;
        else return (temperatureFrom != null && temperatureTo != null) && (f >= temperatureFrom && f <= temperatureTo);
        return false;
    }
}
