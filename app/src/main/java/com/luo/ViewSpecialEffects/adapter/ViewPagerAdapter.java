package com.luo.ViewSpecialEffects.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

	private List<View> views;

	public ViewPagerAdapter(List<View> views) {
		this.views = views;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager)container).removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager)container).addView(views.get(position));
		return views.get(position);
	}

	@Override
	public int getCount() {
		
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		
		return arg0==arg1;
	}
}
