package ec.com.store.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ec.com.store.dto.UserDto;
import ec.com.store.exception.ModeloNotFoundException;
import ec.com.store.models.Usuario;
import ec.com.store.services.UsuarioService;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;


	@Resource(name = "tokenServices")
	private ConsumerTokenServices tokenServices;

	public static final int PASSWORD_MIN_LENGTH = 4;
	public static final int PASSWORD_MAX_LENGTH = 100;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Usuario>> listar() {
		List<Usuario> usuario = new ArrayList<>();

		usuario = usuarioService.listar();
		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> guardar(@Valid @RequestBody Usuario usuario) {
		Usuario newUsuario = new Usuario();
		newUsuario = usuarioService.registrar(usuario);
		if (newUsuario == null) {
			throw new DataIntegrityViolationException("Ya existe un usuario con email: " + usuario.getEmail());
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUsuario.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> actualizar(@RequestBody Usuario usuario) {
		usuarioService.modificar(usuario);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	

	@GetMapping(value = "/usuario/{username:.*}")
	public ResponseEntity<UserDto> obtenerUsuario(@PathVariable("username") String username) {
		Usuario usuario = new Usuario();
		usuario = usuarioService.obtenerUsuario(username);
		if (usuario == null) {
			throw new ModeloNotFoundException("Correo: " + username);
		}
		return new ResponseEntity<UserDto>(new UserDto(usuario), HttpStatus.OK);
	}
	
	@GetMapping(value = "/cedula/{cedula:.*}")
	public ResponseEntity<UserDto> obtenerPorCedula(@PathVariable("cedula") String cedula) {
		Usuario usuario = new Usuario();
		usuario = usuarioService.obtenerPorCedula(cedula);
		if (usuario == null) {
			throw new ModeloNotFoundException("Identidad: " + cedula);
		}
		return new ResponseEntity<UserDto>(new UserDto(usuario), HttpStatus.OK);
	}
	
	
	@DeleteMapping(path="/{id}")
	public void eliminar(@PathVariable(value="id") Integer id) {
		usuarioService.eliminar(id);
	}
	
	@GetMapping(value = "/imprimir", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> imprimirArticulos() throws IOException {				
		byte[] data = null;
		data = usuarioService.imprimir();
		return new ResponseEntity<byte[]>(data, HttpStatus.OK);
	}
}
