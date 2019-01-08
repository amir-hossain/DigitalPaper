package amir.digital.paper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import amir.digital.paper.R;
import amir.digital.paper.model.DrawerItemModel;

public class DrawerItemAdapter extends ArrayAdapter<DrawerItemModel> {
    private Context context;
    private int layoutResourceId;
    private DrawerItemModel data[];
    public DrawerItemAdapter(Context context, int resourceId,DrawerItemModel[] data) {
        super(context, resourceId, data);
        this.layoutResourceId =resourceId;
        this.context=context;
        this.data=data;

    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        View view=LayoutInflater.from(context).inflate(layoutResourceId,parent,false);

        ImageView imageIcon=view.findViewById(R.id.imageViewIcon);

        TextView textViewName = view.findViewById(R.id.textViewName);

        DrawerItemModel dataModel = data[position];

        imageIcon.setImageResource(dataModel.icon);

        textViewName.setText(dataModel.name);

        return view;
    }
}
