package com.board.hwanungyu.and_board;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseUser user = mAuth.getCurrentUser();
//        user.getDisplayName();
//        user.getEmail();
        getFragmentManager().beginTransaction().replace(R.id.mainActivity_framelayout, new ListFragment()).commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.mainActivity_bottomnavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_list:
                        getFragmentManager().beginTransaction().replace(R.id.mainActivity_framelayout, new ListFragment()).commit();
                        return true;

                    case R.id.action_upload:
                        getFragmentManager().beginTransaction().replace(R.id.mainActivity_framelayout, new UploadFragment()).commit();
                        return true;
                }
                return false;
            }
        });

    }
}
