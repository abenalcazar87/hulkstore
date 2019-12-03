package ec.com.store.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ec.com.store.models.Acceso;
import ec.com.store.repositories.AccesoRepository;


@Service
public class AccesoService {

	private final AccesoRepository accesoRepository;

	public AccesoService(AccesoRepository accesoRepository) {
		this.accesoRepository = accesoRepository;
	}
	
	public Acceso registrar(Acceso acceso) {
		return accesoRepository.save(acceso);
	}
	
	public Acceso modificar(Acceso acceso) {
		return accesoRepository.save(acceso);
	}
	
	public List<Acceso> listar() {
		return (List<Acceso>) accesoRepository.findAll();
	}
}
