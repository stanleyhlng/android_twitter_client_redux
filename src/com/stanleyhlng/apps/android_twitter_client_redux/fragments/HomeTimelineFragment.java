package com.stanleyhlng.apps.android_twitter_client_redux.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.stanleyhlng.apps.android_twitter_client.R;
import com.stanleyhlng.apps.android_twitter_client_redux.ProfileActivity;
import com.stanleyhlng.apps.android_twitter_client_redux.TweetsAdapter;
import com.stanleyhlng.apps.android_twitter_client_redux.TwitterRestClientApp;
import com.stanleyhlng.apps.android_twitter_client_redux.models.Tweet;

public class HomeTimelineFragment extends TweetsFragment implements OnItemClickListener {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		lvTweets.setOnItemClickListener(this);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RequestParams params = new RequestParams();
		params.put("count", "25");
		TwitterRestClientApp.getRestClient().getHomeTimeline(params, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);			
				getAdapter().addAll(tweets);
				
				Log.d("DEBUG", jsonTweets.toString());
			}
			
		});		
	}	

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Tweet tweet = (Tweet) adapter.getItem(position);

		Intent intent = new Intent(getActivity(), ProfileActivity.class);
		intent.putExtra("user", tweet.getUser());
		startActivity(intent);
	}
}
