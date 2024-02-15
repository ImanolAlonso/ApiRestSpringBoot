package com.Imanol.SpringBootProyecto.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "libros")
public class LibroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private Integer stock;
    @Column
    private String imagen;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="imagen_blob", columnDefinition="longblob", nullable=true)
    private byte[] imagen_blob;
    @Column
    private LocalDateTime fecha_insercion = LocalDateTime.now();
    @Column
    private LocalDateTime fecha_modificacion = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "editorial_id")
    @JsonBackReference
    private EditorialModel editorial;
    public LibroModel() {

    }
    public LibroModel(String nombre, Integer stock, EditorialModel editorial) {
        this.nombre = nombre;
        this.stock = stock;
        this.editorial = editorial;
    }
    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", stock=" + stock +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public byte[] getImagen_blob() {
        return imagen_blob;
    }

    public void setImagen_blob(byte[] imagen_blob) {
        this.imagen_blob = imagen_blob;
    }

    public LocalDateTime getFecha_insercion() {
        return fecha_insercion;
    }

    public void setFecha_insercion(LocalDateTime fecha_insercion) {
        this.fecha_insercion = fecha_insercion;
    }

    public LocalDateTime getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(LocalDateTime fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public EditorialModel getEditorial() {
        return editorial;
    }

    public void setEditorial(EditorialModel editorial) {
        this.editorial = editorial;
    }
}