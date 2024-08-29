package ec.com.internacional.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.internacional.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
//    Optional<Usuario> findByNombre(String nombre);
    
	Optional<Usuario> findByUsername(String username);
}

