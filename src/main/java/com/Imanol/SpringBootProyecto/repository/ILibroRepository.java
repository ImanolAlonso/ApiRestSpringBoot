package com.Imanol.SpringBootProyecto.repository;

import com.Imanol.SpringBootProyecto.model.LibroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILibroRepository extends JpaRepository<LibroModel,Long> {
    List<LibroModel> findAllByOrderByNombre();
    List<LibroModel> findAllByOrderByStock();
}
