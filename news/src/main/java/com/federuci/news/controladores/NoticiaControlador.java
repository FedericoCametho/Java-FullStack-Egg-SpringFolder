package com.federuci.news.controladores;

import com.federuci.news.entidades.Noticia;
import com.federuci.news.excepciones.MiException;
import com.federuci.news.servicios.NoticiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/news")
public class NoticiaControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;

    @GetMapping("/lista")
    public String listarNoticias(ModelMap modelo){
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "noticia_list";
    }


    @GetMapping("/detalle/{id}")
    public String mostrarNoticia(@PathVariable String id, ModelMap modelo){
        Noticia noticia = noticiaServicio.getNoticiaById(id);
        modelo.addAttribute("noticia", noticia);
        return "noticia_detail";
    }


    @GetMapping("/registrar")
    public String registrarNoticia(){
        return "noticia_form";
    }

    @PostMapping("/registro")
    public String registrar(@RequestParam String titulo, @RequestParam String cuerpo, ModelMap modelo){
        try{
            noticiaServicio.crearNoticia(titulo, cuerpo);
            modelo.put("exito", "La noticia se ha cargado correctamente");
        }catch(MiException ex){
            modelo.put("error", ex.getCause());
            return "noticia_form";
        }
        return "index.html";
    }

    @GetMapping("/lista-eliminar")
    public String listarNoticiasEliminar(ModelMap modelo){
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "noticia_lista_eliminar";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarNoticia(@PathVariable String id, ModelMap modelo){
        Noticia noticia = noticiaServicio.getNoticiaById(id);
        modelo.addAttribute("noticia", noticia);
        return "noticia_eliminar";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo){
        try{
            noticiaServicio.eliminarNoticia(id);
            return "redirect:../lista";
        }catch(MiException ex){
            modelo.put("error", ex.getMessage());
            return "index";
        }
    }

    @GetMapping("/lista-modificar")
    public String listarNoticiasModificar(ModelMap modelo){
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "noticia_lista_modificar";
    }

    @GetMapping("/modificar/{id}")
    public String modificarNoticia(@PathVariable String id, ModelMap modelo){
        Noticia noticia = noticiaServicio.getNoticiaById(id);
        modelo.addAttribute("noticia", noticia);
        return "noticia_modificar";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String titulo, String cuerpo, ModelMap modelo){
        try{
            noticiaServicio.modificarNoticia(id, titulo, cuerpo);
            return "redirect:../lista";
        }catch(MiException ex){
            modelo.put("error", ex.getMessage());
            return "index";
        }
    }

}
