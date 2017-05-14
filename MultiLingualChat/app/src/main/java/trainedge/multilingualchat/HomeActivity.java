package trainedge.multilingualchat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.android.gms.appinvite.AppInviteInvitation;

import com.google.firebase.auth.FirebaseAuth;


import static trainedge.multilingualchat.SettingActivity.IS_SELECTED;
import static trainedge.multilingualchat.SettingActivity.LANGUAGE;
import static trainedge.multilingualchat.SettingActivity.THEME;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static final int REQUEST_INVITE = 232;
    public static final String TAG = "HomeActivity";
    private SharedPreferences app_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app_pref = getSharedPreferences("app_pref", MODE_PRIVATE);
        String theme = app_pref.getString(THEME, "light");
        if (theme.equals("dark")) {
            setTheme(R.style.AppThemeDark);
        } else if (theme.equals("Black")) {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boolean language_selection = app_pref.getBoolean("language_selection", false);

        if (!language_selection) {

            showLanguageSelectionDialog();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
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
            ArrayAdapter adapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1, listReadable);

            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    app_pref.edit().putString(LANGUAGE, String.valueOf(list[which])).apply();
                    app_pref.edit().putBoolean(IS_SELECTED, true).apply();
                }
            });
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void sendInvitation() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Check how many invitations were sent and log.
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                Log.d(TAG, "Invitations sent: " + ids.length);
            } else {
                // Sending failed or it was canceled, show failure message to the user
                Log.d(TAG, "Failed to send invitation.");
            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           // super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chat) {
            Intent objch = new Intent(HomeActivity.this, UserListingActivity.class);
            startActivity(objch);

        } else if (id == R.id.nav_manage) {
            Intent objs = new Intent(HomeActivity.this, SettingActivity.class);
            startActivity(objs);
            return true;
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent objl = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(objl);
            finish();

        } else if (id == R.id.nav_share) {
            sendInvitation();
        } else if (id == R.id.nav_about) {
            Intent objau = new Intent(HomeActivity.this, About_UsActivity.class);
            startActivity(objau);

        } else if (id == R.id.nav_feedback) {
            Intent objf = new Intent(HomeActivity.this, Feedback.class);
            startActivity(objf);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent objch = new Intent(HomeActivity.this, UserListingActivity.class);
        startActivity(objch);
    }
}
