package com.example.chatnest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messageList;

    public MessageAdapter(List<Message> messages) {
        this.messageList = messages;
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        Button btnEnvoyer;

        public MessageViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.etMessage);
            btnEnvoyer = itemView.findViewById(R.id.btnEnvoyer);
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message msg = messageList.get(position);
        holder.message.setText(msg.getTexte());


        holder.btnEnvoyer.setOnClickListener(v->{

        });

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
