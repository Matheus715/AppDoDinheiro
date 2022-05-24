package br.com.appdodinheiro.ui.activity;

import static br.com.appdodinheiro.ui.activity.Constantes.ANOS;
import static br.com.appdodinheiro.ui.activity.Constantes.MESES;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import br.com.appdodinheiro.R;
import br.com.appdodinheiro.database.AppDatabase;
import br.com.appdodinheiro.database.dao.RegistroDAO;
import br.com.appdodinheiro.ui.adapter.DespesasAdapter;
import br.com.appdodinheiro.util.MesUtil;

public class DespesasActivity extends AppCompatActivity {

    private RegistroDAO dao;
    private AppDatabase database;
    private DespesasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        ConfiguraMenuPrincipal();

        database = AppDatabase.getInstance(this);
        dao = database.getRoomRegistroDAO();

        ConfiguraFAB();

        ConfiguraFiltro();
        ConfiguraListaDespesas(new DespesasAdapter(this, dao.registros("Despesa"), database));
    }

    private void ConfiguraFAB() {
        FloatingActionButton FAB = findViewById(R.id.Despesas_Activity_FAB);

        FAB.setOnClickListener(new View.OnClickListener() {
            Intent VaiParaFormulario = new Intent(DespesasActivity.this, FormularioActivity.class);
            @Override
            public void onClick(View v) {
                startActivity(VaiParaFormulario);
            }
        });
    }

    private void ConfiguraFiltro() {
        Spinner SpiniSplendiMes = findViewById(R.id.Despesas_Activity_Mes);
        Spinner SpiniSplendiAno = findViewById(R.id.Despesas_Activity_Ano);

        ArrayAdapter<String> adapterMes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, MESES);
        ArrayAdapter<Integer> adapterAno = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ANOS);

        SpiniSplendiMes.setAdapter(adapterMes);
        SpiniSplendiAno.setAdapter(adapterAno);

        SetDataAtual(SpiniSplendiMes, SpiniSplendiAno);

        CardView BotaoFiltro = findViewById(R.id.Despesas_Activity_Botao_Filtro);

        BotaoFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mesEmTexto = SpiniSplendiMes.getSelectedItem().toString();
                int mesEscolhido = MesUtil.getMesEmInt(mesEmTexto);
                int anoEscolhido = Integer.valueOf(SpiniSplendiAno.getSelectedItem().toString());

                ConfiguraListaDespesas(
                        adapter = new DespesasAdapter(
                                DespesasActivity.this,
                                dao.registrosDoMesEAno("Despesa", mesEscolhido, anoEscolhido),
                                database)
                );
            }
        });
    }

    private void SetDataAtual(Spinner SpiniSplendiMes, Spinner SpiniSplendiAno) {
        Calendar hoje = Calendar.getInstance();
        int MesAtual = hoje.get(Calendar.MONTH);
        int AnoAtual = hoje.get(Calendar.YEAR) - 2000;
        SpiniSplendiMes.setSelection(MesAtual);
        SpiniSplendiAno.setSelection(AnoAtual);
    }

    private void ConfiguraListaDespesas(DespesasAdapter adapter) {
        ListView Lista = findViewById(R.id.Despesas_Activity_Lista);
        Lista.setAdapter(adapter);
    }

    private void ConfiguraMenuPrincipal() {
        ConfiguraBotaoHome();
        ConfiguraBotaoExtrato();
        ConfiguraBotaoReceitas();
        ConfiguraBotaoMetas();
    }

    private void ConfiguraBotaoHome() {
        CardView Icone = findViewById(R.id.Despesas_Activity_MainIcon);
        CardView Titulo = findViewById(R.id.Despesas_Activity_App_Title);
        Intent VaiParaHome = new Intent(DespesasActivity.this, MainActivity.class);
        Icone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaHome);
            }
        });
        Titulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaHome);
            }
        });
    }

    private void ConfiguraBotaoExtrato() {
        CardView BotaoExtrato = findViewById(R.id.Despesas_Activity_Extrato_Container);
        Intent VaiParaExtrato = new Intent(DespesasActivity.this, ExtratoActivity.class);
        BotaoExtrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaExtrato);
            }
        });
    }

    private void ConfiguraBotaoReceitas() {
        CardView BotaoReceitas = findViewById(R.id.Despesas_Activity_Receitas_Container);
        Intent VaiParaReceitas = new Intent(DespesasActivity.this, ReceitasActivity.class);
        BotaoReceitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaReceitas);
            }
        });
    }

    private void ConfiguraBotaoMetas() {
        CardView BotaoMetas = findViewById(R.id.Despesas_Activity_Metas_Container);
        Intent VaiParaMetas = new Intent(DespesasActivity.this, MetasActivity.class);
        BotaoMetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaMetas);
            }
        });
    }

}