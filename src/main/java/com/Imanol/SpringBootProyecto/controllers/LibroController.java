package com.Imanol.SpringBootProyecto.controllers;

import com.Imanol.SpringBootProyecto.model.EditorialModel;
import com.Imanol.SpringBootProyecto.model.LibroModel;
import com.Imanol.SpringBootProyecto.service.EditorialService;
import com.Imanol.SpringBootProyecto.service.LibroService;
import com.Imanol.SpringBootProyecto.util.ImageUtils;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/libro")
public class LibroController {
    @Autowired
    private LibroService libroService;
    @Autowired
    private EditorialService editorialService;

    @GetMapping
    public ArrayList<LibroModel> getLibros(){
        return this.libroService.getLibros();
    }
    @Parameter(name = "criterio", description = "criterio de ordenacion", required = true, example = "stock")
    @GetMapping(path="/ordenar")
    public List<LibroModel> ordenarLibros(@RequestParam String criterio) {
        return libroService.ordenarPorCriterio(criterio);
    }
    @GetMapping("/vista_libros")
    public ModelAndView listado(Model modelo) throws UnsupportedEncodingException {
        List<LibroModel> libros= getLibros();

        modelo.addAttribute("listaLibros", libros);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mostrarLibros.html");
        return modelAndView;
    }

    @PostMapping(path= "/insertar" ,consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<LibroModel> saveLibro(@RequestParam String nombre, @RequestParam Integer stock,
                                @RequestParam Long idEditorial,
                                @RequestPart(name="imagen_blob", required=false) MultipartFile imagen) throws IOException {
        /* Es necesario elegir un id de editorial existente*/
        EditorialModel editorialModel = new EditorialModel();
        Optional<EditorialModel> optionalEditorial = editorialService.getEditorialId(idEditorial);
        if (((Optional<?>) optionalEditorial).isPresent()) {
            editorialModel = optionalEditorial.get();
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LibroModel createdLibro = libroService.saveLibro(new LibroModel(nombre,stock,editorialModel),imagen);
        return new ResponseEntity<>(createdLibro, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public Optional<LibroModel> getLibroId(@PathVariable("id") Long id){
        return libroService.getLibroId(id);
    }

    @GetMapping(path = "/foto/{id}" ,produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> descargarFoto(@PathVariable Long id) {
        byte[] foto = libroService.descargarFoto(id);
        if ( foto != null ) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(foto);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{id}",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<LibroModel>  updateLibroId(@RequestParam String nombre,@RequestParam Integer stock,@RequestParam Long idEditorial,
                                                     @RequestPart(name="imagen", required=false) MultipartFile imagen ,@PathVariable Long id) throws IOException{
        /* Es necesario elegir un id de editorial existente */
        EditorialModel editorialModel = new EditorialModel();
        Optional<EditorialModel> optionalEditorial = editorialService.getEditorialId(idEditorial);
        if (((Optional<?>) optionalEditorial).isPresent()) {
            editorialModel = optionalEditorial.get();
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        /* Tiene que existir el libro con ese id dado para poder actualizarlo */
        Optional<LibroModel> optionalLibro = libroService.getLibroId(id);

        if (((Optional<?>) optionalLibro).isPresent()) {
            LibroModel existingLibro = optionalLibro.get();
            existingLibro.setNombre(nombre);
            existingLibro.setStock(stock);
            existingLibro.setEditorial(editorialModel);
            existingLibro.setFecha_modificacion(LocalDateTime.now());
            existingLibro.setImagen(imagen.getOriginalFilename());
            existingLibro.setImagen_blob(ImageUtils.compressImage(imagen.getBytes()));

            LibroModel updatedLibro = libroService.updateLibroId(existingLibro, imagen);
            return new ResponseEntity<>(updatedLibro, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path="/{id}")
    public String deleteLibro(@PathVariable("id") Long id){
        boolean ok = this.libroService.deleteLibro(id);
        if(ok){
            return "Libro eliminado";
        } else {
            return "Libro no encontrado";
        }
    }
}
