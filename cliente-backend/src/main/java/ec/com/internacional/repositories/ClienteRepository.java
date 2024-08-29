package ec.com.internacional.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.internacional.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	@Query("select u from Cliente u where u.identificacion = :cedula")
	Optional<Cliente> findByCedula(@Param("cedula") String cedula);
}
