package com.anand.installedapplicationslist;

import android.content.pm.ApplicationInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AppsListAdapter extends RecyclerView.Adapter<AppsListAdapter.ViewHolder> {

    private List<ApplicationInfo> mApplications = new ArrayList<>();
    private IAppClickListener mIAppClickListener;

    public AppsListAdapter(List<ApplicationInfo> applicationInfos) {
        mApplications = applicationInfos;
    }

    public void setApplicationInfoList(List<ApplicationInfo> applicationInfoList) {
        mApplications = applicationInfoList;
        notifyDataSetChanged();
    }

    public List<ApplicationInfo> getmApplications() {
        return mApplications;
    }

    public void setmApplications(List<ApplicationInfo> mApplications) {
        this.mApplications = mApplications;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apps_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ApplicationInfo appInfo = mApplications.get(position);
        holder.mAppIcon.setImageDrawable(appInfo.loadIcon(holder.mAppIcon.getContext().getPackageManager()));
        holder.mAppName.setText(appInfo.loadLabel(holder.mAppName.getContext().getPackageManager()));
        holder.mPackageName.setText(appInfo.packageName);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIAppClickListener != null) {
                    mIAppClickListener.onAppClick(appInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mApplications.size();
    }

    public void setAppClickListener(IAppClickListener listener) {
        mIAppClickListener = listener;
    }

    public interface IAppClickListener {
        void onAppClick(ApplicationInfo applicationInfo);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAppIcon;
        private TextView mAppName;
        private TextView mPackageName;
        private View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mAppIcon = (ImageView) itemView.findViewById(R.id.app_icon);
            mAppName = (TextView) itemView.findViewById(R.id.app_name);
            mPackageName = (TextView) itemView.findViewById(R.id.package_name);
            mView = itemView;
        }
    }
}
