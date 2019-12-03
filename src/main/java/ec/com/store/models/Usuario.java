package ec.com.store.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "email", nullable = false, length = 70)
	private String email;
	
	@Size(min=10, max=50, message="Usernom debe tener minimo 3 caracteres y maximo 50")
	@Column(name = "usernom", length = 50)
	private String usernom;

	@Size(min=3, max=150, message="Nombre debe tener minimo 3 caracteres y maximo 150")
	@Column(name = "nombres", length = 150)
	private String nombres;

	@Size(min=3, max=150, message="Apellidos debe tener minimo 3 caracteres y maximo 150")
	@Column(name = "apellidos", length = 150)
	private String apellidos;

	@Size(min=3, max=150, message="Password debe tener minimo 3 caracteres y maximo 150")
	@Column(name = "password", length = 50)
	private String password;

	private String telefono;

	private String direccion;
	
	private String cedula;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date fecha;

	@ManyToOne
	@JoinColumn(name = "rol_id")
	private Rol rol;

	public Usuario() {
	}
	
	public Usuario(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

}
