package com.exemplo.prototipo_PI.prototipo_PI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePreferenceRequest {
    private List<ItemDTO> items;
    private Map<String, String> backUrls;
}