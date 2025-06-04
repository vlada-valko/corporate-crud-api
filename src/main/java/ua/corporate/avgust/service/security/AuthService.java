package ua.corporate.avgust.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.corporate.avgust.api.response.ResponseFactory;
import ua.corporate.avgust.config.security.JWTUtil;
import ua.corporate.avgust.dto.UserDTO;
import ua.corporate.avgust.mapper.UserMapper;
import ua.corporate.avgust.model.User;
import ua.corporate.avgust.repository.UserRepository;
import ua.corporate.avgust.service.UserService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    //region standard fields
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    //endregion standard fields

    public ResponseEntity<?> performLogin (UserDTO userDTO) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());

        authenticationManager.authenticate(authToken);

        User user = userMapper.toModel(userService.readByUsername(userDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));

        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseFactory.success(token, "token successfully created" );

    }
    public  ResponseEntity<?> logout(String authorizationHeader) {
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        if (token == null) {
            return ResponseFactory.error("token is missing", HttpStatus.UNAUTHORIZED);
        }
        try {
            jwtUtil.validateTokenAndRetrieveClaim(token);
            return ResponseFactory.success(token, "Successfully logged out");
        } catch (Exception e) {
            return ResponseFactory.error("Invalid token", HttpStatus.UNAUTHORIZED);
        }
    }


}
