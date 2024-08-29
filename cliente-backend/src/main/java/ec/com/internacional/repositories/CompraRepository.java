package ec.com.internacional.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.internacional.models.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {
	
	boolean existsByIdempotencyKey(String idempotencyKey);
}

