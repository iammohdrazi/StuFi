package com.zaph.loginsignupdesign.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zaph.loginsignupdesign.EditTaskDesk;
import com.zaph.loginsignupdesign.R;

import java.util.ArrayList;

public class DoesAdapter extends RecyclerView.Adapter<DoesAdapter.MyViewHolder>{

    Context context;
    ArrayList<MyDoes> myDoes;

    public DoesAdapter(Context c , ArrayList<MyDoes> p){

        context = c;
        myDoes = p;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notes,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.titledoes.setText(myDoes.get(i).getTitledoes());
        myViewHolder.descdoes.setText(myDoes.get(i).getDescdoes());
        myViewHolder.datedoes.setText(myDoes.get(i).getDatedoes());

        final String getTitleDoes = myDoes.get(i).getTitledoes();
        final String getDescDoes = myDoes.get(i).getDescdoes();
        final String getDateDoes = myDoes.get(i).getDatedoes();
        final String getKeyDoes = myDoes.get(i).getKeydoes();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditTaskDesk.class);
                intent.putExtra("titledoes" , getTitleDoes);
                intent.putExtra("descdoes" , getDescDoes);
                intent.putExtra("datedoes" , getDateDoes);
                intent.putExtra("keydoes", getKeyDoes);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myDoes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titledoes,descdoes,datedoes ,keydoes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titledoes= (TextView) itemView.findViewById(R.id.titledoes);
            descdoes= (TextView) itemView.findViewById(R.id.descdoes);
            datedoes= (TextView) itemView.findViewById(R.id.datedoes);
        }
    }

}
