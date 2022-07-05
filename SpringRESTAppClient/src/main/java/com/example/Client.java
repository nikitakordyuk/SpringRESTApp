package com.example;

import com.example.dto.SensorDTO;
import com.example.springrestapp.dto.MeasurementDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class Client {
    private static final String SENSOR_REGISTER_URL = "http://localhost:8080/sensors/registration";
    private static final String MEASUREMENTS_ADD_URL = "http://localhost:8080/measurements/add";

    public static void main(String[] args) {
        Map<String, Object> jsonData = new HashMap<>();

        List<String> sensors = new ArrayList<>();

        for (int i = 0; i < 13; i++) {
            sensors.add("Sensor " + i);
        }

        Random random = new Random();

        double minTemperature = 0.0;
        double maxTemperature = 45.0;


        for (int i = 0; i < 1000; i++) {
            sendMeasurement((
                    Math.random() * (maxTemperature - minTemperature) + minTemperature),
                    random.nextBoolean(),
                    sensors.get(random.nextInt(sensors.size())));
        }
    }

    public static void sendPost(String url, Map<String, Object> jsonData) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);

        restTemplate.postForObject(url, request, String.class);

        try {
            restTemplate.postForObject(url, request, String.class);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    private static void sendMeasurement(double temperature, boolean isRaining, String sensorName) {
        Map<String, Object> jsonData = new HashMap<>();

        jsonData.put("value", temperature);
        jsonData.put("raining", isRaining);
        jsonData.put("sensor", Map.of("name", sensorName));

        sendPost(MEASUREMENTS_ADD_URL, jsonData);
    }
}
