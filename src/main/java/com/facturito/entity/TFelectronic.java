package com.facturito.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_felectronic")
public class TFelectronic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_felectronic", nullable = false)
    private Long id;

    @Column(name = "file", length = 5000)
    private String file;

    @Column(name = "credentials", length = 500)
    private String credentials;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private TCliente idCliente;

    @Column(name = "m_current", length = 1)
    private String mCurrent;

    @Column(name = "m_user", length = 100)
    private String mUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public TCliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(TCliente idCliente) {
        this.idCliente = idCliente;
    }

    public String getMCurrent() {
        return mCurrent;
    }

    public void setMCurrent(String mCurrent) {
        this.mCurrent = mCurrent;
    }

    public String getMUser() {
        return mUser;
    }

    public void setMUser(String mUser) {
        this.mUser = mUser;
    }

}