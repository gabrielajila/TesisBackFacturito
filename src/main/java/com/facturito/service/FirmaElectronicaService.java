package com.facturito.service;

import com.facturito.model.FileSignature;
import com.facturito.model.RespSimple;

public interface FirmaElectronicaService {

	public RespSimple almacenarArchivo(FileSignature fileSignature);
}
