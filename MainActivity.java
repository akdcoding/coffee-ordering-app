package com.example.android.justjava;

/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity=2;

    public void increment(View view){
        if(quantity == 100){
            Toast.makeText(getApplicationContext(),"Sorry, we can serve upto 100 cups",Toast.LENGTH_SHORT).show();
        }
        else quantity=quantity+1;

        displayQuantity(quantity);
    }

    public void decrement(View view){
        if(quantity == 1){
            Toast.makeText(getApplicationContext(),"Please order at least 1 cup of coffee",Toast.LENGTH_SHORT).show();
        }
        else quantity=quantity-1;

        displayQuantity(quantity);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
            String priceMessage=msg();
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.order_summary_email_subject));
            intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] {"akd.thube@gamil.com"});
            intent.putExtra(Intent.EXTRA_TEXT,priceMessage );
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
            displayMessage(priceMessage);

    }

    public void order(View view){
        String priceMessage=msg();
        displayMessage(priceMessage);
    }

    public String msg(){
        EditText text = (EditText) findViewById(R.id.name_field);
        String name   =  text.getText().toString();

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolateCheckbox = chocolateCheckbox.isChecked();
        String priceMessage= createOrderSummary(name,hasWhippedCream,hasChocolateCheckbox);
        return priceMessage;
    }

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolateCheckbox) {
        int price=0;
        if(hasWhippedCream==true)
            price = price + 5*quantity;
        if(hasChocolateCheckbox==true)
             price = price + 10*quantity;
        price = price + quantity * 30;
        return price;
    }



    private String createOrderSummary(String name,boolean hasWhippedCream, boolean hasChocolateCheckbox){
        String priceMessage=getString(R.string.order_summary_name)+ " : " + name;
        priceMessage += "\n" +getString(R.string.order_summary_quantity)+ " :" + quantity;
        if(hasWhippedCream==true){priceMessage += "\n" + getString(R.string.order_summary_whipped_cream);}
        if(hasChocolateCheckbox==true){priceMessage += "\n"+ getString(R.string.order_summary_chocolate);}
        priceMessage += "\n"+ getString(R.string.order_summary_price) +": ₹" + calculatePrice(hasWhippedCream,hasChocolateCheckbox);
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;

    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}
