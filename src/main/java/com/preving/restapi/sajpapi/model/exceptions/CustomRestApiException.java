package com.preving.restapi.sajpapi.model.exceptions;


import com.preving.restapi.sajpapi.model.exceptions.errors.RestApiError;
import com.preving.restapi.sajpapi.model.exceptions.errors.RestApiErrorCode;
import com.preving.restapi.sajpapi.model.exceptions.errors.RestApiErrorDetail;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CustomRestApiException extends RuntimeException {

    protected RestApiError error;

    public RestApiError getError(){
        return this.error;
    }

    public CustomRestApiException(RestApiErrorCode code) {
        super(code.getMessage());
        error = new RestApiError(code);
    }

    public CustomRestApiException(HttpStatus status, RestApiErrorCode code) {
        super(code.getMessage());
        error = new RestApiError(status, code);
    }

    public CustomRestApiException(RestApiErrorCode code, HttpStatus status) {
        super(code.getMessage());
        error = new RestApiError(code);
    }

    public CustomRestApiException(RestApiErrorCode code, String customMessage) {
        super(code.getMessage());
        error = new RestApiError(code, customMessage);
    }

    public CustomRestApiException(HttpStatus status, RestApiErrorCode code, String customMessage) {
        super(code.getMessage());
        error = new RestApiError(status, code, customMessage);
    }

    public CustomRestApiException(RestApiErrorCode code,  List<RestApiErrorDetail> errorsDetail) {
        super(code.getMessage());
        error = new RestApiError(code, errorsDetail);
    }

    public CustomRestApiException(HttpStatus status, RestApiErrorCode code, List<RestApiErrorDetail> errorsDetail) {
        super(code.getMessage());
        error = new RestApiError(status, code, errorsDetail);
    }

    public CustomRestApiException(RestApiErrorCode code, String customMessage, List<RestApiErrorDetail> errorsDetail) {
        super(code.getMessage());
        error = new RestApiError(code, customMessage, errorsDetail);
    }

    public CustomRestApiException(HttpStatus status, RestApiErrorCode code, String customMessage, List<RestApiErrorDetail> errorsDetail) {
        super(code.getMessage());
        error = new RestApiError(status, code, customMessage, errorsDetail);
    }

}
