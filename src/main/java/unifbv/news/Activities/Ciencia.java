package unifbv.news.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import unifbv.news.Adapter.FeedAdapter;
import unifbv.news.Common.HTTPDataHandler;
import unifbv.news.Model.RSSObject;
import unifbv.news.R;

public class Ciencia extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    RSSObject rssObject;

    //RSS links
    private final String RSS_link="http://rss.nytimes.com/services/xml/rss/nyt/Science.xml";
    private final String RSS_to_Json_APIs = "https://api.rss2json.com/v1/api.json?rss_url="; //Criando uma váriavel que linka a conversão do rss para json

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciencia);

        //Categorias NYTimes
        //RSS_links.add("http://rss.nytimes.com/services/xml/rss/nyt/Science.xml");
        //RSS_links.add("http%3A%2F%2Frss.nytimes.com%2Fservices%2Fxml%2Frss%2Fnyt%2FTechnology.xml");

        //Categorias UOL
        //RSS_links.add("http%3A%2F%2Frss.uol.com.br%2Ffeed%2Fjogos.xml");
        //RSS_links.add("http%3A%2F%2Fcinema.uol.com.br%2Fultnot%2Findex.xml");


        toolbar = (Toolbar)findViewById(R.id.toolbar); //passando a barra de ferramentas da atividade
        toolbar.setTitle("News"); //Titulo da barra de ferramentas
        setSupportActionBar(toolbar); // fazendo com que o toolbar atue como Action Bar


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView); // Buscando o recyclerView para ser mostrado na tela




        // Configurando o gerenciador de layout para ser uma lista Vertical
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Passando botões de activitys para serem mostrados dentro do toolbar
        ScienceOnClick((Button)findViewById(R.id.science),this);
        TecnologyOnClick((Button) findViewById(R.id.tecnology),this);
        JogosOnClick((Button)findViewById(R.id.jogos),this);
        CinemaOnClick((Button)findViewById(R.id.cinema),this);
        // Passando a url para uma váriavel imutável e instanciando a stringBuilder para evitar o gasto desnecessario de memoria
        StringBuilder url_get_data = new StringBuilder(RSS_to_Json_APIs);
        linkAcess(RSS_link,url_get_data,loadRSS()); // Acessando o link


    }

    // Usando AsyncTask para carregar dados e convertê-los
    private AsyncTask<String,String,String> loadRSS() {
        final AsyncTask<String,String,String> loadRSSAsync = new AsyncTask<String, String, String>() {
            //Uma caixa de diálogo mostrando um indicador de progresso e uma mensagem de texto ou visualização opcional.
            ProgressDialog mDialog = new ProgressDialog(Ciencia.this);

            @Override
            protected void onPreExecute() { /// Este passo é usado para configurar a tarefa, por exemplo, mostrando uma barra de progresso na interface do usuário.
                mDialog.setMessage("Aguarde...");
                mDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {  // Esta etapa é usada para executar a tarefa em background ou fazer a chamada para o webservice
                String result;
                HTTPDataHandler http = new HTTPDataHandler();
                result = http.GetHTTPData(params[0]);
                return  result;
            }

            @Override
            protected void onPostExecute(String s) { // O resultado da execução em background é passado para este passo como um parâmetro.
                mDialog.dismiss();
                rssObject = new Gson().fromJson(s,RSSObject.class); // Aqui vamos utilizar a Biblioteca Gson para transformar o Json recebido em Objeto JAVA
                //criando o adaptador e configurando-o para a exibição do RecyclerView
                FeedAdapter adapter = new FeedAdapter(rssObject,getBaseContext());
                recyclerView.setAdapter(adapter);
                //Notificando os observadores em anexo de que os dados subjacentes foram alterados e qualquer visualização que reflita
                //o conjunto de dados deve ser atualizado automaticamente
                adapter.notifyDataSetChanged();
            }
        };
        return loadRSSAsync;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //Inicializando o menu
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_refresh) {
            loadRSS();
        }
        return true;
    }

    //Após o evento de click dentro do toolbar referente as categorias as mesmas irão inicializar
    protected void ScienceOnClick(Button science,final Context context){
        science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Ciencia.class);
                startActivity(intent);
            }
        });
    }
    protected void TecnologyOnClick(Button tecnology, final Context context){
        tecnology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Tecnologia.class);
                startActivity(intent);
            }
        });
    }
    protected void JogosOnClick(Button jogos,final Context context){
        jogos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Jogos.class);
                startActivity(intent);
            }
        });
    }
    protected void CinemaOnClick(Button cinema, final Context context){
        cinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Cinema.class);
                startActivity(intent);
            }
        });
    }
    // Construindo uma API de link para obter dados
    public void linkAcess(String RSS_link, StringBuilder url_get_data, AsyncTask<String,String,String> loadRSSAsync){
        if(url_get_data!=null) {
            url_get_data.append(RSS_link); // Usando o append para converter o rss, adicionando ao final do construtor os caracteres
            loadRSSAsync.execute(url_get_data.toString()); //chamando AsyncTask com o link que acabamos de construir
        }else{
            loadRSSAsync.cancel(true);
        }
    }
}
