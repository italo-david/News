package unifbv.news.Interface;

import android.view.View;

//Criando uma interface que especifique o comportamento do ouvinte
//Foi criada pois o RecyclerView não tem um setOnItemCLickListener
public interface ItemClickListener {

    void onClick(View view, int position,boolean isLongClick);
}
