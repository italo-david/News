package unifbv.news.Activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

import unifbv.news.R;

public class ActivityMainLogin extends AppCompatActivity
{
    private Button btnAvancar;
    private TextInputEditText editNome;
    private String nome;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        
        nome = "SEM NOME"; // Inicializando a variável
        
        btnAvancar = findViewById(R.id.btnAvancar); //Buscando o botão para ser mostrado na tela
        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickMe();
                avancar(v); //chamando o metódo avançar para ir para a outra tela após o evento de click
            }
        });
        editNome = findViewById(R.id.editNome); //Buscando o textinput para ser mostrado na tela
    }
    
    private void avancar(View view)
    {

        if (!editNome.getText().toString().equals("")) // Se o campo de texto estiver preenchido entrar na condição
        {
            Intent intent = new Intent(ActivityMainLogin.this, ActivityEscolherFoto.class);

            if (!editNome.getText().toString().equals(nome)) // Se nome for igual a "SEM NOME"
            {
                nome = editNome.getText().toString(); // Recebendo o input
            }


            intent.putExtra("nome", nome); //Enviando ou compartilhando o váriavel nome com outras activity

            Toast.makeText(this, R.string.bem_vindo, Toast.LENGTH_LONG).show(); //Mostrando mensagem de bem vindo na tela
            startActivity(intent);
        }
        else // Se o campo estiver vazio, irá sinalizar o usuário para preencher o campo
        {
            Toast.makeText(this, R.string.erro_falta_nome, Toast.LENGTH_LONG).show();
        }

    }

    private void ClickMe(){
        //Usando uma classe auxiliar para gerar notificações de grande formato que incluem um anexo de imagem grande.
        NotificationCompat.BigPictureStyle bigPicSt = new NotificationCompat.BigPictureStyle();

        bigPicSt.bigPicture(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.ico_maps)).build();

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(ActivityMainLogin.this).setSmallIcon(R.mipmap.ic_news)
                .setContentTitle(getString(R.string.notificacao_maps_titulo))
                .setContentText(getString(R.string.notificacao_maps_texto))
                .setStyle(bigPicSt);

        notificationManager.notify(0,builder.build());
    }
}
