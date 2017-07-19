package com.yamada.biton.healthtogether;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.yamada.biton.healthtogether.R.drawable.entry_button;
import static com.yamada.biton.healthtogether.R.drawable.release_button;
import static com.yamada.biton.healthtogether.R.drawable.release_button2;
import static com.yamada.biton.healthtogether.R.drawable.share_button;

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

        //文字を設定
        ((TextView) convertView.findViewById(R.id.friendmail)).setText(friendList.get(position).getFriendmail());

        //tagを設定
        ((ImageButton)convertView.findViewById(R.id.info_Button)).setTag(friendList.get(position).getFriendmail());
        ((ImageButton)convertView.findViewById(R.id.schedule_Button)).setTag(friendList.get(position).getFriendmail());

        ((ImageButton)convertView.findViewById(R.id.friendAD)).setTag(friendList.get(position).getFriendmail());

        //情報共有
        String hantei = friendList.get(position).getAllreleaseflag();
        if(hantei.equals("1")){
            ((ImageButton) convertView.findViewById(R.id.info_Button)).setImageResource(release_button2);
        }else{
            ((ImageButton) convertView.findViewById(R.id.info_Button)).setImageResource(share_button);
        }

        //スケジュール公開
        String hantei2 = friendList.get(position).getScheduleflag();
        if(hantei2.equals("1")){
            ((ImageButton) convertView.findViewById(R.id.schedule_Button)).setImageResource(release_button2);
        }else{
            ((ImageButton) convertView.findViewById(R.id.schedule_Button)).setImageResource(share_button);
        }

        //友達
        ((ImageButton) convertView.findViewById(R.id.friendAD)).setImageResource(release_button);
        Global.setMapFlag(friendList.get(position).getFriendmail(),"1");

        return convertView;
    }

}
