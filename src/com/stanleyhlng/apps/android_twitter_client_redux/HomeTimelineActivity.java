package com.stanleyhlng.apps.android_twitter_client_redux;

import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.stanleyhlng.apps.android_twitter_client.R;
import com.stanleyhlng.apps.android_twitter_client_redux.fragments.HomeTimelineFragment;
import com.stanleyhlng.apps.android_twitter_client_redux.fragments.MentionsTimelineFragment;
import com.stanleyhlng.apps.android_twitter_client_redux.models.User;

public class HomeTimelineActivity extends FragmentActivity implements TabListener {
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupNavigationTabs();
		
		TwitterRestClientApp.getRestClient().getCredentials(new JsonHttpResponseHandler() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void onSuccess(JSONObject jsonCredentials) {
				user = User.fromJson(jsonCredentials);
				
				Log.d("DEBUG", user.getScreenName());
				Log.d("DEBUG", user.getProfileImageurl());
				
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {		
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
				}
				
				Log.d("DEBUG", jsonCredentials.toString());
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_refresh:
				Toast.makeText(getBaseContext(), getString(R.string.action_refresh), Toast.LENGTH_SHORT).show();

				/*
				RequestParams params = new RequestParams();
				params.put("count", "25");
				TwitterRestClientApp.getRestClient().getHomeTimeline(params, new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONArray jsonTweets) {
						ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);			
						tweetsFragment.getAdapter().addAll(tweets);
						
						Log.d("DEBUG", jsonTweets.toString());
					}
					
				});
				*/
				
				break;
				
			case R.id.action_new:
				Toast.makeText(getBaseContext(), getString(R.string.action_new), Toast.LENGTH_SHORT).show();
								
				Intent intent = new Intent(getBaseContext(), NewTweetActivity.class);
				intent.putExtra("user_name", user.getName());
				intent.putExtra("user_screen_name", user.getScreenName());
				intent.putExtra("user_profile_image_url", user.getProfileImageurl());
				startActivityForResult(intent, 0);
				
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// Handle the result once the activity returns a result
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String result = data.getStringExtra("result");
				Log.d("DEBUG", result);
				
				try {
					JSONObject jsonTweet = new JSONObject(result);
					Tweet tweet = Tweet.fromJson(jsonTweet);
					
					Log.d("DEBUG", tweet.getBody());
					Log.d("DEBUG", tweet.getUser().getName());
					
					RequestParams params = new RequestParams();
					params.put("status", tweet.getBody());
					TwitterRestClientApp.getRestClient().postUpdate(params, new JsonHttpResponseHandler() {

						@Override
						public void onSuccess(JSONObject jsonTweet) {						
							Tweet tweet = Tweet.fromJson(jsonTweet);
							tweetsFragment.getAdapter().insert(tweet, 0);
						
							Log.d("DEBUG", jsonTweet.toString());
						}
						
					});
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		*/
	}
	
	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab()
				.setText("Home")
				.setTag("HomeTimelineFragment")
				.setIcon(R.drawable.ic_action_home)
				.setTabListener(this);
		Tab tabMentions = actionBar.newTab()
				.setText("Mentions")
				.setTag("MentionsTimelineFragment")
				.setIcon(R.drawable.ic_action_mentions)
				.setTabListener(this);
		
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if (tab.getTag() == "HomeTimelineFragment") {
			// set the fragment in frameLayoutContainer to home timeline
			fts.replace(R.id.frameLayoutContainer, new HomeTimelineFragment());
		} else {
			// set the fragment in frameLayoutContainer to mentions timeline
			fts.replace(R.id.frameLayoutContainer, new MentionsTimelineFragment());			
		}
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
}