package com.poepoemyintswe.mylovelyrealm;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends ActionBarActivity {

  @InjectView(R.id.list_category) ExpandableListView categoryListView;
  private Realm realm;
  private ExpandableAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
    Realm.deleteRealmFile(this);
    realm = Realm.getInstance(this);

    try {
      loadCategoryJson();
      loadRestaurantJson();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override protected void onResume() {
    super.onResume();
    HashMap<Category, List<Restaurant>> listHashMap = new HashMap<>();
    List<Category> categoryList = null;
    if (adapter == null) {
      try {
        categoryList = getCategory();
        for (int i = 0; i < categoryList.size(); i++) {
          listHashMap.put(categoryList.get(i), getRestaurant(i + 1));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    adapter = new ExpandableAdapter(this);
    adapter.setData(categoryList, listHashMap);
    categoryListView.setAdapter(adapter);
    adapter.notifyDataSetChanged();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    realm.close();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public List<Category> getCategory() throws IOException {

    return realm.allObjects(Category.class);
  }

  private void loadCategoryJson() throws IOException {
    // Use streams if you are worried about the size of the JSON whether it was persisted on disk
    // or received from the network.
    InputStream stream = null;
    stream = getAssets().open("category.json");

    // Open a transaction to store items into the realm
    realm.beginTransaction();
    try {
      realm.createAllFromJson(Category.class, stream);
      realm.commitTransaction();
    } catch (IOException e) {
      // Remember to cancel the transaction if anything goes wrong.
      realm.cancelTransaction();
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }

  public List<Restaurant> getRestaurant(int category) throws IOException {
    RealmResults<Restaurant> restaurants =
        realm.where(Restaurant.class).equalTo("category", category).findAll();
    return restaurants;
  }

  private void loadRestaurantJson() throws IOException {
    // Use streams if you are worried about the size of the JSON whether it was persisted on disk
    // or received from the network.
    InputStream stream = null;
    stream = getAssets().open("restaurant.json");

    // Open a transaction to store items into the realm
    realm.beginTransaction();
    try {
      realm.createAllFromJson(Restaurant.class, stream);
      realm.commitTransaction();
    } catch (IOException e) {
      // Remember to cancel the transaction if anything goes wrong.
      realm.cancelTransaction();
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }
}
