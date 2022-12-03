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

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Product> mListFood;
    private IClickItemProductListener iClickItemProductListener;
    private int def = 1;
    Context context;

    public CategoryAdapter(Context context,List<Product> mListFood,IClickItemProductListener iClickItemProductListener) {
        this.mListFood = mListFood;
        this.iClickItemProductListener = iClickItemProductListener;
        this.context = context;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        final Product product = mListFood.get(position);
        if (product == null){
            return;
        }
        String imageName = product.getImage();
        int resID = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        holder.imageView.setImageResource(resID);
        holder.textViewPrice.setText(product.getPrice()+"");
        holder.textViewName.setText(product.getName());
        if(product.getQuantity() == 0){
            product.setQuantity(def);
            holder.quantity.setText(product.getQuantity()+"");
        }
        holder.quantity.setText(product.getQuantity()+"");
        //final int[] i = {Integer.parseInt(holder.quantity.getText().toString())};
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
        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setQuantity(def);
                iClickItemProductListener.onClickBuy(product);
            }
        });
        holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemProductListener.onClickCart(product,Integer.parseInt(holder.quantity.getText().toString()));
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

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewName,textViewPrice,quantity;
        private CardView cardView;
        private Button cong,tru,buy,addCart;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            buy = itemView.findViewById(R.id.BuyNow);
            addCart = itemView.findViewById(R.id.AddtoCart);
            cong = itemView.findViewById(R.id.cong);
            tru = itemView.findViewById(R.id.tru);
            cardView = itemView.findViewById(R.id.layout_cate);
            imageView = itemView.findViewById(R.id.img_food);
            textViewName = itemView.findViewById(R.id.tv_name_food);
            textViewPrice = itemView.findViewById(R.id.tv_price_food);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
