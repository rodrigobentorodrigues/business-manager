package bm.pdm.ifpb.com.businessmanager.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bm.pdm.ifpb.com.businessmanager.domains.Usuario;

public class UsuarioDao {

    private UsuarioContrato contrato;
    private SQLiteDatabase db;
    private Context contexto;

    public UsuarioDao(Context contexto) {
        this.contexto = contexto;
        this.contrato = new UsuarioContrato(contexto);
    }

    public void removerDados(){
        db = contrato.getWritableDatabase();
        db.delete(UsuarioContrato.UsuarioDados.tabela, null, null);
    }

    public void inserirUsuario(Usuario usuario){
        db = contrato.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long resultado;
        contentValues.put(UsuarioContrato.UsuarioDados.colunaNome, usuario.getNome());
        contentValues.put(UsuarioContrato.UsuarioDados.colunaCargo, usuario.getCargo());
        contentValues.put(UsuarioContrato.UsuarioDados.colunaLogin, usuario.getLogin());
        contentValues.put(UsuarioContrato.UsuarioDados.colunaSenha, usuario.getSenha());
        contentValues.put(UsuarioContrato.UsuarioDados.colunaTelefone, usuario.getTelefone());
        contentValues.put(UsuarioContrato.UsuarioDados.colunaIdEmpresa, usuario.getIdEmpresa());
        resultado = db.insert(UsuarioContrato.UsuarioDados.tabela,
                null, contentValues);
        if(resultado == -1){
            Log.d("SQL", "Erro");
            Toast.makeText(contexto, "Erro ao inserir", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("SQL", "Inserido");
            Toast.makeText(contexto, "Inserido com sucesso", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public List<Usuario> todosUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        db = contrato.getReadableDatabase();
        String[] campos = {UsuarioContrato.UsuarioDados._ID, UsuarioContrato.UsuarioDados.colunaNome,
                UsuarioContrato.UsuarioDados.colunaCargo, UsuarioContrato.UsuarioDados.colunaLogin,
                UsuarioContrato.UsuarioDados.colunaSenha, UsuarioContrato.UsuarioDados.colunaTelefone,
                UsuarioContrato.UsuarioDados.colunaIdEmpresa};
        Cursor cursor = db.query(UsuarioContrato.UsuarioDados.tabela, campos,
                null,null, null, null, null);
        while(cursor.moveToNext()){
            Usuario usuario = new Usuario();
            usuario.setId(cursor.getInt(0));
            usuario.setNome(cursor.getString(1));
            usuario.setCargo(cursor.getString(2));
            usuario.setLogin(cursor.getString(3));
            usuario.setSenha(cursor.getString(4));
            usuario.setTelefone(cursor.getString(5));
            usuario.setIdEmpresa(cursor.getInt(6));
            usuarios.add(usuario);
        }
        cursor.close();
        db.close();
        return usuarios;
    }

    public Usuario autenticarUsuario(String login, String senha){
        Usuario usuario = new Usuario();
        db = contrato.getReadableDatabase();
        String[] campos = {UsuarioContrato.UsuarioDados._ID, UsuarioContrato.UsuarioDados.colunaNome,
                UsuarioContrato.UsuarioDados.colunaCargo, UsuarioContrato.UsuarioDados.colunaLogin,
                UsuarioContrato.UsuarioDados.colunaSenha, UsuarioContrato.UsuarioDados.colunaTelefone,
                UsuarioContrato.UsuarioDados.colunaIdEmpresa};
        Cursor cursor = db.query(UsuarioContrato.UsuarioDados.tabela, campos,
                "login = ? AND senha = ?", new String[]{login, senha},
                null, null, null);
        if(cursor.moveToNext()){
            usuario.setId(cursor.getInt(0));
            usuario.setNome(cursor.getString(1));
            usuario.setCargo(cursor.getString(2));
            usuario.setLogin(cursor.getString(3));
            usuario.setSenha(cursor.getString(4));
            usuario.setTelefone(cursor.getString(5));
            usuario.setIdEmpresa(cursor.getInt(6));
        }
        cursor.close();
        db.close();
        return usuario;
    }

}
