package bm.pdm.ifpb.com.businessmanager.domains;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class Duvida implements Serializable {

    private int id;
    private String deUsuario;
    private String paraUsuario;
    private String pergunta;
    private String resposta;

    public Duvida() {
    }

    public Duvida(String deUsuario, String paraUsuario, String pergunta) {
        this.deUsuario = deUsuario;
        this.paraUsuario = paraUsuario;
        this.pergunta = pergunta;
    }

    public Duvida(String deUsuario, String paraUsuario, String pergunta, String resposta) {
        this.deUsuario = deUsuario;
        this.paraUsuario = paraUsuario;
        this.pergunta = pergunta;
        this.resposta = resposta;
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

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    @Override
    public String toString() {
        return "Duvida{" +
                "id=" + id +
                ", deUsuario='" + deUsuario + '\'' +
                ", paraUsuario='" + paraUsuario + '\'' +
                ", pergunta='" + pergunta + '\'' +
                ", resposta='" + resposta + '\'' +
                '}';
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Duvida duvida = (Duvida) o;
        return id == duvida.id &&
                Objects.equals(deUsuario, duvida.deUsuario) &&
                Objects.equals(paraUsuario, duvida.paraUsuario) &&
                Objects.equals(pergunta, duvida.pergunta) &&
                Objects.equals(resposta, duvida.resposta);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, deUsuario, paraUsuario, pergunta, resposta);
    }

}
