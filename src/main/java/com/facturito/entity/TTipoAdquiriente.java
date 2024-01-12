package com.facturito.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_tipo_adquiriente")
public class TTipoAdquiriente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tipo_adquiriente", nullable = false)
	private Long id;

	@Column(name = "tipo", length = 200)
	private String tipo;

	@Column(name = "m_current", length = 2)
	private String mCurrent;

	@Convert(disableConversion = true)
	@Column(name = "m_date")
	private Instant mDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getmCurrent() {
		return mCurrent;
	}

	public void setmCurrent(String mCurrent) {
		this.mCurrent = mCurrent;
	}

	
	public Instant getmDate() {
		return mDate;
	}

	public void setmDate(Instant mDate) {
		this.mDate = mDate;
	}

}