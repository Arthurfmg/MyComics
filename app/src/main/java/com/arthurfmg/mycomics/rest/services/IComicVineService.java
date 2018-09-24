package com.arthurfmg.mycomics.rest.services;

import com.arthurfmg.mycomics.rest.model.ComicVineCharacterModel;
import com.arthurfmg.mycomics.rest.model.ComicVineIssueModel;
import com.arthurfmg.mycomics.rest.model.ComicVineResult;
import com.arthurfmg.mycomics.rest.model.VolumeModel;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by phesto on 11/18/2016.
 */

public interface IComicVineService {

    final String BASE_URL = "http://www.comicvine.com/api/";

    @GET("characters")
    @Headers({"User-Agent: dledford"})
    Call<ComicVineResult<ArrayList<ComicVineCharacterModel>>> getCharacterByName(@Query("api_key") String apiKey, @Query("format") String format,
                                                                                 @Query("filter") String filter,@Query("field_list") String fieldList);

    @GET("volumes")
    @Headers({"User-Agent: Arthurfmg"})
    Call<ComicVineResult<ArrayList<VolumeModel>>> getVolumeByName(@Query("api_key") String apiKey, @Query("format") String format,
                                                                    @Query("filter") String filter, @Query("field_list") String fieldList);

    @GET("volumes/{id}")
    @Headers({"User-Agent: Arthurfmg"})
    Call<ComicVineResult<ArrayList<VolumeModel>>> getVolumeById(@Path("id") String id, @Query("api_key") String apiKey, @Query("format") String format,
                                                                @Query("filter") String filter,@Query("field_list") String fieldList);

    @GET("issues")
    @Headers({"User-Agent: Arthurfmg"})
    Call<ComicVineResult<ArrayList<ComicVineCharacterModel>>> getIssueByNameAndNumber(@Query("api_key") String apiKey, @Query("format") String format,
                                                                                      @Query("filter") String filter);

    @GET("issues")
    @Headers({"User-Agent: Arthurfmg"})
    Call<ComicVineResult<ArrayList<ComicVineIssueModel>>> getIssueByVolume(@Query("api_key") String apiKey, @Query("format") String format,
                                                                                      @Query("filter") String filter);

    @GET("series_list")
    @Headers({"User-Agent: dledford"})
    Call<ComicVineResult<ArrayList<ComicVineCharacterModel>>> getSeriesByName(@Query("api_key") String apiKey, @Query("format") String format,
                                                                              @Query("filter") String filter);

    @GET("issue/{id}")
    @Headers({"User-Agent: Arthurfmg"})
    Call<ComicVineResult<ComicVineIssueModel>> getIssueById(@Path("id") String id, @Query("api_key") String apiKey,
                                                                @Query("format") String format);

    @GET("character/{id}")
    @Headers({"User-Agent: dledford"})
    Call<ComicVineResult<ComicVineCharacterModel>> getCharacterById(@Path("id") String id, @Query("api_key") String apiKey,
                                                                    @Query("format") String format,@Query("field_list") String fieldList);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(new OkHttpClient.Builder().connectTimeout(10000,TimeUnit.SECONDS).readTimeout(10000,TimeUnit.SECONDS)
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
