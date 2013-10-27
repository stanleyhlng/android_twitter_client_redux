package com.stanleyhlng.apps.android_twitter_client_redux.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class User extends BaseModel implements Parcelable {
	
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

		@Override
		public User createFromParcel(Parcel source) {
			return new User(source);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
		
	};
	
	public User() {
	}
	
	public User(Parcel source) {
		try {
			jsonObject = new JSONObject(source.readString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return getString("name");
	}
	
	public long getId() {
		return getLong("id");
	}
	
	public String getTagline() {
		return getString("description");
	}
	
	public String getScreenName() {
		return getString("screen_name");
	}
	
	public String getProfileImageurl() {
		return getString("profile_image_url");
	}
	
	public String getProfileBackgroundImageUrl() {
		return getString("profile_background_image_url");
	}
	
	public int getNumTweets() {
		return getInt("statuses_count");
	}
	
	public int getFollowersCount() {
		return getInt("followers_count");
	}
	
	public int getFriendsCount() {
		return getInt("friends_count");
	}
	
	public static User fromJson(JSONObject jsonObject) {
		User user = new User();
		
		try {
			user.jsonObject = jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return user;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(jsonObject.toString());
	}
}
