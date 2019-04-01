package cs4330.cs.utep.edu.mypricewatcher;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * <h2>CustomListAdapter</h2>
 * This is the required adapter for ListView. It organizes the data
 * that's going to be displayed inside a ListView item.
 * @author Juan Rincon
 */
public class CustomListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Item> items;

    public CustomListAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    /**
     * Required method to override.
     * @return The size fo the list.
     */
    @Override
    public int getCount() {
        return items.size();
    }

    /**
     * Required method to override.
     * @param position
     * @return The item in a certain position in the list.
     */
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    /**
     * Required method to override.
     * @param position
     * @return The position of an item in the list.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Connects data to what's to be displayed in a list item.
     * @param position
     * @param convertView
     * @param parent
     * @return A View.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        }

        Item currentItem = (Item) getItem(position);

        TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
        TextView currPrice = (TextView) convertView.findViewById(R.id.currPrice);
        TextView initPrice = (TextView) convertView.findViewById(R.id.initPrice);
        TextView diffPercent = (TextView) convertView.findViewById(R.id.diffPercent);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);

        /*
        if(currentItem.getImgPath().isEmpty()) {
            img.setImageBitmap(currentItem.getImg());
        } else {
            img.setImageResource(getImageId(context, currentItem.getImgPath()));
        }
        */
        img.setImageResource(getImageId(context, currentItem.getImgPath()));
        itemName.setText(currentItem.getName());
        currPrice.setText(Double.toString(currentItem.getCurrPrice()));
        initPrice.setText(Double.toString(currentItem.getInitPrice()));
        initPrice.setPaintFlags(initPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        diffPercent.setText(Double.toString(currentItem.getDiffPercent()) + "%");

        return convertView;
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}
