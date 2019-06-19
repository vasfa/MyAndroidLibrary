package Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ir.mlibs.notificationlibrary.OnLoadMoreListener;
import ir.mlibs.notificationlibrary.R;
import ir.mlibs.notificationlibrary.RecyclerViewBothSideActivity;
import ir.mlibs.notificationlibrary.Utilitis;

import static ir.mlibs.notificationlibrary.Utilitis.type_loading;

/**
 * Created by vali on 2018-05-06.
 */

public class Adapter_RecyclerviewStyle6 extends RecyclerView.Adapter {

    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading,loading_top, progressstop = true;
    private OnLoadMoreListener onLoadMoreListener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    Context m_context;
    Activity m_activity;
    private int type = 1;
    RecyclerViewBothSideActivity recyclerViewActivity;
    RecyclerView recyclerView;

    public Adapter_RecyclerviewStyle6(Context context,
                                      final Activity activity,
                                      int type,
                                      RecyclerView recyclerView,
                                      RecyclerViewBothSideActivity recyclerViewActivity) {
        this.type = type;
        m_context = context;
        m_activity = activity;
        this.recyclerView = recyclerView;
        this.recyclerViewActivity = recyclerViewActivity;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
//                            if (!loading_top && IsRecyclerViewAtTop()) {
                            if (!loading_top && firstVisibleItem==0) {
                                type_loading=2;
                                onLoadMoreListener.onLoadMore();
                                loading_top = true;
                            } else {
                                totalItemCount = linearLayoutManager.getItemCount();
                                lastVisibleItem = linearLayoutManager
                                        .findLastVisibleItemPosition();
                                if (!loading
                                        && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                    // End has been reached
                                    // Do something
                                    if (onLoadMoreListener != null) {
                                        type_loading=1;
                                        onLoadMoreListener.onLoadMore();
                                    }
                                    loading = true;
                                }
                            }

                        }
                    });


        }

    }

    private boolean IsRecyclerViewAtTop() {
        if (recyclerView.getChildCount() == 0)
            return true;
        return recyclerView.getChildAt(0).getTop() == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        View v =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_row_style6, parent, false);

        vh = new MyViewHolderStyleOne(v);


        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MyViewHolderStyleOne) {

            ((MyViewHolderStyleOne) holder).tv_name.setText(Utilitis.NameList.get(position));
            ((MyViewHolderStyleOne) holder).tv_family.setText(Utilitis.FamilyList.get(position));
            ((MyViewHolderStyleOne) holder).tv_address.setText(Utilitis.AddressList.get(position));
            ((MyViewHolderStyleOne) holder).tv_phone.setText(Utilitis.PhoneList.get(position));
            ((MyViewHolderStyleOne) holder).iv_photo.setImageResource(R.drawable.category2);

        }
    }

    @Override
    public int getItemCount() {
        return Utilitis.NameList.size();
    }

    public void setLoaded() {
        loading = false;
    }
    public void setLoaded_top() {
        loading_top = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public static class MyViewHolderStyleOne extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_family;
        TextView tv_address;
        TextView tv_phone;
        ImageView iv_photo;

        public MyViewHolderStyleOne(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.TextView_name);
            tv_family = (TextView) itemView.findViewById(R.id.TextView_Family);
            tv_address = (TextView) itemView.findViewById(R.id.TextView_Address);
            tv_phone = (TextView) itemView.findViewById(R.id.TextView_Phone);

            iv_photo = (ImageView) itemView.findViewById(R.id.ImageView_Content);
        }
    }

}
