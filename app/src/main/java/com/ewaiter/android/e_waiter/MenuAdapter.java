package com.ewaiter.android.e_waiter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aman on 04-04-2018.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenusViewHolder> {

    private ArrayList<MenuCategory> menuCategories = new ArrayList<>();
    private Context c;
    private String tableNumber;

    private MenuAdapter(Context c, ArrayList<MenuCategory> menuCategories,String tableNumber) {
        this.menuCategories = menuCategories;
        this.c = c;
        this.tableNumber = tableNumber;
    }

    @Override
    public MenuAdapter.MenusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.menu_view_individual,parent,false);
        return new MenuAdapter.MenusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuAdapter.MenusViewHolder holder, int position) {
        final MenuCategory mc = menuCategories.get(position);
        holder.categoryImage.setImageResource(mc.getCategoryImage());
        holder.categoryName.setText(mc.getCategoryName());
        holder.menuViewIndividual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String className = mc.getCategoryOnClick();
                try {
                    Class<?> classType =Class.forName("com.ewaiter.android.e_waiter." + className);
                    Intent intent = new Intent(c,classType);
                    intent.putExtra("categoryName", mc.getCategoryName());
                    intent.putExtra("tableNumber",tableNumber);
                    c.startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }



            }
        });
    }

    @Override
    public int getItemCount() {
        return menuCategories.size();
    }

    public class MenusViewHolder extends RecyclerView.ViewHolder {

        LinearLayout menuViewIndividual;
        ImageView categoryImage;
        TextView categoryName;

        public MenusViewHolder(View itemView) {
            super(itemView);
            menuViewIndividual = itemView.findViewById(R.id.menu_view_individual);
            categoryImage = itemView.findViewById(R.id.category_image);
            categoryName = itemView.findViewById(R.id.category_name);
        }
    }
}
