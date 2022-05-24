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

public class ExtratoAdapter extends BaseAdapter {

    private List<Registro> registros;
    private final Context context;
    private RegistroDAO dao;

    public ExtratoAdapter(Context context, List<Registro> dao) {
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

        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_extrato, viewGroup, false);
        Registro RegistroDevolvido = registros.get(posicao);

        TextView CampoNome = viewCriada.findViewById(R.id.item_extrato_infos_nome);
        TextView CampoValor = viewCriada.findViewById(R.id.item_extrato_infos_valor);
        TextView CampoData = viewCriada.findViewById(R.id.item_extrato_infos_data);
        CardView background = viewCriada.findViewById(R.id.item_extrato_banner);

        String NomeRegistro = RegistroDevolvido.getNome();
        BigDecimal ValorRegistro = RegistroDevolvido.getValor();
        Calendar DataRegistro = RegistroDevolvido.getData();
        String TipoRegistro = RegistroDevolvido.getTipo();

        CampoNome.setText(NomeRegistro);
        CampoValor.setText(MoedaUtil.formataParaBrasileiro(ValorRegistro));
        CampoData.setText(DataUtil.formataData(DataRegistro));

        switch (TipoRegistro) {
            case "Despesa":
                background.setCardBackgroundColor(Color.parseColor("#EC1D23"));
                break;
            case "Receita":
                background.setCardBackgroundColor(Color.parseColor("#1E90D6"));
                break;
            case "Meta":
                background.setCardBackgroundColor(Color.parseColor("#FEBE4E"));
                break;
        }

        return viewCriada;
    }

    public void atualiza(List<Registro> registro){
        this.registros.clear();
        this.registros.addAll(registro);
        notifyDataSetChanged();
    }

    public void remove(Registro registro) {
        registros.remove(registro);
        notifyDataSetChanged();
    }
}
