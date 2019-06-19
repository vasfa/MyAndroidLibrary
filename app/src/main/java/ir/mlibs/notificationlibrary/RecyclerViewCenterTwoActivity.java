package ir.mlibs.notificationlibrary;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import Adapter.Adapter_RecyclerviewStyle5;
import Any_View_Indicator.RecyclerViewIndicator;
import CustomRecyclerView.ZoomCenterLayoutManager;

public class RecyclerViewCenterTwoActivity extends AppCompatActivity {
    RecyclerView rv_content;
    LinearLayoutManager ll_manager;
    GridLayoutManager gl_manager;
    CenterZoomLayoutManager mm;
    Adapter_RecyclerviewStyle5 adapter;
    RecyclerViewIndicator recyclerViewIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_center_two);
        Initialize();
    }

    private void Initialize() {
        try{


            recyclerViewIndicator= (RecyclerViewIndicator) findViewById(R.id.circleIndicator);
            rv_content= (RecyclerView) findViewById(R.id.RecyclerView_Content);
            rv_content.setHasFixedSize(true);
            rv_content.setRecycledViewPool(new RecyclerView.RecycledViewPool());
//            mm=new CenterZoomLayoutManager(RecyclerViewCenterTwoActivity.this,CenterZoomLayoutManager.HORIZONTAL
//                    ,false);
//            gl_manager=new GridLayoutManager(RecyclerViewCenterTwoActivity.this,1, LinearLayoutManager.HORIZONTAL,false);
            rv_content.setLayoutManager(mm);
//            rv_content.setLayoutManager(gl_manager);
//            rv_content.setLayoutManager(new CenterLayoutManager(RecyclerViewCenterTwoActivity.this));
            rv_content.setLayoutManager(new ZoomCenterLayoutManager(RecyclerViewCenterTwoActivity.this,1, LinearLayoutManager.HORIZONTAL,true));

            adapter = new Adapter_RecyclerviewStyle5(RecyclerViewCenterTwoActivity.this,
                    (Activity) RecyclerViewCenterTwoActivity.this,
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
