package ec.com.internacional.controllers;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.internacional.config.JwtTokenProvider;
import ec.com.internacional.models.Compra;
import ec.com.internacional.models.Usuario;
import ec.com.internacional.repositories.UsuarioRepository;
import ec.com.internacional.services.impl.CompraService;
import ec.com.internacional.services.impl.UsuarioService;

@RestController
@RequestMapping("/compras")
public class CompraController {

	@Autowired
    private CompraService compraService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsuarioService usuarioService;  // Inyectamos UsuarioService en lugar de UsuarioRepository

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final String SECRET_KEY = "clave-secreta-compartida";

    private String decryptIdentifier(String encryptedIdentifier) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.decodeBase64(encryptedIdentifier)), StandardCharsets.UTF_8);
    }
    
//	@PostMapping
//	public Compra createCompra(@RequestBody Compra compra) {
//		return compraRepository.save(compra);
//	}
	
//	@PostMapping()
//    public ResponseEntity<?> realizarCompra(@RequestHeader("Authorization") String token, @RequestBody Compra compra) {
//        String jwt = token.replace("Bearer ", "");
//        String username = jwtTokenProvider.getUsernameFromToken(jwt);
//        Long userId = usuarioRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado")).getId();
//
//        Compra nuevaCompra = compraService.realizarCompra(userId, compra);
//
//        // Generar nuevo token
//        String newSessionToken = jwtTokenProvider.generateToken(username);
//        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
//        if (usuario != null) {
//            usuario.setSessionToken(newSessionToken);
//            usuarioRepository.save(usuario);
//        }
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("token", newSessionToken);
//        response.put("compra", nuevaCompra);
//        return ResponseEntity.ok(response);
//    }

    
//    @PostMapping()
//    public ResponseEntity<?> realizarCompra(@RequestHeader("Authorization") String token, @RequestBody Compra compra) {
//        // Remover el prefijo "Bearer " del token recibido
//        String jwt = token.replace("Bearer ", "");
//
//        // Obtener el username del token JWT
//        String username = jwtTokenProvider.getUsernameFromToken(jwt);
//
//        // Obtener el ID del usuario a partir del username
//        Usuario usuario = usuarioRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
//        Long userId = usuario.getId();
//
//        // Realizar la compra y asociarla con el usuario
//        Compra nuevaCompra = compraService.realizarCompra(userId, compra);
//
//        // Generar un nuevo token JWT
//        String newSessionToken = jwtTokenProvider.generateTokenFromUsername(username);
//
//        // Actualizar el token de sesión del usuario
//        usuario.setSessionToken(newSessionToken);
//        usuarioRepository.save(usuario);
//
//        // Preparar la respuesta con el nuevo token y la compra realizada
//        Map<String, Object> response = new HashMap<>();
//        response.put("token", newSessionToken);
//        response.put("compra", nuevaCompra);
//
//        return ResponseEntity.ok(response);
//    }
    
    
//    @PostMapping("/comprar")
//    public ResponseEntity<?> realizarCompra(
//        @RequestHeader("Authorization") String token,
//        @RequestBody Compra compra,
//        @RequestHeader("Session-Token") String sessionToken
//    ) {
//        String jwt = token.replace("Bearer ", "");
//        String username = jwtTokenProvider.getUsernameFromToken(jwt);
//
//        // Utilizamos UsuarioService para validar el sessionToken
//        Usuario usuario = usuarioService.findByUsername(username);
//
//        if (usuario == null || !usuario.getSessionToken().equals(sessionToken)) {
//            return ResponseEntity.status(403).body("Token de sesión no válido");
//        }
//
//        // Realizamos la compra
//        Compra nuevaCompra = compraService.realizarCompra(usuario.getId(), compra);
//
//        // Generamos un nuevo sessionToken
//        String newSessionToken = usuarioService.createSessionToken(username);
//        usuario.setSessionToken(newSessionToken);
//        usuarioService.save(usuario);  // Guardamos el usuario con el nuevo sessionToken
//
//        // Respuesta que incluye el nuevo JWT y la compra realizada
//        Map<String, Object> response = new HashMap<>();
//        response.put("token", jwtTokenProvider.generateTokenFromUsername(username));
//        response.put("sessionToken", newSessionToken);
//        response.put("compra", nuevaCompra);
//
//        return ResponseEntity.ok(response);
//    }
    
    
    @PostMapping()
    public ResponseEntity<?> nuevaCompra(@RequestBody Map<String, Object> compraRequest, Authentication authentication) {
        String username = authentication.getName();

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            String sessionToken = (String) compraRequest.get("sessionToken");

            if (!sessionToken.equals(usuario.getSessionToken())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token de sesión inválido.");
            }
            
           
            try {

                // Verificar si ya existe una compra con el mismo idempotencyKey
//                if (compraService.existsByIdempotencyKey(idempotencyKey)) {
//                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Solicitud duplicada.");
//                }

                Compra compra = new Compra();
                compra.setNroCompra((String) compraRequest.get("nroCompra"));
                compra.setDescripcion((String) compraRequest.get("descripcion"));
                compra.setPrecioTotal(Double.parseDouble(compraRequest.get("precioTotal").toString()));
//                compra.setUsuario(usuario);
                compra.setIdempotencyKey((String) compraRequest.get("idempotencyKey"));

                compraService.realizarCompra(compra);
                return ResponseEntity.ok("Compra registrada exitosamente.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud.");
            }

//            Compra compra = new Compra();
//            compra.setNroCompra((String) compraRequest.get("nroCompra"));
//            compra.setDescripcion((String) compraRequest.get("descripcion"));
//            compra.setPrecioTotal(Double.parseDouble(compraRequest.get("precioTotal").toString()));
////            compra.setUsuario(usuario);
//
//            compraService.realizarCompra(compra);
//            return ResponseEntity.ok("Compra registrada exitosamente.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado.");
    }
    
//	@GetMapping
//	public List<Compra> getAllCompras() {
//		return compraRepository.findAll();
//	}
}
