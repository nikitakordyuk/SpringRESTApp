package com.example.springrestapp.dto;

import com.example.springrestapp.models.Measurement;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class SensorDTO {
    @NotEmpty(message = "Should not be empty")
    @Size(min = 3, max = 30, message = "Name size should be between 3 and 30 characters")
    private String name;

    public SensorDTO() {
    }

    public SensorDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SensorDTO{" +
               "name='" + name + '\'' +
               '}';
    }
}
