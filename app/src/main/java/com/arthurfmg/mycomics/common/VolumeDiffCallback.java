package com.arthurfmg.mycomics.common;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.arthurfmg.mycomics.rest.model.VolumeModel;

import java.util.List;

public class VolumeDiffCallback extends DiffUtil.Callback {

    private List<VolumeModel> oldVolumeList;
    private List<VolumeModel> newVolumeList;

    public VolumeDiffCallback(List<VolumeModel> oldVolumeList, List<VolumeModel> newVolumeList) {
        this.oldVolumeList = oldVolumeList;
        this.newVolumeList = newVolumeList;
    }

    @Override
    public int getOldListSize() {
        return oldVolumeList.size();
    }

    @Override
    public int getNewListSize() {
        return newVolumeList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldVolumeList.get(oldItemPosition).getId() == newVolumeList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        VolumeModel oldVolume = oldVolumeList.get(oldItemPosition);
        VolumeModel newVolume = newVolumeList.get(newItemPosition);

        return oldVolume.getName().equals(newVolume.getName());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
