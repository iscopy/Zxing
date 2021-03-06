package com.example.zxing;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxing.utils.QRCodeUtil;
import com.skateboard.zxinglib.CaptureActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn_sweepCode;
    private TextView tv_codeReturn;
    private Button btn_generateCode;
    private ImageView iv_code;
    private Button btn_generateCodeImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        setPermissions();//动态权限

        //点击扫码
        btn_sweepCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent,1001);
            }
        });

        //点击生成普通二维码
        btn_generateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String generateCode = tv_codeReturn.getText().toString();
                QRCodeUtil.createQRcodeImage(generateCode, iv_code);
            }
        });

        //点击生成中间带图片的二维码
        btn_generateCodeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String generateCode = tv_codeReturn.getText().toString();
                QRCodeUtil.createQRcodeImage(MainActivity.this,generateCode, iv_code);
            }
        });
    }

    public void init(){
        btn_sweepCode = findViewById(R.id.btn_sweepCode);
        tv_codeReturn = findViewById(R.id.tv_codeReturn);
        btn_generateCode = findViewById(R.id.btn_generateCode);
        iv_code = findViewById(R.id.iv_code);
        btn_generateCodeImage = findViewById(R.id.btn_generateCodeImage);
    }

    /**
     * 动态权限申请（相机）
     */
    private void setPermissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        else{
            Log.i("Jurisdiction","权限申请ok");
        }
    }

    //扫码回掉
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1001 && resultCode== Activity.RESULT_OK) {
            String result = data.getStringExtra(CaptureActivity.KEY_DATA);
            tv_codeReturn.setText(result);//显示在输入框中
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
