package ec.com.store.controllers;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.com.store.dto.CarritoListDto;
import ec.com.store.dto.ProductoDto;
import ec.com.store.models.Producto;
import ec.com.store.services.ProductoService;

@RestController
@RequestMapping("/api/producto")
public class ProductoController{

	final static Logger logger = LoggerFactory.getLogger(ProductoController.class);

	private ProductoService productoService;

	@Autowired
	public ProductoController(ProductoService productoService) {
		this.productoService = productoService;
	}

	/**
	 * API REST para crear un producto
	 * @param model
	 * @param reqProducto
	 * @return
	 */
	@PostMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ProductoDto saveProducto(@RequestBody Producto reqProducto) {
		ProductoDto producto = null;
		try {
			producto = productoService.crearProducto(reqProducto);
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}
		if (producto == null) {
			throw new NoSuchElementException("Ya existe un producto con nombre: " + reqProducto.getNombre());
		}
		return producto;
	}

	/**
	 * API REST para obtener todos los productos
	 * @return
	 */
	@GetMapping(produces = "application/json")
	public CarritoListDto<ProductoDto> getProductos() {
		try {
			return productoService.obtenerProductos();
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}

	}
	
	/**
	 * API REST para eliminar un producto
	 * @return
	 */
	@RequestMapping(path="/{id}", produces="application/json")
	public void eliminarPaquete(@PathVariable(value="id") Integer codigo){
		try {
			productoService.eliminar(codigo);
		} catch (DataIntegrityViolationException e) {
			logger.info("Error en el consumo del servicio eliminarPaquete : " +e.getMessage());
			throw new DataIntegrityViolationException(e.getMessage());
		}
	}
	
	@PutMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ProductoDto updateProducto(@RequestBody @Validated Producto reqProducto) {
		ProductoDto producto = null;
		try {
			producto = productoService.actualizarProducto(reqProducto);
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}
		if (producto == null) {
			throw new NoSuchElementException("Ya existe un producto con nombre: " + reqProducto.getNombre());
		}
		return producto;
	}

}
