package com.example.springrestapp.dto;

import com.example.springrestapp.models.Sensor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class MeasurementDTO {
    @Min(value = -100, message = "Value should be bigger than -100")
    @Max(value = 100, message = "Value should be less than 100")
    private float value;

    private boolean raining;

    private Sensor sensor;

    public MeasurementDTO() {
    }

    public MeasurementDTO(float value, boolean raining, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
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

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
               "value=" + value +
               ", raining=" + raining +
               ", sensor=" + sensor +
               '}';
    }
}