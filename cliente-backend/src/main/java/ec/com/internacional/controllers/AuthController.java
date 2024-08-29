package ec.com.internacional.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.internacional.config.JwtTokenProvider;
import ec.com.internacional.dto.AuthResponse;
import ec.com.internacional.dto.LoginRequest;
import ec.com.internacional.services.impl.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsuarioService usuarioService;

   
//	@PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody Usuario user) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
//            );
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            
//            String token = jwtUtil.generateToken(userDetails);
//            		
//            if (token != null) {
//                return ResponseEntity.ok(new AuthResponse(token));
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//            } 
//        } catch (AuthenticationException e) {
//            throw new RuntimeException("Authentication failed", e);
//        }
//    }
    
    
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> credentials) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        credentials.get("username"),
//                        credentials.get("password")
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String jwt = jwtTokenProvider.generateToken(userDetails.getUsername());
//
//        Usuario usuario = usuarioService.findByUsername(credentials.get("username")).orElse(null);
//        if (usuario != null) {
//            usuario.setSessionToken(jwt);
//            usuarioService.saveUser(usuario);
//        }
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("token", jwt);
//        return ResponseEntity.ok(response);
//    }
    
    
//    @PostMapping("/login")
//    public Map<String, String> authenticateUser(@RequestBody Map<String, String> loginRequest) {
//        String username = loginRequest.get("username");
//        String password = loginRequest.get("password");
//
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password));
//
//            String token = jwtTokenProvider.generateToken(authentication);
//
//            Map<String, String> response = new HashMap<>();
//            response.put("token", token);
//            return response;
//
//        } catch (AuthenticationException e) {
//            throw new RuntimeException("Invalid credentials");
//        }
//    }
    
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody Usuario loginRequest, HttpServletResponse response) {
//        String jwt = authService.iniciarSesion(loginRequest.getUsername(), loginRequest.getPassword());
//
//        // Configurar la cookie segura
//        Cookie jwtCookie = new Cookie("jwt", jwt);
//        jwtCookie.setHttpOnly(true);
//        jwtCookie.setSecure(true); // Asegúrate de que tu servidor esté configurado para HTTPS
//        jwtCookie.setPath("/");
//        jwtCookie.setMaxAge(5 * 60); // 5 minutos, por ejemplo
//        response.addCookie(jwtCookie);
//
//        return ResponseEntity.ok("Autenticado exitosamente");
//    }
    


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        // Generar JWT
        String jwt = jwtTokenProvider.generateToken(authentication);
        // Generar sessionToken separado
        String sessionToken = usuarioService.createSessionToken(loginRequest.getUsername());

        // Crear respuesta de autenticación
        AuthResponse authResponse = new AuthResponse(jwt, sessionToken, "Inicio de sesión exitoso");
        return ResponseEntity.ok(authResponse);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        usuarioService.invalidateSessionToken(username);
        return ResponseEntity.ok("Sesión cerrada exitosamente");
    }
    
}
