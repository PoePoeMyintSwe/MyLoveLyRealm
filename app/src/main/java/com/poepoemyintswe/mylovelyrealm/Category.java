package com.poepoemyintswe.mylovelyrealm;

import io.realm.RealmObject;

/**
 * Created by poepoe on 12/20/14.
 */

public class Category extends RealmObject {
  private int catid;
  private String category;


  public int getCatid() {
    return catid;
  }

  public void setCatid(int catid) {
    this.catid = catid;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
}