package cz.uhk.chemdb.model.chemdb.table;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "meltingPoint")
public class MeltingPoint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float temperatureFrom;
    private float temperatureTo;
    private boolean oil;
    @OneToOne
    private Compound compound;

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
}
