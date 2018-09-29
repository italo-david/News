package unifbv.news.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import unifbv.news.ClassesAuxiliares.BitmapHelper;
import unifbv.news.R;

public class ActivityEscolherFoto extends AppCompatActivity {
    public static final int REQUEST_CODE_GALERIA = 20; //definindo uma constante imutável, inalterável, conseguido a partir da adição do modificador final para verificar se a imagem existe
    private TextView bemVindoUsuario;
    private ImageView imagemFoto;
    private Button btnAvancar;
    private String nomeUsuario;  // PARA ENVIAR PARA AS OUTRAS ACTIVITIES
    private Switch Switchwifi;
    private WifiManager wm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_foto);


        bemVindoUsuario = findViewById(R.id.txtBemVindoUsuario); // Buscando um text para ser mostrado na tela
        imagemFoto = findViewById(R.id.imagemFoto); //Buscando uma imagem para ser mostrada na tela
        btnAvancar = findViewById(R.id.btnAvancar); // Buscando um botão para ser mostrado na tela

        if (getIntent().getExtras() != null) { // Se a activity anterior passar dados extras e for diferentes de null entrar na condição
            nomeUsuario = getIntent().getExtras().getString("nome"); //obtendo os dados extras da outra activity

            if (getIntent().getExtras().getString("foto") == null) { // Se o campo foto estiver nulo entrar na condição
                bemVindoUsuario.setText(getString(R.string.bem_vindo) + ", " + nomeUsuario);
            } else {
                bemVindoUsuario.setText("Escolha uma nova foto, " + nomeUsuario);

                imagemFoto.setImageBitmap(BitmapHelper.getInstance().getBitmap());
            }


        }
        //Dando a opção para o usuário se conectar
        Switchwifi = findViewById(R.id.wifi_switch); //Buscando um switch para ser mostrado na tela
        wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE); //Retornando o contexto do processo de toda atividade em execução, no caso o serviço de wifi

        Switchwifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { // Se a verificação do wifi for igual a true, no caso ela está ativada
                    wm.setWifiEnabled(true);
                    Switchwifi.setText(R.string.online); // modificando o texto para conectado
                } else {
                    wm.setWifiEnabled(false);
                    Switchwifi.setText(R.string.desconected);
                }
            }
        });
    }


    public void avancar(View view) {
        //recuperando o serviço que carrega o estado da conexão
        ConnectivityManager gerenciador = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = gerenciador.getActiveNetworkInfo(); // Recebendo a Informação de conexão

        //Se a informação da conexão for diferente de null ou estiver conectado ou pronto para se conectar, entrar na condição
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            Intent intent = new Intent(this, ActivityEscolherCategoria.class);
            intent.putExtra("nome", nomeUsuario);
            startActivity(intent);
        } else {
            Toast.makeText(ActivityEscolherFoto.this, R.string.notice, Toast.LENGTH_SHORT).show(); //Se diferente lançar uma mensagem para o usuário
        }

    }

    public void editarFoto(View view) {
        //Aqui a constante Action_Pick irá retornar o que foi selecionando no caso a imagem
        Intent intentEscolherFoto = new Intent(Intent.ACTION_PICK);

        //A classe Environment fornece acesso as variáveis de ambiente
        //Logo consigo acessar o Diretório padrão no é colocado as fotos que estão disponiveis para o usuário
        File pastaDaFoto = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);// Indo no diretorio através da classe Environment e pegando o caminho da foto
        String caminhoDaFoto = pastaDaFoto.getPath(); //armazenando o caminho da foto em uma variável do tipo string

        Uri data = Uri.parse(caminhoDaFoto); //recebendo o caminho da foto em um atibuto do tipo uri que ele recebe o um diretorio especifico de um recurso como a imagem

        //Trabalhando para definir o type mime,  retornando que é a "image" (quando ele vai buscar em alguma pasta só ira ser mostrado arquivos do tipo image)
        intentEscolherFoto.setDataAndType(data, "image/*");

        startActivityForResult(intentEscolherFoto, REQUEST_CODE_GALERIA);
    }

    //Conteudo resposta da aplicação que respondeu a inteção
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data); //chamando o construtor da superclasse

        if (resultCode == RESULT_OK) //Verficando se o processamento ocorreu corretamente
        {
            if (requestCode == REQUEST_CODE_GALERIA) //Verificando para saber se a aplicação respondeu corretamente referente a resposta da constante
            {
                Uri imagemData = data.getData(); // Acessando o recurso , no caso a imagem
                InputStream inputStream; // Criando uma váriavel do tipo inputstream, com intuito de ler um dado de algum local (uma fonte)

                try {
                    inputStream = getContentResolver().openInputStream(imagemData); // Colocando o caminho do arquivo na váriavel inputSteam

                    Bitmap imagemEmBitmap = BitmapFactory.decodeStream(inputStream); // Decodificando um bitmap de acordo com o caminho da imagem
                    imagemFoto.setImageBitmap(imagemEmBitmap); // recebendo a imagem
                    BitmapHelper.getInstance().setBitmap(imagemEmBitmap);   // salva Bitmap para uso futuro
                    if (BitmapHelper.getInstance().getBitmap() != null)    // se o usuário selecionar foto irá TROCAR O TEXTO DO BOTÃO (que antes estava como "Pular")
                    {
                        btnAvancar.setText(getString(R.string.btn_avancar));
                    }

                } catch (FileNotFoundException e) { //Sinaliza que uma tentativa de abrir o arquivo denotado por um nome de caminho especificado falhou
                    e.printStackTrace();
                    Toast.makeText(this, "erro_pegar_imagem", Toast.LENGTH_LONG);
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION); //passando a especificação da intenção de verificar o estado wifi
        registerReceiver(estadoWifi, intentFilter); //Registrando a recepção do estado do wifi e a intenção
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(estadoWifi); //Parando a verificação
    }
    //Método criado para receber as alterações de estado do wifi
    private BroadcastReceiver estadoWifi = new BroadcastReceiver() {

        //Quando houver uma alteração na conexão Wi-Fi, ocorre uma transmissão e objeto Intent que contém um estado da conexão Wifi.
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN); //Obtendo o estado do wifi chamando o método getIntExtra() da intenção com a passagem de wifiManager

            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    Switchwifi.setChecked(true);
                    Switchwifi.setText(R.string.online);
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    Switchwifi.setChecked(false);
                    Switchwifi.setText(R.string.desconected);
                    break;
            }
        }
    };
}
