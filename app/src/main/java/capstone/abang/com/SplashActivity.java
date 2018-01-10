package capstone.abang.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = (ImageView) findViewById(R.id.imgViewLogo);
        Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        imageView.startAnimation(myAnimation);

        final Intent intent = new Intent(this, LoginActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2500);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
