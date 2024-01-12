package com.facturito.util;


import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;

public class EnvioCorreo {
	Properties props = System.getProperties();
	
	Seguridad seguridad;

	private InternetAddress[] direccionTo;
	private InternetAddress[] direccionTo1;
	
	@Value("${property.email.server}")
	String server;
	@Value("${property.email.trasport}")
	String trasport;
	@Value("${property.email.port}")
	String port;
	@Value("${property.email.usuario}")
	String usuario;
	@Value("${property.email.clave}")
	String clave;
	
	
	
	 
	String correoDesde = "info@contfiables.com"; //desde ese correo  se envia las facturas a otros correos

	public EnvioCorreo() throws Exception {
		props.put("mail.smtp.host", server);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.user", seguridad.decrypt(usuario));
		props.put("mail.smtp.auth", "true");
	}

	public boolean fnConectaCorreo() throws Exception {
		// System.out.println("llego a conecta correo!!!!!!!!!!");
		boolean ban = false;
		javax.mail.Session sesion = javax.mail.Session.getDefaultInstance(props);
		sesion.setDebug(true);
		Transport t;
		try {
			t = sesion.getTransport(trasport);
			t.connect(server, seguridad.decrypt(usuario), seguridad.decrypt(clave));
			ban = true;
			// flMensaje("Conexion Exitosa de Correo!!!");
			// System.out.println("si conecto el mail!!!!");
			t.close();
		} catch (NoSuchProviderException ex) {
			ban = false;
			// System.out.println("error no se conecto el mail!!!!");
			System.out.println("Error Provider: al enviar Correo: " + ex.getMessage());
			// Logger.getLogger(clFLFunciones.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MessagingException ex) {
			ban = false;
			// System.out.println("error no se conecto el mail!!!!!");
			System.out.println("Error Mensaje: al enviar Correo: " + ex.getMessage());
			// Logger.getLogger(clFLFunciones.class.getName()).log(Level.SEVERE, null, ex);
		}
		return ban;
	}

	public void fnNotificar(String sCorreo, String sCC, String sTitulo, String sCuerpo, String sAdjunto)
			throws Exception {
		if (fnConectaCorreo()) {// SI ES QUE SE CONECTA AL SERVIDOR DE CORREO

			javax.mail.Session sesion = javax.mail.Session.getDefaultInstance(props);
			sesion.setDebug(true);
			try {
				// setTo obtiene correos
				setTo(sCC);
				setTo1(sCorreo);
				// Se crea un mensaje vacío
				// Message mensaje = new MimeMessage(sesion);
				MimeMessage mensaje = new MimeMessage(sesion);
				// Se rellenan los atributos y el contenido
				// Asunto
				mensaje.setSubject(sTitulo);
				// Emisor del mensaje
				mensaje.setFrom(new InternetAddress(correoDesde));
				// Receptor del mensaje
				mensaje.setRecipients(Message.RecipientType.TO, direccionTo1);
				// Los correo con copia
				String correoCC = "";
				if (sCC.length() == 0) {
					correoCC = correoDesde;
				} else {
					correoCC = sCC;
				} // controlamos que si no existe mail copia entonces usamos el por defecto
//                mensaje.addRecipients(Message.RecipientType.BCC, new InternetAddress[]{new InternetAddress(correoCC)});
				mensaje.addRecipients(Message.RecipientType.BCC, direccionTo);
				// Cuerpo del mensaje y archivo adjunto
				// Se compone la parte del texto
				BodyPart texto = new MimeBodyPart();
				texto.setContent(sCuerpo, "text/html"); 

				// Se compone el adjunto con la imagen
				BodyPart adjunto = new MimeBodyPart();
				adjunto.setDataHandler(new DataHandler(new FileDataSource(sAdjunto)));
				String nombre = "";
				nombre = sAdjunto.substring(sAdjunto.lastIndexOf('/') + 1, sAdjunto.lastIndexOf('f'));
				adjunto.setFileName(nombre);
				// Una MultiParte para agrupar texto e imagen.
				MimeMultipart multiParte = new MimeMultipart();
				multiParte.addBodyPart(texto);
				multiParte.addBodyPart(adjunto);
				mensaje.setContent(multiParte);

				// enviamos el mensaje
				Transport t = sesion.getTransport(trasport);
				t.connect(server, seguridad.decrypt(usuario), seguridad.decrypt(clave));
				t.sendMessage(mensaje, mensaje.getAllRecipients());
				t.close();
				System.out.println("Se ha enviado una notificacion al Correo Electronico");
			} catch (MessagingException e) {
				System.out.println("Error Cuerpo: al enviar Correo: " + e.getMessage());
				// //System.out.println("Error: " + e.getLocalizedMessage()+" -
				// "+p.getValorPropiedad("smtp")+" - "+p.getValorPropiedad("sUsuarioCorreo")+" -
				// "+flDesEncripta(p.getValorPropiedad("sClaveCorreo")));
				// e.printStackTrace();
			}
		}
	}

