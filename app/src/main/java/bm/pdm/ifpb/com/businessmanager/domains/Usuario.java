package bm.pdm.ifpb.com.businessmanager.domains;

import java.io.Serializable;

public class Usuario implements Serializable{

    private int id;
    private String nome;
    private String cargo;
    private String login;
    private String senha;
    private String telefone;
    private int idEmpresa;
    private byte[] imagem;

    public Usuario(int id, String nome, String cargo,
                   String login, String senha, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
        this.telefone = telefone;
    }

    public Usuario(int id, String nome, String cargo,
                   String login, String senha, String telefone, int idEmpresa, byte[] imagem) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
        this.telefone = telefone;
        this.idEmpresa = idEmpresa;
        this.imagem = imagem;
    }

    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cargo='" + cargo + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", telefone='" + telefone + '\'' +
                ", idEmpresa=" + idEmpresa +
                '}';
    }

}
