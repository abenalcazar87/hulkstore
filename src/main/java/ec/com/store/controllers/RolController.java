package ec.com.store.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.store.models.Rol;
import ec.com.store.services.RolService;


@RestController
@RequestMapping("/api/roles")
public class RolController {
	
	private RolService rolService;

	public RolController(RolService rolService) {
		super();
		this.rolService = rolService;
	}
	
	@PostMapping
	public Rol guardar(@RequestBody Rol rol){
		return rolService.guardar(rol);
	}
	
	@GetMapping
	public List<Rol> listar(){
		return rolService.listar();
	}

	@PutMapping
	public Rol actualizar(@RequestBody Rol rol){
		return rolService.actualizar(rol);
	}
	
//	@DeleteMapping(path="/{id}")
//	public void eliminar(@PathVariable(value="id") Integer id) {
//		rolService.eliminar(id);
//	}
}
