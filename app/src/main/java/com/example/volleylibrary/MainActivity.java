package com.example.volleylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text= findViewById(R.id.Text);
        btn= findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }

    private void getData()
    {
        /**
         * Here we are creating a request to retrieve the data from the given website.
         * There are three ways to create a request
         *      (i), StringRequest (ii). ImageRequest (iii). JSONRequest.
         *
         * we have passed two Response to the StringRequest constructor
         * (i). Response.Listener<String> : we get the data which we wanted to retrieve from the site.
         * (ii). If due to any reason such as loss of internet or any other Response.ErrorListener<> is called.
         */
        StringRequest request= new StringRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/posts",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        // Gson is a open source library with help of which we can easy parse the json file to
//                        // java objects just like that.
//                        Post post= new Gson().fromJson(response, Post.class);
//                        text.setText(post.getTitle());

                        // previously we just had one post in out json file, but now we will get an array of posts.
                        Post[] posts= new Gson().fromJson(response, Post[].class);
                        String title="";
                        for(Post p: posts)
                            title+=p.getTitle()+"\n";

                        text.setText(title);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getStackTrace();
                    }
                });


        // This is an example of another request that is used to post some data on a website
        StringRequest requestPost= new StringRequest(Request.Method.POST, "https://jsonplaceholder.typicode.com/posts",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Gson is a open source library with help of which we can easy parse the json file to
                        // java objects just like that.
                        Post post= new Gson().fromJson(response, Post.class);
                        text.setText(post.getTitle());

//                        // previously we just had one post in out json file, but now we will get an array of posts.
//                        Post[] posts= new Gson().fromJson(response, Post[].class);
//                        String title="";
//                        for(Post p: posts)
//                            title+=p.getTitle()+"\n";
//
//                        text.setText(title);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getStackTrace();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map= new HashMap<>();
                map.put("userId", "3");
                map.put("id", "3");
                map.put("title", "I am a title");
                map.put("body", "3");
                return map;
            }
        };

        RequestQueue q= Volley.newRequestQueue(this);
        q.add(requestPost);
        q.start();
    }
}

/**
 * Json: (Javascript object notation) key and value pairs are used to store the data in the Json, just like a
 * an ArrayList<Map<String, String>> example:
 * [{
 *     "id": 1
 *     "name": "Adarsh"
 *     "age": 20
 * },
 * {
 *     "id": 2
 *     "name": "makaunochi ippo"
 *     "age": 20
 * }]
 *
 * is a valid json file.
 *
 * We can do many operations using Volley library such as getting data from a website,
 * upload a post to the a website, delete and many more.
 *      For instance we have used here Request.Method.GET as we need to retrieve data from website .
 *
 */