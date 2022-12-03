package com.example.drinkandcake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkandcake.model.Product;
import com.example.drinkandcake.my_interface.IClickItemProductListener;
import com.example.drinkandcake.sqlite.CartDao;

import java.util.List;

public class FoodCartAdapter extends RecyclerView.Adapter<FoodCartAdapter.FoodViewHolder>{

    private List<Product> mListFood;
    private IClickItemProductListener iClickItemProductListener;
    private int def = 1;
    private Context context;

    public FoodCartAdapter(Context context,List<Product> mListFood, IClickItemProductListener iClickItemProductListener) {
        this.mListFood = mListFood;
        this.iClickItemProductListener = iClickItemProductListener;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_productcart,parent,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        final Product product = mListFood.get(position);
        if (product == null){
            return;
        }
        String imageName = product.getImage();
        int resID = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        holder.imageView.setImageResource(resID);
        holder.textViewPrice.setText(product.getPrice()+"");
        holder.textViewName.setText(product.getName());
        //holder.imageView.setImageResource(Integer.parseInt(product.getImage()));
        holder.quantity.setText((product.getQuantity()+""));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemProductListener.onClickItemProduct(product);
            }
        });
        def = product.getQuantity();
        holder.cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                def += 1;
                product.setQuantity(def);
                holder.quantity.setText(product.getQuantity()+"");
            }
        });
        holder.tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(def > 1){
                    def -= 1;
                    product.setQuantity(def);
                    holder.quantity.setText(product.getQuantity()+"");
                }
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemProductListener.onClickBuy(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListFood != null){
            return mListFood.size();
        }
        return 0;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textViewName,textViewPrice,quantity;
        private CardView cardView;
        private Button cong,tru,remove;


        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            remove = itemView.findViewById(R.id.remove);
            cong = itemView.findViewById(R.id.cong);
            tru = itemView.findViewById(R.id.tru);
            quantity = itemView.findViewById(R.id.quantity);
            cardView = itemView.findViewById(R.id.layout_Productcart);
            imageView = itemView.findViewById(R.id.img_food);
            textViewName = itemView.findViewById(R.id.tv_name_food);
            textViewPrice = itemView.findViewById(R.id.tv_price_food);
        }
    }
}
