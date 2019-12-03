package ec.com.store.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.store.dto.CarritoListDto;
import ec.com.store.dto.VentaDto;
import ec.com.store.models.Producto;
import ec.com.store.models.Usuario;
import ec.com.store.models.Venta;
import ec.com.store.repositories.ProductoRepository;
import ec.com.store.repositories.UsuarioRepository;
import ec.com.store.repositories.VentaRepository;

@Service
@Transactional
public class VentaService {

	private static Logger log = LoggerFactory.getLogger(VentaService.class);
	
	private VentaRepository ventaRepository;
	private ProductoRepository productoRepository;
	private UsuarioRepository usuarioRepository;
	
	public VentaService() {
		
	}
	
	@Autowired
	public VentaService(VentaRepository ventaRepository, ProductoRepository productoRepository,
			UsuarioRepository usuarioRepository) {
		this.ventaRepository = ventaRepository;
		this.productoRepository = productoRepository;
		this.usuarioRepository = usuarioRepository;
	}

	/**
	 * Método que permite transfomar de tipo Venta a VentaResponse
	 * @param venta
	 * @return
	 */
	private VentaDto doVentaTransform(Venta venta) {
		VentaDto ventaResponse = new VentaDto(venta.getNroDocumento(), venta.getFechaVenta(),
				venta.getSubtotal(), venta.getIva(), venta.getTotal());
		Optional<Usuario> user = usuarioRepository.findByEmail(venta.getUsuario().getEmail());
		log.info("usuario en compras",user.get().getId() + "" + user.get().getNombres());
		ventaResponse.setUsuario(user.get().getNombres() + " " + user.get().getApellidos());
		return ventaResponse;
	}

	/**
	 * Método que obtiene todos los Venta de la bdd
	 * @return
	 */
	public CarritoListDto<VentaDto> obtenerVentas() {
		List<VentaDto> ventas = new ArrayList<>();	
		List<Venta> respuesta = (List<Venta>) ventaRepository.findAll();

		if (respuesta != null && !respuesta.isEmpty()) {
			for (Venta venta : respuesta) {
				ventas.add(doVentaTransform(venta));
			}
		}
		return new CarritoListDto<>(ventas);
	}
	
	/**
	 * Método que obtiene todos los Venta de un empleado especifico
	 * @return
	 */
	public CarritoListDto<VentaDto> obtenerVentasPorIdUsuario(Integer idUsuario) {
		List<VentaDto> ventas = new ArrayList<>();	
		List<Venta> respuesta = (List<Venta>) ventaRepository.findByUsuarioId(idUsuario);

		if (respuesta != null && !respuesta.isEmpty()) {
			for (Venta venta : respuesta) {
				ventas.add(doVentaTransform(venta));
			}
		}
		return new CarritoListDto<>(ventas);
	}

	/**
	 * Método que permite crear un venta en la bdd
	 * @param venta
	 * @return
	 */
	public VentaDto crearVenta(Venta venta) {
		venta.setFechaVenta(new Date());
		venta.setNroDocumento("0000000"+ productoRepository.count() + 1);
		venta.getDetalleList().forEach(detalle ->{
			Optional<Producto> producto = productoRepository.findById(detalle.getProducto().getId());
			producto.get().setCantidad(producto.get().getCantidad() - detalle.getCantidad());
			productoRepository.save(producto.get());
			
			detalle.setVenta(venta);
		});
		return doVentaTransform(ventaRepository.save(venta));
	}

}
