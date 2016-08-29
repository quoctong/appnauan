package com.quocvusolution.utility;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageUtility {
    public static Bitmap loadImageFile(String imagePath) {
        try {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
            return bitmap;
        }
        catch(Exception e) {
        }
        return null;
    }

    public static Bitmap scaleImageFile(String imagePath, int scaleWidth, int scaleHeight) {
        try {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, bmOptions);

            bmOptions.inSampleSize = calculateInSampleSize(bmOptions, scaleWidth, scaleHeight);
            bmOptions.inJustDecodeBounds = false;
            Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imagePath, bmOptions), scaleWidth, scaleHeight, false);
            return bitmap;
        }
        catch(Exception e) {
        }
        return null;
    }

    public static Bitmap scaleWImageFile(String imagePath, int scaleWidth) {
        try {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            bmOptions.inSampleSize = calculateWInSampleSize(bmOptions, scaleWidth);
            bmOptions.inJustDecodeBounds = false;
            int scaleHeight = (int)(((double)scaleWidth/(double)photoW)*(double)photoH);
            Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imagePath, bmOptions), scaleWidth, scaleHeight, false);
            return bitmap;
        }
        catch(Exception e) {
        }
        return null;
    }

    public static Bitmap scaleHImageFile(String imagePath, int scaleHeight) {
        try {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            bmOptions.inSampleSize = calculateHInSampleSize(bmOptions, scaleHeight);
            bmOptions.inJustDecodeBounds = false;
            int scaleWidth = (int)(((double)scaleHeight/(double)photoH)*(double)photoW);
            Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imagePath, bmOptions), scaleWidth, scaleHeight, false);
            return bitmap;
        }
        catch(Exception e) {
        }
        return null;
    }

    public static Bitmap scaleImageResource(Resources res, int id, int scaleWidth, int scaleHeight) {
        try {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, id, bmOptions);

            bmOptions.inSampleSize = calculateInSampleSize(bmOptions, scaleWidth, scaleHeight);
            bmOptions.inJustDecodeBounds = false;
            Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, id, bmOptions), scaleWidth, scaleHeight, false);
            return bitmap;
        }
        catch(Exception e) {
        }
        return null;
    }

    public static Bitmap scaleWImageResource(Resources res, int id, int scaleWidth) {
        try {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, id, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            bmOptions.inSampleSize = calculateWInSampleSize(bmOptions, scaleWidth);
            bmOptions.inJustDecodeBounds = false;
            int scaleHeight = (int)(((double)scaleWidth/(double)photoW)*(double)photoH);
            Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, id, bmOptions), scaleWidth, scaleHeight, false);
            return bitmap;
        }
        catch(Exception e) {
        }
        return null;
    }

    public static Bitmap scaleHImageResource(Resources res, int id, int scaleHeight) {
        try {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, id, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            bmOptions.inSampleSize = calculateHInSampleSize(bmOptions, scaleHeight);
            bmOptions.inJustDecodeBounds = false;
            int scaleWidth = (int)(((double)scaleHeight/(double)photoH)*(double)photoW);
            Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, id, bmOptions), scaleWidth, scaleHeight, false);
            return bitmap;
        }
        catch(Exception e) {
        }
        return null;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static int calculateWInSampleSize(BitmapFactory.Options options, int reqWidth) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static int calculateHInSampleSize(BitmapFactory.Options options, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = 12;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            return output;
        }
        catch(Exception e) {
        }
        return null;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        }
        catch(Exception e) {
        }
        return null;
    }
}
