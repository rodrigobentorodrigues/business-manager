package bm.pdm.ifpb.com.businessmanager.domains;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Tarefa implements Serializable {

    private int id;
    private String deUsuario;
    private String paraUsuario;
    private String titulo;
    private String descricao;
    private String data;
    private boolean concluida;

    public Tarefa(int id, String deUsuario, String paraUsuario, String titulo, String descricao, String data, boolean concluida) {
        this.id = id;
        this.deUsuario = deUsuario;
        this.paraUsuario = paraUsuario;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.concluida = concluida;
    }

    public Tarefa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeUsuario() {
        return deUsuario;
    }

    public void setDeUsuario(String deUsuario) {
        this.deUsuario = deUsuario;
    }

    public String getParaUsuario() {
        return paraUsuario;
    }

    public void setParaUsuario(String paraUsuario) {
        this.paraUsuario = paraUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", deUsuario='" + deUsuario + '\'' +
                ", paraUsuario='" + paraUsuario + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", data='" + data + '\'' +
                ", concluida=" + concluida +
                '}';
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarefa tarefa = (Tarefa) o;
        return id == tarefa.id &&
                concluida == tarefa.concluida &&
                Objects.equals(deUsuario, tarefa.deUsuario) &&
                Objects.equals(paraUsuario, tarefa.paraUsuario) &&
                Objects.equals(titulo, tarefa.titulo) &&
                Objects.equals(descricao, tarefa.descricao) &&
                Objects.equals(data, tarefa.data);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, deUsuario, paraUsuario, titulo, descricao, data, concluida);
    }
}
