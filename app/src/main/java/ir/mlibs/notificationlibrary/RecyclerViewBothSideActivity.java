package ir.mlibs.notificationlibrary;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;

import Adapter.Adapter_RecyclerviewStyle6;

import static ir.mlibs.notificationlibrary.Utilitis.type_loading;
import android.os.Handler;
public class RecyclerViewBothSideActivity extends AppCompatActivity {
    RecyclerView rv_content;
    LinearLayoutManager mLayoutManager;
    Adapter_RecyclerviewStyle6 adapter;
    AVLoadingIndicatorView avLoadingIndicatorView_top;
    AVLoadingIndicatorView avLoadingIndicatorView_bottom;
    Boolean isLoadMore_top = false;
    Boolean isLoadMore_bottom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_both_side);
        Initialize();
    }

    private void Initialize() {
        try {

            rv_content = (RecyclerView) findViewById(R.id.RecyclerView_Content);
            rv_content.setHasFixedSize(true);
            rv_content.setRecycledViewPool(new RecyclerView.RecycledViewPool());

            mLayoutManager = new LinearLayoutManager(this);
            rv_content.setLayoutManager(mLayoutManager);
            avLoadingIndicatorView_top = (AVLoadingIndicatorView) findViewById(R.id.avi_top);
            avLoadingIndicatorView_bottom = (AVLoadingIndicatorView) findViewById(R.id.avi_bottom);

            adapter = new Adapter_RecyclerviewStyle6(RecyclerViewBothSideActivity.this,
                    (Activity) RecyclerViewBothSideActivity.this,
                    1,
                    rv_content,
                    RecyclerViewBothSideActivity.this);

            adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    //
                    //load more
                    //
                    if (type_loading == 1) {
                        avLoadingIndicatorView_bottom.setVisibility(View.VISIBLE);
                        isLoadMore_bottom = true;
                        createFakeContentsBottom();
                    } else if (type_loading == 2) {
                        avLoadingIndicatorView_top.setVisibility(View.VISIBLE);
                        isLoadMore_top = true;
                        createFakeContentstop();
                    }

                }
            });

            createFakeContents();
        } catch (Exception ex) {

        }
    }

    public void createFakeContents() {

        Utilitis.PhoneList.clear();
        Utilitis.AddressList.clear();
        Utilitis.FamilyList.clear();
        Utilitis.NameList.clear();

        for (int i = 0; i <= 20; i++) {
            Utilitis.PhoneList.add("0913521093" + i);
            Utilitis.AddressList.add("بلوار دانشگاه" + i);
            Utilitis.FamilyList.add("آذری " + i);
            Utilitis.NameList.add("مهدی " + i);
        }


        rv_content.setAdapter(adapter);
        rv_content.addItemDecoration(new RecyclerItemDecoration());
    }




    int lasttoppos=0;
    public void createFakeContentstop() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                int aa=0;
                int sizes=0;
                for (int i = lasttoppos-1; i > lasttoppos-20; i--) {
                    Utilitis.PhoneList.add(0,"0913521093" + i);
                    Utilitis.AddressList.add(0,"بلوار دانشگاه" + i);
                    Utilitis.FamilyList.add(0,"آذری " + i);
                    Utilitis.NameList.add(0,"مهدی " + i);
                    aa=i;
                    sizes++;
                }
                lasttoppos=aa;
                avLoadingIndicatorView_top.setVisibility(View.GONE);
                adapter.setLoaded_top();
                adapter.notifyItemRangeInserted(0,sizes);
                adapter.notifyItemChanged(sizes);
            }}, 3000);

    }
    int lastbottompos=20;
    public void createFakeContentsBottom() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                int aa=0;
                for (int i = lastbottompos+1; i < lastbottompos+20; i++) {
                    Utilitis.PhoneList.add("0913521093" + i);
                    Utilitis.AddressList.add("بلوار دانشگاه" + i);
                    Utilitis.FamilyList.add("آذری " + i);
                    Utilitis.NameList.add("مهدی " + i);
                    aa=i;
                }
                lastbottompos=aa;
                adapter.setLoaded();
                avLoadingIndicatorView_bottom.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }}, 3000);

    }
}
