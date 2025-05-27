package com.exemplo.prototipo_PI.prototipo_PI.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTO {
    private String title;
    private int quantity;
    private double unit_price;

}