package com.example.mybloodyapp.view.fragment.HomeCycleFragments;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.adapter.EmptySpinnerAdapter;
import com.example.mybloodyapp.adapter.RecyclerViewAdapterArticles;
import com.example.mybloodyapp.data.model.city.City;
import com.example.mybloodyapp.data.model.favorite.Favorite;
import com.example.mybloodyapp.data.model.posts.Posts;
import com.example.mybloodyapp.data.model.posts.PostsData;
import com.example.mybloodyapp.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mybloodyapp.data.api.RetrofitClient.getClient;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.LoadData;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleHomeNavFragment extends BaseFragment implements RecyclerViewAdapterArticles.OnFavBuClickedListener {

    Context context;
    public RecyclerView recyclerViewArticlePost;
    private List<PostsData> postData = new ArrayList<>();
    private String API_TOKEN ;
    Spinner spinnerArticlePostFragmentCategory;
    EmptySpinnerAdapter spinnerCategoryAdapter;
    EditText etFilterSearchPost;
    ImageButton imageAddArticleNomeNav;
    RecyclerViewAdapterArticles recyclerViewAdapterArticles =
            new RecyclerViewAdapterArticles((ArrayList<PostsData>) postData, context,this);

    public ArticleHomeNavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_nav_article, container, false);
        //setUpActivity();
        // Inflate the layout for this fragment
        //ButterKnife.bind(this, view);

        API_TOKEN =LoadData(getActivity(),"API_TOKEN");
        context = getContext();
        recyclerViewArticlePost = view.findViewById(R.id.recycler_article_fragment);
        recyclerViewArticlePost.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewArticlePost.setItemAnimator(new DefaultItemAnimator());

        spinnerArticlePostFragmentCategory = view.findViewById(R.id.spinner_fragment_article_small_spinner);
        imageAddArticleNomeNav = view.findViewById(R.id.image_add_article_home_nav);
        imageAddArticleNomeNav.setImageResource(R.drawable.ic_add_white);
        etFilterSearchPost = view.findViewById(R.id.et_filter_search_fragment_article_posts);

       /* swipeRefreshArticleLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPosts();
            }
        });*/
        getCategory();
        getPosts();

        spinnerArticlePostFragmentCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerArticlePostFragmentCategory.getSelectedItemId() > 0 || etFilterSearchPost.getText().toString().length() > 0) {
                    getPostWithFilter();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        etFilterSearchPost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return view;
    }


    private void filter(String text) {
        ArrayList<PostsData> filteredPostList = new ArrayList<>();

        for (PostsData item : postData) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredPostList.add(item);
            }
        }

        recyclerViewAdapterArticles.filterList(filteredPostList);
    }


    private void getCategory() {
        getClient().getCategories().enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        spinnerCategoryAdapter = new EmptySpinnerAdapter(getActivity());
                        spinnerCategoryAdapter.setData(response.body().getData(), getString(R.string.select_Category));
                        spinnerArticlePostFragmentCategory.setAdapter(spinnerCategoryAdapter);
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Toast.makeText(getActivity(), "check your internet connection", Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getPosts() {

        getClient().getAllPosts(API_TOKEN, 1).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                try {
                    //swipeRefreshArticleLayout.setRefreshing(false);
                    if (response.body().getStatus() == 1)
                        postData = response.body().getData().getData();
                    recyclerViewAdapterArticles = new RecyclerViewAdapterArticles((ArrayList<PostsData>) postData,
                            context,ArticleHomeNavFragment.this);
                    recyclerViewArticlePost.setAdapter(recyclerViewAdapterArticles);
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Toast.makeText(context, "please check your internet", Toast.LENGTH_LONG).show();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("kkkkkkkkkkkkkkkk", t.getMessage());
            }
        });
    }

    public void getPostWithFilter() {
        getClient().getFilteredPost(API_TOKEN, 1,
                etFilterSearchPost.getText().toString(),
                spinnerCategoryAdapter.selectedId).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                try {
                    if (response.body().getStatus() == 1)
                        postData = response.body().getData().getData();
                    {
                        recyclerViewAdapterArticles = new RecyclerViewAdapterArticles((ArrayList<PostsData>) postData,
                                context,ArticleHomeNavFragment.this);
                        recyclerViewArticlePost.setAdapter(recyclerViewAdapterArticles);
                        recyclerViewAdapterArticles.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                Toast.makeText(context, "please check your internet", Toast.LENGTH_LONG).show();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("kkkkkkkkkkkkkkkk", t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getPosts();
    }

    @Override
    public void onFavBuClicked(boolean isFav, int postion) {
        Log.d("isFav",String.valueOf(postion));
        postData.get(postion).setIsFavourite(isFav);
        recyclerViewAdapterArticles.setData((ArrayList<PostsData>) postData);
        //recyclerViewAdapterArticles.notifyDataSetChanged();
        int postID= postData.get(postion).getId();
            getClient().addFavPost(postID,API_TOKEN).enqueue(new Callback<Favorite>() {
                @Override
                public void onResponse(Call<Favorite> call, Response<Favorite> response) {
                    if (response.body().getStatus()==1){
                        Log.d("isFav",response.body().getMsg());
                    }
                }

                @Override
                public void onFailure(Call<Favorite> call, Throwable t) {
                    Log.d("isFav2",t.getMessage());
                }
            });
    }
}


   /* @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_view_menu,menu);
        MenuItem searchItem =menu.findItem(R.id.action_search);
        SearchView searchView =(SearchView)searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }*/
