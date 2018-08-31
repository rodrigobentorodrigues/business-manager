package bm.pdm.ifpb.com.businessmanager.infra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bm.pdm.ifpb.com.businessmanager.R;
import bm.pdm.ifpb.com.businessmanager.domains.Tarefa;

public class TarefaAdapter extends BaseAdapter{

    private List<Tarefa> tarefas;
    private Context contexto;

    public TarefaAdapter(List<Tarefa> tarefas, Context contexto) {
        this.tarefas = tarefas;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return tarefas.size();
    }

    @Override
    public Object getItem(int position) {
        return tarefas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tarefas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tarefa tarefa = (Tarefa) getItem(position);

        LayoutInflater inflater = (LayoutInflater) contexto.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View visao = inflater.inflate(R.layout.valores_tarefa, parent, false);

        TextView paraUsuario = visao.findViewById(R.id.campoPara);
        TextView titulo = visao.findViewById(R.id.campoTitulo);
        paraUsuario.setText(tarefa.getParaUsuario());
        titulo.setText(tarefa.getTitulo());

        return visao;
    }
}
