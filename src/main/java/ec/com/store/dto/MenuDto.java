package ec.com.store.dto;

import java.util.ArrayList;
import java.util.List;

import ec.com.store.models.Acceso;


public class MenuDto {

	private String descripcion;
	private String path;
	private String tipo;
	private String icono;
	private List<MenuDto> submenus;
	
	public MenuDto(Acceso acceso, List<Acceso> accesos) {
		super();
		this.descripcion = acceso.getDescripcion();
		this.path = acceso.getPath();
		this.tipo = acceso.getTipo();
		this.icono = acceso.getIcono();
		submenus = new ArrayList<>();
		
		accesos.forEach(submenu -> {
			if ("SUBMENU".equals(submenu.getTipo()) && submenu.getAcceso().getId() == acceso.getId()) {
				submenus.add(new MenuDto(submenu));
			}
		});
		
//		acceso.getAccesos().forEach((final Acceso submenu)->{
//			if("SUBMENU".equals(submenu.getTipo())) {
//				submenus.add(new MenuDto(submenu));
//			}
//		});
	}
	
	public MenuDto(Acceso acceso) {
		super();
		this.descripcion = acceso.getDescripcion();
		this.path = acceso.getPath();
		this.tipo = acceso.getTipo();
		this.icono = acceso.getIcono();
		submenus = new ArrayList<>();
		acceso.getAccesos().forEach((final Acceso submenu)->{
			if("SUBMENU".equals(submenu.getTipo())) {
				submenus.add(new MenuDto(submenu));
			}
		});
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
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the icono
	 */
	public String getIcono() {
		return icono;
	}

	/**
	 * @param icono the icono to set
	 */
	public void setIcono(String icono) {
		this.icono = icono;
	}

	/**
	 * @return the submenus
	 */
	public List<MenuDto> getSubmenus() {
		return submenus;
	}

	/**
	 * @param submenus the submenus to set
	 */
	public void setSubmenus(List<MenuDto> submenus) {
		this.submenus = submenus;
	}

	
}