package com.quocvusolution.nhanau;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.quocvusolution.utility.FileUtility;
import com.quocvusolution.utility.HttpUtility;
import com.quocvusolution.utility.ImageUtility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class FoodListFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView mLView;
    FoodListAdapter mAdapter;
    ArrayList<Food> mItems = new ArrayList<Food>();
    ArrayList<FoodPair> mItemPairs = new ArrayList<FoodPair>();
    FoodStore mFoodStore;
    boolean mFirstLoad = false;
    int mPage = 1;
    int mPageSize = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFoodStore = ((MainActivity) getActivity()).getFoodStore();
        mItems = mFoodStore.getList((mPage - 1) * mPageSize, mPageSize);
        if (mItems != null) {
            for (int i = 0; i < mItems.size(); i++) {
                Food item = mItems.get(i);
                fillImage(item);
            }
        }
        String taskUrl = ((MainActivity) getActivity()).getAbsoluteUrlPath(MainActivity.FOOD_LIST_URL);
        GetFoodListTask task = new GetFoodListTask();
        task.execute(taskUrl);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.food_list, container, false);
        mLView = (ListView) view.findViewById(R.id.lv_food_list);
        mLView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Food obj = mItems.get(position);
        ((MainActivity) getActivity()).showFoodDetailFragment(obj.getId());
    }

    public void fillImage(Food item) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels / 2;
        File thumbPhotoFile = FileUtility.getOutputMediaFile(item.getThumbPhoto(), ((MainActivity) getActivity()).getStorageDirName());
        item.setBitmapThumbPhoto(ImageUtility.scaleWImageFile(thumbPhotoFile.getAbsolutePath(), width));
        File createdUserPhotoFile = FileUtility.getOutputMediaFile(item.getCreatedUserPhoto(), ((MainActivity) getActivity()).getStorageDirName());
        item.setBimapCreatedUserPhoto(ImageUtility.loadImageFile(createdUserPhotoFile.getAbsolutePath()));
    }

    public void fillDefImage(Food item) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels / 2;
        item.setBitmapThumbPhoto(ImageUtility.scaleWImageResource(getResources(), R.drawable.empty_food, width));
    }

    public void refresh() {
        ((MainActivity) getActivity()).getSpeech().stop();
        getActivity().setTitle(getResources().getString(R.string.app_name));
        if (mItems != null) {
            mItemPairs.clear();
            int i = 0;
            while (i < mItems.size()) {
                Food leftItem = mItems.get(i);
                Food rightItem = null;
                if ((i + 1) < mItems.size()) {
                    rightItem = mItems.get(i + 1);
                }
                FoodPair pair = new FoodPair();
                pair.setFoodLeft(leftItem);
                pair.setFoodRight(rightItem);
                mItemPairs.add(pair);
                i = i + 2;
            }
            mAdapter = new FoodListAdapter(getActivity(), mItemPairs);
            mLView.setAdapter(mAdapter);
            mLView.requestLayout();
        }
    }

    public void getFoodList(String url, RequestQueue requestQueue) {
        ArrayList<Food> dlItems = new ArrayList<Food>();
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(url, null, future, future);
        requestQueue.add(request);
        try {
            JSONObject jObjectRoot = future.get();
            JSONArray jObjectArr = jObjectRoot.getJSONArray("FoodList");
            if (jObjectArr != null) {
                for (int i = 0; i < jObjectArr.length(); i++) {
                    JSONObject jObject = jObjectArr.getJSONObject(i);
                    Food item = new Food();
                    item.setJData(jObject);
                    fillDefImage(item);
                    dlItems.add(item);
                    Food food = mFoodStore.getById(item.getId());
                    if (food != null) {
                        mFoodStore.update(food);
                    } else {
                        int rowId = mFoodStore.insert(item);
                        item.setRowId(rowId);
                    }
                    GetImageTask task1 = new GetImageTask();
                    task1.execute(item.getThumbPhoto(), item);
                    for (int x = 0; x < item.getPhotos().length; x++) {
                        String photo = item.getPhotos()[x];
                        GetImageTask task2 = new GetImageTask();
                        task2.execute(photo, item);
                    }
                    GetImageTask task3 = new GetImageTask();
                    task3.execute(item.getCreatedUserPhoto(), item);
                }
            }
            if (mItems == null || mItems.size() == 0) {
                mItems = dlItems;
                mFirstLoad = true;
            }
        } catch (Exception e) {
        }
    }

    public class GetFoodListTask extends AsyncTask<Object, Void, Object> {
        @Override
        protected Object doInBackground(Object... url) {
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                getFoodList(url[0].toString(), requestQueue);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            try {
                if (mFirstLoad) {
                    mFirstLoad = false;
                    refresh();
                }
            } catch (Exception e) {
            }
        }
    }

    public class GetImageTask extends AsyncTask<Object, Void, Object> {
        Food Item;
        @Override
        protected Object doInBackground(Object... params) {
            try {
                String fileName = params[0].toString();
                Item = (Food)params[1];
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

