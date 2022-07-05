package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class maps extends AppCompatActivity implements OnMapReadyCallback {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    adapter_rec adapter;
    List<model> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        try {
            recyclerView = findViewById(R.id.recview);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new adapter_rec(modelList);
            recyclerView.setAdapter(adapter);

            fetch();

        }catch (Exception e){
            e.printStackTrace();
        }//catch
    }//oncreate

    private void fetch() {
        try {
            retrofit.apis().getPosts().enqueue(new Callback<List<model>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(Call<List<model>> call, Response<List<model>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        modelList.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    }//if
                }

                @Override
                public void onFailure(Call<List<model>> call, Throwable t) {
                    Toast.makeText(maps.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }//catch
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }//onmapready
}//main