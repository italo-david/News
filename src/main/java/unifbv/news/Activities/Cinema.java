package unifbv.news.Activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.google.gson.Gson;

import unifbv.news.Adapter.FeedAdapter;
import unifbv.news.Common.HTTPDataHandler;

import unifbv.news.Model.RSSObject;
import unifbv.news.R;

public class Cinema extends Ciencia
{

    Toolbar toolbar;
    RecyclerView recyclerView;

    //RSS links
    private final String RSS_link="http%3A%2F%2Fcinema.uol.com.br%2Fultnot%2Findex.xml";
    private final String RSS_to_Json_APIs = "https://api.rss2json.com/v1/api.json?rss_url=";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogos);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("News");
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ScienceOnClick((Button)findViewById(R.id.science),this);
        TecnologyOnClick((Button) findViewById(R.id.tecnology),this);
        JogosOnClick((Button)findViewById(R.id.jogos),this);
        CinemaOnClick((Button)findViewById(R.id.cinema),this);

        StringBuilder url_get_data = new StringBuilder(RSS_to_Json_APIs);
        linkAcess(RSS_link,url_get_data,loadRSS());
    }

    private AsyncTask<String,String,String> loadRSS() {
        final AsyncTask<String,String,String> loadRSSAsync = new AsyncTask<String, String, String>() {

            ProgressDialog mDialog = new ProgressDialog(Cinema.this);

            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Aguarde...");
                mDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                String result;
                HTTPDataHandler http = new HTTPDataHandler();
                result = http.GetHTTPData(params[0]);
                return  result;
            }

            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                rssObject = new Gson().fromJson(s,RSSObject.class);
                FeedAdapter adapter = new FeedAdapter(rssObject,getBaseContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        return loadRSSAsync;
    }
}
