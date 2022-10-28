package com.sistemacitas.core.service;

import com.sistemacitas.core.entity.Cita;
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
public class CitaService implements CitasServiceApi{

	private final CitaRepo citaRepo;

	private final UtilService utilService;
	
	public List<Cita>getAllCitas(){
		return citaRepo.findAll();
	}
	
	public List<Cita>getAllCitasByFecha(Date fecha){
		return citaRepo.findByFechaLlegada(fecha);
	}

	public Cita getCitaById(Long id){
		return citaRepo.findById(id).get();
	}
	
	public void createCita(Cita nuevaCita) {
		citaRepo.save(nuevaCita);
	}

	public void updateCita(Long id,Cita citaAct){
		Optional<Cita> citaBusq = citaRepo.findById(id);
		utilService.copyNonNullProperties(citaAct,citaBusq.get());
		citaRepo.save(citaBusq.get());
	}
	
	public void deleteCitaPorId(Long id) {
		citaRepo.deleteById(id);
	}

	@Override
	public ByteArrayInputStream exportAllData() throws Exception{
		String [] columns = {"NO_CITA","CLIENTE","VEHICULO","FECHA_LLEGADA","OBSERVACIONES"};
		
		Workbook libro = new HSSFWorkbook();//creando el libro de excel
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		Sheet hoja = libro.createSheet("CITAS");//creando la hoja con la primera fila
		Row fila = hoja.createRow(0);
		
		for (int i = 0; i < columns.length; i++) {//insetando registros en la 1ra fila
			Cell registro = fila.createCell(i);
			registro.setCellValue(columns[i]);
		}
		
		List<Cita>citas=this.getAllCitas();
		int initRow=1;
		
		for(Cita ct : citas) {
			fila = hoja.createRow(initRow);//creamos la 2da fila
			//insertamos todos los registros en esa fila
			fila.createCell(0).setCellValue(ct.getId());
			fila.createCell(1).setCellValue(ct.getCliente());
			fila.createCell(2).setCellValue(ct.getVehiculo());
			fila.createCell(3).setCellValue(ct.getFechaLlegada());
			fila.createCell(4).setCellValue(ct.getObservaciones());
			
			initRow++;
		}
		
		//pasar nuestro libro a stream
		libro.write(stream);
		libro.close();
		
		return new ByteArrayInputStream(stream.toByteArray());
	}
}
