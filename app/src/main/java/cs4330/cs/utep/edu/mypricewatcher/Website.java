package cs4330.cs.utep.edu.mypricewatcher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * <h2>Website Activity</h2>
 * Activity that utilizes the widget WebView to display a website.
 * @author Juan Rincon
 */
public class Website extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);

        webView =(WebView)findViewById(R.id.webView);

        Intent intent = getIntent(); //Gets the intent from Main Activity
        String URL = intent.getStringExtra("URL"); //Gets the URL that Main Activity sent

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webView.loadUrl(URL); //loads URL
    }
}
