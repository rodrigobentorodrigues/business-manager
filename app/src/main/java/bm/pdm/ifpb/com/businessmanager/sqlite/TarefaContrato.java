package bm.pdm.ifpb.com.businessmanager.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class TarefaContrato extends SQLiteOpenHelper {

    private static final String sqlCriarTabela = "CREATE TABLE " + TarefaDados.tabela + "( " +
            TarefaDados._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TarefaDados.colunaDeUsuario + " TEXT, " +
            TarefaDados.colunaParaUsuario + " TEXT, " +
            TarefaDados.colunaTitulo + " TEXT, " +
            TarefaDados.colunaDescricao + " TEXT, " +
            TarefaDados.colunaData + " TEXT, " +
            TarefaDados.colunaConcluida + " INTEGER, " +
            TarefaDados.colunaEnviado + " INTEGER);";
    private static final String sqlExcluirTabela = "DROP TABLE IF EXISTS " + TarefaDados.tabela;

    public TarefaContrato(Context context) {
        super(context, TarefaDados.banco, null, TarefaDados.versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCriarTabela);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("UPDATE", "TAREFA");
        db.execSQL(sqlExcluirTabela);
        onCreate(db);
    }

    public static class TarefaDados implements BaseColumns {
        public static final String banco = "tarefa.db";
        public static final String tabela = "tarefa";
        public static final int versao = 4;
        public static final String colunaDeUsuario = "deUsuario";
        public static final String colunaParaUsuario = "paraUsuario";
        public static final String colunaTitulo = "titulo";
        public static final String colunaDescricao = "descricao";
        public static final String colunaData = "data";
        public static final String colunaConcluida = "concluida";
        public static final String colunaEnviado = "enviado";
    }

}
