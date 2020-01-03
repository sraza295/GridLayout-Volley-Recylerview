package com.example.pascolanappdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.igenius.customcheckbox.CustomCheckBox;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<Quiz>list_data;
    Context ct;

    public MyAdapter(List<Quiz> list_data, Context ct) {
        this.list_data = list_data;
        this.ct = ct;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Quiz listData=list_data.get(position);
        Picasso.with(ct)
                .load(listData.getP())
                .into(holder.img);
        holder.txt.setText(listData.getN());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(ct, listData.getN(), Toast.LENGTH_SHORT).show();
                if (holder.chk.isChecked())
                {
                    holder.chk.setChecked(false);
                    holder.img.setBackground(ContextCompat.getDrawable(ct,R.drawable.transparent_border));
                }
                else
                {
                    holder.chk.setChecked(true);
                    holder.img.setBackground(ContextCompat.getDrawable(ct,R.drawable.dotted_border));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    private CircleImageView img;
    private TextView txt;
    private CustomCheckBox chk;

        public ViewHolder(View itemView) {
            super(itemView);
            img=(CircleImageView)itemView.findViewById(R.id.image_view);
            txt=(TextView) itemView.findViewById(R.id.txt_view);
            chk=(CustomCheckBox ) itemView.findViewById(R.id.checkb);

        }
    }
}