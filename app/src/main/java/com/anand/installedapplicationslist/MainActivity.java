package com.anand.installedapplicationslist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    ListView listView;

    List<App> appList;
    ProgressBar progressBar;

    //For testing purpose
    List<Permission> permissionList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listViewApp);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //testing permission
        permissionList = new ArrayList<>();
        //Real code
        appList = new ArrayList<>();

        readApps("com.instagram.android");
    }

    private void readApps(String id){
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_HEROES+id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void readData(String id){
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_HEROESID+id ,null, CODE_GET_REQUEST);
        request.execute();
    }
    private void refreshPermissionList(JSONArray permission) throws JSONException{
        permissionList.clear();

        for(int i = 0;i<permission.length();i++){

            JSONObject obj = permission.getJSONObject(i);

            permissionList.add(new Permission(
                    obj.getString("app_id"),
                    obj.getString("permName"),
                    obj.getString("protectLevel")
            ));
        }

        PermissionAdapter adapter = new PermissionAdapter(permissionList);
        listView.setAdapter(adapter);

    }
    private void refreshAppList(JSONArray app) throws JSONException{

        appList.clear();

        for(int i =0;i<app.length();i++){

            JSONObject obj = app.getJSONObject(i);

            appList.add(new App(
                    obj.getString("app_id"),
                    obj.getString("title"),
                    obj.getString("url"),
                    obj.getString("developerID")
            ));
        }

        AppAdapter adapter = new AppAdapter(appList);
        listView.setAdapter(adapter);
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String>params, int requestCode){

            this.url = url;
            this.params = params;
            this.requestCode = requestCode;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try{
                JSONObject object = new JSONObject(s);
                if(!object.getBoolean("error")){
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshAppList(object.getJSONArray("details"));
                }
            }catch(JSONException e){
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if(requestCode == CODE_POST_REQUEST){
                return requestHandler.sendPostRequest(url, params);
            }

            if(requestCode == CODE_GET_REQUEST){

                return requestHandler.sendGetRequest(url);
            }

            return null;
        }


    }
    class PermissionAdapter extends ArrayAdapter<Permission>{

        List<Permission> permissionList;

        public PermissionAdapter(List<Permission> permissionList){

            super(MainActivity.this, R.layout.layout_app_list, permissionList);

            this.permissionList = permissionList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_app_list, null, true);

            TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
            TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewId);
            TextView textViewUrl = (TextView) listViewItem.findViewById(R.id.textViewUrl);



            final Permission permission = permissionList.get(position);

            textViewName.setText(permission.getPermName());
            textViewId.setText(permission.getAppId());
            textViewUrl.setText(permission.getProtectLevel());


            return listViewItem;
        }
    }
    class AppAdapter extends ArrayAdapter<App>{

        List<App> appList;

        public AppAdapter(List<App> appList){

            super(MainActivity.this, R.layout.activity_main, appList);
            this.appList = appList;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.activity_main, null, true);
            //Add from database
            TextView textViewName = (TextView) listViewItem.findViewById(R.id.textAppName);
            TextView textViewId = (TextView) listViewItem.findViewById(R.id.textAppId);
            TextView textViewUrl = (TextView) listViewItem.findViewById(R.id.textAppUrl);
            TextView textViewDevId = (TextView) listViewItem.findViewById(R.id.textAppDevId);


            final App app = appList.get(position);

            textViewName.setText(app.getTitle());
            textViewId.setText(app.getAppId());
            textViewDevId.setText(app.getDevId());
            textViewUrl.setText(app.getUrl());

            return listViewItem;
        }
    }



}
