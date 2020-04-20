package com.example.mybloodyapp.view.fragment.HomeCycleFragments.articlePost;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.adapter.RecyclerViewAdapterArticles;
import com.example.mybloodyapp.adapter.RecyclerViewAdapterDonations;
import com.example.mybloodyapp.adapter.RecyclerViewAdapterFavoriteArticles;
import com.example.mybloodyapp.data.model.favorite.Favorite;
import com.example.mybloodyapp.data.model.favorite.FavoriteData;
import com.example.mybloodyapp.data.model.favoritePosts.FavoritePostData;
import com.example.mybloodyapp.data.model.favoritePosts.FavoritePosts;
import com.example.mybloodyapp.data.model.posts.Posts;
import com.example.mybloodyapp.data.model.posts.PostsData;
import com.example.mybloodyapp.helper.OnEndLess;
import com.example.mybloodyapp.view.fragment.BaseFragment;
import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mybloodyapp.data.api.RetrofitClient.getClient;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.LoadData;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritePostFragment extends BaseFragment {

    private ArrayList<FavoritePostData> favoritePostData = new ArrayList<>();
    RecyclerViewAdapterFavoriteArticles recyclerViewAdapterFavoriteArticles ;

    @BindView(R.id.img_bu_conact_us_back)
    ImageButton imgBuFavPostBack;
    @BindView(R.id.recycler_fav_post_fragment)
    RecyclerView recyclerFavPostFragment;

    private Integer maxPage = 0;
    private LinearLayoutManager linearLayoutManager;
    private OnEndLess onEndLess;
    private String API_TOKEN;

    public FavoritePostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_posts, container, false);
        setUpActivity();
        ButterKnife.bind(this,view);
        // Inflate the layout for this fragment

        API_TOKEN =LoadData(getActivity(),"API_TOKEN");

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerFavPostFragment.setLayoutManager(linearLayoutManager);
        recyclerFavPostFragment.setItemAnimator(new DefaultItemAnimator());

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                            getFavoritePost(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };

        recyclerFavPostFragment.addOnScrollListener(onEndLess);
        recyclerViewAdapterFavoriteArticles = new RecyclerViewAdapterFavoriteArticles(favoritePostData, getActivity());
        recyclerFavPostFragment.setAdapter(recyclerViewAdapterFavoriteArticles);

        getFavoritePost(1);



        imgBuFavPostBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });


        return view;
    }

    private void getFavoritePost(int page) {
        getClient().getAllFavoritePost(API_TOKEN,page).enqueue(new Callback<FavoritePosts>() {
            @Override
            public void onResponse(Call<FavoritePosts> call, Response<FavoritePosts> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        maxPage = response.body().getData().getLastPage();
                        favoritePostData.addAll(response.body().getData().getData());
                        recyclerViewAdapterFavoriteArticles.notifyDataSetChanged();
                        //Log.i("HHHHHHHHHH",response.body().getData().getData().get(20).getTitle());
                    }
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<FavoritePosts> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
