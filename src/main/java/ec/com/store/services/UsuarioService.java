package ec.com.store.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ec.com.store.models.Usuario;
import ec.com.store.repositories.UsuarioRepository;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;



@Service("userDetailService")
public class UsuarioService implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(UsuarioService.class);

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder bCryptPasswordEncoder;

	public UsuarioService(UsuarioRepository usuarioRepository, 
			PasswordEncoder bCryptPasswordEncoder
			) {
		this.usuarioRepository = usuarioRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findOneByEmailIgnoreCase(email).get();
		if (usuario == null) {
			throw new UsernameNotFoundException(email);
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
//		usuario.getRoles().forEach(role -> {
//			authorities.add(new SimpleGrantedAuthority(role.getNombre()));
//		});
		
		authorities.add(new SimpleGrantedAuthority(usuario.getRol().getNombre()));

		// PasswordEncoder encoder =
		// PasswordEncoderFactories.createDelegatingPasswordEncoder();
		// UserDetails userDetails = new User(usuario.getEmail(),
		// encoder.encode(usuario.getPassword()), authorities);

		UserDetails userDetails = new User(usuario.getEmail(), usuario.getPassword(), authorities);
		return userDetails;
	}

	public Usuario registrar(Usuario usuario) {
		if (!usuarioRepository.findOneByEmailIgnoreCase(usuario.getEmail()).isPresent()) {
			usuario.setFecha(new Date());
//			usuario.setUsuarioCreacion(1);
//			usuario.setEstado(true);
//			usuario.setActivated(false);
//			usuario.setInfoCompleta(false);
//			usuario.setBloqueado(false);
//			usuario.setIntentos(0);
//			;
			usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
//			usuario.setActivationKey(RandomUtil.generateActivationKey());
//			mailService.sendActivationEmail(usuario);
			return usuarioRepository.save(usuario);
		}
		return null;
	}
	
	public Usuario registrarCliente(Usuario usuario) {
		usuario.setFecha(new Date());
		usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
		return usuarioRepository.save(usuario);
	}

	public Usuario modificar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	

	public List<Usuario> listar(){
		List<Usuario> usuario = new ArrayList<>();
		usuario = usuarioRepository.findAll();		
		return usuario;
	}
	
	private Sort sortByIdAsc() {
        return new Sort(Sort.Direction.ASC, "id");
    }


	public Usuario obtenerUsuario(String username) {
		Usuario user = usuarioRepository.findOneByEmailIgnoreCase(username).get();
		/*user.getRol().getAccesos().forEach(acceso -> {
			System.out.println(acceso.getDescripcion());
		});*/
		return user;
	}
	
	
	public Usuario obtenerPorCedula(String cedula) {
		Optional<Usuario> usuario = usuarioRepository.findByCedula(cedula);
		if(usuario.isPresent()) {
			return usuario.get();
		}
		return null;
	}
	
	
	public Usuario guardar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public Usuario actualizar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public void eliminar(Integer usuario) {
		usuarioRepository.deleteById(usuario);
	}
	
	
	
	public byte[] imprimir() {
		byte[] data = null;

		HashMap<String, Object> map = new HashMap<>();
		map.put("FECHA_INGRESO", new Date());
		map.put("CARGO", "Todos");
		try {
			File file = new ClassPathResource("/reports/listarPersonas.jasper").getFile();
			JasperPrint print = JasperFillManager.fillReport(file.getPath(), map,
					new JRBeanCollectionDataSource(listar()));
			data = JasperExportManager.exportReportToPdf(print);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

}
