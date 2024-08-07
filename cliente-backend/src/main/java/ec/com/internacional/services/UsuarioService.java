package ec.com.internacional.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ec.com.internacional.models.Usuario;

@Service
public interface UsuarioService {

	List<Usuario> findAll();

	Optional<Usuario> findById(Long id);
	
	Optional<Usuario> findByCedula(String cedula);

	Usuario save(Usuario usuario);

	void deleteById(Long id);
}
