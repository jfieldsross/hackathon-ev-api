package com.harrydulaney.firebaseauthstarter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseModelDto implements Serializable {

    private BigDecimal code;
    private String description;

    public BaseModelDto() {
    }

    public BaseModelDto(BigDecimal code, String description) {
        this.code = code;
        this.description = description;
    }

    public BigDecimal getCode() {
        return code;
    }

    public void setCode(BigDecimal code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
