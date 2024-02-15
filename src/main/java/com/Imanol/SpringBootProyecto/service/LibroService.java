package com.Imanol.SpringBootProyecto.service;

import com.Imanol.SpringBootProyecto.exceptions.LibroBadRequestException;
import com.Imanol.SpringBootProyecto.model.EditorialModel;
import com.Imanol.SpringBootProyecto.model.LibroModel;
import com.Imanol.SpringBootProyecto.repository.ILibroRepository;
import com.Imanol.SpringBootProyecto.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    ILibroRepository libroRepository;

    public ArrayList<LibroModel> getLibros(){
        return (ArrayList<LibroModel>) libroRepository.findAll();
    }

    public List<LibroModel> ordenarPorCriterio(String criterio) {
        switch (criterio) {
            case "nombre":
                return libroRepository.findAllByOrderByNombre();
            case "stock":
                return libroRepository.findAllByOrderByStock();
            default:
                throw new IllegalArgumentException("Criterio de ordenación no válido, disponibles: stock,nombre");
        }
    }

    public LibroModel saveLibro(LibroModel libro, MultipartFile imagen) throws IOException {
        if (libro.getNombre() == null || libro.getNombre().isEmpty())
            throw new LibroBadRequestException("Debe introducirse el nombre");

        if (libro.getStock() == null || libro.getStock() < 0)
            throw new LibroBadRequestException("Debe introducirse stock y debe ser mayor que 0");

        Long idEditorial = libro.getEditorial().getId();

        if (idEditorial == null || idEditorial <= 0 || idEditorial > 3)
            throw new LibroBadRequestException("Debe introducirse valor de editorial entre 1 y 3");

        String nombre_editorial;
        if ( idEditorial == 1)
            nombre_editorial = "Anaya";
        else if ( idEditorial == 2)
            nombre_editorial = "Planeta";
        else
            nombre_editorial = "RBA";

        LibroModel librosave = new LibroModel(libro.getNombre(), libro.getStock(), new EditorialModel(idEditorial, nombre_editorial));

        if (!imagen.isEmpty()) {
            librosave.setImagen(imagen.getOriginalFilename());
            librosave.setImagen_blob(ImageUtils.compressImage(imagen.getBytes())); // Almacena en BD el binario de la foto

            // El resto de líneas es para almacenar la imagen en disco
            // En despliegue para Docker sin DockerFile no podemos escribir en fichero.
            // Por este motivo se comenta el código correspondienter
            Path dirImg = Paths.get("src//main//resources//static//imagenes");
            String rutaAbsoluta = dirImg.toFile().getAbsolutePath();

            try {
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" +
                        imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
            } catch (IOException e) {
                throw new LibroBadRequestException("Error de escritura");
            }
        }
        else
            throw new LibroBadRequestException("Debe introducirse el fichero imagen");

        return libroRepository.save(librosave);
    }

    public Optional<LibroModel> getLibroId(Long id){
        return libroRepository.findById(id);
    }

    public LibroModel updateLibroId(LibroModel libro, MultipartFile imagen) throws IOException {
        if (libro.getNombre() == null || libro.getNombre().isEmpty())
            throw new LibroBadRequestException("Debe introducirse el nombre");

        if (libro.getStock() == null || libro.getStock() < 0)
            throw new LibroBadRequestException("Debe introducirse stock y debe ser mayor que 0");

        Long idEditorial = libro.getEditorial().getId();

        if (idEditorial == null || idEditorial <= 0 || idEditorial > 3)
            throw new LibroBadRequestException("Debe introducirse valor de editorial entre 1 y 3");

        String nombre_editorial;
        if ( idEditorial == 1)
            nombre_editorial = "Anaya";
        else if ( idEditorial == 2)
            nombre_editorial = "Planeta";
        else
            nombre_editorial = "RBA";

        if (!imagen.isEmpty()) {
            libro.setImagen(imagen.getOriginalFilename());
            libro.setImagen_blob(ImageUtils.compressImage(imagen.getBytes())); // Almacena en BD el binario de la foto

            // El resto de líneas es para almacenar la imagen en disco
            // En despliegue para Docker sin DockerFile no podemos escribir en fichero.
            // Por este motivo se comenta el código correspondienter
            Path dirImg = Paths.get("src//main//resources//static//imagenes");
            String rutaAbsoluta = dirImg.toFile().getAbsolutePath();

            try {
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" +
                        imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
            } catch (IOException e) {
                throw new LibroBadRequestException("Error de escritura");
            }
        }
        else
            throw new LibroBadRequestException("Debe introducirse el fichero imagen");

        return libroRepository.save(libro);
    }
    public byte[] descargarFoto(Long id) {
        LibroModel libro = libroRepository.findById(id).orElse(null);
        return libro != null ? ImageUtils.decompressImage(libro.getImagen_blob()) : null;
    }


    public Boolean deleteLibro (Long id){
        try{
            libroRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
