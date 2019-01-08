package amir.digital.paper.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private List<String> historyList;
    private HistoryAdapter.HistoryClickListener listener;

    public HistoryAdapter(HistoryAdapter.HistoryClickListener listener) {

        this.listener=listener;

    }

    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);

        return new HistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.MyViewHolder holder, int position) {
        final String history = historyList.get(position);
        holder.history.setText(history);

        holder.itemView
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onHistoryClick(history);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void setData(List<String> historyDatas) {
        this.historyList=historyDatas;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView history;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView=view;
            history =view.findViewById(android.R.id.text1);
        }
    }

    public interface HistoryClickListener{
        void onHistoryClick(String history);

    }

}


