package com.facturito.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_producto")
public class TProducto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto", nullable = false)
	private Long id;

	@Column(name = "codigo", nullable = false, length = 25)
	private String codigo;

	@Column(name = "nombre_producto", length = 300)
	private String nombreProducto;

	@Column(name = "precio_unitario", precision = 10, scale = 3)
	private BigDecimal precioUnitario;

	@Column(name = "informacion_adicional", length = 500)
	private String informacionAdicional;

	@ManyToOne
	@JoinColumn(name = "id_tarifa_iva")
	private TTarifaIva idTarifaIva;

	@ManyToOne
	@JoinColumn(name = "id_codigo_ice")
	private TCodigosIce idCodigoIce;


	/*@ManyToOne
	@JoinColumn(name = "id_marca")
	private TMarca marca;*/

	

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private TCliente cliente;

	@Column(name = "created_at")
	private Date cratedAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "activo")
	private Boolean activo;

	/*@Column(name = "marketplace")
	private Boolean marketplace;

	@Column(name = "stock")
	private BigDecimal stock;

	@Column(name = "costo_compra")
	private BigDecimal costoCompra;

	@Column(name = "inventariable")
	private Boolean inventariable;

	@Column(name = "stock_minimo", columnDefinition = "NUMERIC DEFAULT 0")
	private BigDecimal stockMinimo;

	@Column(name = "stock_maximo", columnDefinition = "NUMERIC DEFAULT 0")
	private BigDecimal stockMaximo;*/

	public TProducto(Long id) {
		super();
		this.id = new Long(id);
	}

	public TProducto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public String getInformacionAdicional() {
		return informacionAdicional;
	}

	public void setInformacionAdicional(String informacionAdicional) {
		this.informacionAdicional = informacionAdicional;
	}

	public TTarifaIva getIdTarifaIva() {
		return idTarifaIva;
	}

	public void setIdTarifaIva(TTarifaIva idTarifaIva) {
		this.idTarifaIva = idTarifaIva;
	}

	public TCodigosIce getIdCodigoIce() {
		return idCodigoIce;
	}

	public void setIdCodigoIce(TCodigosIce idCodigoIce) {
		this.idCodigoIce = idCodigoIce;
	}

	

	/*public TMarca getMarca() {
		return marca;
	}

	public void setMarca(TMarca marca) {
		this.marca = marca;
	}*/

	

	public TCliente getCliente() {
		return cliente;
	}

	public void setCliente(TCliente cliente) {
		this.cliente = cliente;
	}

	public Date getCratedAt() {
		return cratedAt;
	}

	public void setCratedAt(Date cratedAt) {
		this.cratedAt = cratedAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	

	public Boolean isActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Boolean getActivo() {
		return activo;
	}

	/*public Boolean isMarketplace() {
		return marketplace;
	}

	public void setMarketplace(Boolean marketplace) {
		this.marketplace = marketplace;
	}
	public BigDecimal getStock() {
		return stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	public BigDecimal getCostoCompra() {
		return costoCompra;
	}

	public void setCostoCompra(BigDecimal costoCompra) {
		this.costoCompra = costoCompra;
	}

	public Boolean getInventariable() {
		return inventariable;
	}

	public void setInventariable(Boolean inventariable) {
		this.inventariable = inventariable;
	}

	
	public Boolean getMarketplace() {
		return marketplace;
	}

	public BigDecimal getStockMinimo() {
		return stockMinimo;
	}

	public void setStockMinimo(BigDecimal stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public BigDecimal getStockMaximo() {
		return stockMaximo;
	}

	public void setStockMaximo(BigDecimal stockMaximo) {
		this.stockMaximo = stockMaximo;
	}*/

}