package ec.com.store.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.com.store.models.Categoria;


@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, String>{

	Categoria findByCodigo(String codigo);
}
