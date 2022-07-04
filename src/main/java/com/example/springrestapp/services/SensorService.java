package com.example.springrestapp.services;

import com.example.springrestapp.dto.SensorDTO;
import com.example.springrestapp.models.Sensor;
import com.example.springrestapp.repositories.SensorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public SensorService(SensorRepository sensorRepository, ModelMapper modelMapper) {
        this.sensorRepository = sensorRepository;
        this.modelMapper = modelMapper;
    }

    public List<Sensor> sensorList() {
        return sensorRepository.findAll();
    }

    public Sensor findSensorById(int id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);
        return sensor.orElse(null);
    }

    public Optional<Sensor> findSensorByName(String name) {
        return sensorRepository.findSensorByName(name);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    @Transactional
    public void update(int id, Sensor sensor) {
        sensor.setId(id);
        sensorRepository.save(sensor);
    }

    @Transactional
    public void delete(int id) {
        sensorRepository.deleteById(id);
    }

    public Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    public SensorDTO convertToPersonDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
