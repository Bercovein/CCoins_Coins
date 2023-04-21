package com.ccoins.coins.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CoinsReportEnum {

    ACQUIRED("acquired"),
    EXPENDED("expended");

    final String value;
}
