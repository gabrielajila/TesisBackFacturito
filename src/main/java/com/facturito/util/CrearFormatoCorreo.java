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
		String emailContfiables = "info@contfiables.com";
		String telefonoContfiables = "+593 96 325 2314";
		String html = "<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "\n" + "<head>\n"
				+ "    <meta charset=\"UTF-8\">\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
				+ "    <title>TWS</title>\n"
				+ "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css\">\n"
				+ "</head>\n" + "\n" + "<body>\n" + "    <style>\n" + "        .container {\n"
				+ "            background-color: #ffffff;\n" + "            max-width: 550px;\n"
				+ "            margin: 0 auto;\n" + "            padding: 2px;\n"
				+ "            border: 2px solid #090909;\n" + "            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);\n"
				+ "        }\n" + "\n" + "        .logo {\n" + "            text-align: center;\n" + "\n"
				+ "        }\n" + "\n" + "        .logo img {\n" + "            max-height: 200px;\n"
				+ "            max-width: 50%;\n" + "        }\n" + "\n" + "        .form-group {\n"
				+ "            margin-bottom: 10px;\n" + "        }\n" + "\n" + "        .con1 {\n"
				+ "            background-color: #b1b1b1;\n" + "        }\n" + "\n" + "        label {\n"
				+ "            display: block;\n" + "            margin-bottom: 5px;\n"
				+ "            color: #494949b4;\n" + "            font-family: Arial, sans-serif;\n"
				+ "            font-size: 12px;\n" + "\n" + "        }\n" + "    </style>\n" + "    </head>\n" + "\n"
				+ "    <body style=\"width: 550px;\" >\n" + "        <div class=\"container\">\n"
				+ "            <div class=\"logo\" style=\"background-color: #313131;\">" + "<img src=\"" + imagenUrl
				+ "\" width=\"550px\" alt=\"Logo\">\n" + "            </div>\n" + "            <form>\n"
				+ "                <div class=\"form-group\">\n"
				+ "                    <h3 style=\" color: #494949b4;\">Estimado(a) " + nombre.toUpperCase() + "</h3>\n"
				+ "                    <label for=\"texto\" class=\"lh-sm\">Adjunto su factura " + numFactura
				+ " emitida el día " + fechaEmision.substring(0, 10)
				+ ", que se encuentra lista para ser visualizada y descargada.\n" + "<br>\n"
				+ "                        <p class=\"lh-sm\">A continuacion, le informamos que el detalle de su transacción es:</p>\n"
				+ "                        <br>\n" + "                        <p class=\"text-break\">\n"
				+ "                            Fecha de la Factura: " + fechaEmision + "<br>\n"
				+ "                            Número de la Factura: " + numFactura + "<br>\n"
				+ "                            Valor total para Cancelar: " + valorFactura + "\n"
				+ "                        </p>\n" + "                        <br>\n"
				+ "                        <p class=\"lh-sm\">Este correo ha sido enviado de forma automática, para cualquier consulta puede\n"
				+ "                            comunicarse con nosotros</p>\n" + "<br>\n"
				+ "                        <p class=\"text-break\">Teléfono: " + telefonoContfiables + "<br>\n"
				+ "                            Correo: <a href=\"#\" class=\"text-reset\">" + emailContfiables
				+ "</a>\n" + "                        </p>\n" + "                        <br>\n"
				+ "                    </label>\n" + "</div>\n" + "</form>\n"
				+ "            <div class=\"container\" style=\"background-color: #e9e0e0; \">\n"
				+ "                <div style=\"display: flex; align-items: center;background-color: #e9e0e0;\">\n"
				+ "                    <p style=\" color: #494949b4; font-size: 12px; font-family: Arial, sans-serif;  text-align: center;\">\n"
				+ "                        Documento electrónico emitido desde: </p>\n" + "\n"
				+ "                    <img src=\"" + imagen + "\" alt=\"logo\" width=\"100\" height=\"90\"\n"
				+ "                        style=\" max-width: 100px;margin-right: 10px;\">\n"
				+ "                </div>\n" + "\n"
				+ "                <div style=\"display: flex; max-width: 550px; align-items: center;\">\n"
				+ "                    <p style=\" color: #000000; font-size: 14px; font-family: Arial, sans-serif;  text-align: center;\">\n"
				+ "                        Siguenos en nuestras redes sociales:</p>\n"
				+ "                    <a style=\" margin-right: 5px; margin-left: 5px;\" href=\"https://www.facebook.com/TWS2.io\">\n"
				+ "                        <img src=\"" + imagenFB
				+ "\" alt=\"Icono\" style=\"width: 16px; height: 16px;\">\n" + "                    </a>\n"
				+ "                    <a style=\" margin-right: 5px; margin-left: 5px;\" href=\"https://wa.me/+593963252314\">\n"
				+ "                        <img src=\"" + imagenWS
				+ "\" alt=\"Icono\" style=\"width: 16px; height: 16px;\">\n" + "                    </a>\n"
				+ "                    <a style=\" margin-right: 5px; margin-left: 5px;\" href=\"https://www.linkedin.com/company/tws%C2%B2/\">\n"
				+ "                        <img src=\"" + imagenLK
				+ "\" alt=\"Icono\" style=\"width: 16px; height: 16px;\">\n" + "                    </a>\n"
				+ "                </div>\n" + "</div>\n" + "        </div>\n" + "    </body>\n" + "</html>";
		return html;

	}

	private String imagenWS = "https://i.ibb.co/QPk8WR5/whatsapp.png";
	private String imagenLK = "https://i.ibb.co/BtgTDW4/linkedin.png";
	private String imagenFB = "https://i.ibb.co/8jj0B4d/facebook-2.png";
	private String imagenUrl = "https://i.ibb.co/WscFkbD/TWS-White.png";
	private String imagen = "https://i.ibb.co/0smwGmF/cbimage.png";
}
