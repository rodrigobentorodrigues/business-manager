package bm.pdm.ifpb.com.businessmanager.infra;

import org.json.JSONException;
import org.json.JSONObject;

import bm.pdm.ifpb.com.businessmanager.domains.Duvida;
import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;

public class ConversorDados {

    public Duvida getDuvida(JSONObject json){
        Duvida duvida = new Duvida();
        try {
            duvida.setId(json.getInt("id"));
            duvida.setDeUsuario(json.getString("deUsuario"));
            duvida.setParaUsuario(json.getString("paraUsuario"));
            duvida.setPergunta(json.getString("pergunta"));
            duvida.setResposta(json.getString("resposta"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return duvida;
    }

    public Tarefa getTarefa(JSONObject json){
        Tarefa tarefa = new Tarefa();
        try {
            tarefa.setId(json.getInt("id"));
            tarefa.setDeUsuario(json.getString("deUsuario"));
            tarefa.setParaUsuario(json.getString("paraUsuario"));
            tarefa.setTitulo(json.getString("titulo"));
            tarefa.setDescricao(json.getString("descricao"));
            tarefa.setData(json.getString("data"));
            tarefa.setConcluida(json.getBoolean("concluida"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tarefa;
    }

    public Usuario getUsuario(JSONObject json){
        Usuario usuario = new Usuario();
        try {
            usuario.setId(json.getInt("id"));
            usuario.setNome(json.getString("nome"));
            usuario.setCargo(json.getString("funcao"));
            usuario.setLogin(json.getString("login"));
            usuario.setSenha(json.getString("senha"));
            usuario.setTelefone(json.getString("telefone"));
            JSONObject empresa = json.getJSONObject("empresa");
            usuario.setIdEmpresa(empresa.getInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usuario;
    }

}
