package cs4330.cs.utep.edu.mypricewatcher;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 *<h1>My Price Watcher</h1>
 * @author Juan Rincon
 */

public class MainActivity extends AppCompatActivity {

    private Item anItem; //Hardcoded for example. Will change in the future.
    private CustomListAdapter adapter; //A requirement for the ListView.
    private ListView list; //An android view that lays things into lists visually.
    private ArrayList<Item> items; //A list of the items to watch.
    Button editButton;

    EditText newName;
    EditText newURL;
    EditText newPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.List);
        editButton = findViewById(R.id.editButton);

        items = new ArrayList<Item>();
        //An example item.
        adapter = new CustomListAdapter(this, items);
        list.setAdapter(adapter); //Connects our item in the list to ListView
        addItem("Sony Alpha a7R III Mirrorless Digital Camera (Body Only)", "https://www.bhphotovideo.com/c/product/1369441-REG/sony_ilce7rm2_b_alpha_a7r_iii_mirrorless.html", "sony_ilce7rm2_b_alpha_a7r_iii_mirrorless_1508916028000_1369441", 3198.00);
        //Makes items on the list clickable
        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), Website.class); //Intent to call Website Activity
                intent.putExtra("URL", anItem.getURL()); //Sending the URL to load in Website Activity
                startActivity(intent); //Starts the activity
            }
        });*/
    }

    /**
     * This is for the popup menu used by each item to edit, delete or view the link.
     * @param v
     */
    public void showPopup(final View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.edit:
                        Toast.makeText(getBaseContext(), "Edit clicked", Toast.LENGTH_SHORT).show();
                        editDialog(v);
                        return true;
                    case R.id.delete:
                        deleteItem(v);
                        return true;
                    case R.id.link:
                        Toast.makeText(getBaseContext(), "Link clicked", Toast.LENGTH_SHORT).show();
                        viewBrowser(v);
                        return true;
                }
                return false;
            }
        });
        popup.inflate(R.menu.popup_menu);
        popup.show();

    }

    /**
     * This handles the edit option for each item. It displays the current info by using
     * a dialog window.
     * @param view
     */
    public void editDialog(View view){
        final int position = list.getPositionForView(view);
        final Dialog edit = new Dialog(MainActivity.this);
        edit.setContentView(R.layout.popup_window);
        edit.setTitle("Edit Item");
        newName = (EditText) edit.findViewById(R.id.NewName);
        newURL = (EditText) edit.findViewById(R.id.NewURL);
        newPrice = (EditText) edit.findViewById(R.id.NewPrice);
        Button submit = (Button) edit.findViewById(R.id.Submit);
        Button cancel = (Button) edit.findViewById(R.id.Cancel);
        newName.setText((CharSequence) items.get(position).getName());
        newURL.setText(items.get(position).getURL());
        newPrice.setText(Double.toString(items.get(position).getInitPrice()));
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

                edit.dismiss();
                if(notNull) {
                    items.get(position).setName(name);
                    items.get(position).setURL(url);
                    items.get(position).setInitPrice(Double.parseDouble(price));
                    adapter.notifyDataSetChanged();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.dismiss();
            }
        });
        edit.show();
        Window window = edit.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    /**
     * This method handles the deletion of the selected item.
     * @param v
     */
    public void deleteItem(View v) {
        int position = list.getPositionForView(v);
        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setTitle("Delete?");
        adb.setMessage("Are you sure you want to delete this item");
        final int positionToRemove = position;
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                items.remove(positionToRemove);
                Toast.makeText(getBaseContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }});
        adb.show();
    }

    /**
     * This method ia called from the popup menu when the user wants
     * to view the items link in a browser.
     * @param view
     */
    public void viewBrowser(View view){
        Intent intent = new Intent(view.getContext(), Website.class); //Intent to call Website Activity
        intent.putExtra("URL", anItem.getURL()); //Sending the URL to load in Website Activity
        startActivity(intent); //Starts the activity
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
