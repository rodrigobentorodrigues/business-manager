package bm.pdm.ifpb.com.businessmanager.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import bm.pdm.ifpb.com.businessmanager.domains.Duvida;

public class DuvidaDao {

    private DuvidaContrato duvidaContrato;
    private Context context;
    private SQLiteDatabase db;

    public DuvidaDao(Context context) {
        this.context = context;
        this.duvidaContrato = new DuvidaContrato(context);
    }

    public void removerDados(){
        db = duvidaContrato.getWritableDatabase();
        db.delete(DuvidaContrato.DuvidaDados.tabela, null, null);
    }

    public void inserirDuvida(Duvida duvida){
        db = duvidaContrato.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DuvidaContrato.DuvidaDados._ID, duvida.getId());
        contentValues.put(DuvidaContrato.DuvidaDados.colunaDeUsuario, duvida.getDeUsuario());
        contentValues.put(DuvidaContrato.DuvidaDados.colunaParaUsuario, duvida.getParaUsuario());
        contentValues.put(DuvidaContrato.DuvidaDados.colunaPergunta, duvida.getPergunta());
        contentValues.put(DuvidaContrato.DuvidaDados.colunaResposta, duvida.getResposta());
        long insert = db.insert(DuvidaContrato.DuvidaDados.tabela, null, contentValues);
        db.close();
        if(insert == -1){
            Log.d("SQL Duvida", "Erro");
        } else {
            Log.d("SQL Duvida", "Inserido");
        }
    }

    public List<Duvida> todasDuvidas(){
        List<Duvida> duvidas = new ArrayList<>();
        db = duvidaContrato.getReadableDatabase();
        String[] campos = {DuvidaContrato.DuvidaDados._ID, DuvidaContrato.DuvidaDados.colunaDeUsuario,
                DuvidaContrato.DuvidaDados.colunaParaUsuario, DuvidaContrato.DuvidaDados.colunaPergunta,
                DuvidaContrato.DuvidaDados.colunaResposta};
        Cursor cursor = db.query(DuvidaContrato.DuvidaDados.tabela, campos,
                null, null, null, null, null);
        while(cursor.moveToNext()){
            Duvida duvida = new Duvida();
            duvida.setId(cursor.getInt(0));
            duvida.setDeUsuario(cursor.getString(1));
            duvida.setParaUsuario(cursor.getString(2));
            duvida.setPergunta(cursor.getString(3));
            duvida.setResposta(cursor.getString(4));
            duvidas.add(duvida);
        }
        cursor.close();
        db.close();
        return duvidas;
    }

    public List<Duvida> todasNaoConcluidas(String usuario){
        Log.d("Usuario", usuario);
        List<Duvida> duvidas = new ArrayList<>();
        db = duvidaContrato.getReadableDatabase();
        String[] campos = {DuvidaContrato.DuvidaDados._ID, DuvidaContrato.DuvidaDados.colunaDeUsuario,
                DuvidaContrato.DuvidaDados.colunaParaUsuario, DuvidaContrato.DuvidaDados.colunaPergunta,
                DuvidaContrato.DuvidaDados.colunaResposta};
        Cursor cursor = db.query(DuvidaContrato.DuvidaDados.tabela, campos,
                "(deUsuario = ? OR paraUsuario = ?) AND (resposta = 'null')",
                new String[]{usuario, usuario}, null, null, null);
        while(cursor.moveToNext()){
            Log.d("Cursor", "Entrou!");
            Duvida duvida = new Duvida();
            duvida.setId(cursor.getInt(0));
            duvida.setDeUsuario(cursor.getString(1));
            duvida.setParaUsuario(cursor.getString(2));
            duvida.setPergunta(cursor.getString(3));
            duvida.setResposta(cursor.getString(4));
            duvidas.add(duvida);
        }
        cursor.close();
        db.close();
        return duvidas;
    }

    public void responderDuvida(Duvida duvida){
        db = duvidaContrato.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DuvidaContrato.DuvidaDados._ID, duvida.getId());
        contentValues.put(DuvidaContrato.DuvidaDados.colunaDeUsuario, duvida.getDeUsuario());
        contentValues.put(DuvidaContrato.DuvidaDados.colunaParaUsuario, duvida.getParaUsuario());
        contentValues.put(DuvidaContrato.DuvidaDados.colunaPergunta, duvida.getPergunta());
        contentValues.put(DuvidaContrato.DuvidaDados.colunaResposta, duvida.getResposta());
        int update = db.update(DuvidaContrato.DuvidaDados.tabela, contentValues,
                "_id = ?", new String[]{String.valueOf(duvida.getId())});
        Log.d("Atualizados", ": " + update);
    }

}
