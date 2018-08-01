package bm.pdm.ifpb.com.businessmanager.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DuvidaContrato extends SQLiteOpenHelper {

    private static final String sqlCriarTabela = "CREATE TABLE " + DuvidaDados.tabela + " (" +
            DuvidaDados._ID + " INTEGER PRIMARY KEY, " +
            DuvidaDados.colunaDeUsuario + " TEXT, " +
            DuvidaDados.colunaParaUsuario + " TEXT, " +
            DuvidaDados.colunaPergunta + " TEXT, " +
            DuvidaDados.colunaResposta +" TEXT);";
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
        db.execSQL(sqlExcluirTabela);
        onCreate(db);
    }

    public static class DuvidaDados implements BaseColumns {
        public static String bancoDados = "duvida.db";
        public static int versaoBanco = 1;
        public static String tabela = "duvida";
        public static String colunaDeUsuario = "deUsuario";
        public static String colunaParaUsuario = "paraUsuario";
        public static String colunaPergunta = "pergunta";
        public static String colunaResposta = "resposta";
    }

}
