package ec.com.internacional.models;

import jakarta.persistence.*;

@Entity
@Table(name = "compras")
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nroCompra;
	private String descripcion;
	private Double precioTotal;
	
//	@ManyToOne
//    @JoinColumn(name = "usuario_id", nullable = false)
//    private Usuario usuario;
	
	@Column(unique = true, nullable = false)
    private String idempotencyKey;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNroCompra() {
		return nroCompra;
	}

	public void setNroCompra(String nroCompra) {
		this.nroCompra = nroCompra;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}
	
	public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

//	public Usuario getUsuario() {
//		return usuario;
//	}
//
//	public void setUsuario(Usuario usuario) {
//		this.usuario = usuario;
//	}

}
