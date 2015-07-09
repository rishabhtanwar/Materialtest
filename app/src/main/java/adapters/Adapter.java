package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.materialtest.R;
import extra.info;

import java.util.Collections;
import java.util.List;

/**
 * Created by nishant on 27/6/15.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.myviewHolder> {
    private  LayoutInflater inflater;
    List<info> data= Collections.emptyList();
    public Adapter(Context context,List<info> data){
       inflater= LayoutInflater.from(context);
        this.data=data;
    }
    @Override
    public myviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= inflater.inflate(R.layout.custom_row, parent, false);
        myviewHolder holder=new myviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myviewHolder holder, int position) {

        info current=data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class myviewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public myviewHolder(View itemView) {

            super(itemView);
            title= (TextView) itemView.findViewById(R.id.listText);
            icon= (ImageView) itemView.findViewById(R.id.listIcon);
        }
    }
}
