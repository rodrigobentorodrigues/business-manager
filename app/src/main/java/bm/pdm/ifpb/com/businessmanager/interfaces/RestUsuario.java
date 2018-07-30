package bm.pdm.ifpb.com.businessmanager.interfaces;

import bm.pdm.ifpb.com.businessmanager.domains.Usuario;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestUsuario {

    @POST("usuario/adicionarFuncionario")
    Call<Usuario> adicionarFuncionario(@Query("id") int id,
                                      @Query("nome") String nome,
                                      @Query("funcao") String funcao,
                                      @Query("telefone") String telefone,
                                      @Query("login") String login,
                                      @Query("senha") String senha,
                                      @Query("imagem") byte[] imagem,
                                      @Query("idEmpresa") int idEmpresa);

    @POST("usuario/adicionarAdministrador")
    Call<Usuario> adicionarAdministrador(@Query("id") int id,
                                      @Query("nome") String nome,
                                      @Query("funcao") String funcao,
                                      @Query("telefone") String telefone,
                                      @Query("login") String login,
                                      @Query("senha") String senha,
                                      @Query("imagem") byte[] imagem,
                                      @Query("nomeEmpresa") String nomeEmpresa);



}
