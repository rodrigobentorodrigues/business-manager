package bm.pdm.ifpb.com.businessmanager.infra;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class ConversorImagem {

    public byte[] toByteArray(Bitmap bitmap){
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteOut);
        return byteOut.toByteArray();
    }

    public Bitmap toBitmap(byte[] array){
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

}
