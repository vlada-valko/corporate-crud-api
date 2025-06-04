package ua.corporate.avgust.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.corporate.avgust.dto.UserDTO;
import ua.corporate.avgust.service.security.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody UserDTO userDTO) {
        return authService.performLogin(userDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false)
                                    String authorizationHeader) {
        return authService.logout(authorizationHeader);
    }
}


