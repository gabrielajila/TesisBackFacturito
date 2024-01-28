package com.facturito.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

import com.facturito.entity.TAdquiriente;
import com.facturito.entity.TFactura;

public class CrearFormatoCorreo {

	public String generarCorreoHTML(TFactura factura) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		TAdquiriente adquiriente = factura.getIdAdquiriente();
		String fechaFormateada = sdf.format(factura.getFechaEmision());
		String nombre = adquiriente.getRazonSocial();
		String numFactura = factura.getPuntoVenta().getEstablecimiento().getEstablecimiento() + "-"
				+ factura.getPuntoVenta().getPuntoEmision() + "-" + factura.getNumComprobante();
		String fechaEmision = fechaFormateada;
		BigDecimal valorFactura = factura.getTotal().setScale(2, RoundingMode.HALF_UP);
		String emailEnvio = "gabyajila4@gmail.com";
		String telefono = "+593 96 325 2314";
		String html = "<!DOCTYPE html>\r\n"
				+ "<html lang=\"es\">\r\n"
				+ "<head>\r\n"
				+ "    <meta charset=\"UTF-8\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "    <title>Envío de Factura</title>\r\n"
				+ "    <style>\r\n"
				+ "        body {\r\n"
				+ "            font-family: Arial, sans-serif;\r\n"
				+ "            margin: 20px;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        #container {\r\n"
				+ "            text-align: center;\r\n"
				+ "            border: 1px solid #ddd;\r\n"
				+ "            padding: 20px;\r\n"
				+ "            max-width: 600px;\r\n"
				+ "            margin: auto;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        #invoiceInfo {\r\n"
				+ "            text-align: left;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        img {\r\n"
				+ "            max-width: 100%;\r\n"
				+ "            height: auto;\r\n"
				+ "        }\r\n"
				+ "    </style>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "\r\n"
				+ "    <div id=\"container\">\r\n"
				+ "        <img src="+imagen+" alt=\"Logo de la empresa\">\r\n"
				+ "        <h2>Envío de Factura</h2>\r\n"
				+ "\r\n"
				+ "        <p>Estimado cliente: "+nombre+"</p>\r\n"
				+ "        <p> Adjunto su factura "+numFactura+" emitida el día "+fechaEmision+", que se encuentra lista para ser visualizada y descargada.</p>\r\n"
				+ "        <p>A continuacion, le informamos que el detalle de su transacción es:</p>\r\n"
				+ "       \r\n"
				+ "\r\n"
				+ "        <div id=\"invoiceInfo\">\r\n"
				+ "            <p><strong>Fecha de Emisión:</strong> "+fechaEmision+"</p>\r\n"
				+ "            <p><strong>Número de Comprobante:</strong> "+numFactura+"</p>\r\n"
				+ "            <p><strong>Valor Total a Pagar:</strong> $"+valorFactura+"</p>\r\n"
				+ "        </div>\r\n"
				+ "    </div>\r\n"
				+ "\r\n"
				+ "</body>\r\n"
				+ "</html>\r\n"
				+ "";
		return html;

	}

//	private String imagenWS = "https://i.ibb.co/QPk8WR5/whatsapp.png";
//	private String imagenLK = "https://i.ibb.co/BtgTDW4/linkedin.png";
//	private String imagenFB = "https://i.ibb.co/8jj0B4d/facebook-2.png";
//	private String imagenUrl = "https://i.ibb.co/WscFkbD/TWS-White.png";
	private String imagen = "https://i.ibb.co/K98jf2H/Facorto.png";
}
