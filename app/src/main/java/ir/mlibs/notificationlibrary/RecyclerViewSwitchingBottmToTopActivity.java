package ir.mlibs.notificationlibrary;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import Adapter.Adapter_RecyclerviewStyle3;

public class RecyclerViewSwitchingBottmToTopActivity extends AppCompatActivity {

    RecyclerView rv_content;
    LinearLayoutManager mLayoutManager;
    Adapter_RecyclerviewStyle3 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_switching);
        Initialize();
    }

    private void Initialize() {
        try {

            rv_content = (RecyclerView) findViewById(R.id.RecyclerView_Content);
            rv_content.setHasFixedSize(true);
            rv_content.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setStackFromEnd(true);
            rv_content.setLayoutManager(mLayoutManager);


            adapter = new Adapter_RecyclerviewStyle3(RecyclerViewSwitchingBottmToTopActivity.this,
                    (Activity) RecyclerViewSwitchingBottmToTopActivity.this,
                    1,
                    rv_content,
                    RecyclerViewSwitchingBottmToTopActivity.this);

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
        } catch (Exception ex) {

        }
    }

    public void createFakeContents() {

        Utilitis.PhoneList.clear();
        Utilitis.AddressList.clear();
        Utilitis.FamilyList.clear();
        Utilitis.NameList.clear();

        for (int i = 0; i < 20; i++) {
            Utilitis.PhoneList.add("0913521093" + i);
            Utilitis.AddressList.add("بلوار دانشگاه" + i);
            Utilitis.FamilyList.add("آذری " + i);
            Utilitis.NameList.add("مهدی " + i);
            if (i % 2 == 0)
                Utilitis.SwitchTypes.add(1);
            else
                Utilitis.SwitchTypes.add(2);
        }


        rv_content.setAdapter(adapter);
    }
}
