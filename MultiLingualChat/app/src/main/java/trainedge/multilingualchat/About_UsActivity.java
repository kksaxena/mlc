package trainedge.multilingualchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class About_UsActivity extends AppCompatActivity {

    private TextView tvabt;
    private TextView tvtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__us);

        tvabt =(TextView)findViewById(R.id.tvabt);
        tvtext =(TextView)findViewById(R.id.tvtext);






    }

}
