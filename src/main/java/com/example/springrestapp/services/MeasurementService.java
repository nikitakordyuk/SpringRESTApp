package com.example.springrestapp.services;

import com.example.springrestapp.dto.MeasurementDTO;
import com.example.springrestapp.models.Measurement;
import com.example.springrestapp.repositories.MeasurementRepository;
import com.example.springrestapp.repositories.SensorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    private final SensorRepository sensorRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository, ModelMapper modelMapper) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
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
    public boolean save(MeasurementDTO measurementDTO) {
        Measurement measurement = convertToMeasurement(measurementDTO);

        if (sensorRepository.findSensorByName(measurement.getSensorName()).isPresent()) {
            measurementRepository.save(measurement);

            return true;
        }

        return false;
    }

    @Transactional
    public void update(int id, Measurement measurement) {
        measurement.setId(id);
        measurementRepository.save(measurement);
    }

    public int rainyDaysCount() {
        return (int) measurementRepository.findAll()
                .stream()
                .filter(Measurement::isRaining)
                .count();
    }

    @Transactional
    public void delete(int id) {
        measurementRepository.deleteById(id);
    }

    public MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    public Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }
}
