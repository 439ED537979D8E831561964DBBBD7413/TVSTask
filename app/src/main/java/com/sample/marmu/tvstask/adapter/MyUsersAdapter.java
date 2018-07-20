package com.sample.marmu.tvstask.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.marmu.tvstask.R;
import com.sample.marmu.tvstask.activity.UserDetailActivity;
import com.sample.marmu.tvstask.model.UserModel;

import java.util.List;

/**
 * Created by azharuddin on 3/8/17.
 */

@SuppressWarnings("unchecked")
public class MyUsersAdapter extends RecyclerView.Adapter<MyUsersAdapter.MyViewHolder> {

    private Activity activity;
    private List<UserModel> usersList;

    public MyUsersAdapter(Activity activity, List<UserModel> usersList) {
        this.activity = activity;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_users, parent, false);

        return new MyUsersAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final UserModel userModel = usersList.get(position);
        holder.tvName.setText(userModel.getName());
        holder.tvDesignation.setText(userModel.getDesignation());
        holder.tvCity.setText(userModel.getCity());
        holder.tvCode.setText(userModel.getCode());
        holder.tvDateOfJoin.setText(userModel.getDateOfJoin());
        holder.tvSalary.setText("$" + userModel.getSalary());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, UserDetailActivity.class);
                intent.putExtra("userModel", userModel);
                activity.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDesignation, tvCity, tvCode, tvDateOfJoin, tvSalary;


        MyViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDesignation = itemView.findViewById(R.id.tvDesignation);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvDateOfJoin = itemView.findViewById(R.id.tvDateOfJoin);
            tvSalary = itemView.findViewById(R.id.tvSalary);

        }
    }
}
