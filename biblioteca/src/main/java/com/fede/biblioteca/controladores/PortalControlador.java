package com.fede.biblioteca.controladores;

import com.fede.biblioteca.excepciones.MiException;
import com.fede.biblioteca.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")        // localhost:8080/
    public String index(){
        return "index";
    }




    @GetMapping("/registrar")
    public String registrar(){
        return "registro_usuario";
    }


    @PostMapping("/registro")
    public String registro_usuario(@RequestParam String nombre, @RequestParam String email, @RequestParam String password,
                                   @RequestParam String password2, ModelMap modelo){

        try{
            usuarioServicio.registrar(nombre, email, password, password2);

            modelo.put("exito", "Usuario Registrado Correctamente!");

            return "index";

        } catch(MiException ex){
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "registro_usuario";
        }

    }


    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo){
        if (error != null){
            modelo.put("error", "Usuario o contraseña invalida");
        }
        return "login_usuario";
    }

    @GetMapping("/inicio")
    public String inicio(){
        return "inicio";
    }

    @PostMapping("/logincheck")
    public String loginCheck(){
        return "inicio";
    }

}
