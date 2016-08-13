package hu.ait.android.shoppinglist.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.ait.android.shoppinglist.AddItemActivity;
import hu.ait.android.shoppinglist.MainActivity;
import hu.ait.android.shoppinglist.PopUpDetailsActivity;
import hu.ait.android.shoppinglist.R;
import hu.ait.android.shoppinglist.data.Category;
import hu.ait.android.shoppinglist.data.Item;

/**
 * Created by nathanmajumder on 4/8/16.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>
    implements ItemTouchHelperAdapter {

    public static final String KEY_ITEM_POSITION = "itemPosition";

    private Context context;
    public Category category;
    private int icon;
    private List<Item> items = new ArrayList<Item>();

    public ItemAdapter(Context context, Category category) {
        this.context = context;
        this.category = category;
        if (category == Category.FOOD) {
            icon = R.drawable.food;
        } else if (category == Category.CLOTHING) {
            icon = R.drawable.clothing;
        } else if (category == Category.ELECTRONICS) {
            icon = R.drawable.electronics;
        } else if (category == Category.SPORTS) {
            icon = R.drawable.sports;
        } else if (category == Category.HOUSEHOLD) {
            icon = R.drawable.household;
        } else {
            icon = R.drawable.misc;
        }

        List<Item> allItems = Item.listAll(Item.class);
        items.clear();
        for (Item item : allItems) {
            if (item.getCategory() == this.category) {
                items.add(0,item);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_row, parent, false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemName.setText(items.get(position).getName());
        holder.itemIcon.setImageResource(icon);
        holder.itemCB.setChecked(items.get(position).isChecked());
        holder.itemCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = items.get(position);
                item.setChecked(holder.itemCB.isChecked());
                notifyDataSetChanged();
            }
        });
        holder.itemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // put up dialog with description and price
                Item item = items.get(position);
                Intent showDetailsIntent = new Intent(context, PopUpDetailsActivity.class);
                showDetailsIntent.putExtra(AddItemActivity.KEY_NAME, item.getName());
                showDetailsIntent.putExtra(AddItemActivity.KEY_DESCRIPTION, item.getDescription());
                showDetailsIntent.putExtra(AddItemActivity.KEY_PRICE, item.getPrice());
                showDetailsIntent.putExtra(AddItemActivity.KEY_CAT_IND,item.getCategoryIndex());
                showDetailsIntent.putExtra(KEY_ITEM_POSITION,position);
                ((Activity) context).startActivityForResult(showDetailsIntent, MainActivity.POPUP_DETAILS_CODE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItem(int position, String name, String descrip, int price) {
        Item item = items.get(position);
        item.setName(name);
        item.setDescription(descrip);
        item.setPrice(price);
        item.save();

        notifyDataSetChanged();
    }


    public void addItem(Item item) {
        items.add(0, item);
        item.save();

        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        Item.delete(items.get(position));
        items.remove(position);

        notifyItemRemoved(position);
    }

    public void removeAll() {
        for (Item item : items) {
            Item.delete(item);
        }
        items.clear();

        notifyDataSetChanged();
    }

    public void removeSelected() {
        for (Item item : items) {
            if (item.isChecked()) {
                Item.delete(item);
            }
        }
        List<Item> allItems = new ArrayList<Item>();
        for (Item item : items) {
            allItems.add(item);
        }
        items.clear();
        for (Item item : allItems) {
            if (!(item.isChecked())) {
                items.add(item);
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(items, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(items, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onItemDismiss(int position) {
        removeItem(position);
        redrawViewHeight();
    }

    private void redrawViewHeight() {
        LinearLayout thisLayout;
        switch (category) {
            case FOOD: thisLayout = MainActivity.foodLayout; break;
            case CLOTHING: thisLayout = MainActivity.clothingLayout; break;
            case ELECTRONICS: thisLayout = MainActivity.electronicsLayout; break;
            case SPORTS: thisLayout = MainActivity.sportsLayout; break;
            case HOUSEHOLD: thisLayout = MainActivity.householdLayout; break;
            default: thisLayout = MainActivity.miscLayout; break;
        }
        ViewGroup.LayoutParams params = thisLayout.getLayoutParams();
        params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                40 * getItemCount() + 20, context.getResources().getDisplayMetrics());
        thisLayout.setLayoutParams(params);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView itemName;
        private CheckBox itemCB;
        private ImageView itemIcon;
        private Button itemBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemIcon = (ImageView) itemView.findViewById(R.id.itemIcon);
            itemCB = (CheckBox) itemView.findViewById(R.id.itemCheckBox);
            itemBtn = (Button) itemView.findViewById(R.id.itemDetailsBtn);
        }
    }

}
