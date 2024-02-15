package com.Imanol.SpringBootProyecto.service;

import com.Imanol.SpringBootProyecto.model.EditorialModel;
import com.Imanol.SpringBootProyecto.repository.IEditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialService {
    @Autowired
    private IEditorialRepository editorialRepository;

    public List<EditorialModel> getLibrosPorEditorial() {
        List<EditorialModel> editoriales = editorialRepository.findAll();
        return editoriales;
    }

    public Optional<EditorialModel> getEditorialId(Long id){
        return editorialRepository.findById(id);
    }
}
