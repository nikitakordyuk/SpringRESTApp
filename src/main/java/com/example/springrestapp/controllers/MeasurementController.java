package com.example.springrestapp.controllers;

import com.example.springrestapp.dto.MeasurementDTO;
import com.example.springrestapp.services.MeasurementService;
import com.example.springrestapp.util.ErrorResponse;
import com.example.springrestapp.util.ErrorsHandler;
import com.example.springrestapp.util.measurement.MeasurementNotCreatedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;

    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<MeasurementDTO> getMeasurements() {
        return measurementService.measurementList()
                .stream()
                .map(measurementService::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public String rainyDaysCount() {
        String wasWere = measurementService.rainyDaysCount() > 1 ? "were " : "was ";
        String dayDays = measurementService.rainyDaysCount() > 1 ? "days " : "day ";

        return "There " + wasWere + measurementService.rainyDaysCount() + " rainy " + dayDays;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new MeasurementNotCreatedException(ErrorsHandler.getErrors(bindingResult));

        if (!measurementService.save(measurementDTO)) {
            throw new MeasurementNotCreatedException("Sensor with name does not exist");
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MeasurementNotCreatedException exception) {
        ErrorResponse measurementErrorResponse = new ErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(measurementErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
