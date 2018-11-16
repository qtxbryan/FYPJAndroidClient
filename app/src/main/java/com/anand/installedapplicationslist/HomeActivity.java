package com.anand.installedapplicationslist;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements AppsListAdapter.IAppClickListener {

    private AppsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        new FetchApplicationList().execute();
    }

    private void initUI() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.apps_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AppsListAdapter(new ArrayList<ApplicationInfo>());
        recyclerView.setAdapter(mAdapter);
        TextView apps = (TextView) findViewById(R.id.apps);
        mAdapter.setAppClickListener(this);
    }

    private List<ApplicationInfo> getApplicationList(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> appList = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != getPackageManager().getLaunchIntentForPackage(info.packageName)) {
                    appList.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return appList;
    }

    @Override
    public void onAppClick(ApplicationInfo applicationInfo) {
        Log.i("APP ID", applicationInfo.name);
        Log.i("APP NAME", applicationInfo.packageName);
        if (applicationInfo != null) {
            try {
                Intent intent = getPackageManager()
                        .getLaunchIntentForPackage(applicationInfo.packageName);

                if (null != intent) {
                    startActivity(intent);
                }
            } catch (ActivityNotFoundException e) {
                Toast.makeText(HomeActivity.this, e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private class FetchApplicationList extends AsyncTask<Void, Void, List<ApplicationInfo>> {

        @Override
        protected List<ApplicationInfo> doInBackground(Void... params) {
            return getApplicationList(getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA));
        }

        @Override
        protected void onPostExecute(List<ApplicationInfo> applicationInfos) {
            if (!isDestroyed() && !isCancelled() && applicationInfos != null) {
                if (mAdapter != null && applicationInfos.size() > 0) {
                    mAdapter.setApplicationInfoList(applicationInfos);
                }
            }

        }
    }
}
