package com.example.cureeasy;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VideoGET {
    @SerializedName("success")
    int success;

    public int getSuccess() {
        return success;
    }

    @SerializedName("body")
    ArrayList<Body> body;

    public ArrayList<Body> getBody() {
        return body;
    }

    public class Body
    {
        @SerializedName("catname")
        String catname;

        public String getCatname() {
            return catname;
        }

        @SerializedName("playlistid")
        String playlistid;

        public String getPlaylistid() {
            return playlistid;
        }
    }
}
