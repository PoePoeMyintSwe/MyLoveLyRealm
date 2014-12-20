package com.poepoemyintswe.mylovelyrealm;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.HashMap;
import java.util.List;

/**
 * Created by poepoe on 12/20/14.
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {

  private Context mContext;
  private List<Category> categoryList;
  private HashMap<Category, List<Restaurant>> restaurantList;

  public ExpandableAdapter(Context mContext) {
    this.mContext = mContext;
  }

  @Override
  public Object getChild(int groupPosition, int childPosititon) {
    return this.restaurantList.get(this.categoryList.get(groupPosition)).get(childPosititon);
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  @Override
  public View getChildView(int groupPosition, final int childPosition, boolean isLastChild,
                           View convertView, ViewGroup parent) {

    final Restaurant restaurant = (Restaurant) getChild(groupPosition, childPosition);
    final RestaurantViewHolder holder;

    if (convertView == null || convertView.getTag() == null) {
      LayoutInflater inflater =
          (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(R.layout.row_restaurant, null);
      holder = new RestaurantViewHolder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (RestaurantViewHolder) convertView.getTag();
    }
    holder.restaurantName.setText(restaurant.getName());
    return convertView;
  }

  @Override
  public int getChildrenCount(int groupPosition) {
    return this.restaurantList.get(this.categoryList.get(groupPosition)).size();
  }

  @Override
  public Object getGroup(int groupPosition) {
    return this.categoryList.get(groupPosition);
  }

  @Override
  public int getGroupCount() {
    return this.categoryList.size();
  }

  @Override
  public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                           ViewGroup parent) {
    Category category = (Category) getGroup(groupPosition);

    final CategoryViewHolder holder;
    if (convertView == null || convertView.getTag() == null) {
      LayoutInflater inflater =
          (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(R.layout.row_category, null);
      holder = new CategoryViewHolder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (CategoryViewHolder) convertView.getTag();
    }

    holder.categoryName.setTypeface(null, Typeface.BOLD);
    holder.categoryName.setText(category.getCategory());
    holder.categoryId.setText(Integer.toString(category.getCatid()));

    return convertView;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }

  public void setData(List<Category> categoryList,
                      HashMap<Category, List<Restaurant>> restaurantList) {
    this.categoryList = categoryList;
    this.restaurantList = restaurantList;
  }

  static class CategoryViewHolder {
    @InjectView(R.id.text_category_name) TextView categoryName;
    @InjectView(R.id.text_category_id) TextView categoryId;

    public CategoryViewHolder(View view) {
      ButterKnife.inject(this, view);
    }
  }

  static class RestaurantViewHolder {
    @InjectView(R.id.text_restaurant_name) TextView restaurantName;

    public RestaurantViewHolder(View view) {
      ButterKnife.inject(this, view);
    }
  }
}
