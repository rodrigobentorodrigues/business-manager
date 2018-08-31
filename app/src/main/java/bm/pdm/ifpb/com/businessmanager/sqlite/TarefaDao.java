package bm.pdm.ifpb.com.businessmanager.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;

public class TarefaDao {

    private Context context;
    private TarefaContrato tarefaContrato;
    private SQLiteDatabase db;

    public TarefaDao(Context context) {
        this.context = context;
        this.tarefaContrato = new TarefaContrato(context);
    }

    public void removerDados(){
        db = tarefaContrato.getWritableDatabase();
        db.delete(TarefaContrato.TarefaDados.tabela, null, null);
    }

    public void inserirTarefa(Tarefa tarefa){
        db = tarefaContrato.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TarefaContrato.TarefaDados._ID, tarefa.getId());
        contentValues.put(TarefaContrato.TarefaDados.colunaDeUsuario, tarefa.getDeUsuario());
        contentValues.put(TarefaContrato.TarefaDados.colunaParaUsuario, tarefa.getParaUsuario());
        contentValues.put(TarefaContrato.TarefaDados.colunaTitulo, tarefa.getTitulo());
        contentValues.put(TarefaContrato.TarefaDados.colunaDescricao, tarefa.getDescricao());
        contentValues.put(TarefaContrato.TarefaDados.colunaData, tarefa.getData());
        contentValues.put(TarefaContrato.TarefaDados.colunaEnviado, tarefa.getEnviado());
        if (tarefa.isConcluida()){
            Log.d("Concluida", "True");
            contentValues.put(TarefaContrato.TarefaDados.colunaConcluida, 1);
        } else {
            Log.d("Concluida", "False");
            contentValues.put(TarefaContrato.TarefaDados.colunaConcluida, 0);
        }
        long insert = db.insert(TarefaContrato.TarefaDados.tabela, null, contentValues);
        Log.d("Tarefa", tarefa.toString());
        if(insert == -1){
            Log.d("SQL", "Erro");
        } else {
            Log.d("SQL", "Inserido");
        }
        db.close();
    }

    public List<Tarefa> todasTarefas(){
        List<Tarefa> tarefas = new ArrayList<>();
        db = tarefaContrato.getReadableDatabase();
        String[] campos = {TarefaContrato.TarefaDados._ID, TarefaContrato.TarefaDados.colunaDeUsuario,
                TarefaContrato.TarefaDados.colunaParaUsuario, TarefaContrato.TarefaDados.colunaTitulo,
                TarefaContrato.TarefaDados.colunaDescricao, TarefaContrato.TarefaDados.colunaData,
                TarefaContrato.TarefaDados.colunaConcluida, TarefaContrato.TarefaDados.colunaEnviado};
        Cursor cursor = db.query(TarefaContrato.TarefaDados.tabela, campos,
                null, null, null, null, null);
        while(cursor.moveToNext()){
            Tarefa tarefa = new Tarefa();
            tarefa.setId(cursor.getInt(0));
            tarefa.setDeUsuario(cursor.getString(1));
            tarefa.setParaUsuario(cursor.getString(2));
            tarefa.setTitulo(cursor.getString(3));
            tarefa.setDescricao(cursor.getString(4));
            tarefa.setData(cursor.getString(5));
            int flag = cursor.getInt(6);
            if(flag == 0){
                tarefa.setConcluida(false);
            } else {
                tarefa.setConcluida(true);
            }
            tarefa.setEnviado(cursor.getInt(7));
            tarefas.add(tarefa);
        }
        cursor.close();
        db.close();
        return tarefas;
    }

    public List<Tarefa> todasNaoConcluidas(String usuario){
        List<Tarefa> tarefas = new ArrayList<>();
        db = tarefaContrato.getReadableDatabase();
        String[] campos = {TarefaContrato.TarefaDados._ID, TarefaContrato.TarefaDados.colunaDeUsuario,
                TarefaContrato.TarefaDados.colunaParaUsuario, TarefaContrato.TarefaDados.colunaTitulo,
                TarefaContrato.TarefaDados.colunaDescricao, TarefaContrato.TarefaDados.colunaData,
                TarefaContrato.TarefaDados.colunaConcluida, TarefaContrato.TarefaDados.colunaEnviado};
        Cursor cursor = db.query(TarefaContrato.TarefaDados.tabela, campos,
                "(concluida = 0) AND (deUsuario = ? OR paraUsuario = ?)",
                new String[]{usuario, usuario}, null, null, null);
        while(cursor.moveToNext()){
            Tarefa tarefa = new Tarefa();
            tarefa.setId(cursor.getInt(0));
            tarefa.setDeUsuario(cursor.getString(1));
            tarefa.setParaUsuario(cursor.getString(2));
            tarefa.setTitulo(cursor.getString(3));
            tarefa.setDescricao(cursor.getString(4));
            tarefa.setData(cursor.getString(5));
            int flag = cursor.getInt(6);
            if(flag == 0){
                tarefa.setConcluida(false);
            } else {
                tarefa.setConcluida(true);
            }
            tarefa.setEnviado(cursor.getInt(7));
            tarefas.add(tarefa);
        }
        cursor.close();
        db.close();
        return tarefas;
    }

    public List<Tarefa> todasNaoEnviadas(String usuario){
        List<Tarefa> tarefas = new ArrayList<>();
        db = tarefaContrato.getReadableDatabase();
        String[] campos = {TarefaContrato.TarefaDados._ID, TarefaContrato.TarefaDados.colunaDeUsuario,
                TarefaContrato.TarefaDados.colunaParaUsuario, TarefaContrato.TarefaDados.colunaTitulo,
                TarefaContrato.TarefaDados.colunaDescricao, TarefaContrato.TarefaDados.colunaData,
                TarefaContrato.TarefaDados.colunaConcluida, TarefaContrato.TarefaDados.colunaEnviado};
        Cursor cursor = db.query(TarefaContrato.TarefaDados.tabela, campos,
                "(enviado = 0) AND (deUsuario = ?)",
                new String[]{usuario}, null, null, null);
        while(cursor.moveToNext()){
            Tarefa tarefa = new Tarefa();
            tarefa.setId(cursor.getInt(0));
            tarefa.setDeUsuario(cursor.getString(1));
            tarefa.setParaUsuario(cursor.getString(2));
            tarefa.setTitulo(cursor.getString(3));
            tarefa.setDescricao(cursor.getString(4));
            tarefa.setData(cursor.getString(5));
            int flag = cursor.getInt(6);
            if(flag == 0){
                tarefa.setConcluida(false);
            } else {
                tarefa.setConcluida(true);
            }
            tarefa.setEnviado(0);
            tarefas.add(tarefa);
        }
        cursor.close();
        db.close();
        return tarefas;
    }

    public void concluirTarefa(Tarefa tarefa){
        db = tarefaContrato.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TarefaContrato.TarefaDados._ID, tarefa.getId());
        contentValues.put(TarefaContrato.TarefaDados.colunaDeUsuario, tarefa.getDeUsuario());
        contentValues.put(TarefaContrato.TarefaDados.colunaParaUsuario, tarefa.getParaUsuario());
        contentValues.put(TarefaContrato.TarefaDados.colunaTitulo, tarefa.getTitulo());
        contentValues.put(TarefaContrato.TarefaDados.colunaDescricao, tarefa.getDescricao());
        contentValues.put(TarefaContrato.TarefaDados.colunaData, tarefa.getData());
        contentValues.put(TarefaContrato.TarefaDados.colunaConcluida, 1);
        contentValues.put(TarefaContrato.TarefaDados.colunaEnviado, tarefa.getEnviado());
        int update = db.update(TarefaContrato.TarefaDados.tabela, contentValues,
                "_id = ?", new String[]{String.valueOf(tarefa.getId())});
        Log.d("Atualizados", ": " + update);
    }

    public void marcarComoEnviada(Tarefa tarefa){
        db = tarefaContrato.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TarefaContrato.TarefaDados._ID, tarefa.getId());
        contentValues.put(TarefaContrato.TarefaDados.colunaDeUsuario, tarefa.getDeUsuario());
        contentValues.put(TarefaContrato.TarefaDados.colunaParaUsuario, tarefa.getParaUsuario());
        contentValues.put(TarefaContrato.TarefaDados.colunaTitulo, tarefa.getTitulo());
        contentValues.put(TarefaContrato.TarefaDados.colunaDescricao, tarefa.getDescricao());
        contentValues.put(TarefaContrato.TarefaDados.colunaData, tarefa.getData());
        contentValues.put(TarefaContrato.TarefaDados.colunaConcluida, tarefa.isConcluida());
        contentValues.put(TarefaContrato.TarefaDados.colunaEnviado, 1);
        int update = db.update(TarefaContrato.TarefaDados.tabela, contentValues,
                "_id = ?", new String[]{String.valueOf(tarefa.getId())});
        Log.d("Atualizados", ": " + update);
    }

}
