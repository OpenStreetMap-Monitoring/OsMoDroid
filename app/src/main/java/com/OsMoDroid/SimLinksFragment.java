package com.OsMoDroid;
import java.text.SimpleDateFormat;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.view.MenuItemCompat;
import android.text.ClipboardManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
public class SimLinksFragment extends Fragment
    {
        //	ArrayList<String> list;
//	ArrayList<String> listids;
        final private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        private GPSLocalServiceClient globalActivity;
        void addlink_new() throws JSONException
            {
//		JSONObject postjson = new JSONObject();
//		postjson.put("device", OsMoDroid.settings.getString("device", ""));
//		postjson.put("random", "1");
//		postjson.put("until", "-1");
                //Netutil.newapicommand((ResultsListener)SimLinksFragment.this,getSherlockActivity(), "om_link_add","json="+postjson.toString());
                globalActivity.mService.myIM.sendToServer("LNKA", true);
            }
        void reflinks()
            {
                globalActivity.mService.myIM.sendToServer("LINK", true);
            }
        void dellink(int u)
            {
                globalActivity.mService.myIM.sendToServer("LNKD:" + u, true);
            }
        @Override
        public void onDestroy()
            {
                LocalService.simlinksadapter=null;
                super.onDestroy();
            }
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
            {
                View view = inflater.inflate(R.layout.simlinks, container, false);
                final ListView lv1 = (ListView) view.findViewById(R.id.listView1);
                lv1.setEmptyView(view.findViewById(android.R.id.empty));
                LocalService.simlinksadapter = new PermLinksAdapter(getActivity(), R.layout.deviceitem, LocalService.simlimkslist,globalActivity.mService);

                lv1.setAdapter(LocalService.simlinksadapter);
                registerForContextMenu(lv1);
                lv1.setOnItemClickListener(new OnItemClickListener()
                {
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
                        {
                            arg0.showContextMenuForChild(arg1);
                        }
                });
                //if(list.size()==0){reflinks();}

                return view;
            }
        @Override
        public void onActivityCreated(Bundle savedInstanceState)
            {
                globalActivity = (GPSLocalServiceClient) getActivity();
                super.onActivityCreated(savedInstanceState);
            }
        @Override
        public void onCreate(Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
                setHasOptionsMenu(true);
                //setRetainInstance(true);
                super.onCreate(savedInstanceState);
            }
        @Override
        public void onResume()
            {
                globalActivity.actionBar.setTitle(R.string.links);
                reflinks();
                super.onResume();
            }
        @Override
        public void onDetach()
            {
                globalActivity = null;
                super.onDetach();
            }
        @Override
        public void onAttach(Activity activity)
            {
                globalActivity = (GPSLocalServiceClient) activity;// TODO Auto-generated method stub
                super.onAttach(activity);
            }
        /* (non-Javadoc)
         * @see com.actionbarsherlock.app.SherlockFragment#onCreateOptionsMenu(com.actionbarsherlock.view.Menu, com.actionbarsherlock.view.MenuInflater)
         */
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
            {
                MenuItem bind = menu.add(0, 1, 0, R.string.addsymlink);
                MenuItemCompat.setShowAsAction(bind, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
                bind.setIcon(android.R.drawable.ic_menu_add);
                MenuItem refresh = menu.add(0, 2, 0, R.string.refresh);
                MenuItemCompat.setShowAsAction(refresh, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
                refresh.setIcon(android.R.drawable.ic_menu_rotate);
                super.onCreateOptionsMenu(menu, inflater);
            }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
            {
                menu.add(0, 5, 3, R.string.openinbrowser).setIcon(android.R.drawable.ic_menu_edit);
                menu.add(0, 1, 2, R.string.sharelink).setIcon(android.R.drawable.ic_menu_share);
                menu.add(0, 3, 1, R.string.copylink).setIcon(android.R.drawable.ic_menu_edit);
                menu.add(0, 2, 4, R.string.deletelink).setIcon(android.R.drawable.ic_menu_delete);
                super.onCreateContextMenu(menu, v, menuInfo);
            }
        public boolean onContextItemSelected(android.view.MenuItem item)
            {
                AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
                if (item.getItemId() == 1)
                    {
                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                        sendIntent.setType("text/plain");
                        sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, LocalService.simlinksadapter.getItem(acmi.position).url);
                        startActivity(Intent.createChooser(sendIntent, "Email"));
                        return true;
                    }
                if (item.getItemId() == 2)
                    {
                        dellink(LocalService.simlinksadapter.getItem(acmi.position).u);
                        return true;
                    }
                if (item.getItemId() == 3)
                    {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboard.setText(LocalService.simlinksadapter.getItem(acmi.position).url);
                        return true;
                    }
                if (item.getItemId() == 5)
                    {
                        Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(LocalService.simlinksadapter.getItem(acmi.position).url));
                        startActivity(browseIntent);
                        return true;
                    }
                return super.onContextItemSelected(item);
            }
        @Override
        public boolean onOptionsItemSelected(MenuItem item)
            {
                switch (item.getItemId())
                    {
                        case 1:
                            try
                                {
                                    addlink_new();
                                }
                            catch (JSONException e)
                                {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            break;
                        case 2:
                            reflinks();
                            break;
                        default:
                            break;
                    }
                return super.onOptionsItemSelected(item);
            }

    }
