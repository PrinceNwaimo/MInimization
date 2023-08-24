package com.recycleBusiness.RecyclePal.controllers;

import com.recycleBusiness.RecyclePal.dto.request.AdminLoginRequest;
import com.recycleBusiness.RecyclePal.dto.request.AdminRegistrationRequest;
import com.recycleBusiness.RecyclePal.dto.response.AdminLoginResponse;
import com.recycleBusiness.RecyclePal.dto.response.AdminRegistrationResponse;
import com.recycleBusiness.RecyclePal.exception.RecycleException;
import com.recycleBusiness.RecyclePal.exception.UsernameNotFoundException;
import com.recycleBusiness.RecyclePal.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<AdminRegistrationResponse> register(@RequestBody AdminRegistrationRequest adminRegistrationRequest) {
        try {
            var response = adminService.register(adminRegistrationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RecycleException exception) {
            var response = new AdminRegistrationResponse();
            response.setMessage(exception.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(@RequestBody AdminLoginRequest adminLoginRequest) {
        try {
            var response = adminService.login(adminLoginRequest);
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        } catch (RecycleException | UsernameNotFoundException exception) {
            var response = new AdminLoginResponse();
            response.setMessage(exception.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/find all")
    public ResponseEntity<?> getAllAdmins(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(adminService.getAllAdmins(page, size));
    }

    @GetMapping("/findAdminById")
    public ResponseEntity<?> getAdminById(@RequestParam Long Id) throws UsernameNotFoundException, RecycleException {
        return ResponseEntity.ok(adminService.getAdminById(Id));
    }
    @GetMapping("/findAdminByEmail")
    public ResponseEntity<?> getAdminByEmail(@RequestParam String email) throws UsernameNotFoundException, RecycleException{
        return ResponseEntity.ok(adminService.getAdminByEmail(email));
    }

    @DeleteMapping("/delete admin")
    public ResponseEntity<?> deleteAdmin(@RequestParam Long Id){
        return ResponseEntity.ok(adminService.deleteAdmin(Id));
    }

}
