package com.recycleBusiness.RecyclePal.controllers;

import com.recycleBusiness.RecyclePal.dto.request.PlasticPickUpRequest;
import com.recycleBusiness.RecyclePal.dto.response.PlasticPickUpResponse;
import com.recycleBusiness.RecyclePal.exception.WasteNotCreated;
import com.recycleBusiness.RecyclePal.service.WasteCollectionRequestServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class WasteCollectionController {
    private final WasteCollectionRequestServices services;
    @RequestMapping

    public ResponseEntity<?> createWaste(@RequestBody PlasticPickUpRequest requestDto){
        try {
           var response = services.createRequestDetails(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (WasteNotCreated e) {
            var response =  new PlasticPickUpResponse();
            response.setMessage(e.getMessage());
           return ResponseEntity.badRequest().body(response);
        }

    }
}
