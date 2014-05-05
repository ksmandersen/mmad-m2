package dk.itu.mmad.imageviewer.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ksma on 05/05/14.
 */
public class ImageDownloadTask extends AsyncTask<String, Void, String> {
    private static final String BASE_URL = "http://www.itu.dk/people/jacok/MMAD/services/images/";

    private Context mContext;
    private ImageView mImageView;
    private Bitmap mBitmap;

    public ImageDownloadTask(ImageView imageView, Context context) {
        mContext = context;
        mImageView = imageView;
    }

    @Override
    protected String doInBackground(String... params) {
        mBitmap = DownloadImage(mContext, params[0]);
        return ImageCache.FilenameFromImageUrl(params[0]);
    }


    public static List<String> DownloadImageUrls() {
        List<String> imageUrls = new ArrayList<String>();

        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(BASE_URL);
            URLConnection connection = url.openConnection();

            inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String response = reader.readLine();
            imageUrls = parseJSONResponse(response);
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return imageUrls;
    }

    @Override
    protected void onPostExecute(String s) {
        if (mImageView != null && mBitmap != null) {
            mImageView.setImageBitmap(mBitmap);
        }
    }

    public static Bitmap DownloadImage(Context context, String imageUrl) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(BASE_URL + imageUrl);
            URLConnection connection = url.openConnection();
            inputStream = connection.getInputStream();

            byte[] buffer = new byte[4096];
            int chunk = -1;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            while ((chunk = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, chunk);
            }

            if (outputStream != null) {
                outputStream.close();
            }

            byte[] imageBytes = outputStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            ImageCache.CacheImage(context, bitmap, imageUrl);
            return bitmap;
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }

    private static List<String> parseJSONResponse(String response) {
        ArrayList<String> result = new ArrayList<String>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(jsonArray.getString(i));
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        return result;
    }
}