	public void fnNotificarSA(String sCorreo, String sCC, String sTitulo, String sCuerpo) throws Exception {
		if (fnConectaCorreo()) {// SI ES QUE SE CONECTA AL SERVIDOR DE CORREO

			javax.mail.Session sesion = javax.mail.Session.getDefaultInstance(props);
			sesion.setDebug(true);
			try {
				// setTo obtiene correos
				setTo(sCC);
				setTo1(sCorreo);
				// Se crea un mensaje vacío
				// Message mensaje = new MimeMessage(sesion);
				MimeMessage mensaje = new MimeMessage(sesion);
				// Se rellenan los atributos y el contenido
				// Asunto
				mensaje.setSubject(sTitulo);
				// Emisor del mensaje
				mensaje.setFrom(new InternetAddress(correoDesde));
				// Receptor del mensaje
				mensaje.setRecipients(Message.RecipientType.TO, direccionTo1);
				// Los correo con copia
				String correoCC = "";
				if (sCC.length() == 0) {
					correoCC = correoDesde;
				} else {
					correoCC = sCC;
				} // controlamos que si no existe mail copia entonces usamos el por defecto
//                mensaje.addRecipients(Message.RecipientType.BCC, new InternetAddress[]{new InternetAddress(correoCC)});
				mensaje.addRecipients(Message.RecipientType.BCC, direccionTo);
				// Cuerpo del mensaje y archivo adjunto
				// Se compone la parte del texto
				BodyPart texto = new MimeBodyPart();
				texto.setText(sCuerpo);

				// Una MultiParte para agrupar texto e imagen.
				MimeMultipart multiParte = new MimeMultipart();
				multiParte.addBodyPart(texto);
				mensaje.setContent(multiParte);
				// enviamos el mensaje
				Transport t = sesion.getTransport(trasport);
				t.connect(server, seguridad.decrypt(usuario), seguridad.decrypt(clave));
				t.sendMessage(mensaje, mensaje.getAllRecipients());
				t.close();
				System.out.println("Se ha enviado una notificacion al Correo Electronico");
			} catch (MessagingException e) {
				System.out.println("Error Cuerpo: al enviar Correo: " + e.getMessage());
				// //System.out.println("Error: " + e.getLocalizedMessage()+" -
				// "+p.getValorPropiedad("smtp")+" - "+p.getValorPropiedad("sUsuarioCorreo")+" -
				// "+flDesEncripta(p.getValorPropiedad("sClaveCorreo")));
				// e.printStackTrace();
			}
		}
	}

