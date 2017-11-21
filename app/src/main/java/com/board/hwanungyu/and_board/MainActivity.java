package com.board.hwanungyu.and_board;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseUser user = mAuth.getCurrentUser();
//        user.getDisplayName();
//        user.getEmail();
        getFragmentManager().beginTransaction().replace(R.id.mainActivity_framelayout, new ListFragment()).commit();

        // GoogleSignInOptions 개체 구성
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

// Build a GoogleApiClient with access to the Google Sign-In API and the options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        //DebugLog.logD(TAG, "Login fail");
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        mAuth = FirebaseAuth.getInstance();

        Button logout = (Button) findViewById(R.id.mainActivity_logOut_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                finish();
            }
        });

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

    private void signOut() {
        mAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        Toast.makeText(MainActivity.this, "로그아웃 성공", Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    public void signOut() {
//
//        mGoogleApiClient.connect();
//        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
//
//            @Override
//            public void onConnected(@Nullable Bundle bundle) {
//
//                mAuth.signOut();
//                if (mGoogleApiClient.isConnected()) {
//
//                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
//
//                        @Override
//                        public void onResult(@NonNull Status status) {
//
//                            if (status.isSuccess()) {
//
//                                //DebugLog.logD(TAG, "User Logged out");
//                                //setResult(ResultCode.SIGN_OUT_SUCCESS);
//                                Toast.makeText(MainActivity.this, "sign out success", Toast.LENGTH_SHORT).show();
//                            } else {
//
//                                //setResult(ResultCode.SIGN_OUT_FAIL);
//                                Toast.makeText(MainActivity.this, "sign out fail", Toast.LENGTH_SHORT).show();
//                            }
//
//                            //hideProgressDialog();
//                            finish();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onConnectionSuspended(int i) {
//
//                //DebugLog.logD(TAG, "Google API Client Connection Suspended");
//
//                //setResult(ResultCode.SIGN_OUT_FAIL);
//                //hideProgressDialog();
//                Toast.makeText(MainActivity.this, "sign out fail", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
//    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("종료 하시겠습니까?").setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

                builder.create().show();
    }
}
