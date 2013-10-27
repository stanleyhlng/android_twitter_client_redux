package com.stanleyhlng.apps.android_twitter_client_redux.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.stanleyhlng.apps.android_twitter_client.R;
import com.stanleyhlng.apps.android_twitter_client_redux.TweetsAdapter;
import com.stanleyhlng.apps.android_twitter_client_redux.models.Tweet;

public class TweetsFragment extends Fragment {
	TweetsAdapter adapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		
		ListView lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
		adapter = (TweetsAdapter) new TweetsAdapter(getActivity(), tweets);
		lvTweets.setAdapter(adapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_tweets, container, false);
	}
	
	public TweetsAdapter getAdapter() {
		return adapter;
	}
}