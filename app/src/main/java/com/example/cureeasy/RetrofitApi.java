package com.example.cureeasy;




import org.json.JSONArray;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitApi {

    public static final  String YOUTUBE_URL="https://www.googleapis.com/youtube/v3/";

    @GET("playlistItems")
    Observable<YoutubeGET> getYoutubeGET(@Query("part") String part, @Query("maxResults") String max, @Query("playlistId") String id, @Query("key") String key);
    public static final String COURSE_URL="https://daac.in/Application/";



    @GET("freevideos")
    Observable<VideoGET>getVideoDetailGET();


}
