package cs4330.cs.utep.edu.mypricewatcher;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *<h1>My Price Watcher</h1>
 * Class that extends AsyncTask to run in the background.
 * Finds and parses all the information needed to save a new item from
 * a URL sent from Google Chrome or browser
 * @author Juan Rincon
 */

public class ParseData extends AsyncTask<String, Void, String[]> {

    String title;
    String price;

    @Override
    protected String[] doInBackground(String... urls) {
        try {
            //JSoup is used to get the name of the item
            Document document = Jsoup.connect(urls[0]).get();
            title = document.title();
            Log.d("Details", title);

            //Inputstreams and bufferedreader are used to find and get the price of the item
            URL url =  new URL(urls[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //Java Regex is used to find and match patterns from the website's code
            Pattern priceFinder = Pattern.compile("(\\d+\\,?\\d+\\.\\d{1,2})"); //Pattern for currency e.g. 1.0, 1.00, 11.00, 1,000.00
            Pattern wordFinder = Pattern.compile("\"price\""); //Pattern for HTML use of price

            boolean foundPrice = false;
            String inputLine;
            while((inputLine = bufferedReader.readLine()) != null) {
                Matcher wordMatcher = wordFinder.matcher(inputLine);
                while(wordMatcher.find()) { //Finds the word "price" in HTML file. If found try to find currency next
                    Matcher priceMatcher = priceFinder.matcher(inputLine);
                    while(priceMatcher.find()) { //Finds any instance of currency following the word "price"
                        price = priceMatcher.group(1); //If currency found then we save it
                        foundPrice = true;
                        break;
                    }
                    if(foundPrice) {
                        break;
                    }
                }
                if(foundPrice) {
                    break;
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Replaces any currency with commas to a parsable form e.g 1,000.00 to 1000.00
        if(price.contains(",")) {
            price = price.replace(",", "");
        }

        String[] data = new String[] {title, urls[0], price}; //Setting the data to send back
        return data;
    }

    @Override
    protected void onPostExecute(String[] data) {
        super.onPostExecute(data);
    }
}
