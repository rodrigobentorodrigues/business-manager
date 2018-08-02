package bm.pdm.ifpb.com.businessmanager.domains;

import android.content.SharedPreferences;

public class DadosUsuario {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedEditor;

    public DadosUsuario(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.sharedEditor = sharedPreferences.edit();
    }

    public void alterarValores(Usuario usuario){
        sharedEditor.putString("nome", usuario.getNome());
        sharedEditor.putString("login", usuario.getLogin());
        sharedEditor.putString("funcao", usuario.getCargo());
        sharedEditor.putInt("idEmpresa", usuario.getIdEmpresa());
        sharedEditor.apply();
    }

    public Usuario autenticado(){
        Usuario usuario = new Usuario();
        usuario.setNome(sharedPreferences.getString("nome", ""));
        usuario.setLogin(sharedPreferences.getString("login", ""));
        usuario.setCargo(sharedPreferences.getString("funcao", ""));
        usuario.setIdEmpresa(sharedPreferences.getInt("idEmpresa", 0));
        return usuario;
    }

}
