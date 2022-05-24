package br.com.appdodinheiro.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.appdodinheiro.R;
import br.com.appdodinheiro.database.AppDatabase;
import br.com.appdodinheiro.database.dao.RegistroDAO;
import br.com.appdodinheiro.model.Registro;
import br.com.appdodinheiro.ui.adapter.ExtratoAdapter;
import br.com.appdodinheiro.ui.adapter.ProximosValoresAdapter;
import br.com.appdodinheiro.util.MesUtil;
import br.com.appdodinheiro.util.MoedaUtil;

public class MainActivity extends AppCompatActivity {

    private RegistroDAO dao;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConfiguraMenuPrincipal();

        database = AppDatabase.getInstance(this);
        dao = database.getRoomRegistroDAO();

        ConfiguraDashboard();
        ConfiguraProximosValores();
        ConfiguraExtrato();

    }

    private void ConfiguraMenuPrincipal() {
        ConfiguraBotaoExtrato();
        ConfiguraBotaoDespesas();
        ConfiguraBotaoReceitas();
        ConfiguraBotaoMetas();
    }

    private void ConfiguraBotaoExtrato() {
        CardView BotaoExtrato = findViewById(R.id.Home_Extrato_Container);
        Intent VaiParaExtrato = new Intent(MainActivity.this, ExtratoActivity.class);
        BotaoExtrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaExtrato);
            }
        });
    }

    private void ConfiguraBotaoDespesas() {
        CardView BotaoDespesas = findViewById(R.id.Home_Despesas_Container);
        Intent VaiParaDespesas = new Intent(MainActivity.this, DespesasActivity.class);
        BotaoDespesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaDespesas);
            }
        });
    }

    private void ConfiguraBotaoReceitas() {
        CardView BotaoReceitas = findViewById(R.id.Home_Receitas_Container);
        Intent VaiParaReceitas = new Intent(MainActivity.this, ReceitasActivity.class);
        BotaoReceitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaReceitas);
            }
        });
    }

    private void ConfiguraBotaoMetas() {
        CardView BotaoMetas = findViewById(R.id.Home_Metas_Container);
        Intent VaiParaMetas = new Intent(MainActivity.this, MetasActivity.class);
        BotaoMetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaMetas);
            }
        });
    }

    private void ConfiguraDashboard() {
        Calendar hoje = Calendar.getInstance();

        BigDecimal despesas = new BigDecimal("0");
        BigDecimal receitas = new BigDecimal("0");
        BigDecimal metas = new BigDecimal("0");

        String mesEmTexto = "";
        mesEmTexto = MesUtil.getMesEmTexto(hoje, mesEmTexto);

        TextView textoDespesas = findViewById(R.id.Home_Dashboard_Despesa_Texto);
        TextView textoReceitas = findViewById(R.id.Home_Dashboard_Receita_Texto);
        TextView textoMetas = findViewById(R.id.Home_Dashboard_Meta_Texto);

        List<Registro> despesasDoMes = dao.registrosDoMesEAno("Despesa", hoje.get(Calendar.MONTH), hoje.get(Calendar.YEAR));
        List<Registro> receitasDoMes = dao.registrosDoMesEAno("Receita", hoje.get(Calendar.MONTH), hoje.get(Calendar.YEAR));
        List<Registro> metasDoMes = dao.registrosDoMesEAno("Meta", hoje.get(Calendar.MONTH), hoje.get(Calendar.YEAR));

        despesas = getSoma(despesasDoMes, despesas);
        textoDespesas.setText("No mês de " + mesEmTexto + " você gastou\n" + MoedaUtil.formataParaBrasileiro(despesas) + "\ncom despesas");

        receitas = getSoma(receitasDoMes, receitas);
        textoReceitas.setText("No mês de " + mesEmTexto + " você recebeu\n" + MoedaUtil.formataParaBrasileiro(receitas) + "\ncom receitas");

        metas = getSoma(metasDoMes, metas);
        textoMetas.setText("No mês de " + mesEmTexto + " você investiu\n" + MoedaUtil.formataParaBrasileiro(metas) + "\nem suas metas!!");
    }

    private BigDecimal getSoma(List<Registro> lista, BigDecimal soma) {
        for (Registro r :
                lista) {
            soma = soma.add(r.getValor());
        }
        return soma;
    }

    private void ConfiguraExtrato() {

        ListView Extrato = findViewById(R.id.Home_Extrato_Lista);

        ExtratoAdapter adapter = new ExtratoAdapter(this, dao.todos());

        Extrato.setAdapter(adapter);
    }

    private void ConfiguraProximosValores() {

        ListView ProximasDespesas = findViewById(R.id.Home_Proximas_Despesas_Lista);
        ListView ProximasReceitas = findViewById(R.id.Home_Proximas_Receitas_Lista);

        ProximosValoresAdapter adapterDespesas = new ProximosValoresAdapter(
                this,
                dao.proximasRegistros(
                        "Despesa",
                        Calendar.getInstance().getTimeInMillis()
                )
        );

        ProximosValoresAdapter adapterReceitas = new ProximosValoresAdapter(
                this,
                dao.proximasRegistros(
                        "Receita",
                        Calendar.getInstance().getTimeInMillis()
                )
        );

        ProximasDespesas.setAdapter(adapterDespesas);
        ProximasReceitas.setAdapter(adapterReceitas);
    }

    private void exemplo() {
        Calendar data = Calendar.getInstance();
        BigDecimal valor;
        dao.salva(new Registro(
                "Viagem Meio do Ano",
                valor = new BigDecimal("100"),
                data = new GregorianCalendar(2022, 03, 20),
                "Meta"
        ));
        dao.salva(new Registro(
                "Viagem Meio do Ano",
                valor = new BigDecimal("100"),
                data = new GregorianCalendar(2022, 04, 20),
                "Meta"
        ));
        dao.salva(new Registro(
                "Viagem Meio do Ano",
                valor = new BigDecimal("100"),
                data = new GregorianCalendar(2022, 05, 20),
                "Meta"
        ));
    }

}