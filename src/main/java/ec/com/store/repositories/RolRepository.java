package ec.com.store.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.com.store.models.Rol;


@Repository
public interface RolRepository extends CrudRepository<Rol, Integer>{
	
	Rol findByNombre(String nombre);
	
}
