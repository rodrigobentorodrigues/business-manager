package bm.pdm.ifpb.com.businessmanager.domains;

public class RepoTemp {

    private static String valor;
    private static Usuario usuario;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        RepoTemp.usuario = usuario;
    }
}
