package com.zaph.loginsignupdesign.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.encoder.QRCode;
import com.zaph.loginsignupdesign.R;
import com.zaph.loginsignupdesign.models.Event;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QrCode extends AppCompatActivity {

    private ImageView eventQrCode;
    private String TAG = "GenerateQrCode";
    private String inputData;
    private QRGEncoder qrgEncoder;
    private Bitmap bitmap;
    private ImageView qrBack;
    private TextView tvqrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_qr_code);

        Intent intent = getIntent();
        Event event= (Event) intent.getSerializableExtra("dataforqr");

        eventQrCode = findViewById(R.id.eventqrcode);
        qrBack = findViewById(R.id.qrback);
        tvqrcode = findViewById(R.id.tvqrcode);

        //import Font
        Typeface MBold = Typeface.createFromAsset(getAssets(),"fonts/bold.ttf");
        tvqrcode.setTypeface(MBold);

        inputData ="Event Id :"+event.getEventId()+"\n\n"+
                "Event Name : "+event.getEventname()+"\n\n"+
                "Event Category :"+event.getEventcategory()+"\n\n"+
                "Event Venue : "+event.getEventvenue()+"\n\n"+
                "Fee : "+event.getEventfee()+"\n\n"+
                "Payment Method : "+event.getEventpayment()+"\n\n"+
            "Joining Criteria : "+event.getJoiningcriteria()+"\n\n"+
            "Date : "+event.getEventdate()+"\n\n"+
            "Time : "+event.getEventtime()+"\n\n"+
            "Prize : "+event.getEventprize()+"\n\n"+
            "Description : "+event.getEventdescription()+"\n\n"+
                "Host : "+event.getHostname()+" , "+event.getHostid()+" , "+"("+event.getHostgender()+")"+"\n\n"+
                "Contact Host : "+event.getHostphone()+" , "+event.getHostcourse()+" , "+event.getHostyear()+"\n\n"+
                "E-Mail : "+event.getHostemail()+"\n\n"+
                "Image : "+event.getBannerUrl();


        if(inputData.length()>0){
            WindowManager manager = (WindowManager)getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width<height ? width:height;
            smallerDimension = smallerDimension*3/4;
            qrgEncoder = new QRGEncoder(inputData,null, QRGContents.Type.TEXT,smallerDimension);
            try{
                bitmap = qrgEncoder.encodeAsBitmap();
                eventQrCode.setImageBitmap(bitmap);
            }catch (WriterException e){
                Log.v(TAG,e.toString());
            }
        }else{
            Toast.makeText(this, "Error in Creating QR code ..", Toast.LENGTH_SHORT).show();
        }

        qrBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
