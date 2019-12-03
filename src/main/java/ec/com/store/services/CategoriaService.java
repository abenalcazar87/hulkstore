package ec.com.store.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import ec.com.store.models.Categoria;
import ec.com.store.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

//	private static Logger log = LoggerFactory.getLogger(CategoriaService.class);
	
	private CategoriaRepository categoriaRepository;

	public CategoriaService() {
		
	}
	
	@Autowired
	public CategoriaService(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;	}



	/**
	 * Método que obtiene todos los categoria de la bdd
	 * @return
	 */
	public List<Categoria> obtenerCategorias() {
		return (List<Categoria>) categoriaRepository.findAll();
	}

	/**
	 * Método que permite crear un Categoria en la bdd
	 * @param categoria
	 * @return
	 */
	public Categoria crearCategoria(Categoria categoria) {
		Categoria categoriaExiste = categoriaRepository.findByCodigo(categoria.getCodigo());
		if (categoriaExiste == null) {
			return categoriaRepository.save(categoria);
		}
		return null;
	}

}
