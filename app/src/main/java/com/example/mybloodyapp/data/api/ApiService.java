package com.example.mybloodyapp.data.api;

import com.example.mybloodyapp.data.model.city.City;
import com.example.mybloodyapp.data.model.createDonation.CreateDonation;
import com.example.mybloodyapp.data.model.donationDetails.DonationDetails;
import com.example.mybloodyapp.data.model.donations.Donations;
import com.example.mybloodyapp.data.model.donations.DonationsData;
import com.example.mybloodyapp.data.model.favorite.Favorite;
import com.example.mybloodyapp.data.model.favoritePosts.FavoritePosts;
import com.example.mybloodyapp.data.model.login.Login;
import com.example.mybloodyapp.data.model.newPassword.NewPassword;
import com.example.mybloodyapp.data.model.notification.Notification;
import com.example.mybloodyapp.data.model.notificationCount.NotificationCount;
import com.example.mybloodyapp.data.model.notificationsSettings.NotificationsSettings;
import com.example.mybloodyapp.data.model.posts.Posts;
import com.example.mybloodyapp.data.model.profile.Profile;
import com.example.mybloodyapp.data.model.profile.ProfileData;
import com.example.mybloodyapp.data.model.resetPassword.ResetPassword;
import com.example.mybloodyapp.data.model.settings.Settings;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {


    @POST("signup")
    @FormUrlEncoded
    Call<Login> setRegister(
            @Field("name") String name,
            @Field("email") String email,
            @Field("birth_date") String birth_date,
            @Field("city_id") String city_id,
            @Field("phone") String phone,
            @Field("donation_last_date") String donation_last_date,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Field("blood_type_id") String blood_type_id
    );


    @POST("login")
    @FormUrlEncoded
    Call<Login> setLogin(@Field("phone") String phone,
                      @Field("password") String password);


    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPassword> setResetPassword(@Field("phone") String phone);


    @POST("new-password")
    @FormUrlEncoded
    Call<NewPassword> setNewPassword(@Field("password") String password,@Field("password_confirmation") String passwordConfirmation,
                                     @Field("pin_code") String pinCode,@Field("phone") String phone);


    @GET("governorates")
    Call<City> getGovernorates();


    @GET("cities")
    Call<City> getCity(@Query("governorate_id") int governorateId);


    @GET("blood-types")
    Call<City> getBloodType();

    @GET("categories")
    Call<City> getCategories();

    @GET("posts")
    Call<Posts> getAllPosts(@Query("api_token") String api_token,
                            @Query("page") int page);

    @GET("posts")
    Call<Posts> getFilteredPost(@Query("api_token") String api_token,
                                @Query("page") int page,
                                @Query("keyword")String keyword,
                                @Query("category_id") int category_id);


    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<Favorite> addFavPost(@Field("post_id") int postID,
                          @Field("api_token") String token);

    @GET("donation-requests")
    Call<Donations> getAllDonation(@Query("api_token") String api_token,
                               @Query("page") int page);

    @GET("donation-requests")
    Call<Donations> getFilteredDonation(@Query("api_token") String api_token,
                                        @Query("blood_type_id") int blood_type_id,
                                        @Query("governorate_id")int governorate_id,
                                        @Query("page") int page);


    @POST("donation-request/create")
    @FormUrlEncoded
    Call<CreateDonation> addDontationRequest(@Field("api_token") String api_token,
                                             @Field("patient_name") String patient_name,
                                             @Field("patient_age") String patient_age,
                                             @Field("blood_type_id") int blood_type_id,
                                             @Field("bags_num") String bags_num,
                                             @Field("hospital_name") String hospital_name,
                                             @Field("hospital_address") String hospital_address,
                                             @Field("city_id") int city_id,
                                             @Field("phone") String phone,
                                             @Field("notes") String notes,
                                             @Field("latitude") Double latitude,
                                             @Field("longitude") Double longitude);

    @GET("post")
    Call<Posts> getPostDetails(@Query("api_token") String api_token,
                               @Query("post_id")  int post_id,
                               @Query("page") int page);


    @GET("my-favourites")
    Call<FavoritePosts> getAllFavoritePost(@Query("api_token") String api_token,
                                           @Query("page") int page);

    @GET("donation-request")
    Call<DonationDetails> getDonationDetails(@Query("api_token") String api_token,
                                             @Query("donation_id") int donation_id);

    @POST("profile")
    @FormUrlEncoded
    Call<Profile> getProfileData(@Field("api_token") String api_token);

    @POST("profile")
    @FormUrlEncoded
    Call<Profile> editProfile(@Field("name") String name,
                              @Field("email") String email,
                              @Field("birth_date") String birth_date,
                              @Field("city_id") String city_id,
                              @Field("phone") String phone,
                              @Field("donation_last_date") String donation_last_date,
                              @Field("password") String password,
                              @Field("password_confirmation") String password_confirmation,
                              @Field("blood_type_id") String blood_type_id,
                              @Field("api_token") String api_token);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationsSettings> getNotificationsSettings(@Field("api_token") String api_token);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationsSettings> setNotificationsSettings(@Field("api_token") String api_token,
                                                         @Field("governorates[]") List<Integer> governorates,
                                                         @Field("blood_types[]") List<Integer> blood_types);


    @GET("notifications")
    Call<Notification> getNotificationData(@Query("api_token") String api_token,
                                           @Query("page") int page);

    @POST("contact")
    @FormUrlEncoded
    Call<Void> setContactUsMessage(@Field("api_token") String api_token,
                                   @Field("title")String title,
                                   @Field("message")String message);

    @GET("settings")
    Call<Settings> getSettingsInfo(@Query("api_token") String api_token);

    @GET("notifications-count")
    Call<NotificationCount> getNotificationCount(@Query("api_token") String api_token);


}
