package com.example.mybloodyapp.view.fragment.HomeCycleFragments.articlePost;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mybloodyapp.R;
import com.example.mybloodyapp.data.model.posts.Posts;
import com.example.mybloodyapp.data.model.posts.PostsData;
import com.example.mybloodyapp.view.activity.HomeCycleActivity;
import com.example.mybloodyapp.view.fragment.BaseFragment;
import com.example.mybloodyapp.view.fragment.HomeCycleFragments.ArticleHomeNavFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mybloodyapp.data.api.RetrofitClient.getClient;
import static com.example.mybloodyapp.helper.HelperMethod.replaceFragment;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.LoadData;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostArticleFragment extends BaseFragment {

    @BindView(R.id.image_background_post_article_fragment)
    ImageView imageBackgroundPostArticleFragment;
    @BindView(R.id.img_bu_post_article_back)
    ImageButton imgBuPostArticleBack;
    @BindView(R.id.img_bu_post_article_favorite)
    ImageButton imgBuPostArticleFavorite;
    @BindView(R.id.txt_post_article_Title)
    TextView txtPostArticleTitle;
    @BindView(R.id.txt_post_article_fragment)
    TextView txtPostArticleFragment;
    private List<PostsData> postData = new ArrayList<>();


    public PostArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_article, container, false);
        ButterKnife.bind(this, view);
        String API_TOKEN = LoadData(getActivity(),"API_TOKEN");


        try {
            setUpActivity();
        }catch (Exception e){
            Toast.makeText(getContext(),"eslam"+ e.getMessage(),Toast.LENGTH_LONG).show();
        }
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        String postIdString = bundle.getString("postIdString");
        int postId = Integer.parseInt(postIdString);

        String postTitle =bundle.getString("postTitle");
        String postContent =bundle.getString("postContent");
        String postImageURL =bundle.getString("postImageURL");
        String isFavorite =bundle.getString("isFavorite");

        txtPostArticleTitle.setText(postTitle);
        txtPostArticleFragment.setText(postContent);
        Glide.with(this).load(postImageURL).into(imageBackgroundPostArticleFragment);
        if (isFavorite.equals("true")){
            imgBuPostArticleFavorite.setImageResource(R.drawable.favourite_icon);
        }
        else{
            imgBuPostArticleFavorite.setImageResource(R.drawable.unfavourite_icon);
        }

        //getPostDetailsFun(API_TOKEN, postId);


        imgBuPostArticleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onBack();
                }catch (Exception e){
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private void getPostDetailsFun(String api_token,final int postId) {
        getClient().getPostDetails(api_token, postId, 1).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        postData = response.body().getData().getData();
                        Glide.with(getContext()).load(postData.get(postId).getThumbnailFullPath()).into(imageBackgroundPostArticleFragment);

                        txtPostArticleTitle.setText(postData.get(postId).getTitle());
                        txtPostArticleFragment.setText(postData.get(postId).getContent());
                        if (postData.get(postId).getIsFavourite()) {
                            imgBuPostArticleFavorite.setImageResource(R.drawable.favourite_icon);
                        } else {
                          imgBuPostArticleFavorite.setImageResource(R.drawable.unfavourite_icon);
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("kkkkkkkkkkkkkkkk", t.getMessage());
            }
        });
    }


    @Override
    public void onBack() {
        /*Intent intent =new Intent(getActivity(), HomeCycleActivity.class);
        startActivity(intent); */
        super.onBack();
    }


}
