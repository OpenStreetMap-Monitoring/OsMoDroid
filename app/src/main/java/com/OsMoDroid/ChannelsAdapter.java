package com.OsMoDroid;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
public class ChannelsAdapter extends ArrayAdapter<Channel>
    {
        public Context context;
        //private TextView channelName;
        //private TextView channelCreated;
        //ToggleButton tg;
        LocalService localservice;
        OnClickListener myCheckChangList = new OnClickListener()
        {
            public void onClick(View v)
                {
                    ((CheckBox) v).toggle();
                    Channel channel = getItem((Integer) v.getTag());
                    //Netutil.newapicommand((ResultsListener)localservice,context, "om_device_channel_active:"+OsMoDroid.settings.getString("device", "")+","+channel.u+","+boolglobalsend);
                    if (channel.send)
                        {
                            localservice.myIM.sendToServer("GD:" + channel.u, true);
                        }
                    else
                        {
                            localservice.myIM.sendToServer("GA:" + channel.u, true);
                        }
                }
        };
        public ChannelsAdapter(Context context, int textViewResourceId, List<Channel> objects, LocalService localservice)
            {
                super(context, textViewResourceId, objects);
                this.localservice = localservice;
            }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
            {
                View row = convertView;
                if (row == null)
                    {
                        //LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        row = inflater.inflate(R.layout.channelsitem, parent, false);
                    }
                Channel channel = getItem(position);
                TextView channelName = (TextView) row.findViewById(R.id.txtName);
                TextView channelCreated = (TextView) row.findViewById(R.id.txtCreated);
                CheckBox tg = (CheckBox) row.findViewById(R.id.checkBox);
                tg.setOnClickListener(myCheckChangList);
                tg.setTag(position);
                if (channel.name != null)
                    {
                        channelName.setText(channel.name + '(' + channel.myNameInGroup + ')');
                    }
                else
                    {
                        channelName.setText(channel.group_id + ':' + channel.myNameInGroup);
                    }
                if (channel.browseurl != null)
                    {
                        channelCreated.setText(channel.browseurl);
                    }
                tg.setChecked(channel.send);
                //channelName.setTextColor(Color.BLACK);
                return row;
            }
    }
