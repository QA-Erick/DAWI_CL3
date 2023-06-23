package com.dawi.services;

import java.io.InputStream;
import com.dawi.models.Producto;

import net.sf.jasperreports.engine.JRException;

public interface ProductoService {
	public Producto saveProducto(Producto producto);
	public InputStream getReportProductos(Producto producto) throws JRException;
}
