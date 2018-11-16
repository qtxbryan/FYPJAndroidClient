package com.anand.installedapplicationslist;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class PermissionActivity extends AppCompatActivity {


    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    ListView listView;
    List<BasePermission> basePermissionList;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        listView = (ListView) findViewById(R.id.listViewPermission);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        basePermissionList = new ArrayList<>();


        readPermission();
    }

    private void readPermission(){

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_PERMISSION, null, CODE_GET_REQUEST);
        request.execute();

    }

    private void refreshPermissionList(JSONArray basePermission) throws JSONException {

        basePermissionList.clear();

        for(int i =0;i<basePermission.length();i++){

            JSONObject obj = basePermission.getJSONObject(i);

            basePermissionList.add(new BasePermission(
                    obj.getString("perm_id"),
                    obj.getString("name"),
                    obj.getString("protect_id")
            ));
        }

        BasePermissionAdapter adapter = new BasePermissionAdapter(basePermissionList);
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
                    refreshPermissionList(object.getJSONArray("details"));
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

    class BasePermissionAdapter extends ArrayAdapter<BasePermission> {

        List<BasePermission> basePermissionList;

        public BasePermissionAdapter(List<BasePermission> basePermissionList){

            super(PermissionActivity.this, R.layout.layout_permission_list, basePermissionList);

            this.basePermissionList = basePermissionList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_permission_list, null, true);

            TextView textViewPermName = (TextView) listViewItem.findViewById(R.id.textViewPermName);
            TextView textViewProtectLevel = (TextView) listViewItem.findViewById(R.id.textViewProtectLevel);
            TextView textViewPermId = (TextView) listViewItem.findViewById(R.id.textViewPermId);

            RelativeLayout permissioncardView = (RelativeLayout) listViewItem.findViewById(R.id.permissioncardView);

            final BasePermission basePermission = basePermissionList.get(position);

            textViewPermName.setText(basePermission.getPermName());

            String protection = "";

            if(basePermission.getProtectLevel().equals("1")){
                protection = "Dangerous";
                permissioncardView.setBackgroundColor(Color.RED);
            }else if (basePermission.getProtectLevel().equals("2")){

                protection = "Normal";
                permissioncardView.setBackgroundColor(Color.GREEN);

            }else if (basePermission.getProtectLevel().equals("3")){

                protection = "Signature";
                permissioncardView.setBackgroundColor(Color.YELLOW);
            }

            textViewProtectLevel.setText(protection);
            textViewPermId.setText(basePermission.getPermId());



            return listViewItem;
        }
    }
}
