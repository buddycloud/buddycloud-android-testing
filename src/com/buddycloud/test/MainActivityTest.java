package com.buddycloud.test;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import com.buddycloud.MainActivity;
import com.buddycloud.R;
import com.buddycloud.card.CardListAdapter;
import com.buddycloud.fragments.ContentFragment;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private static final String ANDROID_TESTER = "android-tester@buddycloud.org";
	private MainActivity activity;

	public MainActivityTest() {
		super(MainActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
	}
	
	public void after() throws Exception {
		tearDown();
	}
	
	public void testAdapterNotEmptyAfterRestart() {
		Assert.assertNotNull(activity.getBackStack());
		getInstrumentation().callActivityOnRestart(activity);
		
		getInstrumentation().runOnMainSync(new Runnable() {
			@Override
			public void run() {
				activity.showChannelFragment(ANDROID_TESTER);
			}
		});
		
		getInstrumentation().waitForIdleSync();
		
		ContentFragment channelFragment = activity.getChannelStreamFrag();
		ListView listView = (ListView) channelFragment.getView().findViewById(R.id.postsStream);
		CardListAdapter adapter = (CardListAdapter) listView.getAdapter();
		Assert.assertFalse(0 == adapter.getCount());
	}
	
	public void testAdapterNotEmptyAfterResume() {
		Assert.assertNotNull(activity.getBackStack());
		getInstrumentation().callActivityOnPause(activity);
		
		getInstrumentation().runOnMainSync(new Runnable() {
			@Override
			public void run() {
				getInstrumentation().callActivityOnResume(activity);
				activity.showChannelFragment(ANDROID_TESTER);
			}
		});
		
		getInstrumentation().waitForIdleSync();
		
		ContentFragment channelFragment = activity.getChannelStreamFrag();
		ListView listView = (ListView) channelFragment.getView().findViewById(R.id.postsStream);
		CardListAdapter adapter = (CardListAdapter) listView.getAdapter();
		Assert.assertFalse(0 == adapter.getCount());
	}
	
	public void testAdapterNotEmptyAfterFullLifeCycle() {
		activity.finish();
		setActivity(null);
		activity = getActivity();
		
		getInstrumentation().runOnMainSync(new Runnable() {
			@Override
			public void run() {
				activity.showChannelFragment(ANDROID_TESTER);
			}
		});
		
		getInstrumentation().waitForIdleSync();
		
		ContentFragment channelFragment = activity.getChannelStreamFrag();
		ListView listView = (ListView) channelFragment.getView().findViewById(R.id.postsStream);
		CardListAdapter adapter = (CardListAdapter) listView.getAdapter();
		Assert.assertFalse(0 == adapter.getCount());
	}
}
