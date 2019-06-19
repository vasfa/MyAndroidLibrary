package ir.mlibs.notificationlibrary;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wang.avi.AVLoadingIndicatorView;

import Adapter.Adapter_RecyclerviewStyle1;
import CustomRecyclerView.CenterLayoutManager;

public class RecyclerViewActivity extends AppCompatActivity {

    RecyclerView rv_content;
    Adapter_RecyclerviewStyle1 adapter;
    AVLoadingIndicatorView avLoadingIndicatorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        Initialize();
    }

    private void Initialize() {
        try{
            avLoadingIndicatorView= (AVLoadingIndicatorView) findViewById(R.id.avi);
            avLoadingIndicatorView.show();
            rv_content= (RecyclerView) findViewById(R.id.RecyclerView_Content);
            rv_content.setHasFixedSize(true);
            rv_content.setRecycledViewPool(new RecyclerView.RecycledViewPool());
//            rv_content.setLayoutManager(gl_manager);
//            rv_content.setLayoutManager(new CenterLayoutManager(RecyclerViewActivity.this));
            rv_content.setLayoutManager(new CenterLayoutManager(RecyclerViewActivity.this,1, LinearLayoutManager.HORIZONTAL,true));

            adapter = new Adapter_RecyclerviewStyle1(RecyclerViewActivity.this,
                    (Activity) RecyclerViewActivity.this,
                    1,
                    rv_content,
                    RecyclerViewActivity.this);

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

        avLoadingIndicatorView.hide();
        rv_content.setAdapter(adapter);
    }


}
