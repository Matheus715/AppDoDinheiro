package br.com.appdodinheiro.ui.activity;

import static br.com.appdodinheiro.ui.activity.Constantes.CHAVE_META;
import static br.com.appdodinheiro.ui.activity.Constantes.CHAVE_REGISTRO;
import static br.com.appdodinheiro.ui.activity.Constantes.TIPOS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

import br.com.appdodinheiro.R;
import br.com.appdodinheiro.database.AppDatabase;
import br.com.appdodinheiro.database.dao.MetaDAO;
import br.com.appdodinheiro.database.dao.RegistroDAO;
import br.com.appdodinheiro.model.Meta;
import br.com.appdodinheiro.model.Registro;

public class FormularioActivity extends AppCompatActivity {

    private RegistroDAO registroDAO;
    private MetaDAO metaDAO;
    private AppDatabase database;
    private Registro RegistroDigitado;
    private Meta MetaDigitada;
    private EditText CampoNome;
    private EditText CampoValor;
    private DatePicker CampoData;
    private Spinner CampoTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        database = AppDatabase.getInstance(this);
        registroDAO = database.getRoomRegistroDAO();
        metaDAO = database.getRoomMetaDAO();

        ConfiguraMenuPrincipal();
        IniciaCampos();
        ConfiguraBotaoAdicionar();

        TextView textoBotaoAdiciona = findViewById(R.id.Formulario_Activity_botao_adciona_texto);

        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_REGISTRO)) {
            RegistroDigitado = (Registro) dados.getSerializableExtra(CHAVE_REGISTRO);
            preencheCamposComRegistro();
            textoBotaoAdiciona.setText("Alterar Registro");
        } else if (dados.hasExtra(CHAVE_META)) {
            MetaDigitada = (Meta) dados.getSerializableExtra(CHAVE_META);
            preencheCamposComMeta();
            textoBotaoAdiciona.setText("Alterar Meta");
        } else {
            RegistroDigitado = new Registro();
        }

    }

    private void preencheCamposComRegistro() {
        CampoNome.setText(RegistroDigitado.getNome());
        CampoValor.setText(String.valueOf(RegistroDigitado.getValor()));
        CampoData.updateDate(RegistroDigitado.getAno(), RegistroDigitado.getMes(), RegistroDigitado.getDia());

        int tipo = 0;
        if ("Despesa".equals(RegistroDigitado.getTipo())) {
            tipo = 1;
        } else if ("Receita".equals(RegistroDigitado.getTipo())) {
            tipo = 2;
        }
        CampoTipo.setSelection(tipo);
    }

    private void preencheCamposComMeta() {
        CampoNome.setText(MetaDigitada.getNome());
        CampoValor.setText(String.valueOf(MetaDigitada.getValorTotal()));
        CampoData.updateDate(MetaDigitada.getAno(), MetaDigitada.getMes(), MetaDigitada.getDia());
        CampoTipo.setSelection(3);
    }

    private void IniciaCampos() {
        CampoNome = findViewById(R.id.Formulario_Activity_local);
        CampoValor = findViewById(R.id.Formulario_Activity_valor);
        CampoData = findViewById(R.id.Formulario_Activity_data);
        CampoTipo = findViewById(R.id.Formulario_Activity_spinner_splendi_tipo);

        ArrayAdapter<String> adapterTipos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, TIPOS);
        CampoTipo.setAdapter(adapterTipos);
    }

    private void ConfiguraBotaoAdicionar() {

        CardView BotaoAdiciona = findViewById(R.id.Formulario_Activity_botao_adciona);
        BotaoAdiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipo = CampoTipo.getSelectedItem().toString();

                if (tipo != "Incluir Meta") {
                    SalvaRegistro();
                } else {
                    SalvaMeta();
                }
            }
        });
    }

    private void SalvaRegistro() {
        String nome = CampoNome.getText().toString();
        BigDecimal valor = new BigDecimal(CampoValor.getText().toString());
        Calendar data = new GregorianCalendar(CampoData.getYear(), CampoData.getMonth(), CampoData.getDayOfMonth());
        String tipo = CampoTipo.getSelectedItem().toString();

        RegistroDigitado.setNome(nome);
        RegistroDigitado.setValor(valor);
        RegistroDigitado.setData(data);
        RegistroDigitado.setTipo(tipo);

        if (RegistroDigitado.temIdValido()) {
            registroDAO.edita(RegistroDigitado);
        } else {
            registroDAO.salva(RegistroDigitado);
        }
        finish();
    }

    private void SalvaMeta() {
        String nome = CampoNome.getText().toString();
        BigDecimal valor = new BigDecimal(CampoValor.getText().toString());
        Calendar data = new GregorianCalendar(CampoData.getYear(), CampoData.getMonth(), CampoData.getDayOfMonth());

        MetaDigitada = new Meta(nome, valor, data);

        if (MetaDigitada.temIdValido()) {
            metaDAO.edita(MetaDigitada);
        } else {
            metaDAO.salva(MetaDigitada);
        }
        finish();
    }

    private void ConfiguraMenuPrincipal() {
        ConfiguraBotaoHome();
        ConfiguraBotaoExtrato();
        ConfiguraBotaoDespesas();
        ConfiguraBotaoReceitas();
        ConfiguraBotaoMetas();
    }

    private void ConfiguraBotaoHome() {
        CardView Icone = findViewById(R.id.Formulario_Activity_MainIcon);
        CardView Titulo = findViewById(R.id.Formulario_Activity_App_Title);
        Intent VaiParaHome = new Intent(FormularioActivity.this, MainActivity.class);
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
        CardView BotaoExtrato = findViewById(R.id.Formulario_Activity_Extrato_Container);
        Intent VaiParaExtrato = new Intent(FormularioActivity.this, ExtratoActivity.class);
        BotaoExtrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaExtrato);
            }
        });
    }

    private void ConfiguraBotaoDespesas() {
        CardView BotaoDespesas = findViewById(R.id.Formulario_Activity_Despesas_Container);
        Intent VaiParaDespesas = new Intent(FormularioActivity.this, DespesasActivity.class);
        BotaoDespesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaDespesas);
            }
        });
    }

    private void ConfiguraBotaoReceitas() {
        CardView BotaoReceitas = findViewById(R.id.Formulario_Activity_Receitas_Container);
        Intent VaiParaReceitas = new Intent(FormularioActivity.this, ReceitasActivity.class);
        BotaoReceitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaReceitas);
            }
        });
    }

    private void ConfiguraBotaoMetas() {
        CardView BotaoMetas = findViewById(R.id.Formulario_Activity_Metas_Container);
        Intent VaiParaMetas = new Intent(FormularioActivity.this, MetasActivity.class);
        BotaoMetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VaiParaMetas);
            }
        });
    }
}