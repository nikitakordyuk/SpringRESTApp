package com.example.springrestapp.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Measurement")
public class Measurement {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    @Min(value = -100, message = "Value should be bigger than -100")
    @Max(value = 100, message = "Value should be less than 100")
    private float value;

    @Column(name = "raining")
    private boolean raining;

    @NotEmpty
    @Column(name = "sensor_name")
    private String sensorName;

    @ManyToOne
    @JoinColumn(name = "sensor_name", referencedColumnName = "name", insertable = false, updatable = false)
    private Sensor sensor;

    public Measurement() {
    }

    public Measurement(float value, boolean raining, String sensorName) {
        this.value = value;
        this.raining = raining;
        this.sensorName = sensorName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "Measurement{" +
               "value=" + value +
               ", raining=" + raining +
               ", sensorName='" + sensorName + '\'' +
               ", sensor=" + sensor +
               '}';
    }
}
