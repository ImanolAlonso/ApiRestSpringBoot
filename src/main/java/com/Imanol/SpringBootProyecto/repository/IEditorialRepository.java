package com.Imanol.SpringBootProyecto.repository;

import com.Imanol.SpringBootProyecto.model.EditorialModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEditorialRepository extends JpaRepository<EditorialModel,Long> {

}
