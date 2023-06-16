package com.example.myprojectandroid;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("Einav", "onTouchEvent: ");
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.content_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);


//        Intent intent = new Intent(this, HomeIntroActivithy.class);
//        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        androidx.fragment.app.FragmentManager fm = getSupportFragmentManager();

        if (fm.findFragmentByTag("home") == null)
            fm.beginTransaction()
                    .replace(R.id.frame, new HomeFragment(), "home")
                    .commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.actionSignOut) {
            FirebaseAuth.getInstance().signOut();
            return true;
        }
        if (id == R.id.menuFavorites) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame, new FavoritesFragment())
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fm;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fm = getFragmentManager();

                for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
                    fm.popBackStack();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new HomeFragment()).addToBackStack(null)
                        .commit();


                getWindow().getDecorView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Einav", "onClick: ");
                    }
                });
//                Intent intent = new Intent(this, HomeIntroActivithy.class);
//                startActivity(intent);
                return true;
            case R.id.navigation_cities:
                fm = getFragmentManager();

                for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
                    fm.popBackStack();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new CitiesRecyclerviewFragment()).addToBackStack(null)
                        .commit();
                return true;

            case R.id.navigation_videos:
                fm = getFragmentManager();

                for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
                    fm.popBackStack();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new VideoRecyclerviewFragment()).addToBackStack(null)
                        .commit();


                return true;
        }
        throw new IllegalArgumentException("Unknown id");
    }


}



