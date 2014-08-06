package com.soyomaker.handsgonew.ui;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.soyomaker.handsgonew.R;
import com.soyomaker.handsgonew.util.LogUtil;

/**
 * 主界面
 * 
 * @author like
 * 
 */
public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	private static final String TAG = "MainActivity";

	private SectionsPagerAdapter mSectionsPagerAdapter;

	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
	}

	private void initView() {
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOffscreenPageLimit(2);// 缓存两页，防止左右切换时Fragment被销毁

		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_collect: {
			Intent intent = new Intent(this, CollectActivity.class);
			startActivity(intent);
		}
			break;
		case R.id.action_settings: {
			Intent intent = new Intent(this, OptionsActivity.class);
			startActivity(intent);
		}
			break;
		case R.id.action_feedback: {
			LogUtil.e(TAG, "意见反馈");
		}
			break;
		case R.id.action_recommend: {
			LogUtil.e(TAG, "应用推荐");
		}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		static final int INDEX_GAME = 0;
		static final int INDEX_STUDY = 1;
		static final int INDEX_SEARCH = 2;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case INDEX_GAME:
				fragment = new GameFragment();
				break;
			case INDEX_STUDY:
				fragment = new StudyFragment();
				break;
			case INDEX_SEARCH:
				fragment = new SearchFragment();
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case INDEX_GAME:
				return getString(R.string.title_section1);
			case INDEX_STUDY:
				return getString(R.string.title_section2);
			case INDEX_SEARCH:
				return getString(R.string.title_section3);
			}
			return null;
		}
	}
}
