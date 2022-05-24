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
import br.com.appdodinheiro.model.Registro;
import br.com.appdodinheiro.util.DataUtil;
import br.com.appdodinheiro.util.MoedaUtil;

public class DespesasAdapter extends BaseAdapter {

    private List<Registro> registros;
    private final Context context;
    private RegistroDAO dao;
    private AppDatabase Database;

    public DespesasAdapter(Context context, List<Registro> dao, AppDatabase database) {
        this.context = context;
        this.registros = dao;
        this.Database = database;
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

        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_despesa, viewGroup, false);
        Registro RegistroDevolvido = registros.get(posicao);

        TextView CampoNome = viewCriada.findViewById(R.id.item_especifico_despesas_nome_texto);
        TextView CampoValor = viewCriada.findViewById(R.id.item_especifico_despesas_valor_total_texto);
        TextView CampoValorAcumulado = viewCriada.findViewById(R.id.item_especifico_despesas_valor_economizado_texto);
        TextView CampoData = viewCriada.findViewById(R.id.item_especifico_despesas_data_texto);

        String NomeRegistro = RegistroDevolvido.getNome();
        BigDecimal ValorRegistro = RegistroDevolvido.getValor();
        Calendar DataRegistro = RegistroDevolvido.getData();

        BigDecimal ValorAcumuladoRegistro = new BigDecimal("0");

        dao = Database.getRoomRegistroDAO();

        List<Registro> valores = dao.todosRegistrosDoNome(RegistroDevolvido.getNome());

        for (Registro r :
                valores) {
            long dataR = r.getData().getTimeInMillis();
            long dataRegistro = RegistroDevolvido.getData().getTimeInMillis();
            if (dataR <= dataRegistro) {
                ValorAcumuladoRegistro = ValorAcumuladoRegistro.add(r.getValor());
            }
        }

        CampoNome.setText(NomeRegistro);
        CampoValor.setText("Valor Total:\n" + MoedaUtil.formataParaBrasileiro(ValorRegistro));
        CampoData.setText("Data de Pagamento:\n" + DataUtil.formataData(DataRegistro));
        CampoValorAcumulado.setText("Valor gasto nos ultimos meses:\n" + MoedaUtil.formataParaBrasileiro(ValorAcumuladoRegistro));

        return viewCriada;
    }
}
