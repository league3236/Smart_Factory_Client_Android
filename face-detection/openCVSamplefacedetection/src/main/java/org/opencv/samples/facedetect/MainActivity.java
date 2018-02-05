package org.opencv.samples.facedetect;

/**
 * Created by 605pc on 2017-08-30.
 */

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private SocketClient client = null;             //클라이언트 소켓 null 로 초기화 인듯.
    private static final String TAG = "MainActivity";   // 이것은 mainActivity 이다.
    String ip;                                              // ip 설정 받기 위하여
    int port;                                               // port 설정 받기 위하여
    @Override
    protected void onPause() {                      //onPause 함수는 activity위에 다른 activity 가 올라오거나 하여 focus를 잃었을때 불려온다.
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {                     //onResume 함수 인터넷 연결을 위한??
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        //client.disConnect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {                        //액티비티 생성??
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };


        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(Manifest.permission.CAMERA, Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            .check();                           //허가 권한 생성하는 듯함, 카메라, 인터넷 등등을 허가 받음



        ((Button)findViewById(R.id.btn)).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {                                   //
                //ip = ((EditText)findViewById(R.id.ip)).getText().toString();                ////ip입력 받는 부분
                ip = "192.168.0.3";
                //port = Integer.valueOf( ((EditText)findViewById(R.id.port)).getText().toString() );   //// port 입력받는 부분
                port = 666;
                AddressContainer.getInstance().setIp(ip);
                AddressContainer.getInstance().setPort(port);
                SocketClient.getNewInstance();
                client = SocketClient.getInstance();
                client.setMainActivity(MainActivity.this);
                client.setAddress(ip, port);            //클라이언트의 ip와 port 설정하는 것 같음.
                client.start();                            //client 시작 ??
            }
        });

    }

    public void ServerConnectError() {                          //서버 접속 에러 떳을 시 실행하게 됨.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("서버에 접속할 수 없습니다.")
                        .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    public void NextActivity() {                                    //다음 액티비티로 넘어가게 된다.
        Log.d(TAG, "NextActivity");                                 // 다음 액티비티 시작
        Intent intent = new Intent(this, CameraActivity.class);         //카메라 액티비티 생성
        startActivity(intent);                                          //카메라 액티비티 실행.
    }
}
