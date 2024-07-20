package com.ProyectoIntegrador.ProyectoIntegrador.service.auth;

import com.ProyectoIntegrador.ProyectoIntegrador.config.JwtService;
import com.ProyectoIntegrador.ProyectoIntegrador.dto.auth.AuthDto;
import com.ProyectoIntegrador.ProyectoIntegrador.dto.auth.LoginDto;
import com.ProyectoIntegrador.ProyectoIntegrador.dto.auth.RegisterDto;
import com.ProyectoIntegrador.ProyectoIntegrador.entity.UserMongoEntity;
import com.ProyectoIntegrador.ProyectoIntegrador.repository.UserMongoRepository;
import com.ProyectoIntegrador.ProyectoIntegrador.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthService {
    @Autowired
    private UserMongoRepository userMongoRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthDto login(final LoginDto request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userMongoRepository.findByEmail(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return new AuthDto(token);
    }

    public AuthDto register(final RegisterDto request){
        UserMongoEntity user = new UserMongoEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userMongoRepository.save(user);
        return new AuthDto(this.jwtService.getToken(user));
    }
}
