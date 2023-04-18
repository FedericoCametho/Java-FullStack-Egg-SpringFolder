package com.fede.biblioteca.entidades;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Libro {
    @Id
    private Long ISBN;
    private String titulo;
    private Integer ejemplares;
    @Temporal(TemporalType.DATE)
    private Date alta;
    @ManyToOne
    private Autor autor;

    @ManyToOne
    private Editorial editorial;

    public Libro() {
    }

    public void setISBN(Long ISBN) {
        this.ISBN = ISBN;
    }

    public Long getISBN() {
        return ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(Integer ejemplares) {
        this.ejemplares = ejemplares;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }
}
