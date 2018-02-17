package com.softricsolutions.videoplayapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.softricsolutions.videoplayapplication.adapter.Videoviewadapter;
import com.softricsolutions.videoplayapplication.model.Videos;
import com.softricsolutions.videoplayapplication.utils.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Web api url
    public static final String DATA_URL = "http://www.bloodfinder.co.in/twitinfotech.com/getVideos.php";

    private static final int STORAGE_PERMISSION_CODE = 123;
        Videoviewadapter videoviewadapter;
        RecyclerView recyclerView;
        LinearLayoutManager mLayoutManager;
        ArrayList<Videos> videoses = new ArrayList<Videos>();
        Videos videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=(RecyclerView)findViewById(R.id.recycleview);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        getVideos();

        ///test

    }

    public void getVideos()
    {
        final String strURL = String.format("%s",DATA_URL);
        Log.e("url", strURL);
        JsonArrayRequest req = new JsonArrayRequest(strURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("res", response.toString());
                        for (int index = 0; index < response.length(); index++) {
                            try {

                                videos = new Videos();
                                JSONObject object = response.getJSONObject(index);

                                videos.setTitle(object.getString("title"));
                                videos.setVideo_url(object.getString("video_url"));
                                videoses.add(videos);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        if (videoses.size() == 0) {
                            Toast.makeText(MainActivity.this, " No  Video  Found", Toast.LENGTH_LONG).show();
                        }
                        videoviewadapter = new Videoviewadapter(MainActivity.this,videoses);
                        recyclerView.setHasFixedSize(true);

                        recyclerView.setAdapter(videoviewadapter);
                        videoviewadapter.notifyDataSetChanged();

                        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

                            @Override
                            public void onClick(View view, int position) {

                                Videos videos=videoses.get(position);
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videos.getVideo_url()));
                                intent.setDataAndType(Uri.parse(videos.getVideo_url()),"video/mp4");
                                startActivity(intent);

/*

                                Intent intent=new Intent(MainActivity.this,VideoPlayActivity.class);
                                intent.putExtra("url",videos.getVideo_url());
                                startActivity(intent);
*/

                                Toast.makeText(getApplicationContext(),videos.getVideo_url() + " is selected!", Toast.LENGTH_SHORT).show();

                            }
                            @Override
                            public void onLongClick(View view, int position) {

                            }
                        }));


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Errrorrr", "Error: " + error.getMessage());
            }
        });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding our request to the queue
        requestQueue.add(req);
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}
