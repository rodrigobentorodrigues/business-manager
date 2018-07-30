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
        this.contrato = new UsuarioContrato(contexto);
        this.contexto = contexto;
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
        contentValues.put(UsuarioContrato.UsuarioDados.colunaImagem, usuario.getImagem());
        resultado = db.insert(UsuarioContrato.
                UsuarioDados.tabela, null, contentValues);
        if(resultado == -1){
            Toast.makeText(contexto, "Erro ao inserir", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(contexto, "Inserido com sucesso", Toast.LENGTH_SHORT).show();
        }
    }

    public List<Usuario> todosUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        db = contrato.getReadableDatabase();
        String[] campos = {UsuarioContrato.UsuarioDados.colunaNome, UsuarioContrato.UsuarioDados.colunaCargo, UsuarioContrato.UsuarioDados.colunaLogin,
                UsuarioContrato.UsuarioDados.colunaSenha, UsuarioContrato.UsuarioDados.colunaTelefone, UsuarioContrato.UsuarioDados.colunaImagem};
        Cursor cursor = db.query(UsuarioContrato.UsuarioDados.tabela, campos, null,
                null, null, null, null);
        Log.i("Cursor", cursor.toString());
        if(cursor != null){
            cursor.moveToFirst();
        }
        while(cursor.moveToNext()){
            Usuario usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            usuario.setNome(cursor.getString(0));
            usuario.setCargo(cursor.getString(1));
            usuario.setLogin(cursor.getString(2));
            usuario.setSenha(cursor.getString(3));
            usuario.setTelefone(cursor.getString(4));
            usuario.setImagem(cursor.getBlob(5));
            usuarios.add(usuario);
        }
        return usuarios;
    }

}
