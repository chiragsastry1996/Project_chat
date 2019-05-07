package com.volvo.project_chat.Item_List;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.volvo.project_chat.Menu.MenuActivity;
import com.volvo.project_chat.Utils.HttpHandler;
import com.volvo.project_chat.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Order_Cart_Activity extends AppCompatActivity {

    public static List<Order_Cart_Model> orderCartModelList;
    private Order_Cart_Adapter order_cart_adapter;
    private RecyclerView recyclerView;
    ProgressBar progressBar;
    Button button_order;
    public static TextView total_amount;
    String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cart_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key = extras.getString("key");
        }

        total_amount = (TextView)findViewById(R.id.total_amount);

        button_order = (Button)findViewById(R.id.order);
        button_order.setVisibility(View.GONE);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        orderCartModelList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.order_list_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);


        new GetList().execute();

    }



    public static void calculate_total_amount() {
    int amount = 0;
        for(int i=0; i<orderCartModelList.size(); i++) {
            amount = amount + Integer.parseInt(orderCartModelList.get(i).getTotal_amount());
        }

        total_amount.setText(String.valueOf(amount));
    }

    public class GetList extends AsyncTask<Void, Void, Void> {
        private static final String TAG = "Order_Cart_Activity";
        ArrayList<ArrayList<String>> detailLists = new ArrayList<>();

        @Override
        public void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        //GET request done in background
        @Override
        public Void doInBackground(Void... arg0) {
            //Calling the HTTPHandler
            HttpHandler sh = new HttpHandler();
            String jsonStr = null;

            // Making a request to url and getting response
            if(key.equals("orders")){
                jsonStr = sh.makeServiceCall("https://teameovai.herokuapp.com/orders");
            } else if(key.equals("cart")){
                jsonStr = sh.makeServiceCall("https://teameovai.herokuapp.com/cart");
            }



            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
//                    JSONObject jsonObj = new JSONObject(jsonStr);

                    String item_id = "N/A";
                    String item_name= "N/A";
                    String item_quantity= "N/A";
                    String item_image= "N/A";
                    String item_unit_price= "N/A";
                    String type= key;

                    JSONArray response = new JSONArray(jsonStr);

                    for(int i=0;i<response.length();i++){

                        JSONObject details = response.getJSONObject(i);
                        item_id = details.getString("id");
//                        if(key.equals("orders")){
//                            item_name = details.getString("Name") ;
//                        } else if(key.equals("cart")){
                            item_name = details.getString("name") ;
//                        }
                        item_quantity = details.getString("quantity");
                        item_image = details.getString("img");
                        item_unit_price = details.getString("unitprice");

//                        System.out.println("ccccc" + display_value);

                        ArrayList<String> detail = new ArrayList<>();

                        detail.add(item_id);
                        detail.add(item_name);
                        detail.add(item_quantity);
                        detail.add(item_image);
                        detail.add(item_unit_price);
                        detail.add(type);

                        detailLists.add(detail);

                    }

                } catch (final Exception e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            if(key.equals("orders")){
                button_order.setVisibility(View.GONE);
            } else if(key.equals("cart")){
                button_order.setVisibility(View.VISIBLE);
            }

            for (int j = 0; j < detailLists.size(); j++){
                orderCartModelList.add(new Order_Cart_Model(detailLists.get(j).get(0), detailLists.get(j).get(1),
                        detailLists.get(j).get(2), detailLists.get(j).get(3), detailLists.get(j).get(4), detailLists.get(j).get(5)));
            }
            order_cart_adapter = new Order_Cart_Adapter(Order_Cart_Activity.this, orderCartModelList);
            recyclerView.setAdapter(order_cart_adapter);
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Order_Cart_Activity.this, MenuActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();  // optional depending on your needs
    }

}
