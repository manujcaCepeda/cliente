package ec.com.internacional.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.internacional.models.Compra;
import ec.com.internacional.models.Usuario;
import ec.com.internacional.repositories.CompraRepository;
import ec.com.internacional.repositories.UsuarioRepository;
import ec.com.internacional.utils.CryptoUtils;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Compra realizarCompra(Compra compra) throws Exception {
//        Usuario usuario = usuarioRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
//
//        compra.setUsuario(usuario);
    	
    	
    	// Desencriptar el identificador único
        String decryptedUniqueId = CryptoUtils.decrypt(compra.getIdempotencyKey());

        // Verificar si ya existe en la base de datos
        if (compraRepository.existsByIdempotencyKey(decryptedUniqueId)) {
            throw new RuntimeException("Compra duplicada: el identificador único ya existe.");
        }

        // Guardar la compra
        compra.setIdempotencyKey(decryptedUniqueId); // Guardar el identificador desencriptado
        
        
        return compraRepository.save(compra);
    }
    
    
    public boolean existsByIdempotencyKey(String idempotencyKey) {
    	return compraRepository.existsByIdempotencyKey(idempotencyKey);
    }
}
