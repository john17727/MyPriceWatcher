package cs4330.cs.utep.edu.mypricewatcher;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {
    static int progress;
    ProgressBar progressBar;
    int progressStatus = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progress = 0;
        progressBar = findViewById(R.id.progressbar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 10){
                    progressStatus = doSomeWork();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Intent returnIntent = new Intent();
                        setResult(MainActivity.RESULT_CANCELED, returnIntent);
                        finish();
                    }
                });
            }
            private int doSomeWork(){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                return ++progress;
            }
        }).start();

        /*Intent returnIntent = new Intent();
        setResult(MainActivity.RESULT_CANCELED, returnIntent);
        finish();*/
    }


}
