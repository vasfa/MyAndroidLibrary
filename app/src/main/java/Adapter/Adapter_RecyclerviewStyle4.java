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

import com.wang.avi.AVLoadingIndicatorView;

import ir.mlibs.notificationlibrary.OnLoadMoreListener;
import ir.mlibs.notificationlibrary.R;
import ir.mlibs.notificationlibrary.RecyclerViewSwitchingTopToBottomActivity;
import ir.mlibs.notificationlibrary.Utilitis;

/**
 * Created by vali on 2018-04-10.
 */

public class Adapter_RecyclerviewStyle4 extends RecyclerView.Adapter {

    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading, progressstop = true;
    private OnLoadMoreListener onLoadMoreListener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    Context m_context;
    Activity m_activity;
    private int type = 1;
    RecyclerViewSwitchingTopToBottomActivity recyclerViewActivity;

    public Adapter_RecyclerviewStyle4(Context context,
                                      final Activity activity,
                                      int type,
                                      RecyclerView recyclerView,
                                      RecyclerViewSwitchingTopToBottomActivity recyclerViewActivity) {
        this.type = type;
        m_context = context;
        m_activity = activity;
        this.recyclerViewActivity=recyclerViewActivity;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && 14 >= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == 1) {
            View v =
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.single_row_style1, null);

            vh = new MyViewHolderStyleOne(v);
        }else if (viewType == 2) {
            View v =
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.single_row_style2, null);

            vh = new MyViewHolderStyleOne(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);
        }

        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        int i=VIEW_PROG;
        if (Utilitis.NameList != null) {
            if (Utilitis.NameList.size()!=1 && Utilitis.NameList.size() == position + 1 && progressstop) {
                i = VIEW_PROG;
            } else {
                i = Utilitis.SwitchTypes.get(position);
            }
        }
        return  i;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MyViewHolderStyleOne) {

            ((MyViewHolderStyleOne) holder).tv_name.setText(Utilitis.NameList.get(position));
            ((MyViewHolderStyleOne) holder).tv_family.setText(Utilitis.FamilyList.get(position));
            ((MyViewHolderStyleOne) holder).tv_address.setText(Utilitis.AddressList.get(position));
            ((MyViewHolderStyleOne) holder).tv_phone.setText(Utilitis.PhoneList.get(position));
            ((MyViewHolderStyleOne) holder).iv_photo.setImageResource(R.drawable.category2);

        }else if (holder instanceof MyViewHolderStyleTwo) {

            ((MyViewHolderStyleTwo) holder).tv_name.setText(Utilitis.NameList.get(position));
            ((MyViewHolderStyleTwo) holder).tv_family.setText(Utilitis.FamilyList.get(position));
            ((MyViewHolderStyleTwo) holder).tv_address.setText(Utilitis.AddressList.get(position));
            ((MyViewHolderStyleTwo) holder).tv_phone.setText(Utilitis.PhoneList.get(position));
            ((MyViewHolderStyleTwo) holder).iv_photo.setImageResource(R.drawable.category2);

        } else {
            ((ProgressViewHolder) holder).avLoadingIndicatorView.show();
        }
    }

    @Override
    public int getItemCount() {
        return Utilitis.NameList.size();
    }

    public void setLoaded() {
        loading = false;
    }
    public void setLoading() {
        progressstop = false;
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

            iv_photo= (ImageView) itemView.findViewById(R.id.ImageView_Content);
        }
    }

    public static class MyViewHolderStyleTwo extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_family;
        TextView tv_address;
        TextView tv_phone;
        ImageView iv_photo;

        public MyViewHolderStyleTwo(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.TextView_name);
            tv_family = (TextView) itemView.findViewById(R.id.TextView_Family);
            tv_address = (TextView) itemView.findViewById(R.id.TextView_Address);
            tv_phone = (TextView) itemView.findViewById(R.id.TextView_Phone);

            iv_photo= (ImageView) itemView.findViewById(R.id.ImageView_Content);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        AVLoadingIndicatorView avLoadingIndicatorView;
        public ProgressViewHolder(View v) {
            super(v);
            avLoadingIndicatorView = (AVLoadingIndicatorView) v.findViewById(R.id.avi);
        }
    }
}
