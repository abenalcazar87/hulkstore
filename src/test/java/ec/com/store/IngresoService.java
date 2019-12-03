package ec.com.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.store.dto.CarritoListDto;
import ec.com.store.dto.IngresoDto;
import ec.com.store.models.Ingreso;
import ec.com.store.models.Producto;
import ec.com.store.models.Usuario;
import ec.com.store.repositories.IngresoRepository;
import ec.com.store.repositories.ProductoRepository;
import ec.com.store.repositories.UsuarioRepository;

@Service
@Transactional
public class IngresoService {

	private IngresoRepository ingresoRepository;
	private ProductoRepository productoRepository;
	private UsuarioRepository usuarioRepository;

	public IngresoService() {

	}

	@Autowired
	public IngresoService(IngresoRepository ingresoRepository, ProductoRepository productoRepository,
			UsuarioRepository usuarioRepository) {
		this.ingresoRepository = ingresoRepository;
		this.productoRepository = productoRepository;
		this.usuarioRepository = usuarioRepository;
	}

	/**
	 * Método que permite transfomar de tipo Ingreso a IngresoResponse
	 * 
	 * @param ingreso
	 * @return
	 */
	private IngresoDto doIngresoResponse(Ingreso ingreso) {
		IngresoDto ingresoResponse = new IngresoDto(ingreso.getId(), ingreso.getCantidad(),
				ingreso.getFechaIngreso(), ingreso.getTotal());
		Optional<Producto> productoOpcional = productoRepository.findById(ingreso.getProducto().getId());
		if (productoOpcional.isPresent()) {
			ingresoResponse.setProducto(productoOpcional.get().getNombre());
			ingresoResponse.setPrecio(productoOpcional.get().getPrecio());
		}
		Optional<Usuario> usuarioOpciona = usuarioRepository.findById(ingreso.getUsuario().getId());
		if (usuarioOpciona.isPresent()) {
			ingresoResponse.setUsuario(usuarioOpciona.get().getNombres() + " " + usuarioOpciona.get().getApellidos());
		}
		return ingresoResponse;
	}

	/**
	 * Método que obtiene todos los ingreso de la bdd
	 * 
	 * @return
	 */
	public CarritoListDto<IngresoDto> obtenerIngresos() {
		List<IngresoDto> productos = new ArrayList<>();
		List<Ingreso> respuesta = (List<Ingreso>) ingresoRepository.findAll();

		if (respuesta != null && !respuesta.isEmpty()) {
			for (Ingreso ingreso : respuesta) {
				productos.add(doIngresoResponse(ingreso));
			}
		}
		return new CarritoListDto<>(productos);
	}

	/**
	 * Método que permite crear un Ingreso en la bdd
	 * 
	 * @param ingreso
	 * @return
	 */
	public IngresoDto crearIngreso(Ingreso ingreso) {
		ingreso.setFechaIngreso(new Date());
		Optional<Producto> productoOpcional = productoRepository.findById(ingreso.getProducto().getId());
		if (productoOpcional.isPresent()) {
			productoOpcional.get().setCantidad(productoOpcional.get().getCantidad() + ingreso.getCantidad());
			productoRepository.save(productoOpcional.get());
		}
		return doIngresoResponse(ingresoRepository.save(ingreso));
	}

	/**
	 * Método que permite eliminar un Ingreso de la bdd
	 * 
	 * @param codigo
	 */
	public void eliminar(Integer codigo) {
		Optional<Ingreso> ingresoOpcional = ingresoRepository.findById(codigo);
		Producto producto = null;
		if (ingresoOpcional.isPresent()) {
			producto = ingresoOpcional.get().getProducto();
			producto.setCantidad(producto.getCantidad() - ingresoOpcional.get().getCantidad());
			productoRepository.save(producto);
			ingresoRepository.deleteById(codigo);
		}
	}

}
