package com.homeworkreminder.utils.networkUtil;

import android.content.Context;

import com.android.volley.Response;

public interface VolleyListener {
    public void onSuccessResponse();
    public void onFailResponse();
}
