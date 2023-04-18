package com.fede.biblioteca.servicios;

import com.fede.biblioteca.entidades.Autor;
import com.fede.biblioteca.entidades.Editorial;
import com.fede.biblioteca.entidades.Libro;
import com.fede.biblioteca.excepciones.MiException;
import com.fede.biblioteca.repositorios.AutorRepositorio;
import com.fede.biblioteca.repositorios.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import javax.transaction.HeuristicMixedException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;

@Service
public class AutorServicio {

    @Autowired
    AutorRepositorio autorRepositorio;
    @Autowired
    LibroServicio libroServicio;

    @Transactional
    public void crearAutor(String nombre) throws MiException {
        this.validarDatos(nombre);
        Autor autor = new Autor();

        autor.setNombre(nombre);

        autorRepositorio.save(autor);
    }

    public List<Autor> listarAutores() {
        List<Autor> autores = new ArrayList();

        autores = autorRepositorio.findAll();

        return autores;
    }
    @Transactional
    public void modificarAutor(String id, String nombre) throws MiException{
        this.validarDatos(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();

            autor.setNombre(nombre);

            autorRepositorio.save(autor);
        }
    }

    @Transactional
    public void eliminarAutor(String id) throws MiException{
        if(id.isEmpty() || id == null){
            throw new MiException("El Id ingresado no puede ser nulo ni vacio");
        }
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autorRepositorio.delete(autor);
        }
    }

    public List<Libro> librosDeUnAutor(String id) throws MiException{
        List<Libro> libros = new ArrayList();
        libros = libroServicio.listarLibrosPorAutor(id);
        return libros;
    }
    public Autor getAutorById(String id){
        return autorRepositorio.getOne(id);
    }

    private void validarDatos(String nombre)throws MiException{
        if (nombre.isEmpty() || nombre == null){
            throw new MiException("EL nombre ingresado no puede ser nulo ni estar vacio");
        }
    }

}