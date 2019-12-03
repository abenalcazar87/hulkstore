package ec.com.store.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.com.store.models.DetalleVenta;

@Repository
public interface DetalleVentaRepository extends CrudRepository<DetalleVenta, Integer>{

}
