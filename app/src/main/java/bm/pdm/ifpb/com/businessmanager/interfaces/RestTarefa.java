package bm.pdm.ifpb.com.businessmanager.interfaces;

import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestTarefa {

    @POST("tarefa/adicionar")
    Call<Tarefa> adicionarTarefa(@Query("id") int id,
                                 @Query("deUsuario") String deUsuario,
                                 @Query("paraUsuario") String paraUsuario,
                                 @Query("titulo") String titulo,
                                 @Query("descricao") String descricao,
                                 @Query("data") String data,
                                 @Query("concluida") boolean concluida);

    @POST("tarefa/concluir")
    Call<Tarefa> responderTarefa(@Query("id") int id,
                                 @Query("deUsuario") String deUsuario,
                                 @Query("paraUsuario") String paraUsuario,
                                 @Query("titulo") String titulo,
                                 @Query("descricao") String descricao,
                                 @Query("data") String data,
                                 @Query("concluida") boolean concluida);

}
