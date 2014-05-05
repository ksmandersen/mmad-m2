package dk.itu.mmad.imageviewer.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by ksma on 05/05/14.
 */
public class ImageHandler {
    public interface ImageHandlerCallback {
        public abstract void imageUrlsUpdated(List<String> imageUrls);
        public abstract void imageDownloaded(Bitmap image, String imageUrl);
    }

    public static void GetImage(final Context context, final String imageUrl, final ImageView imageView, final ImageHandlerCallback callback) {
        Bitmap bitmap = null;
        if (ImageCache.IsCached(context, imageUrl)) {
            bitmap = ImageCache.GetCached(context, imageUrl);
        }

        if (bitmap != null) {
            if (callback != null) {
                callback.imageDownloaded(bitmap, imageUrl);
            }

            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        } else {
            final Handler handler = new Handler();
            ImageDownloadTask task = new ImageDownloadTask(imageView, context);
            task.execute(imageUrl);
        }
    }

    public static void GetImageUrls(final ImageHandlerCallback callback) {
        final Handler handler = new Handler();
        new Thread() {
            public void run() {
                final List<String> imageUrls = ImageDownloadTask.DownloadImageUrls();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.imageUrlsUpdated(imageUrls);
                    }
                });
            }
        }.start();
    }
}
