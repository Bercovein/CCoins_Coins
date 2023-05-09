package com.ccoins.coins.controller;

import com.ccoins.coins.dto.*;
import com.ccoins.coins.service.ICodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/codes")
public class CodesController {

    @Autowired
    private ICodeService service;

    @PostMapping
    public ResponseEntity<List<CodeDTO>> createCodeByGameBarId(@RequestBody @Valid CodeRqDTO request){
        return this.service.createCodesByGameBarId(request);
    }

    @PutMapping("/invalidate")
    public ResponseEntity<CodeDTO> invalidateCode(@RequestBody @Valid StringDTO request){
        return this.service.ínvalidateCode(request);
    }

    @GetMapping("/game/{id}/{state}")
    public ResponseEntity<List<CodeDTO>> getByActive(@PathVariable("id") Long id, @PathVariable("state") String state){
        return this.service.getByActive(id,state);
    }

    @PostMapping("/redeem")
    public ResponseEntity<GenericRsDTO<CoinsDTO>> redeemCode(@RequestBody @Valid RedeemCodeRqDTO request){
        return this.service.redeemCode(request);
    }
}
