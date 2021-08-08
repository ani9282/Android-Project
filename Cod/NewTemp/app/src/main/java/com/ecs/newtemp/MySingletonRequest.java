package com.ecs.newtemp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingletonRequest {
    private static MySingletonRequest mInstace;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private MySingletonRequest(Context context) {
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingletonRequest getInstance(Context context) {
        if (mInstace == null) {
            mInstace = new MySingletonRequest(context);
        }
        return mInstace;
    }

    public<T> void addToRequestQue(Request<T> request) {
        requestQueue.add(request);
    }
}
