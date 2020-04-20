package com.example.mybloodyapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mybloodyapp.R;
import com.example.mybloodyapp.data.model.posts.PostsData;
import com.example.mybloodyapp.view.activity.HomeCycleActivity;
import com.example.mybloodyapp.view.fragment.HomeCycleFragments.ArticleHomeNavFragment;
import com.example.mybloodyapp.view.fragment.HomeCycleFragments.articlePost.PostArticleFragment;
import com.example.mybloodyapp.view.fragment.HomeCycleFragments.donationRequest.AddingDontationRequestFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.mybloodyapp.data.api.RetrofitClient.getClient;
import static com.example.mybloodyapp.helper.HelperMethod.replaceFragment;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.LoadData;

public class RecyclerViewAdapterArticles extends RecyclerView.Adapter<RecyclerViewAdapterArticles.ArticleRowHolder>  {
    private ArrayList<PostsData> postsList;
    Context context;
    private String API_TOKEN;
    boolean isFav =true;


    OnFavBuClickedListener onFavBuClickedListener;

    public interface OnFavBuClickedListener{
        public void onFavBuClicked(boolean isFav ,int postion);
    }



    public RecyclerViewAdapterArticles(ArrayList<PostsData> postsList, Context context,ArticleHomeNavFragment articleHomeNavFragment) {
        this.context = context;
        this.postsList=postsList;
        onFavBuClickedListener = articleHomeNavFragment;
    }

    public void setData(ArrayList<PostsData> postsList){
        this.postsList =postsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewAdapterArticles.ArticleRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_fragment_post,null);
        ArticleRowHolder articleRowHolder = new ArticleRowHolder(view);
        return articleRowHolder;    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapterArticles.ArticleRowHolder holder, final int position) {
        API_TOKEN = LoadData((Activity) context,"API_TOKEN");

        Glide.with(context).load(postsList.get(position).getThumbnailFullPath())
                .into(holder.postBackgroundImage);
        holder.tvTitleOfPost.setText(postsList.get(position).getTitle());

        if (postsList.get(position).getIsFavourite()) {
            holder.imBuIsFav.setImageResource(R.drawable.favourite_icon);
        } else {
            holder.imBuIsFav.setImageResource(R.drawable.unfavourite_icon);
        }


        holder.imBuIsFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postsList.get(position).getIsFavourite()){
                    isFav=false;
                }
                onFavBuClickedListener.onFavBuClicked(isFav,position);
            }
        });

        holder.postBackgroundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeCycleActivity homeCycleActivity =(HomeCycleActivity)context;
                PostArticleFragment postArticleFragment =new PostArticleFragment();
                String postIdString = postsList.get(position).getId().toString();
                String postTitle =postsList.get(position).getTitle();
                String postContent =postsList.get(position).getContent();
                String postImageURL =postsList.get(position).getThumbnailFullPath();
                Boolean isfav =postsList.get(position).getIsFavourite();
                String isFavorite =String.valueOf(isfav);

                Bundle bundle =new Bundle();
                bundle.putString("postIdString",postIdString);
                bundle.putString("postTitle",postTitle);
                bundle.putString("postContent",postContent);
                bundle.putString("postImageURL",postImageURL);
                bundle.putString("isFavorite",isFavorite);


                postArticleFragment.setArguments(bundle);

                replaceFragment(homeCycleActivity.getSupportFragmentManager(),
                        R.id.frame_of_home_fragment_container,postArticleFragment);

            }
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public void filterList(ArrayList<PostsData> filteredList){
        postsList=filteredList;
        notifyDataSetChanged();
    }


    public class ArticleRowHolder extends RecyclerView.ViewHolder {
        ImageView postBackgroundImage;
        TextView tvTitleOfPost;
        ImageButton imBuIsFav;
        public ArticleRowHolder(@NonNull View itemView) {
            super(itemView);
            postBackgroundImage = (ImageView) itemView.findViewById(R.id.image_item_article_home_nav);
            tvTitleOfPost = (TextView) itemView.findViewById(R.id.txt_item_article_home_nav);
            imBuIsFav = (ImageButton) itemView.findViewById(R.id.imageBu_post_article_favorite);

        }
    }




       /* @Override
    public Filter getFilter() {
        return postFilter;
    }

    private Filter postFilter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<PostsData> filtereredList =new ArrayList<>();

            if (constraint == null || constraint.length()==0){
                filtereredList.addAll(postsListFull);

            }

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    };*/
}
