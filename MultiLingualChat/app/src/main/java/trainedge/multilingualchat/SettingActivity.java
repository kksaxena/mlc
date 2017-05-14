package trainedge.multilingualchat;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity implements View.
        OnClickListener {
    public static final String NOTIFICATION = "notification";
    public static final String THEME = "theme";
    public static final String LANGUAGE = "language";
    public static final String IS_SELECTED = "language_selection";
    private EditText etDefEmail;
    private Switch switchWifiOp;
    private Switch switchCloudSyncOp;
    private Button btnClearSettings;
    private TextView tvSelectedLang;
    private TextView tvSelectedTheme;
    private Switch switchNotify;
    private Toolbar toolbar;
    private View selectLang;
    private View selectTheme;
    private View selectNotify;
    private SharedPreferences app_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app_pref = getSharedPreferences("app_pref", MODE_PRIVATE);

        setContentView(R.layout.activity_setting);
        bindViews();
        initListeners();
        initViews();
        updateUI();
    }

    private void initViews() {
        setSupportActionBar(toolbar);
    }

    private void initListeners() {
        selectLang.setOnClickListener(this);
        selectNotify.setOnClickListener(this);
        selectTheme.setOnClickListener(this);
    }

    private void bindViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        selectLang = findViewById(R.id.view_selectLang);
        selectTheme = findViewById(R.id.view_changeTheme);
        selectNotify = findViewById(R.id.view_notification);

        tvSelectedLang = (TextView) findViewById(R.id.tvSelectedLang);
        tvSelectedTheme = (TextView) findViewById(R.id.tvSelectedtheme);
        switchNotify = (Switch) findViewById(R.id.switchNotification);

    }


    @Override
    public void onClick(View v) {
        //clear settings
        SharedPreferences.Editor editor = app_pref.edit();

        switch (v.getId()) {
            case R.id.view_selectLang:
                showLanguageSelectionDialog();
                break;
            case R.id.view_changeTheme:
                showThemeSelectionDialog();
                break;
            case R.id.view_notification:
                switchNotify.setChecked(!switchNotify.isChecked());
                editor.putBoolean(NOTIFICATION, switchNotify.isChecked());
                break;
        }

        editor.apply();
        //pref.edit().clear().apply();
    }

    private void showThemeSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        try {
            final String[] list = {
                    "light",
                    "dark"
            };

            ArrayAdapter adapter = new ArrayAdapter<String>(SettingActivity.this, android.R.layout.simple_list_item_1, list);

            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    app_pref.edit().putString(THEME, String.valueOf(list[which])).apply();

                }
            });
            builder.create().show();
            updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showLanguageSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        try {
            final String[] list = {
                    "en",
                    "fr",
                    "ru",
                    "de",
                    "hi",
                    "gu",
                    "bn-IN",
                    "zn-HK",
                    "br"
            };
            final String[] listReadable = {
                    "english",
                    "french",
                    "russian",
                    "dutch",
                    "hindi",
                    "gujrati",
                    "bengali",
                    "chinese",
                    "brazil"
            };
            ArrayAdapter adapter = new ArrayAdapter<String>(SettingActivity.this, android.R.layout.simple_list_item_1, listReadable);

            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    app_pref.edit().putString(LANGUAGE, String.valueOf(list[which])).apply();
                    app_pref.edit().putBoolean(IS_SELECTED, true).apply();
                }
            });
            builder.create().show();
            updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
    }

    private void updateUI() {
        boolean notify = app_pref.getBoolean(NOTIFICATION, false);
        String theme = app_pref.getString(THEME, "light");
        String language = app_pref.getString(LANGUAGE, "fr");

        tvSelectedLang.setText(language);
        tvSelectedTheme.setText(theme);
        switchNotify.setChecked(notify);
    }
}


