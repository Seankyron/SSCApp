package com.example.sscapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sscapp.R;
import com.example.sscapp.models.GroupedCartItem;
import java.util.List;
import java.util.Map;

public class GroupedItemAdapter extends RecyclerView.Adapter<GroupedItemAdapter.GroupedItemViewHolder> {

    private List<GroupedCartItem> items;

    public GroupedItemAdapter(List<GroupedCartItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public GroupedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grouped_cart, parent, false);
        return new GroupedItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupedItemViewHolder holder, int position) {
        GroupedCartItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class GroupedItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView itemSizesTextView;

        GroupedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.item_name_text_view);
            itemSizesTextView = itemView.findViewById(R.id.item_sizes_text_view);
        }

        void bind(GroupedCartItem item) {
            itemNameTextView.setText(item.getName());

            StringBuilder sizesBuilder = new StringBuilder();
            for (Map.Entry<String, Integer> entry : item.getSizes().entrySet()) {
                sizesBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
            }
            if (sizesBuilder.length() > 0) {
                sizesBuilder.setLength(sizesBuilder.length() - 2); // Remove last ", "
            }
            itemSizesTextView.setText(sizesBuilder.toString());
        }
    }
}