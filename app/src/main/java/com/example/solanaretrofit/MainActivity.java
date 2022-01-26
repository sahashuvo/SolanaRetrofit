package com.example.solanaretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.testnet.solana.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SolanaRpcInterface solanaRpcInterface = retrofit.create(SolanaRpcInterface.class);

        TextView textView = findViewById(R.id.textView);
        String[] params = {"8tfDNiaEyrV6Q1U4DEXrEigs9DoDtkugzFbybENEbCDz"};

        Call<SolanaRpcInterface.GetBalanceResponse> responseCall =
                solanaRpcInterface.retreiveBalance(
                        new SolanaRpcInterface.GetBalanceRequest(
                                "2.0",
                                1,
                                "getBalance",
                                params
                        )
                );

        responseCall.enqueue(new Callback<SolanaRpcInterface.GetBalanceResponse>() {
            @Override
            public void onResponse(Call<SolanaRpcInterface.GetBalanceResponse> call, Response<SolanaRpcInterface.GetBalanceResponse> response) {
                try {
                    if(response.isSuccessful()){
                        textView.setText("onResponse>success: " + response.body().toString());
                    } else {
                        textView.setText("onResponse>not success: " + response.errorBody().string());
                    }
                } catch (IOException exception){
                    textView.setText(exception.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SolanaRpcInterface.GetBalanceResponse> call, Throwable t) {
                t.printStackTrace();
                textView.setText("onFailure: "+t.getMessage());
            }
        });

    }
}