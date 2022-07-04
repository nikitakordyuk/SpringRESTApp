package com.example.springrestapp.util.measurement;

import com.example.springrestapp.models.Measurement;
import com.example.springrestapp.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        if (measurement.getSensor() == null) return;

        if (sensorService.findSensorByName(measurement.getSensor().getName()).isEmpty())
            errors.rejectValue("sensor", "No sensor with this name");
    }
}
