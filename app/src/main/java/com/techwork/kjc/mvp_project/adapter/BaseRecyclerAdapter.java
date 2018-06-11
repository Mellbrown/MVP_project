package com.techwork.kjc.mvp_project.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by mlyg2 on 2018-06-10.
 * 리스트 뷰를 대체하기 위한 Recycler뷰의 어뎁터는 매우 코드가 비대합니다.
 * 최대한 Recycler Adpater를 비대함을 줄이기위해 많이 참조되는 깃을 참조하여 BaseRecyclerAdpater를 만들었습니다.
 *
 * usage :
     선언
     BaseListAdapter<데이터,뷰홀더> 이름;

     할당
     이름 = new BaseListAdapter<데이터, 뷰홀더>(레이아웃,뷰홀더.class) {
        @Override
        public void onCreateAfterViewHolder(final PeopleSelectableViewHolder holder) {
            이벤트 할당
        }
        @Override
        public void dataConvertViewHolder(PeopleSelectableViewHolder holder, PeopleItemData data) {
            아이템 변경
        }
    }
 */

abstract public class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{

    @NonNull
    public ArrayList<T> dataList = new ArrayList<>();
    protected int mModelLayout;
    Class<VH> mViewHolderClass;

    public BaseRecyclerAdapter(int modelLayout, Class<VH> viewHolderClass){
        mModelLayout = modelLayout;
        mViewHolderClass = viewHolderClass;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(mModelLayout, parent, false);
        try {
            Constructor<VH> constructor = mViewHolderClass.getConstructor(View.class);
            VH vh = constructor.newInstance(view);
            onCreateAfterViewHolder(vh);
            return vh;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void onCreateAfterViewHolder(VH holder){}

    @Override
    public void onBindViewHolder(VH holder, int position) {
        dataConvertViewHolder(holder,dataList.get(position));
    }

    abstract public void dataConvertViewHolder(VH holder, T data);
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}