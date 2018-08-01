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

    public void inserirTarefa(Tarefa tarefa){
        db = tarefaContrato.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TarefaContrato.TarefaDados._ID, tarefa.getId());
        contentValues.put(TarefaContrato.TarefaDados.colunaDeUsuario, tarefa.getDeUsuario());
        contentValues.put(TarefaContrato.TarefaDados.colunaParaUsuario, tarefa.getParaUsuario());
        contentValues.put(TarefaContrato.TarefaDados.colunaTitulo, tarefa.getTitulo());
        contentValues.put(TarefaContrato.TarefaDados.colunaDescricao, tarefa.getDescricao());
        contentValues.put(TarefaContrato.TarefaDados.colunaData, tarefa.getData());
        // contentValues.put(TarefaContrato.TarefaDados.colunaConcluida, tarefa.get);
        long insert = db.insert(TarefaContrato.TarefaDados.tabela, null, contentValues);
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
                TarefaContrato.TarefaDados.colunaDescricao, TarefaContrato.TarefaDados.colunaData};
        Cursor cursor = db.query(TarefaContrato.TarefaDados.tabela, campos,
                null, null, null, null, null);
//        if(cursor != null){
//            cursor.moveToFirst();
//        }
        while(cursor.moveToNext()){
            Tarefa tarefa = new Tarefa();
            tarefa.setId(cursor.getInt(0));
            tarefa.setDeUsuario(cursor.getString(1));
            tarefa.setParaUsuario(cursor.getString(2));
            tarefa.setTitulo(cursor.getString(3));
            tarefa.setDescricao(cursor.getString(4));
            tarefa.setData(cursor.getString(5));
            tarefas.add(tarefa);
        }
        cursor.close();
        db.close();
        return tarefas;
    }

}
