package com.dawi.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawi.models.Producto;
import com.dawi.repositories.ProductoDao;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;

@Service
public class ProductoServiceImpl implements ProductoService {
	@Autowired
	private ProductoDao repository;
	
	@Override
	public Producto saveProducto(Producto producto) {
		return repository.save(producto);
	}

	@Override
	public InputStream getReportProductos(Producto producto) throws JRException {
		try {
			JRBeanCollectionDataSource dts = new JRBeanCollectionDataSource(null);

			System.out.println(producto.getNomPrd());
			System.out.println(producto.getDesPrd());
			System.out.println(producto.getFchPrd());
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("nomPrd", producto.getNomPrd());
			parameters.put("desPrd", producto.getDesPrd());
			parameters.put("fchPrd", producto.getFchPrd());
			
			JasperReport jasperReportObj = getJasperReportCompiled();
			JasperPrint jPrint = JasperFillManager.fillReport(jasperReportObj, parameters, dts);
			InputStream result = new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jPrint));
			return result;
		} catch (JRException e) {
			throw e;
		}
	}

	private JasperReport getJasperReportCompiled() {
		try {
			InputStream studentReporStream = getClass().getResourceAsStream("/jasper/producto_report.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(studentReporStream);
			JRSaver.saveObject(jasperReport, "producto_report.jasper");
			return jasperReport;
		} catch (Exception e) {
			return null;
		}
	}
}
