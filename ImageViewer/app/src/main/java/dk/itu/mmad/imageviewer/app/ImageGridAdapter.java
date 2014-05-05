package dk.itu.mmad.imageviewer.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ksma on 04/05/14.
 */
public class ImageGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mImages;

    public void setImagesUrls(List<String> imagesUrls) {
        mImages = imagesUrls;
    }

    public ImageGridAdapter(Context context, List<String> images) {
        mContext = context;
        mImages = images;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public String getItem(int i) {
        return mImages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        if (getItem(position) != null) {
            ImageHandler.GetImage(mContext, getItem(position), imageView, null);
        }

        return imageView;
    }


}
