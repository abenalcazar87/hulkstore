package ec.com.store.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.store.dto.CarritoListDto;
import ec.com.store.dto.VentaDto;
import ec.com.store.models.Venta;
import ec.com.store.services.VentaService;

@RestController
@RequestMapping("/api/venta")
public class VentaController{

	final static Logger logger = LoggerFactory.getLogger(VentaController.class);

	private VentaService ventaService;

	@Autowired
	public VentaController(VentaService ventaService) {
		this.ventaService = ventaService;
	}

	/**
	 * API REST para crear una nueva venta
	 * @param model
	 * @param reqVenta
	 * @return
	 */
	@PostMapping(produces = "application/json")
	public VentaDto saveVenta(ModelMap model, @RequestBody Venta reqVenta) {
		VentaDto venta = null;
		try {
			venta = ventaService.crearVenta(reqVenta);
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}
		return venta;
	}

	/**
	 * API REST para obtener todas las ventas
	 * @return
	 */
	@GetMapping(produces = "application/json")
	public CarritoListDto<VentaDto> getVentas() {
		try {
			return ventaService.obtenerVentas();
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}

	}
	
	
	/**
	 * API REST para obtener todas las ventas
	 * @return
	 */
	@GetMapping(value="/{idUsuario}", produces = "application/json")
	public CarritoListDto<VentaDto> getVentas(@PathVariable("idUsuario") Integer idUsuario) {
		try {
			return ventaService.obtenerVentasPorIdUsuario(idUsuario);
		} catch (DataIntegrityViolationException ex) {
			logger.debug(ex.getMessage(), ex);
			throw new DataIntegrityViolationException("Verifique los datos ingresados: ");
		}

	}
	

}
