package com.fede.biblioteca.servicios;

import com.fede.biblioteca.entidades.Autor;
import com.fede.biblioteca.entidades.Editorial;
import com.fede.biblioteca.entidades.Libro;
import com.fede.biblioteca.excepciones.MiException;
import com.fede.biblioteca.repositorios.AutorRepositorio;
import com.fede.biblioteca.repositorios.EditorialRepositorio;
import com.fede.biblioteca.repositorios.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {
        this.validarDatos(isbn, titulo, ejemplares, idAutor, idEditorial);

        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        Libro libro = new Libro();

        libro.setISBN(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    public List<Libro> listarLibros(){
        List<Libro> libros = new ArrayList();

        libros = libroRepositorio.findAll();

        return libros;
    }
    @Transactional
    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
        this.validarDatos(isbn, titulo, ejemplares, idAutor, idEditorial);

        Optional<Libro> respuestaLibro = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()){
            autor = respuestaAutor.get();
        }

        if(respuestaEditorial.isPresent()){
            editorial = respuestaEditorial.get();
        }

        if (respuestaLibro.isPresent()){
            Libro libro = respuestaLibro.get();

            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);

            libroRepositorio.save(libro);
        }

    }

    @Transactional
    public void eliminarLibro(Long isbn) throws MiException{
        if (isbn == null){
            throw new MiException("El ISBN ingresado no puede ser nulo");
        }
        Optional<Libro> respuesta =  libroRepositorio.findById(isbn);
        if(respuesta.isPresent()){
            Libro libro = respuesta.get();
            libroRepositorio.delete(libro);
        }
    }

    public List<Libro> listarLibrosPorAutor(String idAutor) throws MiException{
        if (idAutor == null || idAutor.isEmpty()){
            throw new MiException("El id del autor no puede ser nulo o vacio");
        }
        return libroRepositorio.buscarPorAutor(idAutor);
    }

    public List<Libro> listarLibrosPorEditorial(String idEditorial) throws MiException{
        if (idEditorial == null || idEditorial.isEmpty()){
            throw new MiException("El id de la Editorial no puede ser nulo o vacio");
        }
        return libroRepositorio.buscarPorEditorial(idEditorial);
    }


    public Libro getLibroByIsbn(Long isbn){
        return libroRepositorio.getOne(isbn);
    }
    private void validarDatos(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
        if (isbn == null){
            throw new MiException("El ISBN no puede ser nulo");
        }
        if (titulo.isEmpty() || titulo == null){
            throw new MiException("El titulo no puede ser nulo ni estar vacio");
        }
        if (ejemplares == null || ejemplares<0){
            throw new MiException("Los ejemplares no pueden ser nulo ni negativo");
        }
        if (idAutor.isEmpty() || idAutor == null){
            throw new MiException("El Id del Autor no puede ser nulo ni estar vacio");
        }
        if (idEditorial.isEmpty() || idEditorial == null){
            throw new MiException("El Id de la Editorial no puede ser nulo ni estar vacio");
        }
    }


}
