package com.example.chatbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class chatAdapter extends RecyclerView.Adapter {
    private ArrayList<chatModel> chatModelArrayList;
    private Context context;

    public chatAdapter(ArrayList<chatModel> chatModelArrayList, Context context) {
        this.chatModelArrayList = chatModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
                return new userviewholder(view);
            case 1:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bot,parent,false);
                return new botviewholder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        chatModel chatModel=chatModelArrayList.get(position);
        switch ((chatModel.getSender())){
            case "user":
                ((userviewholder)holder).textView.setText(chatModel.getMessage());
                break;
            case "bot":
                ((botviewholder)holder).textView2.setText(chatModel.getMessage());
                break;

        }

    }

    @Override
    public int getItemViewType(int position) {
        switch (chatModelArrayList.get(position).getSender()) {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    @Override

    public int getItemCount() {
        return chatModelArrayList.size();
    }

    public static class userviewholder extends RecyclerView.ViewHolder{
        TextView textView;

        public userviewholder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text_user);
        }
    }

    public static class botviewholder extends RecyclerView.ViewHolder{
        TextView textView2;

        public botviewholder(@NonNull View itemView) {
            super(itemView);
            textView2=itemView.findViewById(R.id.text_bot);
        }
    }
}

//
//    public int getItemViewType(int position){
//        switch (chatModelArrayList.get(position).getSender()){
//            case "user":
//                return 0;
//            case "bot":
//                return 1;
//            default:
//                return -1;
//        }
