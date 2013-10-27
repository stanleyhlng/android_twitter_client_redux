package com.stanleyhlng.apps.android_twitter_client_redux;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.stanleyhlng.apps.android_twitter_client.R;
import com.stanleyhlng.apps.android_twitter_client_redux.fragments.UserTimelineFragment;
import com.stanleyhlng.apps.android_twitter_client_redux.models.User;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		User user = (User) getIntent().getParcelableExtra("user");
		// title
		getActionBar().setTitle(String.format("@%s", user.getScreenName()));
		// icon
		ImageLoader.getInstance().loadImage(user.getProfileImageurl(), new SimpleImageLoadingListener() {

			@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				getActionBar().setIcon(new BitmapDrawable(getResources(), loadedImage));
			}
			
		});
		
		populateUser(user);
		populateUserTimeline(user);
	}

	private void populateUserTimeline(User user) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		// set the fragment in frameLayoutUserTimeline to user_timeline
		UserTimelineFragment fragment = new UserTimelineFragment();
		Bundle bundle = new Bundle();
		bundle.putLong("user_id", user.getId());
		fragment.setArguments(bundle);
		fts.replace(R.id.frameLayoutUserTimeline, fragment);			
		fts.commit();
	}

	private void populateUser(User user) {	
		TextView tvName = (TextView) findViewById(R.id.tvName);
		tvName.setText(user.getName());
		
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		tvTagline.setText(user.getTagline());
		
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		tvFollowers.setText(String.format("%d Followers", user.getFollowersCount()));

		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		tvFollowing.setText(String.format("%d Following", user.getFriendsCount()));
	
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		ImageLoader.getInstance().displayImage(user.getProfileImageurl(), ivProfileImage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
