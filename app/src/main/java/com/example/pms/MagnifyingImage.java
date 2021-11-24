package com.example.pms;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.List;

public class MagnifyingImage extends AppCompatActivity {
    private String TAG = "확인";
    private Intent intent;
    private String URL;
    private PhotoView photoView;
    private ImageButton CancelBtn, DownBtn;

    private String outputFilePath;
    private String savePath = "스마트 주차관리";
    private String FileName = null;

    private Context mContext;
    private DownloadManager mDownloadManager;
    private Long mDownloadQueueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnifying_image);

        mContext = getBaseContext();

        intent = getIntent();
        URL = intent.getStringExtra("URL");
        FileName = URL.substring(URL.lastIndexOf('/') + 1);

        photoView = findViewById(R.id.Img);
        CancelBtn = findViewById(R.id.CancelBtn);
        DownBtn = findViewById(R.id.DownBtn);

        Glide.with(this)
                .asBitmap()
                .load(URL)
                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(photoView);

        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputFilePath = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM + "/" + savePath) + "/" + FileName;
                URLDownloading(Uri.parse(URL));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter completeFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadCompleteReceiver, completeFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(downloadCompleteReceiver);
    }

    private void URLDownloading(Uri url) {
        if (mDownloadManager == null) {
            mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        }
        File outputFile = new File(outputFilePath);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }

        Uri downloadUri = url;
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setTitle("다운로드 항목");
        request.setDestinationUri(Uri.fromFile(outputFile));
        request.setAllowedOverMetered(true);

        mDownloadQueueId = mDownloadManager.enqueue(request);
    }

    private BroadcastReceiver downloadCompleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            if (mDownloadQueueId == reference) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(reference);
                Cursor cursor = mDownloadManager.query(query);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                int status = cursor.getInt(columnIndex);

                cursor.close();

                switch (status) {
                    case DownloadManager.STATUS_SUCCESSFUL:
                        Toast.makeText(mContext, "다운로드 성공", Toast.LENGTH_SHORT).show();
                        break;

                    case DownloadManager.STATUS_PAUSED:
                        Toast.makeText(mContext, "다운로드 중단", Toast.LENGTH_SHORT).show();
                        break;

                    case DownloadManager.STATUS_FAILED:
                        Toast.makeText(mContext, "다운로드 실패", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    };
}

