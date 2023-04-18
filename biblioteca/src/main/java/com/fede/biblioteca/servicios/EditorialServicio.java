package com.fede.biblioteca.servicios;

import com.fede.biblioteca.entidades.Editorial;
import com.fede.biblioteca.entidades.Libro;
import com.fede.biblioteca.excepciones.MiException;
import com.fede.biblioteca.repositorios.EditorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EditorialServicio {

    @Autowired
    EditorialRepositorio editorialRepositorio;
    @Autowired
    LibroServicio libroServicio;

    @Transactional
    public void crearEditorial(String nombre) throws MiException{
        this.validarDatos(nombre);
        Editorial editorial = new Editorial();

        editorial.setNombre(nombre);

        editorialRepositorio.save(editorial);
    }

    public List<Editorial> listarEditoriales(){
        List<Editorial> editoriales = new ArrayList();

        editoriales = editorialRepositorio.findAll();

        return editoriales;
    }
    @Transactional
    public void modificarEditorial(String id, String nombre) throws MiException{
        this.validarDatos(nombre);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();

            editorial.setNombre(nombre);

            editorialRepositorio.save(editorial);
        }

    }

    @Transactional
    public void eliminarEditorial(String id)throws MiException{
        if (id == null || id.isEmpty()){
            throw new MiException("El id ingresado no puede ser nulo o estar vacio");
        }
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorialRepositorio.delete(editorial);
        }
    }

    public List<Libro> librosDeUnaEditorial(String id) throws MiException{
        List<Libro> libros = new ArrayList();
        libros = libroServicio.listarLibrosPorEditorial(id);
        return libros;
    }


    public Editorial getEditorialById(String id){
        return editorialRepositorio.getOne(id);
    }

    private void validarDatos(String nombre)throws MiException{
        if (nombre.isEmpty() || nombre == null){
            throw new MiException("EL nombre ingresado no puede ser nulo ni estar vacio");
        }
    }

}
