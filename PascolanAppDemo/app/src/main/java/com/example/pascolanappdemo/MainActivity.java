package com.example.pascolanappdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{

    private static final String HI = "https://pascolan-config.s3.us-east-2.amazonaws.com/android/v1/prod/Category/hi/category.json";
    private List<Quiz> list_data;
    private MyAdapter adapter;
    private RecyclerView rv;
    private GridLayoutManager gm;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        rv=(RecyclerView)findViewById(R.id.recycler_view);
        gm=new GridLayoutManager(this,3);
        rv.setLayoutManager(gm);
        list_data=new ArrayList<>();

        getImageData();

    }

    private void getImageData() {
        request=new JsonArrayRequest(HI, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    response = shuffleJsonArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject=null;
                for (int i=0; i<response.length(); i++){
                    try {
                        jsonObject=response.getJSONObject(i);
                        Quiz listData=new Quiz(jsonObject.getString("p"),jsonObject.getString("n"));
                        list_data.add(listData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setupData(list_data);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    private void setupData(List<Quiz> list_data) {
        adapter=new MyAdapter(list_data,this);
        rv.setAdapter(adapter);

    }

    public static JSONArray shuffleJsonArray (JSONArray array) throws JSONException {
        // Implementing Fisher–Yates shuffle
        Random rnd = new Random();
        for (int i = array.length() - 1; i >= 0; i--)
        {
            int j = rnd.nextInt(i + 1);
            // Simple swap
            Object object = array.get(j);
            array.put(j, array.get(i));
            array.put(i, object);
        }
        return array;
    }
}
