package com.exemplo.prototipo_PI.prototipo_PI.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CreatePreferenceResponse {
    private String initPoint;

    public CreatePreferenceResponse(String initPoint) {
        this.initPoint = initPoint;
    }

    public String getInitPoint() {
        return initPoint;
    }
}