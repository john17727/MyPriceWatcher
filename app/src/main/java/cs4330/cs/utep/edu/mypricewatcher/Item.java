package cs4330.cs.utep.edu.mypricewatcher;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <h2>Item</h2>
 * The structure for an item in a list.
 * @author Juan Rincon
 */
public class Item implements Parcelable {
    private String name;
    private String URL;
    private String imgPath;
    private double currPrice;
    private double initPrice;
    private double diffPercent;

    public Item(String name, String URL, double initPrice) {
        this.name = name;
        this.URL = URL;
        this.initPrice = initPrice;
    }

    public Item(String name, String URL, String imgPath, double initPrice) {
        this.name = name;
        this.URL = URL;
        this.imgPath = imgPath;
        this.initPrice = initPrice;
    }

    /**
     * Returns the name of an item.
     * @return The name of an item.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the URL of an item.
     * @return The URL of an item.
     */
    public String getURL() {
        return this.URL;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    /**
     * Calls the getSimulatedPrice method from the PriceFinder class to return
     * a different random generated number for the current price.
     * @return A random generated number.
     */
    public double getCurrPrice() {
        PriceFinder simPrice = new PriceFinder(initPrice + 500, 2500.00);
        currPrice = simPrice.getSimulatedPrice();
        return this.currPrice;
    }

    /**
     * Returns the initial price for an item.
     * @return The initial price for an item.
     */
    public double getInitPrice() {
        return this.initPrice;
    }

    /**
     * Calculates the percent difference from the initial price and
     * current price.
     * @return A percentage number.
     */
    public int getDiffPercent() {
        diffPercent = ((initPrice - currPrice) / initPrice) * 100;
        diffPercent *= -1;
        return (int) this.diffPercent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(URL);
        dest.writeDouble(currPrice);
        dest.writeDouble(initPrice);
    }
}
