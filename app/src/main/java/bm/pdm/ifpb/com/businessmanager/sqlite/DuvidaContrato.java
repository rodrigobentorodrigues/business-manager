package bm.pdm.ifpb.com.businessmanager.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DuvidaContrato extends SQLiteOpenHelper {

    private static final String sqlCriarTabela = "CREATE TABLE " + DuvidaDados.tabela + " (" +
            DuvidaDados._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DuvidaDados.colunaDeUsuario + " TEXT, " +
            DuvidaDados.colunaParaUsuario + " TEXT, " +
            DuvidaDados.colunaPergunta + " TEXT, " +
            DuvidaDados.colunaResposta +" TEXT, "+
            DuvidaDados.colunaEnviado + " INTEGER);";
    private static final String sqlExcluirTabela = "DROP TABLE IF EXISTS " + DuvidaDados.tabela;

    public DuvidaContrato(Context context) {
        super(context, DuvidaDados.bancoDados, null, DuvidaDados.versaoBanco);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCriarTabela);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("UPDATE", "DUVIDA");
        db.execSQL(sqlExcluirTabela);
        onCreate(db);
    }

    public static class DuvidaDados implements BaseColumns {
        public static final String bancoDados = "duvida.db";
        public static final int versaoBanco = 4;
        public static final String tabela = "duvida";
        public static final String colunaDeUsuario = "deUsuario";
        public static final String colunaParaUsuario = "paraUsuario";
        public static final String colunaPergunta = "pergunta";
        public static final String colunaResposta = "resposta";
        public static final String colunaEnviado = "enviado";
    }

}
