package com.sistemacitas.core.repo;

import com.sistemacitas.core.models.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CitaRepo extends JpaRepository<Cita, Long>{

	List<Cita>findByFechaLlegada(Date fechaLlegada);
}
