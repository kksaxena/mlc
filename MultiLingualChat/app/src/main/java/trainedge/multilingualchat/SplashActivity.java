package trainedge.multilingualchat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static android.R.attr.label;

public class SplashActivity extends AppCompatActivity
{
    ImageView logo;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = (ImageView) findViewById(R.id.ivlogo);

        logo.animate()
                .scaleX(1.5f)
                .scaleY(1.5f )
                .setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        Intent i=new Intent(SplashActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                });

    }

}

