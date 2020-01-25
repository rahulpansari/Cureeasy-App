package com.example.cureeasy;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;



import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressCustom;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;




public class Youtube extends AppCompatActivity  {
RecyclerView youtuberecycler;
ACProgressCustom dialog;
    static ArrayList<YoutubeGET.Items> youtubedata = new ArrayList<YoutubeGET.Items>();
Toolbar toolbar;
    ActionBar actionBar;
ProgressBar progress_youtube;
YoutubeRecyclerAdapter adapter;
   int i=0;
   Context c;
   String playlist,key,snippet,max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        snippet="snippet";
        max="50";
playlist="PL3238FF8B48F88AF4";
        key="AIzaSyCz6eNHNW5qstY0XlU8gMaS056q42rVLek";
showProgress();
       // progress_youtube=findViewById(R.id.progress_ytube);
      //  progress_youtube.setVisibility(View.VISIBLE);
        youtuberecycler=findViewById(R.id.youtube_recyclerview);
        toolbar=findViewById(R.id.youtube_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
setSupportActionBar(toolbar);
actionBar=getSupportActionBar();
actionBar.setTitle("Videos");

        c=this;


    }
    public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_bar,menu);
        Retrofit retrofit = RetrofitMethod.RetrofitMethod(RetrofitApi.YOUTUBE_URL);
        RetrofitApi api = retrofit.create(RetrofitApi.class);
        Observable<YoutubeGET> getSlider=api.getYoutubeGET(snippet,max,playlist,key);
        getSlider.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<YoutubeGET>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(YoutubeGET youtubeList) {
                        youtubedata = new ArrayList<YoutubeGET.Items>(youtubeList.getItems());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        dismissProgress();
                       // progress_youtube.setVisibility(View.GONE);
                        RecyclerView.LayoutManager manager=new LinearLayoutManager(c,LinearLayoutManager.VERTICAL,false);
                        youtuberecycler.setLayoutManager(manager);
                        adapter=new YoutubeRecyclerAdapter(c,youtubedata);
                        youtuberecycler.setAdapter(adapter);

                    }




                });
       /* MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              actionBar.setTitle("");
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                actionBar.setTitle("Free Videos");
                return false;
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText!=null) {
                    ArrayList<YoutubeGET.Items> match = new ArrayList<YoutubeGET.Items>();
                    for (i = 0; i < youtubedata.size(); i++) {
                        if(youtubedata.get(i).getSnippet().getTitle().contains(newText))
                            match.add(youtubedata.get(i));

                    }
                    adapter=new YoutubeRecyclerAdapter(c,match);
                    youtuberecycler.setAdapter(adapter);

                }return false;
            }
        });*/
        return  true;
    }
    public void showProgress() {

        dialog = new ACProgressCustom.Builder(this)
                .useImages(R.drawable.endo, R.drawable.neurologist, R.drawable.optho, R.drawable.psyc).speed((float) 1.8)
                .build();
        dialog.setCancelable(false);
        dialog.show();


    }

    public void dismissProgress() {
        dialog.dismiss();
    }
}

class MyYoutube
{
    String title,url;
    public MyYoutube(String t)
    {
        title=t;

    }
}