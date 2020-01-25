package in.daaconline.doomshell.rahul.retrofit;


import in.daaconline.doomshell.rahul.Interview.InterviewCategoryGET;
import in.daaconline.doomshell.rahul.Interview.InterviewQuestionGET;
import in.daaconline.doomshell.rahul.TeamGET;
import in.daaconline.doomshell.rahul.contactus.ContactGET;
import in.daaconline.doomshell.rahul.himanshu.AllJobs;
import in.daaconline.doomshell.rahul.himanshu.Apply;
import in.daaconline.doomshell.rahul.courses.CourseDetailGET;
import in.daaconline.doomshell.rahul.courses.CoursesGET;
import in.daaconline.doomshell.rahul.himanshu.JobSliderGET;
import in.daaconline.doomshell.rahul.login.ContactList;
import in.daaconline.doomshell.rahul.login.Login1;
import in.daaconline.doomshell.rahul.login.Login2;
import in.daaconline.doomshell.rahul.mainscreen.SliderGET;
import in.daaconline.doomshell.rahul.quiz.QuizCategoryGET;
import in.daaconline.doomshell.rahul.quiz.QuizQuestionsGET;
import in.daaconline.doomshell.rahul.videos.VideoGET;
import in.daaconline.doomshell.rahul.videos.YoutubeGET;

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
   static String Login_url = "https://daac.in/Application/";
    String Register_url = "https://daac.in/Application/app_user_verify";

@GET("app_user")
Call<Login1> getloginget(@Query("user_name") String uname, @Query("user_id") String uid, @Query("imeino") String imei);
@PUT("app_user_verify")
Call<Login2> putin(@Query("id")String id, @Query("user_name") String uname, @Query("mobile") String mob, @Query("otp") String otp, @Query("imei_no") String imei);

    public static final  String YOUTUBE_URL="https://www.googleapis.com/youtube/v3/";

    @GET("playlistItems")
    Observable<YoutubeGET> getYoutubeGET(@Query("part")String part, @Query("maxResults") String max, @Query("playlistId")String id, @Query("key")String key);
    public static final String COURSE_URL="https://daac.in/Application/";
    @GET("all_courses")
    Observable<CoursesGET>getCoursesGET();


    @GET("fetch_question_cat")
    Observable<QuizCategoryGET>getQuestionCategoryGET();

    @PUT("fetch_question")
    Observable<QuizQuestionsGET>getQuestionGET(@Query("course_id")String courseid);

    @GET("sliders")
    Observable<SliderGET> getSliderGET();

    @GET("enquiry")
    Observable<CourseDetailGET> getCourseDetailGET(@Query("userid")String uid, @Query("course_id")String course_id);

    @GET("freevideos")
    Observable<VideoGET>getVideoDetailGET();

    @GET("our_team")
    Observable<TeamGET>getTeamGET();

//hhh
@FormUrlEncoded
    @POST("job_application")
    Observable<Apply>senddata(@Field("user_id") String userid, @Field("job_id") String jobid);

@FormUrlEncoded
@POST("user_contacts")
Observable<ContactList>sendcontact(@Field("userid")String userid, @Field("contacts") JSONArray contact);

    @GET("fetch_latest_placement")
    Observable<AllJobs> getAllJobList(@Query("user_id") String userid);

    @GET("fetch_mocks")
    Observable<InterviewCategoryGET>getInterviewCategoryGET();

    @GET("fetch_question")
    Observable<InterviewQuestionGET>getInterviewQuestionGET(@Query("course_id")String courseid);

    @GET("coursesliders")
    Observable<JobSliderGET> getJobSliderGET();

    @GET("contact_us")
    Observable<ContactGET>getContactGET();
}
