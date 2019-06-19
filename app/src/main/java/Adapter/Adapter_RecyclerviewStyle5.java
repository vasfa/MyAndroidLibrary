package Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import ir.mlibs.notificationlibrary.OnLoadMoreListener;
import ir.mlibs.notificationlibrary.R;
import ir.mlibs.notificationlibrary.Utilitis;

/**
 * Created by vali on 2018-04-29.
 */

public class Adapter_RecyclerviewStyle5 extends RecyclerView.Adapter {

    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading, progressstop = true;
    private OnLoadMoreListener onLoadMoreListener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    Context m_context;
    Activity m_activity;
    private int type = 1;

    public Adapter_RecyclerviewStyle5(Context context,
                                      final Activity activity,
                                      int type,
                                      RecyclerView recyclerView) {
        this.type = type;
        m_context = context;
        m_activity = activity;

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
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
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
                            .inflate(R.layout.single_row_style5, null);

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
        int i = VIEW_PROG;
        if (Utilitis.NameList != null) {
            if (Utilitis.NameList.size() != 1 && Utilitis.NameList.size() == position + 1 && progressstop) {
                i = VIEW_PROG;
            } else {
                i = 1;
            }
        }
        return i;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MyViewHolderStyleOne) {

            ((MyViewHolderStyleOne) holder).tv_name.setText(Utilitis.NameList.get(position));
            ((MyViewHolderStyleOne) holder).tv_family.setText(Utilitis.FamilyList.get(position));
            ((MyViewHolderStyleOne) holder).tv_address.setText(Utilitis.AddressList.get(position));
            ((MyViewHolderStyleOne) holder).tv_phone.setText(Utilitis.PhoneList.get(position));
            ((MyViewHolderStyleOne) holder).iv_photo.setImageResource(R.drawable.category2);

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

        public MyViewHolderStyleOne(final View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.TextView_name);
            tv_family = (TextView) itemView.findViewById(R.id.TextView_Family);
            tv_address = (TextView) itemView.findViewById(R.id.TextView_Address);
            tv_phone = (TextView) itemView.findViewById(R.id.TextView_Phone);

            iv_photo = (ImageView) itemView.findViewById(R.id.ImageView_Content);

            itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        // run scale animation and make it bigger
                        Animation anim = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_in_tv);
                        itemView.startAnimation(anim);
                        anim.setFillAfter(true);
                    } else {
                        // run scale animation and make it smaller
                        Animation anim = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_out_tv);
                        itemView.startAnimation(anim);
                        anim.setFillAfter(true);
                    }
                }
            });
//            itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if (hasFocus) {
//                        // run scale animation and make it bigger
//                        Animation anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.scale_in_tv);
//                        itemView.startAnimation(anim);
//                        anim.setFillAfter(true);
//                    } else {
//                        // run scale animation and make it smaller
//                        Animation anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.scale_out_tv);
//                        itemView.startAnimation(anim);
//                        anim.setFillAfter(true);
//                    }
//                }
//            });
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