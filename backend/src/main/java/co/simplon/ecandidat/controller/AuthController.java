package co.simplon.ecandidat.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.ecandidat.dto.LoginDto;
import co.simplon.ecandidat.entity.RoleEntity;
import co.simplon.ecandidat.entity.UserEntity;
import co.simplon.ecandidat.repository.RoleRepository;
import co.simplon.ecandidat.repository.UserRepository;
import co.simplon.ecandidat.service.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthenticationManager authManager,
            PasswordEncoder passwordEncoder,
            TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody UserEntity user) {
        Authentication auth = this.authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        String token = tokenService.generateToken(auth);

        UserEntity userConnected = (UserEntity) auth.getPrincipal();
        return ResponseEntity.ok(new LoginDto(token, userConnected.getEmail()));
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@Valid @RequestBody UserEntity user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        RoleEntity userRole = roleRepository.findByAuthority("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new RoleEntity("ROLE_USER")));

        Set<RoleEntity> authorities = new HashSet<>();
        authorities.add(userRole);
        user.setAuthorities(authorities);

        UserEntity savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
