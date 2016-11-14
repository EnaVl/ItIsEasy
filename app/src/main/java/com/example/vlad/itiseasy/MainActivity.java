package com.example.vlad.itiseasy;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView showScore, titleText, action_settings;
    Button btnStart;
    RadioGroup rgb;
    RadioButton rb1, rb2, rb3;
    static Typeface typeface;
    ImageView btnSettings;

    Intent intent_key;
    Intent intent_sound;
    static Intent musicService;
    
    static Boolean playTheMusic = true;


    final int REQUEST_CODE_GAME = 1;
    final int REQUEST_CODE_SOUNDS = 2;
    final int REQUEST_CODE_KEYBOARD = 3;

    private Drawer.Result drawerResult = null;

    private int ACTIVITY_GAME = 1;
    int RBN_CODE = 0;

    final static String TAG = "myLog";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent_key = new Intent(this, KeyboardsSwitcher.class);
        intent_sound = new Intent(this, Sound.class);
        musicService = new Intent(this, MusicService.class).putExtra("CODE_MUSIC", 1);
        startService(musicService);

        drawerResult = new Drawer()
                .withActivity(this)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_sound).withIcon(FontAwesome.Icon.faw_volume_down).withIdentifier(1),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_keyboard).withIcon(FontAwesome.Icon.faw_keyboard_o).withIdentifier(2)
                        // new DividerDrawerItem(),
                        //new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_github).withBadge("12+").withIdentifier(1)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                      try {
                            switch (drawerItem.getIdentifier()) {
                                case 1:
                                    startActivityForResult(intent_sound, REQUEST_CODE_SOUNDS);
                                    break;
                                case 2:
                                    startActivityForResult(intent_key, REQUEST_CODE_KEYBOARD);
                                    break;
                            }
                        } catch (NullPointerException e ) {
                            e.printStackTrace();
                        }
                    }
                 })
                .build();

        btnSettings = (ImageView) findViewById(R.id.btnSettings);
        btnStart = (Button) findViewById(R.id.btnStart);
        showScore = (TextView) findViewById(R.id.showScore);
        titleText = (TextView) findViewById(R.id.titleText);
        action_settings = (TextView) findViewById(R.id.action_settings);
        rgb = (RadioGroup) findViewById(R.id.rgb);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);


        typeface = Typeface.createFromAsset(getAssets(), "menulis.ttf");
        titleText.setTypeface(typeface);
        showScore.setTypeface(typeface);
        action_settings.setTypeface(typeface);
        rb1.setTypeface(typeface);
        rb2.setTypeface(typeface);
        rb3.setTypeface(typeface);
        btnStart.setTypeface(typeface);


        btnStart.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(playTheMusic){
            musicService = new Intent(this, MusicService.class).putExtra("CODE_MUSIC", 1);
            startService(musicService);}
    }



    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnSettings:
                drawerResult.openDrawer();
                break;
            case R.id.btnStart:
                RBN_CODE = checkRbtn();
                Intent intent = new Intent(this, Introduction.class);
                intent.putExtra("RNB_CODE", RBN_CODE);
                intent.putExtra("activity", ACTIVITY_GAME);
                startActivityForResult(intent, REQUEST_CODE_GAME);
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }
        switch(requestCode){
            case REQUEST_CODE_GAME:
                titleText.setText(R.string.titleAgain);
                String score = data.getStringExtra("score");
                showScore.setText("Your result: " + score);

                break;
            case REQUEST_CODE_SOUNDS:
                playTheMusic = data.getBooleanExtra("sound_state", false);
                if(!playTheMusic){
                    stopService(musicService);
                }
                break;
            case REQUEST_CODE_KEYBOARD:
                ACTIVITY_GAME = data.getIntExtra("activity", 0);
                break;
        }

    }

    //Проверка какая сложность выбрана
    public int checkRbtn() {
        switch (rgb.getCheckedRadioButtonId()) {
            case R.id.rb1:
                return 0;
            case R.id.rb2:
                return 1;
            case R.id.rb3:
                return 2;
            default:
                return 0;
        }
    }

    public void onBackPressed() {
        // Закрываем Navigation Drawer по нажатию системной кнопки "Назад" если он открыт
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        stopService(musicService);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
