package hu.ait.android.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class AddItemActivity extends AppCompatActivity {

    public static final String KEY_NAME = "itemName";
    public static final String KEY_DESCRIPTION = "itemDescription";
    public static final String KEY_PRICE = "itemPrice";
    public static final String KEY_CAT_IND = "itemCategoryIndex";

    Spinner categorySpinner;

    LinearLayout layout;
    EditText etItemName;
    EditText etItemDescription;
    EditText etPrice;
    Button cancelBtn;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        layout = (LinearLayout) findViewById(R.id.addItemLayout);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        saveBtn = (Button) findViewById(R.id.saveItemBtn);
        etItemName = (EditText) findViewById(R.id.etItemName);
        etItemDescription = (EditText) findViewById(R.id.etDescription);
        etPrice = (EditText) findViewById(R.id.etItemPrice);

        categorySpinner = (Spinner) findViewById(R.id.spinnerCategories);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, resultIntent);
                finish();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etItemName.getText().toString().trim().length() == 0) {
                    etItemName.setError(getString(R.string.itemNoNameError));
                    return;
                } else if (etPrice.getText().toString().trim().length() == 0) {
                    etPrice.setError(getString(R.string.itemNoPriceError));
                    return;
                } else if (categorySpinner.getSelectedItem() == null) {
                    return;
                }

                String itemName = etItemName.getText().toString();
                String itemDescription = etItemDescription.getText().toString();
                int itemPrice = Integer.parseInt(etPrice.getText().toString());
                int categoryIndex = categorySpinner.getSelectedItemPosition();

                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_NAME,itemName);
                resultIntent.putExtra(KEY_DESCRIPTION,itemDescription);
                resultIntent.putExtra(KEY_CAT_IND,categoryIndex);
                resultIntent.putExtra(KEY_PRICE,itemPrice);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
