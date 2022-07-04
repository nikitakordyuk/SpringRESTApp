package com.example.springrestapp.services;

import com.example.springrestapp.dto.MeasurementDTO;
import com.example.springrestapp.models.Measurement;
import com.example.springrestapp.repositories.MeasurementRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    private final SensorService sensorService;

    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService, ModelMapper modelMapper) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    public List<Measurement> measurementList() {
        return measurementRepository.findAll();
    }

    public Measurement findMeasurementById(int id) {
        Optional<Measurement> measurement = measurementRepository.findById(id);
        return measurement.orElse(null);
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurement(measurement);

        measurementRepository.save(measurement);
    }

    @Transactional
    public void update(int id, Measurement measurement) {
        measurement.setId(id);
        measurementRepository.save(measurement);
    }

    public Long rainyDaysCount() {
        return measurementRepository.findAll()
                .stream()
                .filter(Measurement::getRaining)
                .count();
    }

    @Transactional
    public void delete(int id) {
        measurementRepository.deleteById(id);
    }

    public void enrichMeasurement(Measurement measurement) {
        if (sensorService.findSensorByName(measurement.getSensor().getName()).isPresent())
            measurement.setSensor(sensorService.findSensorByName(measurement.getSensor().getName()).get());

        measurement.setMeasurementDateTime(LocalDateTime.now());
    }

    public MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    public Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }
}
