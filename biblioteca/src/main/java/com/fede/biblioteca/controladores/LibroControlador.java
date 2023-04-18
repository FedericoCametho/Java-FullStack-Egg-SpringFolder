package com.fede.biblioteca.controladores;

import com.fede.biblioteca.entidades.Autor;
import com.fede.biblioteca.entidades.Editorial;
import com.fede.biblioteca.entidades.Libro;
import com.fede.biblioteca.excepciones.MiException;
import com.fede.biblioteca.servicios.AutorServicio;
import com.fede.biblioteca.servicios.EditorialServicio;
import com.fede.biblioteca.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/libro")    // localhost8080:/libro
public class LibroControlador {

    @Autowired
    AutorServicio autorServicio;
    @Autowired
    LibroServicio libroServicio;
    @Autowired
    EditorialServicio editorialServicio;

    @GetMapping("/registrar")   // localhost8080:/libro/registrar
    public String registrarLibro(ModelMap modelo){
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "libro_form";
    }

    @PostMapping("/registro") // recibe del formulario que tiene este action .  Usar el required=false es porque si ingresa un valor
                                            // numerico nulo al controlador, ni se ejecuta, entonces de esta manera hacemos que si hay un nulo
    //                                      // o vacio numerico, entre igual y manejemos el error desde la excepcion creada en el servicio

    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
                           @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
                           @RequestParam String idEditorial, ModelMap modelo){
            // los modelos en spring sirven para insertar en este modelo toda la ifnormacion que vamos a necesitar mostrar por pantalla
            // o que vamos a necesitar desde el lado de la interfaz del usuario
        try{
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito","El libro fue cargado correctamente");
        }catch (MiException ex){
            modelo.put("error", ex.getMessage());

            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            return "libro_form";
        }
        return "index";
    }

    @GetMapping("/lista") // localhost8080:/libro/lista
    public String listarLibros(ModelMap modelo){

        List<Libro> libros = libroServicio.listarLibros();
        modelo.addAttribute("libros", libros);
        return "libro_list";
    }

    @GetMapping("/modificar/{isbn}")
    public String modificarLibro(@PathVariable Long isbn, ModelMap modelo){
        Libro libro = libroServicio.getLibroByIsbn(isbn);
        modelo.addAttribute("libro", libro);
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "libro_modificar";
    }


    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn,  String titulo, Integer ejemplares, String idAutor,
                            String idEditorial, ModelMap modelo) throws MiException{
        try{
            libroServicio.modificarLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            return "redirect:../lista";
        }catch(MiException ex){
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            modelo.put("error", ex.getMessage());
            return "libro_modificar";
        }
    }

    @GetMapping("/borrar/{isbn}")
    public String borrarLibro(@PathVariable Long isbn, ModelMap modelo){
        Libro libro = libroServicio.getLibroByIsbn(isbn);
        modelo.addAttribute("libro", libro);
        return "libro_borrar";
    }

    @PostMapping("/borrar/{isbn}")
    public String borrar(@PathVariable Long isbn, ModelMap modelo){
        try{
            libroServicio.eliminarLibro(isbn);
            return "redirect:../lista";
        } catch(MiException ex){
            modelo.put("error", ex.getMessage());
            return "index";
        }
    }


}
