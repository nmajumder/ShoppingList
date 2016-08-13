package hu.ait.android.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import hu.ait.android.shoppinglist.adapter.ItemAdapter;
import hu.ait.android.shoppinglist.adapter.ItemTouchHelperCallback;
import hu.ait.android.shoppinglist.data.Category;
import hu.ait.android.shoppinglist.data.Item;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_ITEM_CODE = 0;
    public static final int POPUP_DETAILS_CODE = 1;

    public static LinearLayout foodLayout;
    public static LinearLayout electronicsLayout;
    public static LinearLayout clothingLayout;
    public static LinearLayout sportsLayout;
    public static LinearLayout householdLayout;
    public static LinearLayout miscLayout;

    TextView tvFoodCat;
    TextView tvElectronicsCat;
    TextView tvClothingCat;
    TextView tvSportsCat;
    TextView tvHouseholdCat;
    TextView tvMiscCat;

    ItemAdapter itemRecyclerAdapters[] = new ItemAdapter[6];
    RecyclerView recyclerViews[] = new RecyclerView[6];
    LinearLayoutManager layoutManagers[] = new LinearLayoutManager[6];
    ItemTouchHelper.Callback callbacks[] = new ItemTouchHelper.Callback[6];
    ItemTouchHelper touchHelpers[] = new ItemTouchHelper[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        foodLayout = (LinearLayout) findViewById(R.id.foodLayout);
        clothingLayout = (LinearLayout) findViewById(R.id.clothingLayout);
        electronicsLayout = (LinearLayout) findViewById(R.id.electronicsLayout);
        sportsLayout = (LinearLayout) findViewById(R.id.sportsLayout);
        householdLayout = (LinearLayout) findViewById(R.id.householdLayout);
        miscLayout = (LinearLayout) findViewById(R.id.miscLayout);

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);

        foodLayout.setBackgroundResource(R.drawable.category_border);
        clothingLayout.setBackgroundResource(R.drawable.category_border);
        electronicsLayout.setBackgroundResource(R.drawable.category_border);
        sportsLayout.setBackgroundResource(R.drawable.category_border);
        householdLayout.setBackgroundResource(R.drawable.category_border);
        miscLayout.setBackgroundResource(R.drawable.category_border);

        tvFoodCat = (TextView) foodLayout.findViewById(R.id.tvCategory);
        tvFoodCat.setText(R.string.foodLabel);
        tvFoodCat.setBackgroundResource(R.color.colorFood);
        tvFoodCat.setTextColor(Color.parseColor("#FFFFFF"));

        tvClothingCat = (TextView) clothingLayout.findViewById(R.id.tvCategory);
        tvClothingCat.setText(R.string.clothingLabel);
        tvClothingCat.setBackgroundResource(R.color.colorClothing);
        tvClothingCat.setTextColor(Color.parseColor("#FFFFFF"));

        tvElectronicsCat = (TextView) electronicsLayout.findViewById(R.id.tvCategory);
        tvElectronicsCat.setText(R.string.electronicsLabel);
        tvElectronicsCat.setBackgroundResource(R.color.colorElectronics);
        tvElectronicsCat.setTextColor(Color.parseColor("#FFFFFF"));

        tvSportsCat = (TextView) sportsLayout.findViewById(R.id.tvCategory);
        tvSportsCat.setText(R.string.sportsLabel);
        tvSportsCat.setBackgroundResource(R.color.colorSports);
        tvSportsCat.setTextColor(Color.parseColor("#FFFFFF"));

        tvHouseholdCat = (TextView) householdLayout.findViewById(R.id.tvCategory);
        tvHouseholdCat.setText(R.string.householdLabel);
        tvHouseholdCat.setBackgroundResource(R.color.colorHousehold);
        tvHouseholdCat.setTextColor(Color.parseColor("#FFFFFF"));

        tvMiscCat = (TextView) miscLayout.findViewById(R.id.tvCategory);
        tvMiscCat.setText(R.string.miscLabel);
        tvMiscCat.setBackgroundResource(R.color.colorMisc);
        tvMiscCat.setTextColor(Color.parseColor("#FFFFFF"));

        itemRecyclerAdapters[0] = new ItemAdapter(this, Category.FOOD);
        itemRecyclerAdapters[1] = new ItemAdapter(this, Category.CLOTHING);
        itemRecyclerAdapters[2] = new ItemAdapter(this, Category.ELECTRONICS);
        itemRecyclerAdapters[3] = new ItemAdapter(this, Category.SPORTS);
        itemRecyclerAdapters[4] = new ItemAdapter(this, Category.HOUSEHOLD);
        itemRecyclerAdapters[5] = new ItemAdapter(this, Category.MISC);
        recyclerViews[0] = (RecyclerView) foodLayout.findViewById(R.id.recyclerView);
        recyclerViews[1] = (RecyclerView) clothingLayout.findViewById(R.id.recyclerView);
        recyclerViews[2] = (RecyclerView) electronicsLayout.findViewById(R.id.recyclerView);
        recyclerViews[3] = (RecyclerView) sportsLayout.findViewById(R.id.recyclerView);
        recyclerViews[4] = (RecyclerView) householdLayout.findViewById(R.id.recyclerView);
        recyclerViews[5] = (RecyclerView) miscLayout.findViewById(R.id.recyclerView);
        for (int i = 0; i < 6; i++) {
            layoutManagers[i] = new LinearLayoutManager(this);
            recyclerViews[i].setHasFixedSize(true);
            recyclerViews[i].setLayoutManager(layoutManagers[i]);
            recyclerViews[i].setAdapter(itemRecyclerAdapters[i]);

            callbacks[i] = new ItemTouchHelperCallback(itemRecyclerAdapters[i]);
            touchHelpers[i] = new ItemTouchHelper(callbacks[i]);
            touchHelpers[i].attachToRecyclerView(recyclerViews[i]);
        }

        adjustViewHeights();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // extract data
                String name = data.getStringExtra(AddItemActivity.KEY_NAME);
                String descrip = data.getStringExtra(AddItemActivity.KEY_DESCRIPTION);
                int price = data.getIntExtra(AddItemActivity.KEY_PRICE, 0);
                int categoryIndex = data.getIntExtra(AddItemActivity.KEY_CAT_IND,0);
                Category cat;
                switch (categoryIndex) {
                    case 0: cat = Category.FOOD; break;
                    case 1: cat = Category.CLOTHING; break;
                    case 2: cat = Category.ELECTRONICS; break;
                    case 3: cat = Category.SPORTS; break;
                    case 4: cat = Category.HOUSEHOLD; break;
                    default: cat = Category.MISC; break;
                }
                Item newItem = new Item(name,descrip,cat,price);
                itemRecyclerAdapters[categoryIndex].addItem(newItem);
                adjustViewHeights();
            }
        } else if (requestCode == POPUP_DETAILS_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String name = data.getStringExtra(AddItemActivity.KEY_NAME);
                String descrip = data.getStringExtra(AddItemActivity.KEY_DESCRIPTION);
                int price = data.getIntExtra(AddItemActivity.KEY_PRICE, 0);
                int catInd = data.getIntExtra(AddItemActivity.KEY_CAT_IND,0);
                int itemPos = data.getIntExtra(ItemAdapter.KEY_ITEM_POSITION,0);
                itemRecyclerAdapters[catInd].updateItem(itemPos,name,descrip,price);
            }
        }
    }

    public void adjustViewHeights() {

        int numItems1 = itemRecyclerAdapters[0].getItemCount();
        ViewGroup.LayoutParams params1 = foodLayout.getLayoutParams();
        params1.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                40*numItems1+20, getResources().getDisplayMetrics());
        foodLayout.setLayoutParams(params1);

        int numItems2 = itemRecyclerAdapters[1].getItemCount();
        ViewGroup.LayoutParams params2 = clothingLayout.getLayoutParams();
        params2.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                40*numItems2+20, getResources().getDisplayMetrics());
        clothingLayout.setLayoutParams(params2);

        int numItems3 = itemRecyclerAdapters[2].getItemCount();
        ViewGroup.LayoutParams params3 = electronicsLayout.getLayoutParams();
        params3.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                40*numItems3+20, getResources().getDisplayMetrics());
        electronicsLayout.setLayoutParams(params3);

        int numItems4 = itemRecyclerAdapters[3].getItemCount();
        ViewGroup.LayoutParams params4 = sportsLayout.getLayoutParams();
        params4.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                40*numItems4+20, getResources().getDisplayMetrics());
        sportsLayout.setLayoutParams(params4);

        int numItems5 = itemRecyclerAdapters[4].getItemCount();
        ViewGroup.LayoutParams params5 = householdLayout.getLayoutParams();
        params5.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                40*numItems5+20, getResources().getDisplayMetrics());
        householdLayout.setLayoutParams(params5);

        int numItems6 = itemRecyclerAdapters[5].getItemCount();
        ViewGroup.LayoutParams params6 = miscLayout.getLayoutParams();
        params6.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                40*numItems6+20, getResources().getDisplayMetrics());
        miscLayout.setLayoutParams(params6);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItemAction:
                // make new intent and go to add item activity
                Intent addItemIntent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivityForResult(addItemIntent,ADD_ITEM_CODE);
                break;
            case R.id.deleteAllAction:
                // delete all items
                for (int i = 0; i < 6; i++) {
                    itemRecyclerAdapters[i].removeAll();
                }
                adjustViewHeights();
                break;
            case R.id.deleteSelectedAction:
                // delete selected items
                for (int i = 0; i < 6; i++) {
                    itemRecyclerAdapters[i].removeSelected();
                }
                adjustViewHeights();
                break;
            default:
                break;
        }

        return true;
    }
}
