package bm.pdm.ifpb.com.businessmanager.domains;

import android.content.SharedPreferences;

public class Configuracao {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Configuracao(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.editor = sharedPreferences.edit();
    }

    public String getRepositorio(){
        return sharedPreferences.getString("chave", "remoto");
    }

    public void setRepositorio(String valor){
        editor.putString("chave", valor);
        editor.apply();
    }

}
