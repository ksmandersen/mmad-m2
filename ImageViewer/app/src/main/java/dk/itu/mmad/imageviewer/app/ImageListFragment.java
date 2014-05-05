package dk.itu.mmad.imageviewer.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by ksma on 04/05/14.
 */
public class ImageListFragment extends Fragment implements ImageHandler.ImageHandlerCallback {

    public static final String INTENT_EXTRA_SELECTED_IMAGE = "dk.itu.mmad.imageviewer.intent_extra_image";
    public static final String INTENT_EXTRA_ALL_IMAGES = "dk.itu.mmad.imageviewer.all_images";
    private static final String DOWNLOAD_STARTED = "downloadStarted";

    private GridView mGridView;
    private List<String> mImageUrls = new ArrayList<String>();
    private ImageGridAdapter mImageGridAdapter;
    private boolean mDownloadStarted;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mImageGridAdapter = new ImageGridAdapter(getActivity(), mImageUrls);

        View rootView = inflater.inflate(R.layout.image_list_fragment, container, false);
        mGridView = (GridView)rootView.findViewById(R.id.image_grid_view);
        mGridView.setAdapter(mImageGridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), FullscreenImageActivity.class);
                intent.putExtra(INTENT_EXTRA_SELECTED_IMAGE, mImageGridAdapter.getItem(i));
                intent.putStringArrayListExtra(INTENT_EXTRA_ALL_IMAGES, (ArrayList<String>)mImageUrls);
                startActivity(intent);
            }
        });

        if (savedInstanceState != savedInstanceState) {
            mDownloadStarted = savedInstanceState.getBoolean(DOWNLOAD_STARTED);
        }

        downloadImageList();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(DOWNLOAD_STARTED, mDownloadStarted);
    }

    private void downloadImageList() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www.itu.dk/people/jacok/MMAD/services/images/")
                .build();

        if (!mDownloadStarted) {
            mDownloadStarted = true;
            ImageHandler.GetImageUrls(this);
        }
    }

    @Override
    public void imageUrlsUpdated(List<String> imageUrls) {
        this.mImageUrls = imageUrls;

        mImageGridAdapter.setImagesUrls(imageUrls);
        mImageGridAdapter.notifyDataSetChanged();

        mDownloadStarted = false;
    }

    @Override
    public void imageDownloaded(Bitmap image, String imageUrl) {

    }
}
