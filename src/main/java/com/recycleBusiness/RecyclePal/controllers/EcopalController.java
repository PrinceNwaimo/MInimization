package com.recycleBusiness.RecyclePal.controllers;
import com.recycleBusiness.RecyclePal.dto.request.EcopalRegistrationRequest;
import com.recycleBusiness.RecyclePal.dto.request.EcopalSubmitRequest;
import com.recycleBusiness.RecyclePal.dto.request.UpdateEcopalRequest;
import com.recycleBusiness.RecyclePal.dto.response.EcopalRegistrationResponse;
import com.recycleBusiness.RecyclePal.dto.response.EcopalSubmitResponse;
import com.recycleBusiness.RecyclePal.dto.response.EcopalUpdateResponse;
import com.recycleBusiness.RecyclePal.exception.*;
import com.recycleBusiness.RecyclePal.service.EcopalServiceImplementation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/ecopal")
@Slf4j
@CrossOrigin("*")
public class EcopalController {
    private final EcopalServiceImplementation service;

    @PostMapping("/register")
    public ResponseEntity<EcopalRegistrationResponse> registrationResponse(@RequestBody EcopalRegistrationRequest registrationRequest) throws EcopalAlreadyExistException {
            EcopalRegistrationResponse response = service.registration(registrationRequest);
            return ResponseEntity.ok(response);
    }


    @PatchMapping("/completeRegistration")
    public ResponseEntity<EcopalUpdateResponse> updateProfile(
            @RequestBody UpdateEcopalRequest request) {
        try {
            EcopalUpdateResponse response = service.updateProfile(request);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (EcopalWithEmailOrUsernameExist | UsernameNotFoundException e) {
            EcopalUpdateResponse response = new EcopalUpdateResponse();
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/createWasteResponse")
    public ResponseEntity<?> createWasteRequest(@RequestBody EcopalSubmitRequest request) {
        try {
            EcopalSubmitResponse response = service.submitRequest(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (WasteNotCreated e) {
            var response =  new EcopalSubmitResponse();
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }







}
