package com.exemplo.prototipo_PI.prototipo_PI.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreatePreferenceRequest {
    private List<ItemDTO> items;
    private String successUrl;
    private String failureUrl;
}