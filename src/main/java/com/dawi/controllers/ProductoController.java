package com.dawi.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dawi.models.Producto;
import com.dawi.services.ProductoServiceImpl;

import net.sf.jasperreports.engine.JRException;

@Controller
public class ProductoController {
	Producto ultimoProducto;
	
	@Autowired
	private ProductoServiceImpl prodService;
	
	@GetMapping("/")
	public String login() {
		return "redirect:/login";
	}
	
	@GetMapping("/registrar")
	public String registro(@ModelAttribute("producto") Producto producto) {
		return "registrarProducto";
	}
	
	@PostMapping("/registrar")
	public String registrarProducto(@ModelAttribute("producto") Producto producto) {
		Date fechaRegistro = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		producto.setFchPrd(sdf.format(fechaRegistro));
		ultimoProducto = prodService.saveProducto(producto);
		return "redirect:/registrar?exito";
	}
	
	@GetMapping(value = "/constancia", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> constanciaProductos(HttpServletRequest request, HttpServletResponse response) throws IOException, JRException {
		try {
			InputStream report = prodService.getReportProductos(ultimoProducto);
			byte[] data = report.readAllBytes();
			report.close();
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_PDF);
			header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"constancia_producto.pdf\"");
			return new ResponseEntity<byte[]>(data,header,HttpStatus.CREATED);
		} catch (IOException ex) {
			throw new RuntimeException("IOError retornando archivo");
		}
	}
}
