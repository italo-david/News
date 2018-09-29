package unifbv.news.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import unifbv.news.ClassesAuxiliares.Categoria;
import unifbv.news.ClassesAuxiliares.CategoriaAdapter;
import unifbv.news.R;



public class ActivityEscolherCategoria extends AppCompatActivity
{
    public ListView listaCategorias;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_categoria);

        


        //Criando o adaptador para converter a matriz de visualizações
        ArrayAdapter<Categoria> arrayAdapter = new CategoriaAdapter(this, criarCategorias());

         // Conectando o adaptador com a minha ListView
        listaCategorias = findViewById(R.id.listaCategorias); // Buscando um listView para ser apresentado na tela
        listaCategorias.setAdapter(arrayAdapter);

        //Anexando o manipulador de eventos de clique
        listaCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = null;

                //Acessando a posição da linha para obter o item de dados correto
                if (position == 0)
                {
                    intent = new Intent(ActivityEscolherCategoria.this, Tecnologia.class);
                }
                else if (position == 1)
                {
                    intent = new Intent(ActivityEscolherCategoria.this, Ciencia.class);
                }
                else if (position == 2)
                {
                    intent = new Intent(ActivityEscolherCategoria.this, Jogos.class);
                }
                else if (position == 3)
                {
                    intent = new Intent(ActivityEscolherCategoria.this, Cinema.class);
                }
                else if (position == 4) {
                    intent = new Intent(ActivityEscolherCategoria.this, MapsActivity.class);
                }
                
                
                
                intent.putExtra("categoria", position); //adicionando um objeto o valor para uma intent para enviar para outra Activity.

                
                startActivity(intent);
            }
        });
    }
    //Criando as categorias
    private ArrayList<Categoria> criarCategorias()
    {
        ArrayList<Categoria> categorias = new ArrayList<>();
        Categoria elementoDaCategoria;
    
        elementoDaCategoria = new Categoria(getString(R.string.categoria_ciencia),
                getString(R.string.fonte) + " " + getString(R.string.new_york_times),
                R.drawable.ico_ciencia);
        categorias.add(elementoDaCategoria);
        
        elementoDaCategoria = new Categoria(getString(R.string.categoria_tecnologia),
                getString(R.string.fonte) + " " + getString(R.string.new_york_times),
                R.drawable.ico_tecnologia);
        categorias.add(elementoDaCategoria);
    
        elementoDaCategoria = new Categoria(getString(R.string.categoria_jogos),
                getString(R.string.fonte) + " " + getString(R.string.uol),
                R.drawable.ico_jogos);
        categorias.add(elementoDaCategoria);
        
        elementoDaCategoria = new Categoria(getString(R.string.categoria_cinema),
                getString(R.string.fonte) + " " + getString(R.string.uol),
                R.drawable.ico_cinema);
        categorias.add(elementoDaCategoria);

        elementoDaCategoria = new Categoria(getString(R.string.categoria_maps),
                getString(R.string.fonte) + " " + getString(R.string.google_maps),
                R.drawable.ico_maps);
        categorias.add(elementoDaCategoria);
        
        return categorias;
    }
}
