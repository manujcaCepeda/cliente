package ec.com.internacional.services.impl;


import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ec.com.internacional.models.Usuario;
import ec.com.internacional.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Usuario saveUser(Usuario user) {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRol("ROLE_USER"); // Puedes ajustar esto según sea necesario
        return usuarioRepository.save(user);
    }
    
	public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElse(null);  // Manejo de caso cuando no se encuentra el usuario
    }
    
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Usuario usuario = usuarioRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
//        return usuario;
//    }
    
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Usuario usuario = usuarioRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con username: " + username));
//
//        return new User(usuario.getUsername(), usuario.getPassword(), new ArrayList<>());
//    }
    
    
    
 // Carga un usuario por su nombre de usuario
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + username));

        return new org.springframework.security.core.userdetails.User(usuario.getUsername(), usuario.getPassword(), new ArrayList<>());
    }

    public String createSessionToken(String username) {
        String sessionToken = UUID.randomUUID().toString();
        Usuario usuario = findByUsername(username);

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        usuario.setSessionToken(sessionToken);
        usuarioRepository.save(usuario);

        return sessionToken;
    }
    
    public void invalidateSessionToken(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if (usuario != null) {
            usuario.setSessionToken(null);
            usuarioRepository.save(usuario);
        }
    }
    
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
    
}

