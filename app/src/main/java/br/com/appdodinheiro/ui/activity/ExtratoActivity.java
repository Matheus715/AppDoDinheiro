package br.com.appdodinheiro.ui.activity;

import static br.com.appdodinheiro.ui.activity.Constantes.ANOS;
import static br.com.appdodinheiro.ui.activity.Constantes.CHAVE_REGISTRO;
import static br.com.appdodinheiro.ui.activity.Constantes.MESES;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Calendar;

import br.com.appdodinheiro.R;
import br.com.appdodinheiro.database.AppDatabase;
import br.com.appdodinheiro.database.dao.RegistroDAO;
import br.com.appdodinheiro.model.Registro;
import br.com.appdodinheiro.ui.adapter.ExtratoAdapter;
import br.com.appdodinheiro.util.MesUtil;

public class ExtratoActivity extends AppCompatActivity {

    private RegistroDAO dao;
    private AppDatabase database;
    private ExtratoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato);

        ConfiguraMenuPrincipal();

        database = AppDatabase.getInstance(this);
        dao = database.getRoomRegistroDAO();

        ConfiguraFiltro();
        ConfiguraExtrato(adapter = new ExtratoAdapter(this, dao.todos()));
    }

    private void ConfiguraFiltro() {
        Spinner SpiniSplendiMes = findViewById(R.id.Extrato_Activity_Mes);
        Spinner SpiniSplendiAno = findViewById(R.id.Extrato_Activity_Ano);

        ArrayAdapter<String> adapterMes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, MESES);
        ArrayAdapter<Integer> adapterAno = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ANOS);

        SpiniSplendiMes.setAdapter(adapterMes);
        SpiniSplendiAno.setAdapter(adapterAno);

        SetDataAtual(SpiniSplendiMes, SpiniSplendiAno);

        CardView BotaoFiltro = findViewById(R.id.Extrato_Activity_Botao_Filtro);

        BotaoFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mesEmTexto = SpiniSplendiMes.getSelectedItem().toString();
                int mesEscolhido = MesUtil.getMesEmInt(mesEmTexto);
                int anoEscolhido = Integer.valueOf(SpiniSplendiAno.getSelectedItem().toString());

                ConfiguraExtrato(adapter = new ExtratoAdapter(ExtratoActivity.this, dao.todosRegistrosDoMesEAno(mesEscolhido, anoEscolhido)));
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

    private void ConfiguraExtrato(ExtratoAdapter adapter) {
        ListView Extrato = findViewById(R.id.Extrato_Activity_Extrato_Lista);
        Extrato.setAdapter(adapter);

        Extrato.setOnItemClickListener((adapterView, view, posicao, id) -> {
            Registro registroEscolhido = (Registro) adapterView.getItemAtPosition(posicao);
            Intent vaiParaFormularioActivity = new Intent(ExtratoActivity.this, FormularioActivity.class);
            vaiParaFormularioActivity.putExtra(CHAVE_REGISTRO, registroEscolhido);
            startActivity(vaiParaFormularioActivity);
        });

        ConfigurarCliqueLongo(dao, Extrato, adapter);
    }

    private void ConfigurarCliqueLongo(RegistroDAO dao, ListView Extrato, ExtratoAdapter adapter) {
        Extrato.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Registro RegistroEscolhido = (Registro) adapterView.getItemAtPosition(posicao);
                dao.remove(RegistroEscolhido);
                adapter.remove(RegistroEscolhido);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.atualiza(dao.todos());
    }

    private void ConfiguraMenuPrincipal() {
        ConfiguraBotaoHome();
        ConfiguraBotaoDespesas();
        ConfiguraBotaoReceitas();
        ConfiguraBotaoMetas();
    }

    private void ConfiguraBotaoHome() {
        CardView Icone = findViewById(R.id.Extrato_Activity_MainIcon);
        CardView Titulo = findViewById(R.id.Extrato_Activity_App_Title);
        Intent VaiParaHome = new Intent(ExtratoActivity.this, MainActivity.class);
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
        CardView BotaoDespesas = findViewById(R.id.Extrato_Activity_Despesas_Container);
        Intent VaiParaDespesas = new Intent(ExtratoActivity.this, DespesasActivity.class);
        BotaoDespesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaDespesas);
            }
        });
    }

    private void ConfiguraBotaoReceitas() {
        CardView BotaoReceitas = findViewById(R.id.Extrato_Activity_Receitas_Container);
        Intent VaiParaReceitas = new Intent(ExtratoActivity.this, ReceitasActivity.class);
        BotaoReceitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaReceitas);
            }
        });
    }

    private void ConfiguraBotaoMetas() {
        CardView BotaoMetas = findViewById(R.id.Extrato_Activity_Metas_Container);
        Intent VaiParaMetas = new Intent(ExtratoActivity.this, MetasActivity.class);
        BotaoMetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaMetas);
            }
        });
    }

}