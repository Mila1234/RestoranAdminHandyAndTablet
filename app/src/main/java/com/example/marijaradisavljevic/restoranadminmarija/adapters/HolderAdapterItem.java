package com.example.marijaradisavljevic.restoranadminmarija.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by marija.radisavljevic on 5/19/2016.
 */
public abstract class HolderAdapterItem  implements   AdapterItem{

    public HolderAdapterItem() {
    }

    public View getView(Context context, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(this.getViewLayoutResId(), parent, false);
            HolderAdapterItem.IViewHolder viewHolder = this.createViewHolder();
            viewHolder.findViews(convertView);
            convertView.setTag(viewHolder);
        }

        ((HolderAdapterItem.IViewHolder)convertView.getTag()).fillData(this);
        return convertView;
    }

    protected abstract int getViewLayoutResId();

    protected abstract <T extends HolderAdapterItem> HolderAdapterItem.IViewHolder<T> createViewHolder();

    public interface IViewHolder<T extends HolderAdapterItem> {
        void findViews(View var1);

        void fillData(T var1);
    }
}
