package com.example.pms;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.ToLongBiFunction;

public class MainActivity extends AppCompatActivity {
    int maxBufferSize = 11;//최대 버퍼 사이즈
    int nReadSize;//받은 Data Size

    String ADDR = "220.81.104.188";//서버 IP
    int PORT = 5555;//서버 PORT

    byte[] STX = {0x10, 0x01};//시작코드
    byte[] LOCAL_CODE = {(byte) 0x1F, 0x25, (byte) 0x3A, (byte) 0xC1, (byte) 0x90, 0x26};//주차장 코드

    byte TYPE_BAR_CONTROL = 0x01;//TYPE : 차단기 ON/OFF
    byte BAR_CONTROL_ON = 0x01;//DATA : 차단기 ON
    byte BAR_CONTROL_OFF = 0x00;//DATA : 차단기 OFF

    byte TYPE_BAR_RESULT = 0x02;//TYPE : 차단기 ON/OFF 결과
    byte BAR_OPEN_OK = 0x01;//DATA : 차단기 ON/OFF 성공
    byte BAR_OPEN_NO = 0x00;//DATA : 차단기 ON/OFF 실패

    byte DATA_LEN = 0x01;//DATA 길이

    //차단기 On 명령 Message
    byte[] BAR_ON = {STX[0], STX[1],
            LOCAL_CODE[0], LOCAL_CODE[1], LOCAL_CODE[2], LOCAL_CODE[3], LOCAL_CODE[4], LOCAL_CODE[5],
            TYPE_BAR_CONTROL, DATA_LEN, BAR_CONTROL_ON};

    //차단기 Off 명령 Message
    byte[] BAR_OFF = {STX[0], STX[1],
            LOCAL_CODE[0], LOCAL_CODE[1], LOCAL_CODE[2], LOCAL_CODE[3], LOCAL_CODE[4], LOCAL_CODE[5],
            TYPE_BAR_CONTROL, DATA_LEN, BAR_CONTROL_OFF};

    //차단기 ON/OFF 성공
    byte[] BAR_RESULT_ON = {STX[0], STX[1],
            LOCAL_CODE[0], LOCAL_CODE[1], LOCAL_CODE[2], LOCAL_CODE[3], LOCAL_CODE[4], LOCAL_CODE[5],
            TYPE_BAR_RESULT, DATA_LEN, BAR_OPEN_OK};

    //차단기 ON/OFF 실패
    byte[] BAR_RESULT_OFF = {STX[0], STX[1],
            LOCAL_CODE[0], LOCAL_CODE[1], LOCAL_CODE[2], LOCAL_CODE[3], LOCAL_CODE[4], LOCAL_CODE[5],
            TYPE_BAR_RESULT, DATA_LEN, BAR_OPEN_NO};

    byte[] SEND_MESSAGE;  //보내는 Message;
    byte[] RECV_MESSAGE = new byte[maxBufferSize];//받는 Message
    int Status = 0;//현재 Bar 상태( 0 : 닫힌 상태, 1 : 열린 상태)
    Handler handler = new Handler();// 토스트를 띄우기 위한 메인스레드 핸들러 객체 생성

    private Toolbar toolbar;
    private CardView register, search, barrier, analytics, mypage, setting;
    private Switch barrierSwitch;
    CustomDialog customDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("SwitchStatus",MODE_PRIVATE);
        Status = sharedPreferences.getInt("SwitchStatus", 0);

        register = (CardView) findViewById(R.id.register_card);
        search = (CardView) findViewById(R.id.search_card);
        barrier = (CardView) findViewById(R.id.barrier_card);
        analytics = (CardView) findViewById(R.id.analytics_card);
        mypage = (CardView) findViewById(R.id.mypage_card);
        setting = (CardView)findViewById(R.id.setting_card);
        barrierSwitch = (Switch) findViewById(R.id.barrier_switch);

