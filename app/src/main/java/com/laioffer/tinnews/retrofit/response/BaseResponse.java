package com.laioffer.tinnews.retrofit.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseResponse {
    // according to newsapi.org's response structure
    public String status;
    public int totalResult;
    @SerializedName("articles")
    public List<News> articles;
}
