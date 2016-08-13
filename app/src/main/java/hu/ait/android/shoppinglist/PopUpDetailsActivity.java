package hu.ait.android.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import hu.ait.android.shoppinglist.adapter.ItemAdapter;

public class PopUpDetailsActivity extends AppCompatActivity {

    LinearLayout titleBar;
    TextView catTitle;
    TextView itemName;
    TextView itemPrice;
    TextView itemDescrip;
    ImageView catIcon;
    Button dismissBtn;
    Button editBtn;

    private String name;
    private String descrip;
    private int price;
    private int itemPosition;
    private int catInd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_details);

        titleBar = (LinearLayout) findViewById(R.id.popupTitleBar);
        catTitle = (TextView) findViewById(R.id.popupActivityTitle);
        itemName = (TextView) findViewById(R.id.popupActivityName);
        itemDescrip = (TextView) findViewById(R.id.popupActivityDescription);
        itemPrice = (TextView) findViewById(R.id.popupActivityPrice);
        catIcon = (ImageView) findViewById(R.id.popupActivityIcon);
        dismissBtn = (Button) findViewById(R.id.popupBackBtn);
        editBtn = (Button) findViewById(R.id.popupEditBtn);

        name = getIntent().getStringExtra(AddItemActivity.KEY_NAME);
        descrip = getIntent().getStringExtra(AddItemActivity.KEY_DESCRIPTION);
        price = getIntent().getIntExtra(AddItemActivity.KEY_PRICE, 0);
        catInd = getIntent().getIntExtra(AddItemActivity.KEY_CAT_IND,0);
        itemPosition = getIntent().getIntExtra(ItemAdapter.KEY_ITEM_POSITION, 0);
        switch (catInd) {
            case 0:
                catTitle.setText(R.string.foodLabel);
                catIcon.setImageResource(R.drawable.food);
                titleBar.setBackgroundResource(R.color.colorFood);
                break;
            case 1:
                catTitle.setText(R.string.clothingLabel);
                catIcon.setImageResource(R.drawable.clothing);
                titleBar.setBackgroundResource(R.color.colorClothing);
                break;
            case 2:
                catTitle.setText(R.string.electronicsLabel);
                catIcon.setImageResource(R.drawable.electronics);
                titleBar.setBackgroundResource(R.color.colorElectronics);
                break;
            case 3:
                catTitle.setText(R.string.sportsLabel);
                catIcon.setImageResource(R.drawable.sports);
                titleBar.setBackgroundResource(R.color.colorSports);
                break;
            case 4:
                catTitle.setText(R.string.householdLabel);
                catIcon.setImageResource(R.drawable.household);
                titleBar.setBackgroundResource(R.color.colorHousehold);
                break;
            default:
                catTitle.setText(R.string.miscLabel);
                catIcon.setImageResource(R.drawable.misc);
                titleBar.setBackgroundResource(R.color.colorMisc);
                break;
        }
        itemName.setText(name);
        itemDescrip.setText(descrip);
        itemPrice.setText(price + getString(R.string.priceCurrency));

        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(AddItemActivity.KEY_NAME, name);
                resultIntent.putExtra(AddItemActivity.KEY_DESCRIPTION, descrip);
                resultIntent.putExtra(AddItemActivity.KEY_PRICE, price);
                resultIntent.putExtra(AddItemActivity.KEY_CAT_IND, catInd);
                resultIntent.putExtra(ItemAdapter.KEY_ITEM_POSITION, itemPosition);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                //startAnim();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(PopUpDetailsActivity.this, PopUpEditActivity.class);
                editIntent.putExtra(AddItemActivity.KEY_NAME, name);
                editIntent.putExtra(AddItemActivity.KEY_DESCRIPTION, descrip);
                editIntent.putExtra(AddItemActivity.KEY_PRICE, price);
                editIntent.putExtra(AddItemActivity.KEY_CAT_IND, catInd);
                startActivityForResult(editIntent, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                // extract data
                String thisName = data.getStringExtra(AddItemActivity.KEY_NAME);
                String thisDescrip = data.getStringExtra(AddItemActivity.KEY_DESCRIPTION);
                int thisPrice = data.getIntExtra(AddItemActivity.KEY_PRICE, 0);
                itemName.setText(thisName);
                itemDescrip.setText(thisDescrip);
                itemPrice.setText(thisPrice + getString(R.string.priceCurrency));
                price = thisPrice;
                descrip = thisDescrip;
                name = thisName;
            }
        }
    }

}
