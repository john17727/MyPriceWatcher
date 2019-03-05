package cs4330.cs.utep.edu.mypricewatcher;

import android.app.Activity;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

/**
 *<h1>My Price Watcher</h1>
 * @author Juan Rincon
 */

public class MainActivity extends AppCompatActivity {

    private Item anItem; //Hardcoded for example. Will change in the future.
    private CustomListAdapter adapter; //A requirement for the ListView.
    private ListView list; //An android view that lays things into lists visually.
    private ArrayList<Item> items; //A list of the items to watch.
    PopupWindow addOrEdit;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addOrEdit = new PopupWindow(this);
        layout = new LinearLayout(this);

        items = new ArrayList<Item>();
        //An example item.
        anItem = new Item("Sony Alpha a7R III Mirrorless Digital Camera (Body Only)", "https://www.bhphotovideo.com/c/product/1369441-REG/sony_ilce7rm2_b_alpha_a7r_iii_mirrorless.html", "sony_ilce7rm2_b_alpha_a7r_iii_mirrorless_1508916028000_1369441", 3198.00);
        items.add(anItem); //Adding only one item
        list = (ListView) findViewById(R.id.List);
        adapter = new CustomListAdapter(this, items);
        list.setAdapter(adapter); //Connects our item in the list to ListView

        //Makes items on the list clickable
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), Website.class); //Intent to call Website Activity
                intent.putExtra("URL", anItem.getURL()); //Sending the URL to load in Website Activity
                startActivity(intent); //Starts the activity
            }
        });
    }

    /**
     * This method inflates the menu item on the action bar. This is so the menu
     * item can actually appear in the action bar.
     * @param menu
     * @return A boolean.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * This method handles what happens when the item is clicked.
     * In this case the currPrice variable is updated.
     * @param item
     * @return A boolean.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.refresh:
                anItem.getCurrPrice();
                list.setAdapter(adapter);
            case R.id.add:
                addOrEdit.showAtLocation(layout, Gravity.CENTER, 0, 0);
                addOrEdit.update(50, 50, 300, 80);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("allItems", items);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        items = savedInstanceState.getParcelableArrayList("allItems");
    }
}
