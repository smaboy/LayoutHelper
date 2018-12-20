package com.example.smaboy.layouthelper.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.smaboy.layouthelper.Entity.UserInfoGroup;
import com.example.smaboy.layouthelper.R;

/**
 * 类名: MyRecyclerViewAdapter
 * 类作用描述: java类作用描述
 * 作者: Smaboy
 * 创建时间: 2018/12/20 18:46
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {


    private final Context context;
    private final UserInfoGroup userinfoGroup;

    public MyRecyclerViewAdapter(Context context, UserInfoGroup userInfoGroup) {
        this.context=context;
        this.userinfoGroup=userInfoGroup;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.recycler_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.name.setText(userinfoGroup.getUserinfogroup().get(i).getGrouptitle());


    }

    @Override
    public int getItemCount() {
        return userinfoGroup==null||userinfoGroup.getUserinfogroup()==null ? 0 : userinfoGroup.getUserinfogroup().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView age;
        TextView phone;
         MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            age=itemView.findViewById(R.id.age);
            phone=itemView.findViewById(R.id.phone);
        }
    }
}
