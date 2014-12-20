package com.poepoemyintswe.mylovelyrealm;

import io.realm.RealmObject;

/**
 * Created by poepoe on 12/19/14.
 */

public class Restaurant extends RealmObject {

  private int resId;
  private String name;
  private int category;

  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  public int getResId() {
    return resId;
  }

  public void setResId(int resId) {
    this.resId = resId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
