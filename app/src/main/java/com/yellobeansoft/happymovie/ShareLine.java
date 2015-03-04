package com.yellobeansoft.happymovie;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by Paul on 2/25/15.
 */

public class ShareLine extends Activity{
    static final int REQUEST_ACTION_PICK = 1;
    public static final String PACKAGE_NAME = "jp.naver.line.android";
    public static final String CLASS_NAME = "jp.naver.line.android.activity.selectchat.SelectChatActivity";
    private List<ApplicationInfo> m_appList;
    private Context mContext;


    public ShareLine(Context mContext) {
        this.mContext = mContext;
    }

    public void galleryHandler(View view) {
        if(checkLineInstalled()){
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent,"select"), REQUEST_ACTION_PICK);
        }else{
            Toast toast = Toast.makeText(this, "LINEãŒã‚¤ãƒ³ã‚¹ãƒˆãƒ¼ãƒ«ã•ã‚Œã¦ã„ã¾ã›ã‚“", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void sendTextHandler(View view, String sendText) {

        if(checkLineInstalled()){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setClassName(PACKAGE_NAME, CLASS_NAME);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, sendText);
            view.getContext().startActivity(intent);
            //startActivity(intent);
        }else{
            Toast toast = Toast.makeText(view.getContext(), "ไม่พบ LINE", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    private boolean checkLineInstalled(){

        PackageManager pm = mContext.getPackageManager();
        m_appList = pm.getInstalledApplications(0);
        boolean lineInstallFlag = false;
        for (ApplicationInfo ai : m_appList) {
            if(ai.packageName.equals(PACKAGE_NAME)){
                lineInstallFlag = true;
                break;
            }
        }
        return lineInstallFlag;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String fname = "send_image.jpg";
        String fileFullPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + fname;
        FileOutputStream out = null;

        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_ACTION_PICK){
                try {
                    InputStream iStream = getContentResolver().openInputStream(data.getData());
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    Bitmap bm = BitmapFactory.decodeStream(iStream);
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.flush();
                    byte[] w = os.toByteArray();
                    os.close();
                    iStream.close();
                    out = new FileOutputStream(fileFullPath);
                    out.write(w, 0, w.length);
                    out.flush();

                    Uri uri = Uri.fromFile(new File(fileFullPath));

                    Intent intent = new Intent(Intent.ACTION_SEND);

                    intent.setClassName(PACKAGE_NAME, CLASS_NAME);
                    intent.setType("image/jpeg");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    //Bitmapã§æ™®é€šã«åˆ©ç”¨ãŒã§ãã¾ã™ã€‚
                    //((ImageView)findViewById(R.id.imageView1)).setImageBitmap(bm);
                    startActivity(intent);
                }catch (IOException e) {
                    Log.d("test_error",e.getMessage());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}