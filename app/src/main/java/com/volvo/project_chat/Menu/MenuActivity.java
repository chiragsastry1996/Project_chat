package com.volvo.project_chat.Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.volvo.project_chat.Item_List.Order_Cart_Activity;
import com.volvo.project_chat.R;

public class MenuActivity extends AppCompatActivity {

    ImageView button_oders, button_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        button_oders = (ImageView) findViewById(R.id.orders);
        button_cart = (ImageView) findViewById(R.id.cart);

        button_oders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, Order_Cart_Activity.class);
                intent.putExtra("key", "orders");
                startActivity(intent);
                finish();
            }
        });

        button_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, Order_Cart_Activity.class);
                intent.putExtra("key", "cart");
                startActivity(intent);
                finish();
            }
        });

    }
}
