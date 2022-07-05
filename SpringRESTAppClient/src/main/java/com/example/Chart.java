package com.example;

import com.example.springrestapp.dto.MeasurementDTO;
import com.example.springrestapp.dto.MeasurementResponse;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Chart {
    private static final String URL = "http://localhost:8080/measurements";

    public static void main(String[] args) {
        List<Double> temperatures = getTemperatures();

        double[] xData = IntStream.range(0, temperatures.size()).asDoubleStream().toArray();

        double[] yData = temperatures
                .stream()
                .mapToDouble(x -> x)
                .toArray();

        XYChart xyChart = QuickChart
                .getChart("Temperatures", "X", "Y", "y(x)", xData, yData);

        new SwingWrapper<>(xyChart).displayChart();
    }

    private static List<Double> getTemperatures() {
        final RestTemplate restTemplate = new RestTemplate();

        MeasurementResponse measurementResponse = restTemplate.getForObject(URL, MeasurementResponse.class);

        if (measurementResponse == null || measurementResponse.getMeasurementDTOList().isEmpty())
            return Collections.emptyList();

        return measurementResponse.getMeasurementDTOList()
                .stream()
                .map(MeasurementDTO::getValue)
                .collect(Collectors.toList());
    }
}
