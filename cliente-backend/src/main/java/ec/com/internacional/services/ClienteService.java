package ec.com.internacional.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ec.com.internacional.models.Cliente;

@Service
public interface ClienteService {

	List<Cliente> findAll();

	Optional<Cliente> findById(Long id);
	
	Optional<Cliente> findByCedula(String cedula);

	Cliente save(Cliente cliente);

	void deleteById(Long id);
}
