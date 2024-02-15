package com.Imanol.SpringBootProyecto.controllers;

import com.Imanol.SpringBootProyecto.model.EditorialModel;
import com.Imanol.SpringBootProyecto.service.EditorialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/libro")
public class EditorialController {

    @Autowired
    private EditorialService editorialService;
    @Operation(summary = "Obtiene todos los libros por editorial", description = "Obtiene una lista de libros agrupados por editorial", tags = {"editoriales"})
    @ApiResponse(responseCode = "200", description = "Lista de libros por editorial")
    @GetMapping("/librosPorEditorial")
    public ResponseEntity<List<EditorialModel>> getLibrosPorEditorial() {
        List<EditorialModel> librosPorEditorial = editorialService.getLibrosPorEditorial();
        return new ResponseEntity<>(librosPorEditorial, HttpStatus.OK);
    }

}