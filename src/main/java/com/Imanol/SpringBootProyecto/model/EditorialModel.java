package com.Imanol.SpringBootProyecto.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "editoriales")
public class EditorialModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre_editorial;
    @OneToMany(mappedBy = "editorial", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LibroModel> libros;
    public EditorialModel() {
    }
    public EditorialModel(Long id, String nombre_editorial) {
        this.id = id;
        this.nombre_editorial = nombre_editorial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEditorial() {
        return nombre_editorial;
    }

    public void setNombreEditorial(String nombre_editorial) {
        this.nombre_editorial = nombre_editorial;
    }

    public List<LibroModel> getLibros() {
        return libros;
    }

    public void setLibros(List<LibroModel> libros) {
        this.libros = libros;
    }
}
