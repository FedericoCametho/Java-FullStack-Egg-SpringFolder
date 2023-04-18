package com.federuci.news.repositorios;

import com.federuci.news.entidades.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticiaRepositorio extends JpaRepository<Noticia, String> {

    @Query("SELECT n FROM Noticia n WHERE n.titulo = :titulo")
    public Noticia buscarNoticiaPorTitulo(@Param("titulo") String titulo);

}
