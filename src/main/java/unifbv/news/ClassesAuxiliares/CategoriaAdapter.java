package unifbv.news.ClassesAuxiliares;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import unifbv.news.R;

//Esse adaptador Possui um construtor e um getView() método para descrever a conversão entre o item de dados
//e a visualização a ser exibida.
public class CategoriaAdapter extends ArrayAdapter<Categoria> {
    private final Context context;
    private final ArrayList<Categoria> elementos;

    //Vizualizar o cache de pesquisa
    public static class Holder {
        TextView nome;
        TextView descricao;
        ImageView image;
    }

    public CategoriaAdapter(Context context, ArrayList<Categoria> elementos) {
        super(context, R.layout.linha_listview_personalizada, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
       //Verificando se uma view existente está sendo reutilizada, caso contrário, infle a view
        Holder holder; //Exibir cache de pesquisa armazenado na tag
        if (view == null) {
            //Se não houver reutilização, insira uma nova vizualização para a linha
            holder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.linha_listview_personalizada, parent, false);
            holder.nome = view.findViewById(R.id.nomeCategoria);
            holder.descricao = view.findViewById(R.id.descricaoCategoria);
            holder.image = view.findViewById(R.id.imagemCategoria);
            //Colocar em cache o objeto holder dentro da nova exibição
            view.setTag(holder);

        } else {
            //A vizualização está sendo reciclada,assim recuperando o objeto holder da tag
            holder = (Holder) view.getTag();
        }

        // Preenchendo os dados do objeto de dados por meio do objeto holder no Template view
        holder.nome.setText(elementos.get(position).getNome());
        holder.descricao.setText(elementos.get(position).getDescricao());
        holder.image.setImageResource(elementos.get(position).getImagem());

        return view;   //Retorna a vizualização completa para renderizar na tela
    }
}
