package ec.com.store.dto;

import java.util.ArrayList;
import java.util.List;

import ec.com.store.models.Acceso;
import ec.com.store.models.Rol;


public class RolDto {

	private String nombre;
	private String descripcion;
	private List<MenuDto> menus;
	
	public RolDto(Rol rol) {
		super();
		this.nombre = rol.getNombre();
		this.descripcion = rol.getDescripcion();
		this.menus = new ArrayList<>();
		rol.getAccesos().forEach((final Acceso acceso)->{
			if("MODULO".equals(acceso.getTipo())) {
				 menus.add(new MenuDto(acceso, rol.getAccesos()));
			}
		});
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombres the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the menus
	 */
	public List<MenuDto> getMenus() {
		return menus;
	}

	/**
	 * @param menus the menus to set
	 */
	public void setMenus(List<MenuDto> menus) {
		this.menus = menus;
	}

}