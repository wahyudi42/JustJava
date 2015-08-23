package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    // global variabel
    int quantity = 0;
    boolean checkbox_1;
    boolean checkbox_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //display(quantity);
        //displayPrice(quantity * 3000);

        int price = calculatePrice();
        //displayMessage("$" + price);
        String priceSummary = createOrderSumary(price);
        displayMessage(priceSummary);

        //String price = (quantity * 3000) + " rupiah untuk " + quantity + " gelas kopi. Bayarlah!";

        // Get user's name
        EditText nameField = (EditText) findViewById(R.id.editText);
        Editable nameEditable = nameField.getText();
        String name = nameEditable.toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.item_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.item_checkbox_2);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        String message = priceSummary;

        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculate the price of the order
     *
     * @return total price
     * */

    public int calculatePrice(){
        int price;
        price = quantity * (5 + cekTopping());
        return price;
    }

    /**
     * Create summary of the order
     *
     * @param price of the order
     * @return text summary
     * */
    private String createOrderSumary(int price){

        String priceMessage = getNama();
        priceMessage = priceMessage + "\nAdd Whipped Cream? " + getState();
        priceMessage = priceMessage + "\nAdd Chocolate? " + getState_2();
        priceMessage= priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal: $" + price;
        priceMessage = priceMessage + "\nThank You!";
        return priceMessage;
    }

    public String getNama(){
        EditText nama = (EditText) findViewById(R.id.editText);
        String namaPembeli = nama.getText().toString();
        return namaPembeli;
    }

    public boolean getState(){
        CheckBox checkBox = (CheckBox) findViewById(R.id.item_checkbox);
        checkbox_1  = checkBox.isChecked();
        Log.v("MainActivity", String.valueOf(checkbox_1));
        return checkbox_1;
    }

    public boolean getState_2(){
        CheckBox checkBox = (CheckBox) findViewById(R.id.item_checkbox_2);
        checkbox_2  = checkBox.isChecked();
        Log.v("MainActivity", String.valueOf(checkbox_2));
        return checkbox_2;
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void plusQuantity(View view) {

        if (quantity > 100){
            Toast.makeText(MainActivity.this,
                    "Sorry! You can't order more than 100 cup of coffees.", Toast.LENGTH_SHORT).show();
        }else {
            quantity = quantity + 1;
            displayQuantity(quantity);
            //displayPrice(quantity * 3000);
        }
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void minusQuantity(View view) {
        if (quantity <1){
            Toast.makeText(MainActivity.this,
                    "Invalid Number! Please try again.", Toast.LENGTH_SHORT).show();
        }else {
            quantity = quantity - 1;
            displayQuantity(quantity);
            //displayPrice(quantity * 3000);
        }
    }

    /**
     *  This method for check the boolean toopings
     * */
    public int cekTopping(){
        int tambahan = 0;

        if(getState()){
            tambahan = 1;
            if(getState_2()){
                tambahan = 3;
            }
        } else if(getState_2()){
            tambahan =  2;
        }

        return tambahan;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen. (not used)
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}