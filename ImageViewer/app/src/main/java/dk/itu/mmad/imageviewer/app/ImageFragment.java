package dk.itu.mmad.imageviewer.app;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by ksma on 05/05/14.
 */
public class ImageFragment extends Fragment {
    private String mImageUrl;

    private static final String IMAGE_URL = "imageUrl";

    public ImageFragment(){}
    public ImageFragment(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView rootView = (ImageView)inflater.inflate(R.layout.image_fragment, container, false);

        if (savedInstanceState != null) {
            mImageUrl = savedInstanceState.getString(IMAGE_URL);
        }

        ImageHandler.GetImage(getActivity(), mImageUrl, rootView, null);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(IMAGE_URL, mImageUrl);
    }
}
