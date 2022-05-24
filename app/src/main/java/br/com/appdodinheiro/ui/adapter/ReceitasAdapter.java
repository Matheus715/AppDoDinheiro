package br.com.appdodinheiro.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import br.com.appdodinheiro.R;
import br.com.appdodinheiro.database.dao.RegistroDAO;
import br.com.appdodinheiro.model.Registro;
import br.com.appdodinheiro.util.DataUtil;
import br.com.appdodinheiro.util.MoedaUtil;

public class ReceitasAdapter extends BaseAdapter {

    private List<Registro> registros;
    private final Context context;
    private RegistroDAO dao;

    public ReceitasAdapter(Context context, List<Registro> dao) {
        this.context = context;
        this.registros = dao;
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

        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_receita, viewGroup, false);
        Registro RegistroDevolvido = registros.get(posicao);

        TextView CampoNome = viewCriada.findViewById(R.id.item_especifico_receitas_nome_texto);
        TextView CampoValor = viewCriada.findViewById(R.id.item_especifico_receitas_valor_total_texto);
        TextView CampoData = viewCriada.findViewById(R.id.item_especifico_receitas_data_texto);

        String NomeRegistro = RegistroDevolvido.getNome();
        BigDecimal ValorRegistro = RegistroDevolvido.getValor();
        Calendar DataRegistro = RegistroDevolvido.getData();

        CampoNome.setText(NomeRegistro);
        CampoValor.setText("Valor Total:\n" + MoedaUtil.formataParaBrasileiro(ValorRegistro));
        CampoData.setText("Data de Recebimento:\n" + DataUtil.formataData(DataRegistro));

        return viewCriada;
    }
}
