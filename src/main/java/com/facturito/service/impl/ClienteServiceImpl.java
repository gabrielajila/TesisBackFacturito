package com.facturito.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import java.awt.image.BufferedImage;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.facturito.DAO.TClienteDAO;
import com.facturito.DAO.TEstablecimientoDAO;
import com.facturito.DAO.TSecuenciaFacturaDAO;
import com.facturito.DAO.TSecuenciaProductoDAO;
import com.facturito.DAO.TfelectronicDAO;
import com.facturito.controller.FirmaElectronicaController;
import com.facturito.entity.TCliente;
import com.facturito.entity.TEstablecimiento;
import com.facturito.model.FileSignature;
import com.facturito.model.RespSimple;
import com.facturito.service.ClienteService;
import com.facturito.service.FirmaElectronicaService;
import com.facturito.service.PuntoVentaService;
import com.facturito.util.FacturitoEnum;
import com.facturito.util.Role;
import com.facturito.util.Seguridad;
import com.facturito.entity.TFelectronic;
import com.facturito.entity.TPuntoVenta;
import com.facturito.entity.TSecuenciaFactura;
import com.facturito.entity.TSecuenciaProducto;
import com.facturito.exception.CustomException;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private TClienteDAO clienteDAO;

	@Autowired
	FirmaElectronicaController firmaElectronicaController;

	@Autowired
	private FirmaElectronicaService firmaElectronicaService;

	@Autowired
	private TfelectronicDAO tfelectronicDAO;

	@Autowired
	private TSecuenciaFacturaDAO tSecuenciaFacturaDAO;

	@Autowired
	private TSecuenciaProductoDAO tSecuenciaProductoDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TEstablecimientoDAO establecimientoDAO;

	@Autowired
	private PuntoVentaService puntoVentaService;

	@Autowired
	private Seguridad seguridad;

	@Value("${path.pc}")
	private String path;

	private Logger log = Logger.getLogger(ClienteServiceImpl.class.getName());

	@Override
	public RespSimple exiteUsuario(TCliente usuario) {
		RespSimple respuesta = new RespSimple();
		try {
			TCliente c = clienteDAO.getCliente(usuario.getUser());
			if (c != null) {
				respuesta.setError(FacturitoEnum.TRANSACCION_OK.getId());
			} else {
				respuesta.setError(FacturitoEnum.TRANSACCION_ERROR.getId());
			}
			return respuesta;
		} catch (Exception e) {
			log.log(Level.SEVERE, "ERROR AL EXISTE USUARIO ", e);
			respuesta.setError(FacturitoEnum.TRANSACCION_ERROR.getId());
			return respuesta;
		}
	}

	@Override
	public String getIdentificacionCliente(String usuario) {
		return clienteDAO.getIdentificacionCliente(usuario);
	}

	@Override
	public Long getIdCliente(String usuario) {
		return clienteDAO.getIdCliente(usuario);
	}

	@Override
	public TCliente getCliente(String usuario) {
		return clienteDAO.getCliente(usuario);
	}

	@Override
	@Transactional
	public RespSimple almacenarCliente(FileSignature fileSignature) {

		RespSimple respuesta = new RespSimple();
		try {
			ZoneId zonaEcuador = ZoneId.of("America/Guayaquil");
			LocalDateTime fechaHoraEcuador = LocalDateTime.now(zonaEcuador);
			Date fecha = Date.from(fechaHoraEcuador.toInstant(ZoneOffset.UTC));
			boolean existeCliente = clienteDAO.existsByIdentificationOrUser(
					fileSignature.getCliente().getIdentification(), fileSignature.getCliente().getUser());
			if (existeCliente) {
				respuesta.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
				respuesta.setMensaje("Ya existe un registo con esa identificación o usuario.");
				return respuesta;
			}
			RespSimple respuestaSimple = firmaElectronicaService.almacenarArchivo(fileSignature);
			if (respuestaSimple.getError().equals(FacturitoEnum.TRANSACCION_OK.getId())) {
				if (!fileSignature.getLogo64().isEmpty()) {
					crearLogo(fileSignature);
					fileSignature.getCliente().setLogo(fileSignature.getCliente().getIdentification().concat(".png"));
				}
				fileSignature.getCliente().setFechaRegistro(fecha);
				fileSignature.getCliente()
						.setArchivoFirma(fileSignature.getCliente().getIdentification().concat(".p12"));
				fileSignature.getCliente().setClaveFirma(seguridad.encrypt(fileSignature.getPassword()));
				TCliente t_Cliente = new TCliente();
				t_Cliente = fileSignature.getCliente();
				t_Cliente.setPassword(passwordEncoder.encode(t_Cliente.getPassword()));
				t_Cliente.setRole(Role.ADMINISTRATOR);
				t_Cliente = clienteDAO.save(t_Cliente);
				// ======================
				fileSignature.setCliente(t_Cliente);
				TFelectronic tfelectronic = new TFelectronic();
				tfelectronic.setCredentials(fileSignature.getPassword());
				tfelectronic.setIdCliente(fileSignature.getCliente());
				tfelectronic.setMCurrent(FacturitoEnum.MCURRENT.getId());
				tfelectronic.setMUser(FacturitoEnum.USERADMIN.getId());
				tfelectronic.setFile(respuestaSimple.getParametroRespuesta());
				tfelectronicDAO.save(tfelectronic);
				// CREAR ESTABLECIMIENTO PARA EL USUARIO
				TEstablecimiento establecimiento = new TEstablecimiento();
				establecimiento.setActivo(true);
				establecimiento.setCliente(t_Cliente);
				establecimiento.setDireccion("Matriz");
				establecimiento.setEstablecimiento("001");
				establecimiento.setNombre("Matriz");
				TEstablecimiento establecimientoDB = establecimientoDAO.save(establecimiento);
				TPuntoVenta puntoVenta = new TPuntoVenta();
				puntoVenta.setEstablecimiento(establecimientoDB);
				puntoVenta.setActivo(true);
				puntoVenta.setPuntoEmision("100");
				puntoVenta.setNombre("Punto electrónico");
				puntoVenta.setPrincipal(true);
				puntoVentaService.registrarPuntoVenta(puntoVenta);
				TSecuenciaFactura t_SecuenciaFactura = new TSecuenciaFactura();
				t_SecuenciaFactura.setCodigoDocumento("01");
				t_SecuenciaFactura.setEstablecimiento(establecimiento.getEstablecimiento());
				t_SecuenciaFactura.setPuntoEmision(puntoVenta.getPuntoEmision());
				t_SecuenciaFactura.setSecuencial(1);
				t_SecuenciaFactura.setIdCliente(t_Cliente);
				tSecuenciaFacturaDAO.save(t_SecuenciaFactura);
				TSecuenciaProducto tSecuenciaProducto = new TSecuenciaProducto();
				tSecuenciaProducto.setSecuencial(1);
				tSecuenciaProducto.setIdCliente(t_Cliente);
				tSecuenciaProductoDAO.save(tSecuenciaProducto);

				respuesta.setError(FacturitoEnum.TRANSACCION_OK.getId());
				respuesta.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
				respuesta.setMensaje("Registro Exitoso / Puede Iniciar Sesión");
				return respuesta;
			} else {
				respuestaSimple.setError(FacturitoEnum.TRANSACCION_ERROR.getId());
				respuesta.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
				respuesta.setMensaje("Error al registrar el usuario");
				return respuestaSimple;
			}
		} catch (CustomException ce) {
			respuesta.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			respuesta.setMensaje(ce.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return respuesta;
		} catch (Exception e) {
			log.log(Level.SEVERE, "ERROR AL ALMACENAR CLIENTE ", e);
			respuesta.setError(FacturitoEnum.TRANSACCION_ERROR.getId());
			respuesta.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			respuesta.setMensaje("Error al registrar el usuario");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return respuesta;
		}
	}

	@Override
	public RespSimple actualizarCliente(FileSignature fileSignature) {
		RespSimple respuesta = new RespSimple();
		try {
			TCliente t_cliente = clienteDAO.findById(fileSignature.getCliente().getIdCliente()).get();
			if (!fileSignature.getLogo64().isEmpty()) {
				crearLogo(fileSignature);
				fileSignature.getCliente().setLogo(fileSignature.getCliente().getIdentification().concat(".png"));
				t_cliente.setLogo(fileSignature.getCliente().getLogo());
			}

			t_cliente.setIdentification(fileSignature.getCliente().getIdentification());
			t_cliente.setNombres(fileSignature.getCliente().getNombres());
			t_cliente.setApellidos(fileSignature.getCliente().getApellidos());
			t_cliente.setCiudad(fileSignature.getCliente().getCiudad());
			t_cliente.setTelefono(fileSignature.getCliente().getTelefono());
			t_cliente.setEmailCliente(fileSignature.getCliente().getEmailCliente());

			clienteDAO.save(t_cliente);

			respuesta.setError(FacturitoEnum.TRANSACCION_OK.getId());
			if (!fileSignature.getFile64().isEmpty()) {
				RespSimple respuestaSimple = firmaElectronicaService.almacenarArchivo(fileSignature);
				if (respuestaSimple.getError().equals(FacturitoEnum.TRANSACCION_OK.getId())) {
					t_cliente.setArchivoFirma(fileSignature.getCliente().getIdentification().concat(".p12"));
					t_cliente.setClaveFirma(seguridad.encrypt(fileSignature.getPassword()));
					clienteDAO.save(t_cliente);
					TFelectronic tfelectronic = new TFelectronic();
					tfelectronic.setCredentials(fileSignature.getPassword());
					tfelectronic.setIdCliente(fileSignature.getCliente());
					tfelectronic.setMCurrent(FacturitoEnum.MCURRENT.getId());
					tfelectronic.setMUser(FacturitoEnum.USERADMIN.getId());
					tfelectronic.setFile(respuestaSimple.getParametroRespuesta());
					tfelectronicDAO.save(tfelectronic);
					respuesta.setError(FacturitoEnum.TRANSACCION_OK.getId());
				} else {
					return respuestaSimple;
				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, "ERROR AL ACTUALIZAR EL USUARIO", e);
			respuesta.setError(FacturitoEnum.TRANSACCION_ERROR.getId());
		}
		return respuesta;

	}

	@Override
	public String decrypt(String palabra) throws Exception {
		return seguridad.decrypt(palabra);
	}

	@Override
	public String encrypt(String palabra) throws Exception {
		return seguridad.encrypt(palabra);
	}

	@Override
	public String obtenerLogo(String ruc) {
		return imageToBase64(path.concat("").concat(ruc).concat("/").concat(ruc.concat(".png")));
	}

	public boolean crearLogo(FileSignature fileSignature) {
		try {
			int width = 335; // Ancho deseado de la imagen
			int height = 168; // Alto deseado de la imagen
			byte[] data = DatatypeConverter.parseBase64Binary(fileSignature.getLogo64());
			File file = new File(path.concat("").concat(fileSignature.getCliente().getIdentification()).concat("/")
					.concat(fileSignature.getCliente().getIdentification().concat(".png")));
			try {

				ByteArrayInputStream inStreambj = new ByteArrayInputStream(data);
				BufferedImage newImage = ImageIO.read(inStreambj);
				BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				resizedImage.getGraphics().drawImage(newImage, 0, 0, width, height, null);

				ImageIO.write(resizedImage, "png", file);
			} catch (Exception e) {
				log.log(Level.SEVERE, "ERROR AL CREAR LOGO ", e);
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "ERROR AL CREAR LOGO ", e);
		}
		return true;
	}

	public String imageToBase64(String imagePath) {

		File file = new File(imagePath);
		try (FileInputStream imageInFile = new FileInputStream(file)) {
			byte[] imageData = new byte[(int) file.length()];
			imageInFile.read(imageData);
			return Base64.encodeBase64String(imageData);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Integer obtenerSecuenciaProducto(Long id_cliente) {
		TCliente tCliente = clienteDAO.findById(id_cliente).get();
		return tSecuenciaProductoDAO.findByIdCliente(tCliente).getSecuencial();
	}

	// =====DESACTIVAR USUARIO DE KEYCLOACK
	@Override
	public ResponseEntity<RespSimple> eliminarUsuario(String usuario) {
		RespSimple respuesta = new RespSimple();
		try {
			TCliente cliente = clienteDAO.getCliente(usuario);
			Path certificado = Paths.get(path + cliente.getIdentification() + "/" + cliente.getArchivoFirma());
			Files.delete(certificado);
			cliente.setClaveFirma("");
			clienteDAO.save(cliente);
			respuesta.setCodigo(FacturitoEnum.TRANSACCION_OK.getId());
			respuesta.setMensaje("Eliminado");
			return new ResponseEntity<RespSimple>(respuesta, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.setMensaje("Ocurrió un error al intentar eliminar su cuenta.");
			respuesta.setCodigo(FacturitoEnum.TRANSACCION_ERROR.getId());
			return new ResponseEntity<RespSimple>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
