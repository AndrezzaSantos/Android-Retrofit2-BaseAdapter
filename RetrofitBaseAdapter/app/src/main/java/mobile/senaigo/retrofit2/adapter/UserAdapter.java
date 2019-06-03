package mobile.senaigo.retrofit2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import mobile.senaigo.com.meuretrofit2.R;
import mobile.senaigo.com.meuretrofit2.model.User;

public class UserAdapter extends BaseAdapter {
    Context context;
    List<User> colecao;
    LayoutInflater inflter;

    public UserAdapter(final Context applicationContext, final List<User> colecao) {
        this.context = applicationContext;
        this.colecao = colecao;
        Log.i("teste", this.colecao.size()+"");
    }

    @Override
    public int getCount() {
        return this.colecao != null ? this.colecao.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return this.colecao.get(i);
    }

    private User parsetItem(int i){
        return this.colecao.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.user, viewGroup, false);
        }
        User user = parsetItem(i);
        TextView campoid, campoTexto1;
        campoid = view.findViewById(R.id.txtUserId2);
        campoTexto1 = view.findViewById(R.id.txtTitle2);
        campoid.setText(user.getId() + "");
        campoTexto1.setText(user.getName());

        return view;
    }
}

	
	
	
