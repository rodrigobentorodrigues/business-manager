package bm.pdm.ifpb.com.businessmanager.infra;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Duvida;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DuvidaAdapter extends BaseAdapter{

    private List<Duvida> duvidas;
    private Context contexto;

    public DuvidaAdapter(List<Duvida> duvidas, Context contexto) {
        this.duvidas = duvidas;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return duvidas.size();
    }

    @Override
    public Object getItem(int position) {
        return duvidas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Duvida duvida = (Duvida) getItem(position);
        LayoutInflater inflater = (LayoutInflater) contexto.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View visao = inflater.inflate(R.layout.valores_duvida, parent, false);

        TextView deUsu = visao.findViewById(R.id.deUsuario);
        TextView paraUsu = visao.findViewById(R.id.paraUsuario);
        TextView mensagem = visao.findViewById(R.id.mensagem);

        deUsu.setText(duvida.getDeUsuario());
        paraUsu.setText(duvida.getParaUsuario());

        String pergunta = duvida.getPergunta();
        mensagem.setText(pergunta);

        return visao;
    }
}
