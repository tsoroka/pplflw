package com.example.pplflwapi.controller.model;

import io.swagger.annotations.ApiModel;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@ApiModel("Error response")
public class ErrorResponse {

    private String message;

    private LocalDateTime timestamp;
}
