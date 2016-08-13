package hu.ait.android.shoppinglist.data;

import com.orm.SugarRecord;

/**
 * Created by nathanmajumder on 4/7/16.
 */

public class Item extends SugarRecord {

    // put all class members of an item here
    String name;
    String description;
    Category category;
    int price;
    Boolean checked;

    public Item() {
    }

    // constructor with all parameters of an Item

    public Item(String name, String description, Category category, int price) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.checked = false;
    }

    // getters and setters

    public int getCategoryIndex() {
        switch (category) {
            case FOOD: return 0;
            case CLOTHING: return 1;
            case ELECTRONICS: return 2;
            case SPORTS: return 3;
            case HOUSEHOLD: return 4;
            default: return 5;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Boolean isChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
