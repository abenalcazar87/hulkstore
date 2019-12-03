package ec.com.store.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.store.dto.CarritoListDto;
import ec.com.store.dto.ProductoDto;
import ec.com.store.models.Producto;
import ec.com.store.repositories.CategoriaRepository;
import ec.com.store.repositories.ProductoRepository;

@Service
@Transactional
public class ProductoService {

//private static Logger log = LoggerFactory.getLogger(ProductoService.class);
	
	private ProductoRepository productoRepository;
	private CategoriaRepository categoriaRepository;

	public ProductoService() {
		
	}
	
	@Autowired
	public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
		this.productoRepository = productoRepository;
		this.categoriaRepository = categoriaRepository;
	}

	/**
	 * Método que permite transfomar de tipo Producto a ProductoResponse
	 * @param producto
	 * @return
	 */
	private ProductoDto doProductoResponse(Producto producto) {
		ProductoDto productoResponse = new ProductoDto(producto.getId(), producto.getNombre(), producto.getDescripcion(),
				producto.getFecha(), producto.getCantidad(), producto.getUrl(), producto.getPrecio());
		productoResponse.setCategoria(categoriaRepository.findByCodigo(producto.getCategoria().getCodigo()).getDescripcion());
		return productoResponse;
	}

	/**
	 * Método que obtiene todos los producto de la bdd
	 * @return
	 */
	public CarritoListDto<ProductoDto> obtenerProductos() {
		List<ProductoDto> productos = new ArrayList<>();
		List<Producto> respuesta = (List<Producto>) productoRepository.findAll();

		if (respuesta != null && !respuesta.isEmpty()) {
			for (Producto prod : respuesta) {
				productos.add(doProductoResponse(prod));
			}
		}
		return new CarritoListDto<>(productos);
	}

	/**
	 * Método que permite crear un Producto en la bdd
	 * @param producto
	 * @return
	 */
	public ProductoDto crearProducto(Producto producto) {
		producto.setFecha(new Date());
		Producto productoExiste = productoRepository.findByNombre(producto.getNombre());
		if (productoExiste == null) {
			producto = productoRepository.save(producto);
			return doProductoResponse(producto);
		}
		return null;
	}
	
	/**
	 * Método que permite crear un Producto en la bdd
	 * 
	 * @param producto
	 * @return
	 */
	public ProductoDto actualizarProducto(Producto producto) {
		producto.setFecha(new Date());
		if (producto.getId() != null) {
			producto = productoRepository.save(producto);
			return doProductoResponse(producto);
		}
		return null;
	}

	/**
	 * Método que permite eliminar un Producto de la bdd
	 * @param codigo
	 */
	public void eliminar(Integer codigo) {
		productoRepository.deleteById(codigo);
	}
}
