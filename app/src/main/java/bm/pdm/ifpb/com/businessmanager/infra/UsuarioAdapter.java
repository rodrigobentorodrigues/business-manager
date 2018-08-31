package bm.pdm.ifpb.com.businessmanager.infra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Usuario;

public class UsuarioAdapter extends BaseAdapter {

    private List<Usuario> usuarios;
    private Context contexto;

    public UsuarioAdapter(List<Usuario> usuarios, Context contexto) {
        this.usuarios = usuarios;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return usuarios.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Usuario usuario = (Usuario) getItem(position);

        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View visao = inflater.inflate(R.layout.valores_usuario, parent, false);

        TextView inicial = visao.findViewById(R.id.valorInicial);
        TextView nome = visao.findViewById(R.id.valorNome);
        TextView numero = visao.findViewById(R.id.valorNumero);
        inicial.setText(usuario.getNome().substring(0, 1));
        nome.setText(usuario.getNome());
        numero.setText(usuario.getTelefone());

        return visao;
    }
}
