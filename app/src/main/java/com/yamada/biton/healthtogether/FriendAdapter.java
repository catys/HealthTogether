package com.yamada.biton.healthtogether;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 優太 on 2017/07/03.
 */

public class FriendAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<FriendInfo> friendList;

    public FriendAdapter(Context context){
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setFriendList(ArrayList<FriendInfo> friendList){
        this.friendList = friendList;
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return friendList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.friendrow,parent,false);

        ((TextView) convertView.findViewById(R.id.mymail)).setText(friendList.get(position).getMymail());
        ((TextView) convertView.findViewById(R.id.friendmail)).setText(friendList.get(position).getFriendmail());

        return convertView;
    }

}
