package com.example.cureeasy;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class YoutubeGET {
    @SerializedName("pageInfo")

    public PageInfo pageinfo;

    public PageInfo getPageinfo() {
        return pageinfo;
    }

    public class PageInfo
    {
        @SerializedName("totalResults")

        public int toatlResults;

        public int getToatlResults() {
            return toatlResults;
        }
    }

    @SerializedName("items")

    ArrayList<Items> items;

    public  ArrayList<Items> getItems() {
        return items;
    }

    public class Items
    {
        @SerializedName("snippet")
public Snippet snippet;

        public Snippet getSnippet() {
            return snippet;
        }

        public class Snippet {
            @SerializedName("title")
            public String title;

            public String getTitle() {
                return title;
            }
            @SerializedName("thumbnails")
            public Thumbnails thumbnails;

            public Thumbnails getThumbnails() {
                return thumbnails;
            }
            public class Thumbnails
            {
                @SerializedName("default")
                public ImagesUrl def;

                public ImagesUrl getDef() {
                    return def;
                }
                public class ImagesUrl
                {
                    @SerializedName("url")
                    public String url;

                    public String getUrl() {
                        return url;
                    }
                }
            }
            @SerializedName("resourceId")
            public ResourceId resourceId;

            public ResourceId getResourceId() {
                return resourceId;
            }

            public class ResourceId
            {
                @SerializedName("videoId")
                public String videoid;

                public String getVideoid() {
                    return videoid;
                }
            }
        }
    }
}
