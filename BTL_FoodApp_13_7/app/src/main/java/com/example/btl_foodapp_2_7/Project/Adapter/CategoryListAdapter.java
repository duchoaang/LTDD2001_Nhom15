package com.example.btl_foodapp_2_7.Project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_foodapp_2_7.Project.Activity.ItemsActivity;
import com.example.btl_foodapp_2_7.Project.Model.BuaAn;
import com.example.btl_foodapp_2_7.Project.Model.Category;
import com.example.btl_foodapp_2_7.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder>{

    Context context;
    List<BuaAn> list;

    public CategoryListAdapter(List<BuaAn> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_category_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BuaAn BuaAn = list.get(position);
        holder.name.setText(BuaAn.getTenBuaAn());
        int buaAn = list.get(position).getId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ItemsActivity.class);
                intent.putExtra("buaAnId", buaAn);

                v.getContext().startActivity(intent);
            }
        });
        // Tải và hiển thị hình ảnh từ SQLite bằng Picasso
        Picasso.get()
                .load(BuaAn.getImage())
                .placeholder(R.drawable.pic_sushi) // Hình ảnh mặc định hiển thị trong khi đang tải
                .error(R.drawable.pic_sushi) // Hình ảnh hiển thị khi xảy ra lỗi
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.hor_img);
            name = itemView.findViewById(R.id.hor_text);
        }
    }
}
