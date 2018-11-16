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

public class MethodActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;


    ListView listView;
    List<Method> methodList;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_method);

        listView = (ListView) findViewById(R.id.listViewMethod);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        methodList = new ArrayList<>();

        //get from permissionActivity perm_id
        readMethod(4);
    }

    private void readMethod(int id){

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_METHOD+id, null, CODE_GET_REQUEST);
        request.execute();

    }

    private void refreshMethodList(JSONArray method) throws JSONException {

        methodList.clear();

        for(int i =0;i<method.length();i++){

            JSONObject obj = method.getJSONObject(i);

            methodList.add(new Method(
                    obj.getString("name")
            ));
        }

        MethodAdapter adapter = new MethodAdapter(methodList);
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
                    refreshMethodList(object.getJSONArray("details"));
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

    class MethodAdapter extends ArrayAdapter<Method> {

        List<Method> methodList;

        public MethodAdapter(List<Method> methodList){

            super(MethodActivity.this, R.layout.layout_method_list, methodList);

            this.methodList = methodList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_method_list, null, true);

            TextView textViewMethod = (TextView) listViewItem.findViewById(R.id.textViewMethod);


            final Method method = methodList.get(position);

            textViewMethod.setText(method.getName());

            return listViewItem;
        }
    }

}
