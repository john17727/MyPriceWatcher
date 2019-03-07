package cs4330.cs.utep.edu.mypricewatcher;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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

    EditText newName;
    EditText newURL;
    EditText newPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.List);

        items = new ArrayList<Item>();
        //An example item.
        adapter = new CustomListAdapter(this, items);
        list.setAdapter(adapter); //Connects our item in the list to ListView
        addItem("Sony Alpha a7R III Mirrorless Digital Camera (Body Only)", "https://www.bhphotovideo.com/c/product/1369441-REG/sony_ilce7rm2_b_alpha_a7r_iii_mirrorless.html", "sony_ilce7rm2_b_alpha_a7r_iii_mirrorless_1508916028000_1369441", 3198.00);
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
                break;
            case R.id.add:
                showDialog();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void addItem(String name, String url, String imgPath, double initPrice) {
        anItem = new Item(name, url, imgPath, initPrice);
        items.add(anItem);
        adapter.notifyDataSetChanged();
    }

    public void addItem(String name, String url, double initPrice) {
        anItem = new Item(name, url, initPrice);
        items.add(anItem);
        adapter.notifyDataSetChanged();
    }

    public void showDialog() {
        final Dialog add = new Dialog(MainActivity.this);
        add.setContentView(R.layout.popup_window);
        add.setTitle("New Item");
        newName = (EditText) add.findViewById(R.id.NewName);
        newURL = (EditText) add.findViewById(R.id.NewURL);
        newPrice = (EditText) add.findViewById(R.id.NewPrice);
        Button submit = (Button) add.findViewById(R.id.Submit);
        Button cancel = (Button) add.findViewById(R.id.Cancel);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean notNull = true;
                String name = newName.getText().toString();
                String url = newURL.getText().toString();
                String price = newPrice.getText().toString();
                if(name.matches("") || url.matches("") || price.matches("")) {
                    notNull = false;
                }

                add.dismiss();
                if(notNull) {
                    addItem(name, url, Double.parseDouble(price));
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add.dismiss();
            }
        });
        add.show();
        Window window = add.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
