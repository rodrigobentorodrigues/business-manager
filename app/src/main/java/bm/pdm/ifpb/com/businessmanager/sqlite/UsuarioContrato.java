package bm.pdm.ifpb.com.businessmanager.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class UsuarioContrato extends SQLiteOpenHelper {

    public UsuarioContrato(Context context) {
        super(context, UsuarioDados.nomeBanco, null, UsuarioDados.versaoBanco);
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

    public static class UsuarioDados implements BaseColumns {
        public static final String nomeBanco = "usuario.db";
        public static final int versaoBanco = 2;
        public static final String tabela = "usuario";
        public static final String colunaNome = "nome";
        public static final String colunaCargo = "cargo";
        public static final String colunaLogin = "login";
        public static final String colunaSenha = "senha";
        public static final String colunaTelefone = "telefone";
        public static final String colunaIdEmpresa = "idEmpresa";
        public static final String colunaImagem = "imagem";
    }

    private static final String sqlCriarTabela =
            "CREATE TABLE " + UsuarioDados.tabela + " (" +
                    UsuarioDados._ID + " SERIAL PRIMARY KEY," +
                    UsuarioDados.colunaNome + " TEXT," +
                    UsuarioDados.colunaCargo + " TEXT, " +
                    UsuarioDados.colunaLogin + " TEXT, " +
                    UsuarioDados.colunaSenha + " TEXT, " +
                    UsuarioDados.colunaTelefone + " TEXT, " +
                    UsuarioDados.colunaIdEmpresa + " INTEGER, " +
                    UsuarioDados.colunaImagem + " BLOB);";
    private static final String sqlExcluirTabela =
            "DROP TABLE IF EXISTS " + UsuarioDados.tabela;

}
