package com.fede.biblioteca.controladores;

import com.fede.biblioteca.entidades.Editorial;
import com.fede.biblioteca.excepciones.MiException;
import com.fede.biblioteca.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/editorial")    //localhost:8080/editorial
public class EditorialControlador {
    @Autowired
    EditorialServicio editorialServicio;

    @GetMapping("/registrar") //localhost:8080/editorial/registrar
    public String registrarEditorial(String nombre){
        return "editorial_form";
    }

    @PostMapping("/registro") // recibe del formulario que tiene este action
    public String registro(@RequestParam String nombreEditorial, ModelMap modelo){
        try{
            editorialServicio.crearEditorial(nombreEditorial);
            modelo.put("exito", "La Editorial se ha cargado correctamente");
        } catch(MiException ex){
            modelo.put("error", ex.getMessage());
            return "editorial_form";
        }

        return "index";
    }

    @GetMapping("/lista")    // localhost8080:/editorial/lista
    public String listarEditoriales(ModelMap modelo){
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        return "editorial_list";
    }

    @GetMapping("/modificar/{id}") // localhost8080:/editorial/modificar
    public String modificarEditorial(@PathVariable String id, String nombre, ModelMap modelo){
        Editorial editorial = editorialServicio.getEditorialById(id);
        modelo.addAttribute("editorial", editorial);
        return "editorial_modificar";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        try{
            editorialServicio.modificarEditorial(id, nombre);
            return "redirect:../lista";
        }catch(MiException ex){
            modelo.put("error", ex.getMessage());
            return "editorial_modificar";
        }
    }


        @GetMapping("borrar/{id}")
        public String borrarEditorial(@PathVariable String id, String nombre, ModelMap modelo){
            Editorial editorial = editorialServicio.getEditorialById(id);
            modelo.addAttribute("editorial", editorial);
            return "editorial_borrar";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable String id, ModelMap modelo) throws MiException{
        try{
            if (editorialServicio.librosDeUnaEditorial(id).isEmpty()){
                editorialServicio.eliminarEditorial(id);
                return "redirect:../lista";
            }
            modelo.put("error", "El autor que desea borrar de la base de datos, Posee Libros asociados. Debera primero eliminar los libros asociados a dicho autor para luego eliminarlo");
            return "index";
        }catch(MiException ex){
            modelo.put("error", ex.getMessage());
            return "index";
        }
    }

}
