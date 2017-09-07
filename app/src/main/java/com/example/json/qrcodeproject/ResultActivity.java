package com.example.json.qrcodeproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.json.qrcodeproject.decode.DecodeThread;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends Activity  {

    @BindView(R.id.btnResultActivityShare)
    Button btnResultActivityShare;
    private ImageView mResultImage;
    private TextView mResultText;
    private  String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        mResultImage = (ImageView) findViewById(R.id.result_image);
        mResultText = (TextView) findViewById(R.id.result_text);

        if (null != extras) {
            int width = extras.getInt("width");
            int height = extras.getInt("height");

            LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(width, height);
            lps.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
            lps.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
            lps.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());

            mResultImage.setLayoutParams(lps);

            result = extras.getString("result");
            mResultText.setText(result);

            Bitmap barcode = null;
            byte[] compressedBitmap = extras.getByteArray(DecodeThread.BARCODE_BITMAP);
            if (compressedBitmap != null) {
                barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
                // Mutable copy:
                barcode = barcode.copy(Bitmap.Config.RGB_565, true);
            }

            mResultImage.setImageBitmap(barcode);
        }

        btnResultActivityShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(result)){
                    Toast.makeText(ResultActivity.this, "未获取到二维码内容，无法分享", Toast.LENGTH_SHORT).show();
                }else {
                    AndroidShare as = new AndroidShare(ResultActivity.this,
                            "扫描出二维码的内容为："+result, "");
                    as.show();
                }
            }
        });
    }
}
