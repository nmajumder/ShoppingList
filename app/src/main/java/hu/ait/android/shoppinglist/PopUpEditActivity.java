package hu.ait.android.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PopUpEditActivity extends AppCompatActivity {

    LinearLayout titleBar;
    TextView catTitle;
    EditText itemName;
    EditText itemPrice;
    EditText itemDescrip;
    ImageView catIcon;
    Button cancelBtn;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_edit);

        titleBar = (LinearLayout) findViewById(R.id.editTitleBar);
        catTitle = (TextView) findViewById(R.id.editActivityTitle);
        itemName = (EditText) findViewById(R.id.editActivityName);
        itemDescrip = (EditText) findViewById(R.id.editActivityDescription);
        itemPrice = (EditText) findViewById(R.id.editActivityPrice);
        catIcon = (ImageView) findViewById(R.id.editActivityIcon);
        cancelBtn = (Button) findViewById(R.id.editCancelBtn);
        saveBtn = (Button) findViewById(R.id.editSaveBtn);

        String name = getIntent().getStringExtra(AddItemActivity.KEY_NAME);
        String description = getIntent().getStringExtra(AddItemActivity.KEY_DESCRIPTION);
        int price = getIntent().getIntExtra(AddItemActivity.KEY_PRICE, 0);
        int catInd = getIntent().getIntExtra(AddItemActivity.KEY_CAT_IND,0);
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

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, cancelIntent);
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemName.getText().toString().trim().length() == 0) {
                    itemName.setError(getString(R.string.itemNoNameError));
                    return;
                } else if (itemPrice.getText().toString().trim().length() == 0) {
                    itemPrice.setError(getString(R.string.itemNoPriceError));
                    return;
                }
                String newName = itemName.getText().toString();
                String newDescrip = itemDescrip.getText().toString();
                int newPrice = Integer.parseInt(itemPrice.getText().toString());
                Intent saveIntent = new Intent();
                saveIntent.putExtra(AddItemActivity.KEY_NAME, newName);
                saveIntent.putExtra(AddItemActivity.KEY_DESCRIPTION, newDescrip);
                saveIntent.putExtra(AddItemActivity.KEY_PRICE, newPrice);
                setResult(Activity.RESULT_OK,saveIntent);
                finish();
            }
        });

    }
}
