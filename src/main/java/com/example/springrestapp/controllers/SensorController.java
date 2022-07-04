package com.example.springrestapp.controllers;

import com.example.springrestapp.dto.SensorDTO;
import com.example.springrestapp.services.SensorService;
import com.example.springrestapp.util.ErrorResponse;
import com.example.springrestapp.util.ErrorsHandler;
import com.example.springrestapp.util.sensor.SensorNotCreatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        if (sensorService.findSensorByName(sensorDTO.getName()).isPresent())
            bindingResult.addError(new ObjectError(
                    "Sensor", "Sensor with this name already exists"));

        if (bindingResult.hasErrors())
            throw new SensorNotCreatedException(ErrorsHandler.getErrors(bindingResult));

        sensorService.save(sensorService.convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorNotCreatedException exception) {
        ErrorResponse sensorErrorResponse = new ErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
