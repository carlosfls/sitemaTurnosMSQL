package com.sistemacitas.core.service;

import com.sistemacitas.core.models.Cita;
import com.sistemacitas.core.repo.CitaRepo;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CitaService{

	private final CitaRepo citaRepo;

	private final UtilService utilService;
	
	public List<Cita>getAllCitas(){
		return citaRepo.findAll();
	}
	
	public List<Cita>getAllCitasByFecha(Date fecha){
		return citaRepo.findByFechaLlegada(fecha);
	}

	public Cita getCitaById(Long id) throws Exception {
		Optional<Cita> citaBusq =citaRepo.findById(id);
		if (citaBusq.isPresent()){
		  return citaBusq.get();
		}
		throw new Exception("No existe la cita con id "+id);
	}
	
	public void createCita(Cita nuevaCita) {
		citaRepo.save(nuevaCita);
	}

	public void updateCita(Long id,Cita citaAct) throws Exception {
		Optional<Cita> citaBusq = citaRepo.findById(id);
		if (citaBusq.isPresent()) {
			utilService.copyNonNullProperties(citaAct, citaBusq.get());
			citaRepo.save(citaBusq.get());
		}else {
			throw new Exception("No existe la cita con id "+id);
		}
	}
	
	public void deleteCitaById(Long id) {
		citaRepo.deleteById(id);
	}

	public ByteArrayInputStream exportAllData() throws Exception{
		List<Cita>citas=this.getAllCitas();
		String [] columns = {"ID","CLIENTE","VEHICULO","FECHA_LLEGADA","OBSERVACIONES"};
		Workbook libro = new HSSFWorkbook();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Sheet hoja = libro.createSheet("CITAS");
		hoja.setDefaultColumnWidth(5);
		Row fila = hoja.createRow(0);
		for (int i = 0; i < columns.length; i++) {
			Cell registro = fila.createCell(i);
			registro.setCellValue(columns[i]);
		}
		int initRow=1;
		for(Cita ct : citas) {
			fila = hoja.createRow(initRow);
			fila.createCell(0).setCellValue(ct.getId());
			fila.createCell(1).setCellValue(ct.getCliente());
			fila.createCell(2).setCellValue(ct.getVehiculo());
			fila.createCell(3).setCellValue(ct.getFechaLlegada());
			fila.createCell(4).setCellValue(ct.getObservaciones());
			initRow++;
		}
		libro.write(stream);
		libro.close();
		return new ByteArrayInputStream(stream.toByteArray());
	}
}
