package dk.itu.mmad.imageviewer.app;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by ksma on 04/05/14.
 */
public class FullscreenImageFragment extends Fragment {
    private String mCurrentImageUrl;
    private ArrayList<String> mImageUrls;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    private static final String CURRENT_IMAGE_URL = "currentImageUrl";
    private static final String IMAGE_URLS = "imageUrls";

    public FullscreenImageFragment() {}

    public FullscreenImageFragment(String imageUrl, ArrayList<String> imageUrls) {
        mCurrentImageUrl = imageUrl;
        mImageUrls = imageUrls;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fullscreen_image_fragment, container, false);

        if (savedInstanceState != null) {
            mCurrentImageUrl = savedInstanceState.getString(CURRENT_IMAGE_URL);
            mImageUrls = savedInstanceState.getStringArrayList(IMAGE_URLS);
        }

        mViewPager = (ViewPager)rootView.findViewById(R.id.image_pager);
        mPagerAdapter = new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new ImageFragment(getImageUrl(position));
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titleFromUrl(getImageUrl(position));
            }

            @Override
            public int getCount() {
                return mImageUrls.size();
            }

            public String getImageUrl(int position) {
                return mImageUrls.get(position);
            }

            public String titleFromUrl(String imageUrl) {
                return Uri.parse(imageUrl).getLastPathSegment();
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getActivity().setTitle(mPagerAdapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        int currentIndex = mImageUrls.indexOf(mCurrentImageUrl);
        mViewPager.setCurrentItem(currentIndex, false);
        getActivity().setTitle(mPagerAdapter.getPageTitle(currentIndex));

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_IMAGE_URL, mCurrentImageUrl);
        outState.putStringArrayList(IMAGE_URLS, mImageUrls);
    }
}
