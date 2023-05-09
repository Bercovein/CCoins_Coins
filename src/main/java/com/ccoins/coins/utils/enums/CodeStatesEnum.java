package com.ccoins.coins.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeStatesEnum {

    CLOSED("Cerrado"),
    EXPIRED("Expirado"),
    OPEN("Abierto");

    final String value;
}
