package ec.com.store.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.com.store.models.Ingreso;

@Repository
public interface IngresoRepository extends CrudRepository<Ingreso, Integer>{

}
