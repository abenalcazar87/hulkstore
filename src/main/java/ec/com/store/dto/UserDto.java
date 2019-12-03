package ec.com.store.dto;

import ec.com.store.models.Usuario;

public class UserDto {

	private String nombres;
	private String apellidos;
	private String email;
	private RolDto rol;
	
	public UserDto(Usuario usuario) {
		super();
		this.nombres = usuario.getNombres();
		this.apellidos = usuario.getApellidos();
		this.email = usuario.getEmail();
		this.rol = new RolDto(usuario.getRol());
	}

	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * @param nombres the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the rol
	 */
	public RolDto getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(RolDto rol) {
		this.rol = rol;
	}

}