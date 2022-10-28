package com.sistemacitas.core.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemacitas.core.entity.Cita;

public interface CitaRepo extends JpaRepository<Cita, Long>{

	List<Cita>findByFechaLlegada(Date fechaLlegada);
}
