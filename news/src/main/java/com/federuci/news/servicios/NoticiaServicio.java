package com.federuci.news.servicios;

import com.federuci.news.entidades.Noticia;
import com.federuci.news.excepciones.MiException;
import com.federuci.news.repositorios.NoticiaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
public class NoticiaServicio {

    @Autowired
    NoticiaRepositorio noticiaRepositorio;

    @Transactional
    public void crearNoticia(String titulo, String cuerpo) throws MiException {
        this.validarDatos(titulo, cuerpo);
        Noticia noticia = new Noticia();
        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);

        noticiaRepositorio.save(noticia);
    }

    @Transactional
    public void modificarNoticia(String id, String titulo, String cuerpo) throws MiException{
        this.validarDatos(titulo, cuerpo);
        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);

        if (respuesta.isPresent()){
            Noticia noticia = respuesta.get();
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);

            noticiaRepositorio.save(noticia);
        }
    }

    public List<Noticia> listarNoticias(){
        List<Noticia> noticias = new ArrayList();
        noticias = noticiaRepositorio.findAll();
        return noticias;
    }

    @Transactional
    public void eliminarNoticia(String id) throws MiException{
        if (id == null || id.isEmpty()){
            throw new MiException("El id ingresado no puede ser nulo o estar vacio");
        }
        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);
        if (respuesta.isPresent()){
            Noticia noticia = respuesta.get();
            noticiaRepositorio.delete(noticia);
        }
    }



    public Noticia getNoticiaById(String id){
        return noticiaRepositorio.getOne(id);
    }
    private void validarDatos(String titulo, String cuerpo) throws MiException{
        if (titulo == null || titulo.isEmpty()){
            throw new MiException("El titulo no puede ser nulo ni vacio");
        }
        if(cuerpo == null || cuerpo.isEmpty()){
            throw new MiException("El cuerpo de la noticia no puede ser nulo o estar vacio");
        }
    }

}
