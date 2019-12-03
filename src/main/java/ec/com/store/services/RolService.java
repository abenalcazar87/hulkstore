package ec.com.store.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ec.com.store.models.Rol;
import ec.com.store.repositories.RolRepository;


@Service
public class RolService {

	private RolRepository rolRepository;

	public RolService(RolRepository rolRepository) {
		this.rolRepository = rolRepository;
	}

	public Rol guardar(Rol rol) {
		if (rolRepository.findByNombre(rol.getNombre()) != null) {
			return null;
		}
		return rolRepository.save(rol);
	}

	public List<Rol> listar() {
		return (List<Rol>) rolRepository.findAll();
	}

	public Rol actualizar(Rol rol) {
		return rolRepository.save(rol);
	}

	public void eliminar(Integer id) {
		rolRepository.deleteById(id);
	}

	public Rol obtnerPorId(Integer id) {
		return rolRepository.findById(id).isPresent() 
				? rolRepository.findById(id).get() : null;
	}

}
