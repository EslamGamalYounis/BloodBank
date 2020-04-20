package com.example.mybloodyapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mybloodyapp.R;
import com.example.mybloodyapp.data.model.favoritePosts.FavoritePostData;
import com.example.mybloodyapp.view.activity.HomeCycleActivity;
import com.example.mybloodyapp.view.fragment.HomeCycleFragments.articlePost.PostArticleFragment;

import java.util.ArrayList;

import static com.example.mybloodyapp.helper.HelperMethod.replaceFragment;

public class RecyclerViewAdapterFavoriteArticles extends RecyclerView.Adapter<RecyclerViewAdapterFavoriteArticles.ArticleRowHolder>  {
    private ArrayList<FavoritePostData> postsList;
    Context context;


    public RecyclerViewAdapterFavoriteArticles(ArrayList<FavoritePostData> postsList, Context context) {
        this.context = context;
        this.postsList=postsList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterFavoriteArticles.ArticleRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_fragment_post,null);
        ArticleRowHolder articleRowHolder = new ArticleRowHolder(view);
        return articleRowHolder;    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapterFavoriteArticles.ArticleRowHolder holder, final int position) {
        Glide.with(context).load(postsList.get(position).getThumbnailFullPath())
                .into(holder.postBackgroundImage);
        holder.tvTitleOfPost.setText(postsList.get(position).getTitle());
        holder.imBuIsFav.setImageResource(R.drawable.favourite_icon);


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

}
