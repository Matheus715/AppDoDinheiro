package br.com.appdodinheiro.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import br.com.appdodinheiro.R;
import br.com.appdodinheiro.database.dao.RegistroDAO;
import br.com.appdodinheiro.model.Registro;
import br.com.appdodinheiro.util.DataUtil;
import br.com.appdodinheiro.util.MoedaUtil;

public class ProximosValoresAdapter extends BaseAdapter {


    private List<Registro> registros;
    private final Context context;
    private RegistroDAO dao;

    public ProximosValoresAdapter(Context context, List<Registro> dao) {
        this.context = context;
        registros = dao;
    }

    @Override
    public int getCount() {
        return registros.size();
    }

    @Override
    public Object getItem(int posicao) {
        return registros.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return registros.get(posicao).getID();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {

        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_proximos_valores, viewGroup, false);
        Registro RegistroDevolvido = registros.get(posicao);

        TextView CampoNome = viewCriada.findViewById(R.id.list_item_infos_local);
        TextView CampoValor = viewCriada.findViewById(R.id.list_item_infos_valor);
        TextView CampoData = viewCriada.findViewById(R.id.list_item_infos_data);

        String NomeRegistro = RegistroDevolvido.getNome();
        BigDecimal ValorRegistro = RegistroDevolvido.getValor();
        Calendar DataRegistro = RegistroDevolvido.getData();

        CampoNome.setText(NomeRegistro);
        CampoValor.setText(MoedaUtil.formataParaBrasileiro(ValorRegistro));
        CampoData.setText(DataUtil.formataData(DataRegistro));

        return viewCriada;
    }

    public void atualiza(List<Registro> registros) {
        this.registros.clear();
        this.registros.addAll(registros);
        notifyDataSetChanged();
    }

    public void remove(Registro registro) {
        registros.remove(registro);
        notifyDataSetChanged();
    }
}
