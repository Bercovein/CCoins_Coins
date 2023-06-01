package com.ccoins.coins.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedeemCodeResponsesEnum {

    NOT_FOUND_OR_ACTIVE("1","El código ingresado no es válido."),
    EXPIRED("2","El código ingresado está expirado."),
    ALREADY_CLAIMED("3","El código ingresado ya fué reclamado."),
    ALREADY_CLAIMED_BY_OTHER("4","El código ingresado ya fué reclamado por otro cliente."),
    ALREADY_CLAIMED_BY_PARTY("5","El código ingresado ya fué reclamado por un jugador de la mesa."),
    CLIENT_DONT_EXIST_BY_PARTY("6","El cliente no existe"),
    SUCCESS(null,"El código fué canjeado con éxito.");

    final String code;
    final String message;
}
