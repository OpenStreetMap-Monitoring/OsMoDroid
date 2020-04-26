package com.OsMoDroid;

import android.content.Context;
import android.graphics.Color;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
public class ChannelChatAdapter extends ArrayAdapter<ChatMessage>
    {
        //	private TextView txtFrom;
//	private TextView txtText;
//	private TextView txtTime;
//	private TextView txtFromAddr;
        public ChannelChatAdapter(Context context, int textViewResourceId, List<ChatMessage> objects)
            {
                super(context, textViewResourceId, objects);
            }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
            {
                View row = convertView;
                if (row == null)
                    {
                        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        row = inflater.inflate(R.layout.devicechatitem, parent, false);
                    }
                ChatMessage message = getItem(position);
                TextView txtFrom = (TextView) row.findViewById(R.id.txtFrom);
                TextView txtText = (TextView) row.findViewById(R.id.txtText);
                TextView txtTime = (TextView) row.findViewById(R.id.txtTime);
                TextView txtFromAddr = (TextView) row.findViewById(R.id.txtFromAddr);
                txtFromAddr.setVisibility(View.GONE);
                txtFrom.setText(message.name);
                txtText.setText(message.text);
                txtTime.setText(message.time);
//		        txtText.setTextColor(Color.BLACK);
//		        txtFrom.setTextColor(Color.BLACK);
//		        txtTime.setTextColor(Color.BLACK);
//		        txtFrom.setBackgroundColor(message.color);
//		        txtText.setBackgroundColor(message.color);
//		        txtTime.setBackgroundColor(message.color);
                row.setBackgroundColor(message.color);
                //txtFrom.setBackgroundColor(message.color);
                if (message.text.startsWith(OsMoDroid.settings.getString("devicename", "") + ","))
                    {
//		        	txtText.setBackgroundColor(Color.DKGRAY);
//		        	txtFrom.setBackgroundColor(Color.DKGRAY);
//		        	txtTime.setBackgroundColor(Color.DKGRAY);
                        row.setBackgroundColor(Color.DKGRAY);
                        txtText.setTextColor(Color.YELLOW);
                        txtFrom.setTextColor(Color.YELLOW);
                        txtTime.setTextColor(Color.YELLOW);
                    }
                Linkify.addLinks(txtText, Linkify.ALL);
                return row;
            }
    }
