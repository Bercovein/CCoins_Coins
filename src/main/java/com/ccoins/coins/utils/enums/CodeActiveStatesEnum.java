package com.ccoins.coins.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeActiveStatesEnum {

    ACTIVE("active"),
    INACTIVE("inactive");

    final String value;
}
