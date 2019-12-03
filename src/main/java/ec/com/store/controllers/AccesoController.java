package ec.com.store.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ec.com.store.models.Acceso;
import ec.com.store.services.AccesoService;


@RestController
@RequestMapping("/api/accesos")
public class AccesoController {

	@Autowired
	private AccesoService accesoService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Acceso>> listar() {
		List<Acceso> acceso = new ArrayList<>();

		acceso = accesoService.listar();

		return new ResponseEntity<List<Acceso>>(acceso, HttpStatus.OK);
	}
	

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registrar(@Valid @RequestBody Acceso acceso) {
		Acceso newAcceso  = new Acceso();
		newAcceso  = accesoService.registrar(acceso);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newAcceso .getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> actualizar(@RequestBody Acceso acceso) {		
		accesoService.modificar(acceso);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
