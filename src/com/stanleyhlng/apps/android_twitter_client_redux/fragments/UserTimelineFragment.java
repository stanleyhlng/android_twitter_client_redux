package com.stanleyhlng.apps.android_twitter_client_redux.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.stanleyhlng.apps.android_twitter_client_redux.TwitterRestClientApp;
import com.stanleyhlng.apps.android_twitter_client_redux.models.Tweet;

public class UserTimelineFragment extends TweetsFragment {	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		RequestParams params = new RequestParams();
		params.put("count", "25");
		params.put("user_id", String.valueOf(getArguments().getLong("user_id")));
		TwitterRestClientApp.getRestClient().getUserTimeline(params, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);			
				getAdapter().addAll(tweets);
				
				Log.d("DEBUG", jsonTweets.toString());
			}
			
		});
	}
}
