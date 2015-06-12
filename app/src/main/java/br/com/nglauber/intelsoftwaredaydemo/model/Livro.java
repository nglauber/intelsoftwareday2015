package br.com.nglauber.intelsoftwaredaydemo.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

@Table(name = "Livros")
public class Livro extends Model implements Serializable {
    public static final String COLUNA_TITULO = "titulo";
    public static final String COLUNA_AUTOR = "autor";
    public static final String COLUNA_ANO = "ano";
    public static final String COLUNA_PAGINAS = "paginas";
    public static final String COLUNA_CAPA = "capa";

    @Column(name = COLUNA_TITULO, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String titulo;
    @Column(name = COLUNA_AUTOR)
    public String autor;
    @Column(name = COLUNA_ANO)
    public int ano;
    @Column(name = COLUNA_PAGINAS)
    public int paginas;
    @Column(name = COLUNA_CAPA)
    public String capa;

    public Livro(){
    }

    public Livro(String titulo, String autor, int ano, int paginas, String capa) {
        this.ano = ano;
        this.autor = autor;
        this.capa = capa;
        this.paginas = paginas;
        this.titulo = titulo;
    }
}
