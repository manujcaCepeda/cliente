package ec.com.internacional.services.impl;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ec.com.internacional.config.JwtTokenProvider;
import ec.com.internacional.models.Usuario;
import ec.com.internacional.repositories.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String iniciarSesion(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Crear y almacenar el token de sesión
        String sessionToken = UUID.randomUUID().toString();
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setSessionToken(sessionToken);
        usuarioRepository.save(usuario);

        // Generar JWT con el token de sesión
        String jwt = "";//jwtTokenProvider.generateToken(authentication, sessionToken);

        return jwt;
    }
    
    public void cerrarSesion(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setSessionToken(null); // O generar un nuevo token si es necesario
        usuarioRepository.save(usuario);
    }
    
    
}
