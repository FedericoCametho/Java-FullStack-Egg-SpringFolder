package com.fede.biblioteca.controladores;

import com.fede.biblioteca.entidades.Autor;
import com.fede.biblioteca.excepciones.MiException;
import com.fede.biblioteca.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/autor")    // localhost:8080/autor
public class AutorControlador {
    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar") //localhost:8080/autor/registrar
    public String registrarAutor(){
        return "autor_form";
    }


    @PostMapping("/registro")   // recibe del formulario que tiene este action
    public String registro(@RequestParam String nombreAutor, ModelMap modelo){
        try {
            autorServicio.crearAutor(nombreAutor);
            modelo.put("exito", "El Autor se a cargado exitosamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_form";
        }
        return "index";
    }


    @GetMapping("/lista")   // localhost8080:/autor/lista
    public String listarAutores(ModelMap modelo){
        List<Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);

        return "autor_list";
    }


    @GetMapping("/modificar/{id}")   // localhost8080:/autor/modificar
    public String modificarAutor(@PathVariable String id, ModelMap modelo){
        Autor autor = autorServicio.getAutorById(id);
        modelo.put("autor", autor);
        return "autor_modificar";
    }


    @PostMapping("/modificar/{id}")
    public String modificacion(@PathVariable String id, String nombre, ModelMap modelo){
        try{
            autorServicio.modificarAutor(id, nombre);
            return "redirect:../lista";
        }catch (MiException ex){
            modelo.put("error", ex.getMessage());
            return "autor_modificar";
        }
    }

    @GetMapping("/borrar/{id}")
    public String borraAutor(@PathVariable String id, ModelMap modelo){
        Autor autor = autorServicio.getAutorById(id);
        modelo.put("autor", autor);
        return "autor_borrar";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable String id, ModelMap modelo) throws MiException{
        try{
            if (autorServicio.librosDeUnAutor(id).isEmpty()){
                autorServicio.eliminarAutor(id);
                return "redirect:../lista";
            }
            modelo.put("error", "El autor que desea borrar de la base de datos, Posee Libros asociados. Debera primero eliminar los libros asociados a dicho autor para luego eliminarlo");
            return "index";
        } catch(MiException ex){
            modelo.put("error", ex.getMessage());
            return "index";
        }
    }

}
