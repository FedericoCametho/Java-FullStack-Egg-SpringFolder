package com.federuci.news.controladores;

import com.federuci.news.entidades.Noticia;
import com.federuci.news.servicios.NoticiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    NoticiaServicio noticiaServicio;

    @GetMapping("/")
    public String index(ModelMap modelo){
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "index";
    }

    @GetMapping("/registrar")
    public String registrar(){
        return "registro_usuario";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


}
