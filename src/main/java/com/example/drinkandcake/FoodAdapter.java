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

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{

    private List<Product> mListFood;
    private IClickItemProductListener iClickItemProductListener;
    private Context context;

    public FoodAdapter(Context context,List<Product> mListFood, IClickItemProductListener iClickItemProductListener) {
        this.mListFood = mListFood;
        this.iClickItemProductListener = iClickItemProductListener;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_food,parent,false);
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


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemProductListener.onClickItemProduct(product);
            }
        });
        holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemProductListener.onClickCart(product,1);
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
        private TextView textViewName,textViewPrice;
        private CardView cardView;
        private Button addCart;


        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            addCart = itemView.findViewById(R.id.addCart);
            cardView = itemView.findViewById(R.id.layout_item);
            imageView = itemView.findViewById(R.id.img_food);
            textViewName = itemView.findViewById(R.id.tv_name_food);
            textViewPrice = itemView.findViewById(R.id.tv_price_food);
        }
    }
}
