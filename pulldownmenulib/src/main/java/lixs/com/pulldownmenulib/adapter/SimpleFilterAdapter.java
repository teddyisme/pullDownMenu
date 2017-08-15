package lixs.com.pulldownmenulib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import lixs.com.pulldownmenulib.R;


/**
 * description
 * author  XinSheng
 * date 2017/7/25
 */
public class SimpleFilterAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private List<Map<String, Object>> mData;
    private SimpleClick listener;
    private Context mContext;


    public SimpleFilterAdapter(Context mContext, List<Map<String, Object>> data, SimpleClick listener) {
        this.mData = data;
        this.listener = listener;
        this.mContext = mContext;
    }

    public void setData(List<Map<String, Object>> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple, parent, false);
        v.setOnClickListener(this);
        return new SimpleViewHodle(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SimpleViewHodle viewHolder = (SimpleViewHodle) holder;
        viewHolder.text.setText(mData.get(position).get("title").toString());
        viewHolder.itemView.setTag(position);

        if ((boolean) mData.get(position).get("select")) {
            viewHolder.text.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            viewHolder.text.setTextColor(mContext.getResources().getColor(R.color.color333));
        }

    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onItemClick((int) view.getTag());
        }
    }


    public static class SimpleViewHodle extends RecyclerView.ViewHolder implements View.OnClickListener {
        SimpleClick mListener;
        TextView text;

        public SimpleViewHodle(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(getAdapterPosition());
        }
    }

    public interface SimpleClick {
        void onItemClick(int po);
    }
}
