package com.stanleyhlng.apps.android_twitter_client_redux;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.stanleyhlng.apps.android_twitter_client.R;
import com.stanleyhlng.apps.android_twitter_client_redux.models.User;

public class NewTweetActivity extends Activity {
	JSONObject jsonUser = new JSONObject();
	JSONObject jsonTweet = new JSONObject();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_tweet);
		
		Intent intent = getIntent();
		User user = (User) intent.getParcelableExtra("user");
		Log.d("DEBUG", user.getJSONString());
				
		EditText etText = (EditText) findViewById(R.id.etBody);
		etText.addTextChangedListener(new TextWatcher() {
			Button btnTweet = (Button) findViewById(R.id.btnTweet);
			
			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				btnTweet.setEnabled(s.toString().trim().length() != 0);
			}
		
		});
		
		ImageView imageView = (ImageView) findViewById(R.id.ivProfile);
		ImageLoader.getInstance().displayImage(user.getProfileImageurl(), imageView);

		TextView nameView = (TextView) findViewById(R.id.tvName);
		String formattedName = String.format("<b>%s</b>",
				user.getName()
				);
		nameView.setText(Html.fromHtml(formattedName));

		TextView bodyView = (TextView) findViewById(R.id.tvBody);
		String formattedBody = String.format("<small><font color='#777777'>@%s</font></small>", 
				user.getScreenName()
				);
		bodyView.setText(Html.fromHtml(formattedBody));
		
		// formulate json object
		try {
			jsonUser = new JSONObject(user.getJSONString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_tweet, menu);
		return true;
	}

	public void onCancelTweet(View view) {
		Toast.makeText(getBaseContext(), getString(R.string.button_cancel), Toast.LENGTH_SHORT).show();
		finish();
	}

	public void onNewTweet(View view) {
		Toast.makeText(getBaseContext(), getString(R.string.button_tweet), Toast.LENGTH_SHORT).show();
		
		EditText etText = (EditText) findViewById(R.id.etBody);
		String text = etText.getText().toString().trim();
		Log.d("DEBUG", text);

		if (text.length() == 0) {
			etText.setError(getString(R.string.error_field_required));
		} else {
			// formulate json object
			try {
				jsonTweet.put("text", text);
				jsonTweet.put("user", jsonUser.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			Intent intent = new Intent();
			intent.putExtra("result", jsonTweet.toString());
			setResult(RESULT_OK, intent);
			finish();
		}
	}
}