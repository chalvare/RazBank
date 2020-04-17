package com.razbank.razbank.responses;

import com.razbank.razbank.utils.ResponseInfo;
import lombok.Data;

@Data
public class GenericResponse {
    ResponseInfo responseInfo;
    String message;
}
