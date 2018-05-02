package com.ewaiter.android.e_waiter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Aman on 04-04-2018.
 */

class TablesAdapter extends RecyclerView.Adapter<TablesAdapter.TablesViewHolder> {

    ArrayList<Table> tableNumbers = new ArrayList<>();
    Context c;

    public TablesAdapter(Context c, ArrayList<Table> tableNumbers) {
        this.tableNumbers = tableNumbers;
        this.c = c;
    }

    @Override
    public TablesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.table_view_individual,parent,false);
        return new TablesAdapter.TablesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TablesViewHolder holder, int position) {
        final Table tableNumber = tableNumbers.get(position);
        Log.i("stressed", String.valueOf(tableNumber.isStatus()));
        if(tableNumber.isStatus() == false) {
            holder.tableTV.setBackgroundResource(R.drawable.table_shape);
        }
        else {
            holder.tableTV.setBackgroundResource(R.drawable.table_shape_colored);
        }
        holder.tableTV.setText(tableNumber.getTableNumber());
        holder.single_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Toast.makeText(c,tableNumber.getTableNumber() + " Selected!",Toast.LENGTH_SHORT).show();
                    holder.tableTV.setBackgroundResource(R.drawable.table_shape_colored);
                    Intent intent = new Intent(c,MenuCategorySelectActivity.class);
                    intent.putExtra("tableNumber",tableNumber.getTableNumber());
                    c.startActivity(intent);
                    tableNumber.setStatus(true);
            }
        });
    }


    @Override
    public int getItemCount() {
        return tableNumbers.size();
    }

    public class TablesViewHolder extends RecyclerView.ViewHolder {

        LinearLayout single_table;
        TextView tableTV;

        public TablesViewHolder(View itemView) {
            super(itemView);
            single_table = itemView.findViewById(R.id.single_table);
            tableTV = itemView.findViewById(R.id.tableTV);
        }
    }
}
