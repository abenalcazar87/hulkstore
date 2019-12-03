package ec.com.store.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.com.store.models.Producto;
import ec.com.store.models.Usuario;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Integer>{

	//Producto findByIdProducto(Integer id);
	Optional<Producto> findById(Integer id);
	Producto findByNombre(String nombre);
}
