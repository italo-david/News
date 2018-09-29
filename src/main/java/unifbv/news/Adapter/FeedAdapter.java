package unifbv.news.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import unifbv.news.Interface.ItemClickListener;
import unifbv.news.Model.RSSObject;
import unifbv.news.R;

class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView txtTitle, txtPubDate, txtContent;
    private ItemClickListener itemClickListener;


    //Criado para relacionar os campos, como fazemos em uma activity para poder acessar os componentes visuais
    public FeedViewHolder(View itemView) {
        super(itemView);


        txtTitle =  itemView.findViewById(R.id.txtTitle); // Buscando um item para ser mostrado na tela
        txtPubDate =  itemView.findViewById(R.id.txtPubDate);
        txtContent =  itemView.findViewById(R.id.txtContent);

        //Set Event
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return false;
    }


}

// Adapter está recebendo no construtor a lista de objetos, que no caso é o feedViewHolder
public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;


    public FeedAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    //Esse metódo é chamado quando o RecyclerView precisa de um novo RecyclerView.ViewHolder do tipo dado para representar um item
    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row, parent, false);
        return new FeedViewHolder(itemView);
    }

    //Método que recebe o ViewHolder e a posição da lista.
    // Aqui é recuperado o objeto da lista de Objetos pela posição e associado à ViewHolder.
    //Esse método deve atualizar o conteúdo do itemView para refletir o item na posição determinada
    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {

        holder.txtTitle.setText(rssObject.getItems().get(position).getTitle());
        holder.txtPubDate.setText(rssObject.getItems().get(position).getPubDate());
        holder.txtContent.setText(rssObject.getItems().get(position).getContent());

        //adicionando o onclick para recyclerVIew item
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
                    browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //Adicionando um sinalizador á intenção
                    mContext.startActivity(browserIntent);
                }
            }
        });


    }

    //Método que deverá retornar quantos itens há na lista.
    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }


}
