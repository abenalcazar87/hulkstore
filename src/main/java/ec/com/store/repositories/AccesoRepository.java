package ec.com.store.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.com.store.models.Acceso;

@Repository
public interface AccesoRepository extends CrudRepository<Acceso, Integer>{

//	@Query("select a from Acceso s where ");
//	List<Acceso> findByUser(String usuario);
}