        ImageButton backbtn = (ImageButton) findViewById(R.id.BackBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ManageCustomer.class));
                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CarSearch.class));
                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
            }
        });

        //스위치 상태저장
        if(Status == 0)
            barrierSwitch.setChecked(false);
        else
            barrierSwitch.setChecked(true);

        barrierSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Status == 0)
                    customDialog = new CustomDialog(MainActivity.this, Confirm, Cancel, "차단바가 열립니다");
                else
                    customDialog = new CustomDialog(MainActivity.this, Confirm, Cancel, "차단바가 닫힙니다");
                customDialog.show();
            }
        });

        analytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Statistics.class));
                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
            }
        });

        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyPage.class));
                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
            }
        });

        setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Setting.class));
                overridePendingTransition(R.anim.horizon_enter, R.anim.none);
            }
        });
    }

    private View.OnClickListener Confirm = new View.OnClickListener() {
        public void onClick(View v) {
            if (Status == 0) {
                SEND_MESSAGE = BAR_ON;
                barrierSwitch.setChecked(true);
            } else {
                SEND_MESSAGE = BAR_OFF;
                barrierSwitch.setChecked(false);
            }
            SocketThread thread = new SocketThread(ADDR, SEND_MESSAGE);
            thread.start();
            customDialog.dismiss();
        }
    };

    private View.OnClickListener Cancel = new View.OnClickListener() {
        public void onClick(View v) {
            if (barrierSwitch.isChecked())
                barrierSwitch.setChecked(false);
            else
                barrierSwitch.setChecked(true);
            customDialog.dismiss();
        }
    };

    class SocketThread extends Thread implements Serializable {
        String IP; // 서버 IP
        byte[] DATA; // 전송 데이터

        public SocketThread(String host, byte[] data) {
            this.IP = host;
            this.DATA = data;
        }

        @Override
        public void run() {
            try {
                editor = sharedPreferences.edit();

                Socket socket = new Socket(IP, PORT); // 소켓 열어주기
                OutputStream outstream = socket.getOutputStream(); //소켓의 출력 스트림 참조
                outstream.write(DATA); // 출력 스트림에 데이터 넣기
                outstream.flush(); // 출력

                InputStream instream = socket.getInputStream(); // 소켓의 입력 스트림 참조
                nReadSize = instream.read(RECV_MESSAGE);

                //서버측 응답 결과 띄워줄 러너블 객체 생성하여 메인스레드 핸들러로 전달
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (nReadSize > 0) {
                            //보내는 Message가 Bar Open 명령일 때
                            if (java.util.Arrays.equals(SEND_MESSAGE, BAR_ON) == true) {
                                if (java.util.Arrays.equals(RECV_MESSAGE, BAR_RESULT_ON) == true) {
                                    Status = 1;
                                    editor.putInt("SwitchStatus", Status);
                                    editor.apply();
                                    Toast.makeText(MainActivity.this, "차단기가 열렸습니다.", Toast.LENGTH_LONG).show();
                                } else if (java.util.Arrays.equals(RECV_MESSAGE, BAR_RESULT_OFF) == true) {
                                    Toast.makeText(MainActivity.this, "차단기가 열리지 않습니다.", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "다시 시도하세요.", Toast.LENGTH_LONG).show();
                                }

                                //보내는 Message가 Bar Off 명령일 때
                            } else if (java.util.Arrays.equals(SEND_MESSAGE, BAR_OFF) == true) {
                                if (java.util.Arrays.equals(RECV_MESSAGE, BAR_RESULT_ON) == true) {
                                    Status = 0;
                                    editor.putInt("SwitchStatus", Status);
                                    editor.apply();
                                    Toast.makeText(MainActivity.this, "차단기가 닫혔습니다.", Toast.LENGTH_LONG).show();
                                } else if (java.util.Arrays.equals(RECV_MESSAGE, BAR_RESULT_OFF) == true) {
                                    Toast.makeText(MainActivity.this, "차단기가 닫히지 않습니다.", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "다시 시도하세요.", Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                });
                socket.close(); // 소켓 해제
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Byte to HexString
    public String ByteToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (final byte b : data)
            sb.append(String.format("%02x ", b & 0xff));

        return sb.toString();
    }
}