	public void fnNotificarContfiables(String sCorreo,String correoDesd, String sTitulo, String sCuerpo) throws Exception {
		if (fnConectaCorreo()) {// SI ES QUE SE CONECTA AL SERVIDOR DE CORREO

			javax.mail.Session sesion = javax.mail.Session.getDefaultInstance(props);
			sesion.setDebug(true);
			try {
				// setTo obtiene correos
				setTo1(sCorreo);
				// Se crea un mensaje vacío
				// Message mensaje = new MimeMessage(sesion);
				MimeMessage mensaje = new MimeMessage(sesion);
				// Se rellenan los atributos y el contenido
				// Asunto
				mensaje.setSubject(sTitulo);
				// Emisor del mensaje
				mensaje.setFrom(new InternetAddress(correoDesd));
				// Receptor del mensaje
				mensaje.setRecipients(Message.RecipientType.TO, direccionTo1);
				mensaje.addRecipients(Message.RecipientType.BCC, direccionTo);
				// Cuerpo del mensaje y archivo adjunto
				// Se compone la parte del texto
				BodyPart texto = new MimeBodyPart();
				texto.setText(sCuerpo);

				// Una MultiParte para agrupar texto e imagen.
				MimeMultipart multiParte = new MimeMultipart();
				multiParte.addBodyPart(texto);
				mensaje.setContent(multiParte);
				// enviamos el mensaje
				Transport t = sesion.getTransport(trasport);
				t.connect(server, seguridad.decrypt(usuario), seguridad.decrypt(clave));
				t.sendMessage(mensaje, mensaje.getAllRecipients());
				t.close();
				System.out.println("Se ha enviado una notificacion al Correo Electronico");
			} catch (MessagingException e) {
				System.out.println("Error Cuerpo: al enviar Correo: " + e.getMessage());
				// //System.out.println("Error: " + e.getLocalizedMessage()+" -
				// "+p.getValorPropiedad("smtp")+" - "+p.getValorPropiedad("sUsuarioCorreo")+" -
				// "+flDesEncripta(p.getValorPropiedad("sClaveCorreo")));
				// e.printStackTrace();
			}
		}
	}

	public void fnNotificar(String sCorreo, String sTitulo, String sCuerpo, String sAdjunto) throws Exception {
		if (fnConectaCorreo()) {// SI ES QUE SE CONECTA AL SERVIDOR DE CORREO

			javax.mail.Session sesion = javax.mail.Session.getDefaultInstance(props);
			sesion.setDebug(true);
			try {
				// setTo obtiene correos
				setTo1(sCorreo);
				// Se crea un mensaje vacío
				// Message mensaje = new MimeMessage(sesion);
				MimeMessage mensaje = new MimeMessage(sesion);
				// Se rellenan los atributos y el contenido
				// Asunto
				mensaje.setSubject(sTitulo);
				// Emisor del mensaje
				mensaje.setFrom(new InternetAddress(correoDesde));
				// Receptor del mensaje
				mensaje.setRecipients(Message.RecipientType.TO, direccionTo1);
				// Los correo con copia
				// Cuerpo del mensaje y archivo adjunto
				// Se compone la parte del texto
				BodyPart texto = new MimeBodyPart();
				texto.setText(sCuerpo);

				// Se compone el adjunto con la imagen
				BodyPart adjunto = new MimeBodyPart();
				adjunto.setDataHandler(new DataHandler(new FileDataSource(sAdjunto)));
				String nombre = "";
				nombre = sAdjunto.substring(sAdjunto.lastIndexOf('/') + 1);
				adjunto.setFileName(nombre);
				// Una MultiParte para agrupar texto e imagen.
				MimeMultipart multiParte = new MimeMultipart();
				multiParte.addBodyPart(texto);
				multiParte.addBodyPart(adjunto);
				mensaje.setContent(multiParte);

				// enviamos el mensaje
				Transport t = sesion.getTransport(trasport);
				t.connect(server, seguridad.decrypt(usuario),seguridad.decrypt(clave));
				t.sendMessage(mensaje, mensaje.getAllRecipients());
				t.close();
				System.out.println("Se ha enviado una notificacion al Correo Electronico");
			} catch (MessagingException e) {
				System.out.println("Error Cuerpo: al enviar Correo: " + e.getMessage());
				// //System.out.println("Error: " + e.getLocalizedMessage()+" -
				// "+p.getValorPropiedad("smtp")+" - "+p.getValorPropiedad("sUsuarioCorreo")+" -
				// "+flDesEncripta(p.getValorPropiedad("sClaveCorreo")));
				// e.printStackTrace();
			}
		}
	}

	public void setTo(String mails) {
		String[] tmp = mails.split(",");
		direccionTo = new InternetAddress[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			try {
				direccionTo[i] = new InternetAddress(tmp[i]);
			} catch (AddressException ex) {
				// System.out.println(ex);
			}
		}
	}

	public void setTo1(String mails) {
		if (mails != null) {
			String[] tmp = mails.split(",");
			direccionTo1 = new InternetAddress[tmp.length];
			for (int i = 0; i < tmp.length; i++) {
				try {
					direccionTo1[i] = new InternetAddress(tmp[i]);
				} catch (AddressException ex) {
					// System.out.println(ex);
				}
			}
		}
	}
}
