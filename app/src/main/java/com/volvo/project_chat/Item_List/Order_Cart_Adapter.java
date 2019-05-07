package com.volvo.project_chat.Item_List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.volvo.project_chat.R;

import java.util.List;

public class Order_Cart_Adapter extends RecyclerView.Adapter<Order_Cart_Adapter.MyViewHolder>{

    private Context mContext;
    List<Order_Cart_Model> orderCartModelList;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView item_image;
        TextView item_name, item_price, item_quantity, item_increase, item_decrease, item_total_amount;


        public MyViewHolder(View view) {
            super(view);

            item_image = (ImageView)view.findViewById(R.id.item_image);
            item_name = (TextView)view.findViewById(R.id.item_name);
            item_price = (TextView)view.findViewById(R.id.item_price);
            item_quantity = (TextView)view.findViewById(R.id.item_quantity);
            item_increase = (TextView)view.findViewById(R.id.item_increase);
            item_decrease = (TextView)view.findViewById(R.id.item_decrease);
            item_total_amount = (TextView)view.findViewById(R.id.item_total_amount);

        }
    }

    public Order_Cart_Adapter(Context mContext, List<Order_Cart_Model> newsList) {
        this.mContext = mContext;
        this.orderCartModelList = newsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.status_list_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Order_Cart_Model order_cart_model = orderCartModelList.get(position);

        Glide.with(mContext).load(order_cart_model.getImage()).into(holder.item_image);
        holder.item_name.setText(order_cart_model.getName());
        holder.item_price.setText(order_cart_model.getUnit_price());
        holder.item_quantity.setText(order_cart_model.getQuantity());
        int total_new = Integer.parseInt(order_cart_model.getQuantity().toString())*Integer.parseInt(order_cart_model.getUnit_price().toString());
        holder.item_total_amount.setText(String.valueOf(total_new));
        order_cart_model.setTotal_amount(String.valueOf(total_new));

        if(order_cart_model.getKey().equals("orders")) {

            holder.item_increase.setVisibility(View.GONE);
            holder.item_decrease.setVisibility(View.GONE);

        } else if (order_cart_model.getKey().equals("cart")) {
            holder.item_increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity_new = Integer.parseInt(holder.item_quantity.getText().toString());
                    quantity_new++;
                    holder.item_quantity.setText(String.valueOf(quantity_new));
                    int total_new = quantity_new*Integer.parseInt(order_cart_model.getUnit_price().toString());
                    holder.item_total_amount.setText(String.valueOf(total_new));
                    order_cart_model.setTotal_amount(String.valueOf(total_new));
                    Order_Cart_Activity.calculate_total_amount();
                }
            });

            holder.item_decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity_new = Integer.parseInt(holder.item_quantity.getText().toString());
                    quantity_new--;
                    holder.item_quantity.setText(String.valueOf(quantity_new));
                    int total_new = quantity_new*Integer.parseInt(order_cart_model.getUnit_price().toString());
                    holder.item_total_amount.setText(String.valueOf(total_new));
                    order_cart_model.setTotal_amount(String.valueOf(total_new));
                    Order_Cart_Activity.calculate_total_amount();
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return orderCartModelList.size();
    }

}