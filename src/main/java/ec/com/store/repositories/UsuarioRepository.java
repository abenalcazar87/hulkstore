package ec.com.store.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.com.store.models.Usuario;


@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{

	Optional<Usuario> findByEmail(String email);
	
	//Usuario findByIdUsuario(Integer id);
	//Usuario findById(Integer id);
	
	Optional<Usuario> findById(Integer id);
	
	Optional<Usuario> findOneByEmailIgnoreCase(String correo);
	
	Optional<Usuario> findByCedula(String cedula);
	
	List<Usuario> findAll();
}
