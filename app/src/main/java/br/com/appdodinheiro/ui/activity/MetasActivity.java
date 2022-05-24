package br.com.appdodinheiro.ui.activity;

import static br.com.appdodinheiro.ui.activity.Constantes.CHAVE_META;
import static br.com.appdodinheiro.ui.activity.Constantes.CHAVE_REGISTRO;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.appdodinheiro.R;
import br.com.appdodinheiro.database.AppDatabase;
import br.com.appdodinheiro.database.dao.MetaDAO;
import br.com.appdodinheiro.database.dao.RegistroDAO;
import br.com.appdodinheiro.model.Meta;
import br.com.appdodinheiro.model.Registro;
import br.com.appdodinheiro.ui.adapter.MetasAdapterMetas;
import br.com.appdodinheiro.ui.adapter.MetasAdapterRegistros;

public class MetasActivity extends AppCompatActivity {

    private RegistroDAO registroDAO;
    private MetaDAO metaDAO;
    private AppDatabase database;
    private MetasAdapterRegistros adapterRegistros;
    private MetasAdapterMetas adapterMetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metas);

        ConfiguraMenuPrincipal();

        ConfiguraFAB();

        database = AppDatabase.getInstance(this);
        registroDAO = database.getRoomRegistroDAO();
        metaDAO = database.getRoomMetaDAO();

        ConfiguraBotoesLista();

        ConfiguraListaRegistros(adapterRegistros = new MetasAdapterRegistros(this, registroDAO.registros("Meta"), database));
    }

    private void ConfiguraBotoesLista() {
        CardView registros = findViewById(R.id.Metas_Activity_Botao_Registros);
        CardView metas = findViewById(R.id.Metas_Activity_Botao_Metas);
        TextView registrosTexto = findViewById(R.id.Metas_Activity_Botao_Registros_Texto);
        TextView metasTexto = findViewById(R.id.Metas_Activity_Botao_Metas_Texto);

        registros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registros.setCardBackgroundColor(Color.parseColor("#FEBE4E"));
                metas.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                registrosTexto.setTextColor(Color.parseColor("#FFFFFF"));
                metasTexto.setTextColor(Color.parseColor("#FEBE4E"));

                ConfiguraListaRegistros(adapterRegistros = new MetasAdapterRegistros(MetasActivity.this, registroDAO.registros("Meta"), database));
            }
        });

        metas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metas.setCardBackgroundColor(Color.parseColor("#FEBE4E"));
                registros.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                registrosTexto.setTextColor(Color.parseColor("#FEBE4E"));
                metasTexto.setTextColor(Color.parseColor("#FFFFFF"));
                ConfiguraListaMetas(adapterMetas = new MetasAdapterMetas(MetasActivity.this, metaDAO.todos(), database));
            }
        });

    }

    private void ConfiguraFAB() {
        FloatingActionButton FAB = findViewById(R.id.Metas_Activity_FAB);

        FAB.setOnClickListener(new View.OnClickListener() {
            Intent VaiParaFormulario = new Intent(MetasActivity.this, FormularioActivity.class);

            @Override
            public void onClick(View v) {
                startActivity(VaiParaFormulario);
            }
        });
    }

    private void ConfiguraListaRegistros(MetasAdapterRegistros adapter) {
        ListView Lista = findViewById(R.id.Metas_Activity_Lista);
        Lista.setAdapter(adapter);
    }

    private void ConfiguraListaMetas(MetasAdapterMetas adapter) {
        ListView Lista = findViewById(R.id.Metas_Activity_Lista);
        Lista.setAdapter(adapter);
        Lista.setOnItemClickListener((adapterView, view, posicao, id) -> {
            Meta metaEscolhida = (Meta) adapterView.getItemAtPosition(posicao);
            Intent vaiParaFormularioActivity = new Intent(MetasActivity.this, FormularioActivity.class);
            vaiParaFormularioActivity.putExtra(CHAVE_META, metaEscolhida);
            startActivity(vaiParaFormularioActivity);
        });
    }

    private void ConfiguraMenuPrincipal() {
        ConfiguraBotaoHome();
        ConfiguraBotaoExtrato();
        ConfiguraBotaoReceitas();
        ConfiguraBotaoDespesas();
    }

    private void ConfiguraBotaoHome() {
        CardView Icone = findViewById(R.id.Metas_Activity_MainIcon);
        CardView Titulo = findViewById(R.id.Metas_Activity_App_Title);
        Intent VaiParaHome = new Intent(MetasActivity.this, MainActivity.class);
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
        CardView BotaoExtrato = findViewById(R.id.Metas_Activity_Extrato_Container);
        Intent VaiParaExtrato = new Intent(MetasActivity.this, ExtratoActivity.class);
        BotaoExtrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaExtrato);
            }
        });
    }

    private void ConfiguraBotaoReceitas() {
        CardView BotaoReceitas = findViewById(R.id.Metas_Activity_Receitas_Container);
        Intent VaiParaReceitas = new Intent(MetasActivity.this, ReceitasActivity.class);
        BotaoReceitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaReceitas);
            }
        });
    }

    private void ConfiguraBotaoDespesas() {
        CardView BotaoDespesas = findViewById(R.id.Metas_Activity_Despesas_Container);
        Intent VaiParaDespesas = new Intent(MetasActivity.this, DespesasActivity.class);
        BotaoDespesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaDespesas);
            }
        });
    }
}