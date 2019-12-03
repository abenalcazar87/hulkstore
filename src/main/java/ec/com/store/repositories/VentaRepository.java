package ec.com.store.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.com.store.models.Venta;

@Repository
public interface VentaRepository extends CrudRepository<Venta, Integer>{

	List<Venta> findByUsuarioId(Integer id);
}
