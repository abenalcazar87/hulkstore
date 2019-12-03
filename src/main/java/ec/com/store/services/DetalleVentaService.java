package ec.com.store.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.store.dto.DetalleVentaDto;
import ec.com.store.models.DetalleVenta;
import ec.com.store.repositories.DetalleVentaRepository;
import ec.com.store.repositories.ProductoRepository;

@Service
@Transactional
public class DetalleVentaService {

//private static Logger log = LoggerFactory.getLogger(DetalleVentaService.class);
	
	private DetalleVentaRepository detalleVentaRepository;
	private ProductoRepository productoRepository;

	public DetalleVentaService() {
		
	}
	
	@Autowired
	public DetalleVentaService(DetalleVentaRepository detalleVentaRepository, ProductoRepository productoRepository) {
		this.detalleVentaRepository = detalleVentaRepository;
		this.productoRepository = productoRepository;
	}

	/**
	 * Método que permite transfomar de tipo DetalleVenta a DetalleVentaResponse
	 * @param detalleVenta
	 * @return
	 */
	private DetalleVentaDto doDetalleventaResponse(DetalleVenta detalleVenta) {
		DetalleVentaDto detalleVentaResponse = new DetalleVentaDto(detalleVenta.getCantidad(), detalleVenta.getSubtotal(),
				detalleVenta.getTotal());
		detalleVentaResponse.setProducto(productoRepository.findById(detalleVenta.getProducto().getId()).get().getNombre());
		return detalleVentaResponse;
	}

	/**
	 * Método que obtiene todos los detalleVenta de la bdd
	 * @return
	 */
	public List<DetalleVenta> obtenerDetalleVentas() {
		return (List<DetalleVenta>) detalleVentaRepository.findAll();
	}

	/**
	 * Método que permite crear un DetalleVenta en la bdd
	 * @param detalleVenta
	 * @return
	 */
	public DetalleVentaDto crearDetalleVenta(DetalleVenta detalleVenta) {
		detalleVenta = detalleVentaRepository.save(detalleVenta);
		return doDetalleventaResponse(detalleVenta);
	}

}
