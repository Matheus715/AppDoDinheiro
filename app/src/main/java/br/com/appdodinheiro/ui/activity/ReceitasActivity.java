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
import br.com.appdodinheiro.ui.adapter.ReceitasAdapter;
import br.com.appdodinheiro.util.MesUtil;

public class ReceitasActivity extends AppCompatActivity {

    private RegistroDAO dao;
    private AppDatabase database;
    private ReceitasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        ConfiguraMenuPrincipal();

        database = AppDatabase.getInstance(this);
        dao = database.getRoomRegistroDAO();

        ConfiguraFAB();

        ConfiguraFiltro();
        ConfiguraListaReceitas(adapter = new ReceitasAdapter(this, dao.registros("Receita")));
    }

    private void ConfiguraFAB() {
        FloatingActionButton FAB = findViewById(R.id.Receitas_Activity_FAB);

        FAB.setOnClickListener(new View.OnClickListener() {
            Intent VaiParaFormulario = new Intent(ReceitasActivity.this, FormularioActivity.class);

            @Override
            public void onClick(View v) {
                startActivity(VaiParaFormulario);
            }
        });
    }


    private void ConfiguraFiltro() {
        Spinner SpiniSplendiMes = findViewById(R.id.Receitas_Activity_Mes);
        Spinner SpiniSplendiAno = findViewById(R.id.Receitas_Activity_Ano);

        ArrayAdapter<String> adapterMes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, MESES);
        ArrayAdapter<Integer> adapterAno = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ANOS);

        SpiniSplendiMes.setAdapter(adapterMes);
        SpiniSplendiAno.setAdapter(adapterAno);

        SetDataAtual(SpiniSplendiMes, SpiniSplendiAno);

        CardView BotaoFiltro = findViewById(R.id.Receitas_Activity_Botao_Filtro);

        BotaoFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mesEmTexto = SpiniSplendiMes.getSelectedItem().toString();
                int mesEscolhido = MesUtil.getMesEmInt(mesEmTexto);
                int anoEscolhido = Integer.valueOf(SpiniSplendiAno.getSelectedItem().toString());

                ConfiguraListaReceitas(
                        adapter = new ReceitasAdapter(
                                ReceitasActivity.this,
                                dao.registrosDoMesEAno("Receita", mesEscolhido, anoEscolhido))
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

    private void ConfiguraListaReceitas(ReceitasAdapter adapter) {
        ListView Lista = findViewById(R.id.Receitas_Activity_Lista);
        Lista.setAdapter(adapter);
    }

    private void ConfiguraMenuPrincipal() {
        ConfiguraBotaoHome();
        ConfiguraBotaoExtrato();
        ConfiguraBotaoDespesas();
        ConfiguraBotaoMetas();
    }

    private void ConfiguraBotaoHome() {
        CardView Icone = findViewById(R.id.Receitas_Activity_MainIcon);
        CardView Titulo = findViewById(R.id.Receitas_Activity_App_Title);
        Intent VaiParaHome = new Intent(ReceitasActivity.this, MainActivity.class);
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

    private void ConfiguraBotaoDespesas() {
        CardView BotaoDespesas = findViewById(R.id.Receitas_Activity_Despesas_Container);
        Intent VaiParaDespesas = new Intent(ReceitasActivity.this, DespesasActivity.class);
        BotaoDespesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaDespesas);
            }
        });
    }

    private void ConfiguraBotaoExtrato() {
        CardView BotaoExtrato = findViewById(R.id.Receitas_Activity_Extrato_Container);
        Intent VaiParaExtrato = new Intent(ReceitasActivity.this, ExtratoActivity.class);
        BotaoExtrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaExtrato);
            }
        });
    }

    private void ConfiguraBotaoMetas() {
        CardView BotaoMetas = findViewById(R.id.Receitas_Activity_Metas_Container);
        Intent VaiParaMetas = new Intent(ReceitasActivity.this, MetasActivity.class);
        BotaoMetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaMetas);
            }
        });
    }

}