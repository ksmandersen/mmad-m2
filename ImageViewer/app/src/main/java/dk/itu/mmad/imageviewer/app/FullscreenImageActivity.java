package dk.itu.mmad.imageviewer.app;

import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ksma on 04/05/14.
 */
public class FullscreenImageActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        String filename = intent.getStringExtra(ImageListFragment.INTENT_EXTRA_SELECTED_IMAGE);
        ArrayList<String> imageUrls = intent.getStringArrayListExtra(ImageListFragment.INTENT_EXTRA_ALL_IMAGES);

        return new FullscreenImageFragment(filename, imageUrls);
    }
}
