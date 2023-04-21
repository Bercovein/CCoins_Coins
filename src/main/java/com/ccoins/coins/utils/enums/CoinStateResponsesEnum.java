package com.ccoins.coins.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CoinStateResponsesEnum {

    NOT_FOUND_COINS("No se ha encontrado el registro."),
    WRONG_STATE("Se encuentra en un estado incorrecto. Realice un ajuste si es necesario."),
    ERROR_STATE("Ha ocurrido un error. Reintente nuevamente."),
    SUCCESSFULLY_DELIVERED("Se cambió el estado a Entregado correctamente. Puede entregar el premio al cliente."),
    SUCCESSFULLY_CANCELED("Se cambió el estado a Cancelado correctamente."),
    SUCCESSFULLY_ADJUSTED("El ajuste se realizó correctamente. Verifique el reporte de ser necesario.");

    final String message;
}
