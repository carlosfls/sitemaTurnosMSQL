package com.sistemacitas.core.controller;

import com.sistemacitas.core.models.Cita;
import com.sistemacitas.core.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("citas")
@RequiredArgsConstructor
public class CitaController {

	private final CitaService citaService;
	
	@GetMapping("/")
	public String getCitasHome(@ModelAttribute("cita")Cita cita,Model modelo) {
		List<Cita>listadoCitas=new ArrayList<>();
		listadoCitas.addAll(citaService.getAllCitas());
		modelo.addAttribute("listadoCitas", listadoCitas);
		
		return "citasHome";
	}
	
	@PostMapping("/")
	public String getCitasPorFecha(@ModelAttribute("cita")Cita cita,Model modelo) {
		List<Cita> listadoCitas = new ArrayList<>(citaService.getAllCitasByFecha(cita.getFechaLlegada()));
		modelo.addAttribute("listadoCitas", listadoCitas);
		return "citasHome";
	}
	
	@GetMapping("nuevo")
	public String getCitasForm(@ModelAttribute("cita")Cita cita) {
		return "citasForm";
	}
	
	@PostMapping("nuevo")
	public String saveCita(@ModelAttribute("cita")Cita cita) {
		citaService.createCita(cita);
		return "redirect:/citas/";
	}

	@GetMapping("update/{id}")
	public String getCitasFormUpdate(@PathVariable("id") Long id,Model modelo) {
		Cita cBusq = citaService.getCitaById(id);
		modelo.addAttribute(cBusq);
		return "citasUpdateForm";
	}

	@PostMapping("update/{id}")
	public String updateCita(@PathVariable Long id,@ModelAttribute("cita")Cita cita) {
		citaService.updateCita(id,cita);
		return "redirect:/citas/";
	}
	
	@GetMapping("eliminar/{id}")
	public String deleteCitaPorId(@PathVariable("id") Long id) {
		citaService.deleteCitaPorId(id);
		return "redirect:/citas/";
	}
	
	@GetMapping("exportar/all")
	public ResponseEntity<InputStreamResource> exportAllData() throws Exception{
		ByteArrayInputStream stream = citaService.exportAllData();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=citas.xls");
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
	}
	
}
