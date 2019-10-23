package com.beauty.myapplication.api;

import com.beauty.myapplication.Model.Businessresponse;
import com.beauty.myapplication.Model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceApi {

    //User Register
    @FormUrlEncoded
    @POST("register")
    Call<UserResponse> registerUser(@Field("name") String name,
                                    @Field("contact_no") String contcatnumber,
                                    @Field("email") String email,
                                    @Field("password") String password,
                                    @Field("device_type") String devicetype,
                                    @Field("device_token") String devicetoken);

    //User Login
    @FormUrlEncoded
    @POST("login")
    Call<UserResponse> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_type") String devicetype,
            @Field("device_token") String devicetoken);

    //ContentDatas List
    @FormUrlEncoded
    @POST("business_list")
    Call<Businessresponse> getList(@Field("user_id") String user_id,
                                   @Query("business_category") String business_category);

    @FormUrlEncoded
    @POST("business_list")
    Call<Businessresponse> getSearch(@Field("user_id") String user_id,
                                     @Query("business_category") String business_category,
                                     @Query("keywords") String keywords);
}

