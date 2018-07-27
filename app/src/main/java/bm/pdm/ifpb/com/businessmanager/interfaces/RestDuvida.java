package bm.pdm.ifpb.com.businessmanager.interfaces;

import bm.pdm.ifpb.com.businessmanager.domains.Duvida;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestDuvida {

    @POST("duvida/adicionar")
    Call<Duvida> adicionarDuvida(@Query("id") int id,
                                 @Query("deUsuario") String deUsuario,
                                 @Query("paraUsuario") String paraUsuario,
                                 @Query("pergunta") String pergunta,
                                 @Query("resposta") String resposta);

    @POST("duvida/responder")
    Call<Duvida> responderDuvida(@Query("id") int id,
                                 @Query("deUsuario") String deUsuario,
                                 @Query("paraUsuario") String paraUsuario,
                                 @Query("pergunta") String pergunta,
                                 @Query("resposta") String resposta);

}
