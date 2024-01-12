package com.facturito.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.facturito.util.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_cliente")
public class TCliente implements UserDetails {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente", nullable = false)
	private Long idCliente;
	@Column(name = "identificacion")
	private String identification;

	@Column(name = "nombres")
	private String nombres;

	@Column(name = "apellidos")
	private String apellidos;

	@Column(name = "password")
	private String password;

	@Column(name = "ciudad")
	private String ciudad;

	private String telefono;

	@ManyToOne
	@JoinColumn(name = "id_tipo_identificacion")
	private TTipoIdentificacion idTipoIdentificacion;

	@Column(name = "email_cliente")
	private String emailCliente;

	@Column(name = "cuser")
	private String user;

	@Column(name = "archivo_firma")
	private String archivoFirma;

	@Column(name = "clave_firma")
	private String claveFirma;

	@Column(name = "logo")
	private String logo;

	@Column(name = "fecha_registro")
	private Date fechaRegistro;

	@Enumerated(EnumType.STRING)
	private Role role;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "id_version_contfiables") private TVersionContfiables
	 * versionContfiables;
	 */

	public TCliente() {
		super();
	}

	public TCliente(Long idCliente) {
		super();
		this.idCliente = idCliente;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
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



	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public TTipoIdentificacion getIdTipoIdentificacion() {
		return idTipoIdentificacion;
	}

	public void setIdTipoIdentificacion(TTipoIdentificacion idTipoIdentificacion) {
		this.idTipoIdentificacion = idTipoIdentificacion;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getArchivoFirma() {
		return archivoFirma;
	}

	public void setArchivoFirma(String archivoFirma) {
		this.archivoFirma = archivoFirma;
	}

	public String getClaveFirma() {
		return claveFirma;
	}

	public void setClaveFirma(String claveFirma) {
		this.claveFirma = claveFirma;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = role.getPermissions().stream()
				.map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name())).collect(Collectors.toList());
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
		return authorities;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return user;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
