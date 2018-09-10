package com.example.d.dinislamtestandroid.list;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.d.dinislamtestandroid.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {


    private List<ItemOnList> itemsOnList;

    public ListAdapter(List<ItemOnList> itemOnLists) {
        this.itemsOnList = itemOnLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        final ItemOnList item = itemsOnList.get(position);
        final Boolean checkboxB = item.getCheckboxItem();

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Support.fastClick(item.getId(), item.getNameItem(), checkboxB);
            }
        });

        holder.name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Support.longClick(item.getId(), item.getNameItem(), checkboxB);
                return true;
            }
        });

        holder.name.setText(item.getNameItem());
        if (checkboxB == true) {
            holder.imageView.setImageResource(R.drawable.positive_item);
            holder.checkBox.setChecked(true);
        }
        else {
            holder.imageView.setImageResource(R.drawable.negative_item);
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Support.checkboxClick(item.getId(), item.getNameItem(), isChecked);
                Support.updateRecycleList();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (itemsOnList == null)
            return 0;
        return itemsOnList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.listitem_Name);
            imageView = (ImageView) itemView.findViewById(R.id.image_in_item);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_in_item);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}

