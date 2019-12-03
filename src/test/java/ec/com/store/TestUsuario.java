package ec.com.store;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import ec.com.store.dto.CarritoDto;
import ec.com.store.models.Rol;
import ec.com.store.models.Usuario;
import ec.com.store.repositories.RolRepository;
import ec.com.store.repositories.UsuarioRepository;
import ec.com.store.services.UsuarioService;
public class TestUsuario {
	
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UsuarioService usuarioService;
	@MockBean
	private UsuarioRepository usuarioRepository;
	@MockBean
	private RolRepository rolRepository;
	@Mock
	private Usuario user;

	@TestConfiguration
	static class UsuarioServiceImplTestContextConfiguration {
		@Bean
		public UsuarioService employeeService() {
			return new UsuarioService(UsuarioRepository, BCryptPasswordEncoder)
		}
	}

	
	private Rol findRol() {
		Rol rol = new Rol();
		rol.setId(1);
		rol.setRol("ROL_ADMIN");
		rol.setDescripcion("ROL ADMINISTRADOR");
		return rol;
	}

	private Usuario createUsuario() {
		Usuario usuario = new Usuario();
		usuario.setId(1);
		usuario.setApellidos("new Apellido");
		usuario.setNombres("new Nombre");
		usuario.setDireccion("direccion");
		usuario.setTelefono("0986152434");
		usuario.setCorreo("abenalcazar@gmail.com");
		usuario.setRol(findRol());
		return usuario;
	}

	private Usuario findUsuario() {
		Usuario usuario = new Usuario();
		usuario.setId(2);
		usuario.setApellidos("Benalcazar");
		usuario.setNombres("Anibal");
		usuario.setDireccion("direccion");
		usuario.setTelefono("086113485");
		usuario.setCorreo("ventas@store.com");
		return usuario;
	}

	@Test
	public void whenValidName_thenUsuarioShouldBeFound() {
		String name = "new Apellido";
		user = createUsuario();
		Usuario usuario = findUsuario();
		when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(usuario);
		when(usuarioRepository.save(user)).thenReturn(user);
		CarritoDto<Usuario> savedUsuario = usuarioService.crearUsuario(user);
		// Assert
		assertThat(savedUsuario.getData().getApellidos(), is(equalTo(name)));
	}

	@Test
	public void whenValidName_thenusuarioShouldNotFound() {
		user = createUsuario();
		when(usuarioRepository.findByCorreo(user.getCorreo())).thenReturn(user);
		when(usuarioRepository.save(user)).thenReturn(user);
		CarritoDto<Usuario> savedUsuario = usuarioService.crearUsuario(user);
		// Assert
		assertThat(savedUsuario, is(equalTo(null)));
	}

	@Test
	public void whenValidList_thenUsuarioShouldBeFound() {
		List<Usuario> lista = new ArrayList<>();
		lista.add(createUsuario());
		when(usuarioRepository.findAll()).thenReturn(lista);
		List<Usuario> getUses = usuarioService.obtenerUsuarios();
		// Assert
		assertThat(getUses.size(), is(equalTo(1)));
	}

	@Test
	public void shouldReturnusuario_whenGetProductByCorroPasswordIsCalled() {
		String name = "new Apellido";
		user = createUsuario();
		when(usuarioRepository.findByCorreoAndPassword(user.getCorreo(), user.getPassword())).thenReturn(user);
		CarritoDto<Usuario> savedUsuario = usuarioService.obtenerPorCorreoPassword(user.getCorreo(),
				user.getPassword());
		// Assert
		assertThat(savedUsuario.getData().getApellidos(), is(equalTo(name)));
	}
	
	@Test
	public void obtenerPass() {
		System.out.println(bCryptPasswordEncoder.encode("admin"));
	}

}
