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
import br.com.appdodinheiro.database.AppDatabase;
import br.com.appdodinheiro.database.dao.RegistroDAO;
import br.com.appdodinheiro.model.Meta;
import br.com.appdodinheiro.model.Registro;
import br.com.appdodinheiro.util.DataUtil;
import br.com.appdodinheiro.util.MoedaUtil;

public class MetasAdapterMetas extends BaseAdapter {
    private List<Meta> metas;
    private final Context context;
    private RegistroDAO dao;
    private AppDatabase Database;

    public MetasAdapterMetas(Context context, List<Meta> dao, AppDatabase database) {
        this.context = context;
        this.metas = dao;
        this.Database = database;
    }

    @Override
    public int getCount() {
        return metas.size();
    }

    @Override
    public Object getItem(int posicao) {
        return metas.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return metas.get(posicao).getID();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {

        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_meta, viewGroup, false);
        Meta MetaDevolvida = metas.get(posicao);

        TextView CampoNome = viewCriada.findViewById(R.id.item_especifico_metas_nome_texto);
        TextView CampoValor = viewCriada.findViewById(R.id.item_especifico_metas_valor_total_texto);
        TextView CampoValorAcumulado = viewCriada.findViewById(R.id.item_especifico_metas_valor_economizado_texto);
        TextView CampoData = viewCriada.findViewById(R.id.item_especifico_metas_data_texto);

        String NomeRegistro = MetaDevolvida.getNome();
        BigDecimal ValorRegistro = MetaDevolvida.getValorTotal();
        Calendar DataRegistro = MetaDevolvida.getDataDeFechamento();

        BigDecimal ValorAcumuladoRegistro = new BigDecimal("0");

        dao = Database.getRoomRegistroDAO();

        List<Registro> valores = dao.todosRegistrosDoNome(MetaDevolvida.getNome());

        for (Registro r :
                valores) {
            ValorAcumuladoRegistro = ValorAcumuladoRegistro.add(r.getValor());
        }

        CampoNome.setText(NomeRegistro);
        CampoValor.setText("Valor Total:\n" + MoedaUtil.formataParaBrasileiro(ValorRegistro));
        CampoData.setText("Data de Fechamento:\n" + DataUtil.formataData(DataRegistro));
        CampoValorAcumulado.setText("Total já investido:\n" + MoedaUtil.formataParaBrasileiro(ValorAcumuladoRegistro));

        return viewCriada;
    }
}
