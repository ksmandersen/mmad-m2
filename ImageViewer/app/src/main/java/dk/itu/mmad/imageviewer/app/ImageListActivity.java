package dk.itu.mmad.imageviewer.app;

import android.support.v4.app.Fragment;

/**
 * Created by ksma on 04/05/14.
 */
public class ImageListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ImageListFragment();
    }
}
