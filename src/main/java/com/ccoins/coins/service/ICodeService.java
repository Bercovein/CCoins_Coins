package com.ccoins.coins.service;

import com.ccoins.coins.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICodeService {

    ResponseEntity<List<CodeDTO>> createCodesByGameBarId(CodeRqDTO request);

    ResponseEntity<CodeDTO> invalidateCode(StringDTO request);

    ResponseEntity<List<CodeDTO>> getByActive(Long id, String state);

    ResponseEntity<GenericRsDTO<CoinsDTO>> redeemCode(RedeemCodeRqDTO request);
}
