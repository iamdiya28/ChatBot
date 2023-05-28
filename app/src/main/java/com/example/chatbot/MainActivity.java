package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import javax.xml.transform.sax.SAXResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText editText;
    private FloatingActionButton floatingActionButton;
    private final String bot_key="bot";
    private final String user_key="user";
    private ArrayList<chatModel>chatModelArrayList;
    private chatAdapter chatAdapter;
    private String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.chats);
        editText=findViewById(R.id.editmessage);
        floatingActionButton=findViewById(R.id.button);
        chatModelArrayList=new ArrayList<>();
        chatAdapter= new chatAdapter(chatModelArrayList,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(chatAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"PLease Enter your Message",Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(editText.getText().toString());
                editText.setText("");
            }
        });

    }
    private void getResponse(String message){
        this.message = message;
        chatModelArrayList.add(new chatModel(message,user_key));
        chatAdapter.notifyDataSetChanged();
        String url="http://api.brainshop.ai/get?bid=173393&key=JJPi7iJFCC1SefGH&uid=[uid]&msg="+message;
        String base_url="http://api.brainshop.ai/";
        Retrofit retrofit= new Retrofit
                .Builder().baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI rai= retrofit.create(retrofitAPI.class);
        Call<msgmodel> call=rai.getmessage(url);
        call.enqueue(new Callback<msgmodel>() {
            @Override
            public void onResponse(Call<msgmodel> call, Response<msgmodel> response) {
                if(response.isSuccessful()){
                    msgmodel msgmodel1= response.body();
                    chatModelArrayList.add(new chatModel(msgmodel1.getCnt(), bot_key));
                    chatAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<msgmodel> call, Throwable t) {
                chatModelArrayList.add(new chatModel("please revert your question",bot_key));
                chatAdapter.notifyDataSetChanged();

            }
        });


    }
}