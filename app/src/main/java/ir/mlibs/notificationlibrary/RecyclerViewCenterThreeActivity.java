package ir.mlibs.notificationlibrary;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;

import Adapter.Adapter_RecyclerviewStyle5;
import Any_View_Indicator.RecyclerViewIndicator;

public class RecyclerViewCenterThreeActivity extends AppCompatActivity {
    RecyclerView rv_content;
    LinearLayoutManager ll_manager;
    GridLayoutManager gl_manager;
    CenterZoomLayoutManager mm;
    Adapter_RecyclerviewStyle5 adapter;
    RecyclerViewIndicator recyclerViewIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_center_three);
        Initialize();
    }

    private void Initialize() {
        try{


            recyclerViewIndicator= (RecyclerViewIndicator) findViewById(R.id.circleIndicator);
            rv_content= (RecyclerView) findViewById(R.id.RecyclerView_Content);
            rv_content.setHasFixedSize(true);
            rv_content.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            mm=new CenterZoomLayoutManager(RecyclerViewCenterThreeActivity.this,CenterZoomLayoutManager.VERTICAL
                    ,false);
            rv_content.setLayoutManager(mm);
//            rv_content.setLayoutManager(new CenterZoomLayoutManager(RecyclerViewCenterThreeActivity.this,
//                    LinearLayoutManager.VERTICAL,false));
            RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(RecyclerViewCenterThreeActivity.this) {
                @Override protected int getVerticalSnapPreference() {
                    return LinearSmoothScroller.SNAP_TO_START;
                }
            };
            smoothScroller.setTargetPosition(0);
            mm.startSmoothScroll(smoothScroller);
            adapter = new Adapter_RecyclerviewStyle5(RecyclerViewCenterThreeActivity.this,
                    (Activity) RecyclerViewCenterThreeActivity.this,
                    1,
                    rv_content);

            adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    //
                    //load more
                    //
//                    isLoadMore=true;
//                    ActionChargeCode();
                }
            });
            recyclerViewIndicator.setRecyclerView(rv_content);
            //rv_content.addItemDecoration(new LinePagerIndicatorDecoration());
//            PagerSnapHelper snapHelper = new PagerSnapHelper();
//            snapHelper.attachToRecyclerView(rv_content);
            createFakeContents();
        }
        catch (Exception ex)
        {

        }
    }

    public void createFakeContents()
    {

        Utilitis.PhoneList.clear();
        Utilitis.AddressList.clear();
        Utilitis.FamilyList.clear();
        Utilitis.NameList.clear();

        for(int i=0;i<20;i++)
        {
            Utilitis.PhoneList.add("0913521093"+i);
            Utilitis.AddressList.add("بلوار دانشگاه"+i);
            Utilitis.FamilyList.add("آذری "+i);
            Utilitis.NameList.add("مهدی "+i);
        }


        rv_content.setAdapter(adapter);
    }


}
