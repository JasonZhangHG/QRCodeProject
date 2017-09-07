package com.example.json.qrcodeproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.btnSaveBitMap)
    Button btnSaveBitMap;
    @BindView(R.id.btnShareBitMap)
    Button btnShareBitMap;
    private EditText et;
    private ImageView iv;
    private Button btn1, btn2;
    private Bitmap bitmapQR;
    private File sdcard = Environment.getExternalStorageDirectory().getAbsoluteFile();
    private String fileName;
    private String content;
    private String shareBitMapPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        et = (EditText) findViewById(R.id.et);
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.iv);
        btnSaveBitMap.setOnClickListener(this);
        btnShareBitMap.setOnClickListener(this);
    }

    public Bitmap Create2DCode(String str) throws WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 480, 480, hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.btn1:
                content = et.getText().toString().trim();
                try {
                    if (!TextUtils.isEmpty(content)) {
                        bitmapQR = Create2DCode(content);
                        iv.setImageBitmap(bitmapQR);
                    } else {
                        Toast.makeText(MainActivity.this, "请输入要生成的字符串", Toast.LENGTH_SHORT).show();
                    }
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn2:
                startActivity(new Intent(MainActivity.this, CaptureActivity.class));
                break;
            case R.id.btnSaveBitMap:
                if (bitmapQR == null){
                    Toast.makeText(MainActivity.this, "请先生成二维码再保存", Toast.LENGTH_SHORT).show();
                }else {
                     fileName = sdcard+"/"+content+".png";
                     saveImg(bitmapQR,fileName);
                }

                break;
            case R.id.btnShareBitMap:

                if (bitmapQR == null){
                    Toast.makeText(MainActivity.this, "请先生成二维码再分享", Toast.LENGTH_SHORT).show();
                }else {
                    fileName = sdcard+"/"+content+".png";
                    share(bitmapQR,fileName);
                }
                break;

            default:
                break;
        }
    }

    public void saveImg(Bitmap bitmap,String fileName) {

        File file = new File(fileName) ;
        if(!file.exists()){
            try {
                file.createNewFile() ;
                FileOutputStream fos = new FileOutputStream(file) ;
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, fos) ;
                Toast.makeText(this, "保存二维码成功", Toast.LENGTH_SHORT).show();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this, "二维码已保存，无需再次保存", Toast.LENGTH_SHORT).show();
        }
    }

    public void share(Bitmap bitmap,String fileName){

        File file = new File(fileName) ;
        if(!file.exists()){
            try {
                file.createNewFile() ;
                FileOutputStream fos = new FileOutputStream(file) ;
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, fos) ;
                fos.close();
                shareBitMapPath = fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            shareBitMapPath = fileName;
        }

        AndroidShare as = new AndroidShare(
                MainActivity.this,
                "分享二维码",
                shareBitMapPath);
        as.show();

        Toast.makeText(this, "开始分享了"+shareBitMapPath, Toast.LENGTH_SHORT).show();
    }

}
