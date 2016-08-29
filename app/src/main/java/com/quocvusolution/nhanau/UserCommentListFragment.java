package com.quocvusolution.nhanau;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.quocvusolution.utility.AndroidUtility;
import com.quocvusolution.utility.FileUtility;
import com.quocvusolution.utility.HttpUtility;
import com.quocvusolution.utility.ImageUtility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class UserCommentListFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView mLView;
    UserCommentListAdapter mAdapter;
    ArrayList<UserComment> mItems = new ArrayList<UserComment>();

    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GetUserCommentListTask task = new GetUserCommentListTask();
        String taskUrl = ((MainActivity) getActivity()).getAbsoluteUrlPath(MainActivity.USER_COMMENT_LIST_URL);
        task.execute(taskUrl);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.food_detail_comment_list, container, false);
        mView = view;
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void fillImage(UserComment item) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        File userPhotoFile = FileUtility.getOutputMediaFile(item.getUserPhoto(), ((MainActivity) getActivity()).getStorageDirName());
        item.setBitmapUserPhoto(ImageUtility.loadImageFile(userPhotoFile.getAbsolutePath()));
    }

    public void refresh() {
        if (mItems != null) {
            mLView = (ListView) mView.findViewById(R.id.lv_item_detail_comment_list);
            mAdapter = new UserCommentListAdapter(getActivity(), mItems);
            mLView.setAdapter(mAdapter);
            AndroidUtility.setDynamicHeight(mLView);
            TextView tvViewLoading = (TextView) mView.findViewById(R.id.tv_comment_list_loading);
            tvViewLoading.setVisibility(View.GONE);
            LinearLayout laCommentList = (LinearLayout) mView.findViewById(R.id.la_comment_list);
            laCommentList.setVisibility(View.VISIBLE);
            mLView.requestLayout();
        }
    }

    public void getUserCommentList(String url, RequestQueue requestQueue) {
        ArrayList<UserComment> dlListItem = new ArrayList<UserComment>();
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(url, null, future, future);
        requestQueue.add(request);
        try {
            JSONObject jObjectRoot = future.get();
            JSONArray jObjectArr = jObjectRoot.getJSONArray("CommentList");
            for (int i = 0; i < jObjectArr.length(); i++) {
                JSONObject jObject = jObjectArr.getJSONObject(i);
                UserComment item = new UserComment();
                item.setJData(jObject);
                dlListItem.add(item);
                GetImageTask task1 = new GetImageTask();
                task1.execute(item.getUserPhoto(), item);
            }
        } catch (Exception e) {
        }
        mItems = dlListItem;
    }

    public class GetUserCommentListTask extends AsyncTask<Object, Void, Object> {
        @Override
        protected Object doInBackground(Object... param) {
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                getUserCommentList(param[0].toString(), requestQueue);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            try {
                refresh();
            } catch (Exception e) {
            }
        }
    }

    public class GetImageTask extends AsyncTask<Object, Void, Object> {
        UserComment Item;
        @Override
        protected Object doInBackground(Object... params) {
            try {
                String fileName = params[0].toString();
                Item = (UserComment)params[1];
                String photoPathUrl = ((MainActivity) getActivity()).getAbsoluteUrlPath(MainActivity.PHOTO_PATH);
                File file = FileUtility.getOutputMediaFile(fileName, ((MainActivity) getActivity()).getStorageDirName());
                HttpUtility.downloadFile(photoPathUrl + "/" + fileName, file.getAbsolutePath());
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            try {
                fillImage(Item);
                mAdapter.notifyDataSetChanged();
            } catch (Exception e) {
            }
        }
    }
}

