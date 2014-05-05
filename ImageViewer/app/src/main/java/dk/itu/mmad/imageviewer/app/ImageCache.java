package dk.itu.mmad.imageviewer.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ksma on 05/05/14.
 */
public class ImageCache {
    public static boolean IsCached(Context context, String imageUrl) {
        File imageFile = context.getFileStreamPath(FilenameFromImageUrl(imageUrl));
        return imageFile.exists();
    }

    public static Bitmap GetCached(Context context, String imageUrl) {
        Bitmap bitmap = null;

        File imageFile = context.getFileStreamPath(FilenameFromImageUrl(imageUrl));
        if (imageFile.exists()) {
            bitmap = BitmapFactory.decodeFile(imageFile.getPath());
        }

        return bitmap;
    }

    public static void CacheImage(Context context, Bitmap bitmap, String imageUrl) {
        try {
            String filename = FilenameFromImageUrl(imageUrl);
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
            outputStream.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String FilenameFromImageUrl(String imageUrl) {
        return Uri.parse(imageUrl.toString()).getLastPathSegment();
    }
}
