package cse.asu.questionoftheday;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.cse.asu.questionoftheday.R;
import com.example.cse.asu.questionoftheday.R.layout;
import com.example.cse.asu.questionoftheday.R.menu;

import cse.asu.questionoftheday.model.Section;
import cse.asu.questionoftheday.model.User;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ManageQuestionsActivity extends Activity {
	LinearLayout topicLayout;
	ArrayList<String> topics;
	Section section;
	User user;
	TextView topicText;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_questions);
		
		setTitle("Question of the Day");
		
		topicText = (TextView) findViewById(R.id.TopicText);
		Button qButton = (Button) findViewById(R.id.QButton);
		Button sButton = (Button) findViewById(R.id.SButton);
		Button rButton = (Button) findViewById(R.id.RButton);
		topicLayout = (LinearLayout) findViewById(R.id.TopicLayout);
		
		user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		ArrayList<String> listOfSections = new ArrayList<String>(user.getListOfSections());

		section = (Section) getIntent().getExtras().getParcelable("SECTION_KEY");
		topics = new ArrayList<String>();
		
		topicText.setText("Topics for " + section.getSectionID());
		topicText.setTextSize(24);
		
		qButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View currentView) 
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Questions Menu");
				builder.setItems(new CharSequence[] {"Create New Question", "Manage Questions", "Send Questions By Topic", "Search Questions"}, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which){
						case 0:
							Intent myIntent1 = new Intent(context, CreateQuestionActivity.class);
							myIntent1.putExtra("USER_KEY", user);
							myIntent1.putExtra("SECTION_KEY", section);
							startActivity(myIntent1);
							break;
						case 1:
							Intent myIntent2 = new Intent(context, ManageQuestionsActivity.class);
							myIntent2.putExtra("USER_KEY", user);
							myIntent2.putExtra("SECTION_KEY", section);
							startActivity(myIntent2);
							break;
						case 2:
							Intent myIntent3 = new Intent(context, SendByTopicActivity.class);
							myIntent3.putExtra("USER_KEY", user);
							myIntent3.putExtra("SECTION_KEY", section);
							startActivity(myIntent3);
							break;
						case 3:
							Intent myIntent4 = new Intent(context, SearchQuestionActivity.class);
							myIntent4.putExtra("USER_KEY", user);
							myIntent4.putExtra("SECTION_KEY", section);
							startActivity(myIntent4);
							break;
						}
						
					}
				});
				builder.create().show();
			}
		});
		
		sButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View currentView) 
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Statistics Menu");
				builder.setItems(new CharSequence[] {"Statistics By Student Name", "Statistics By Topic", "Statistical Graphs"}, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which){
						case 0:
							Intent myIntent1 = new Intent(context, StatsByNameActivity.class);
							myIntent1.putExtra("USER_KEY", user);
							myIntent1.putExtra("SECTION_KEY", section);
							startActivity(myIntent1);
							break;
						case 1:
							Intent myIntent2 = new Intent(context, StatsByTopicActivity.class);
							myIntent2.putExtra("USER_KEY", user);
							myIntent2.putExtra("SECTION_KEY", section);
							startActivity(myIntent2);
							break;
						case 2:
							Intent myIntent3 = new Intent(context, GraphsActivity.class);
							myIntent3.putExtra("USER_KEY", user);
							myIntent3.putExtra("SECTION_KEY", section);
							startActivity(myIntent3);
							break;
						}
						
					}
				});
				builder.create().show();
			}
		});
		
		rButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View currentView) 
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Roster Menu");
				builder.setItems(new CharSequence[] {"Show Student Roster", "Show TAs"}, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which){
						case 0:
							Intent myIntent1 = new Intent(context, StudentRosterActivity.class);
							myIntent1.putExtra("USER_KEY", user);
							myIntent1.putExtra("SECTION_KEY", section);
							myIntent1.putExtra("RELOADED_KEY", false);
							startActivity(myIntent1);
							break;
						case 1:
							Intent myIntent2 = new Intent(context, TAActivity.class);
							myIntent2.putExtra("USER_KEY", user);
							myIntent2.putExtra("SECTION_KEY", section);
							startActivity(myIntent2);
							break;
						}
						
					}
				});
				builder.create().show();
			}
		});
		
		instantiateTopics();
		populateButtons();
		
	}

	private void populateButtons() {
		
		if(topics.size() == 0)
		{
			topicText.setText("There are no questions created for this section");
			topicText.setGravity(Gravity.CENTER);
		}
		for (int i=0; i<topics.size(); i++)
		{
			final int index = i;
			Button button = new Button(this);
			button.setText(topics.get(i));
			button.setTextSize(18);
			button.setGravity(Gravity.CENTER);
			button.setTextAppearance(this, R.style.Theme_Cse);
			topicLayout.addView(button);
			
			button.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					Intent myIntent = new Intent(v.getContext(), TopicHomeActivity.class);
					myIntent.putExtra("USER_KEY", user);
					myIntent.putExtra("SECTION_KEY", section);
					myIntent.putExtra("TOPIC_KEY", topics.get(index));
					startActivity(myIntent);
				}
			});
		}
		
		
	}

	private void instantiateTopics() {
		
		try {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			HttpClient defaultClient =  new DefaultHttpClient();
			HttpPost post = new HttpPost();
			post.setURI(new URI("http://cse110.courses.asu.edu/index.php/mobile/getTopics/" + section.getSectionID()));
			HttpResponse httpResponse = defaultClient.execute(post);
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
			String json = ""; 
			String temp = "";
			
			while ((temp = reader.readLine()) != null)
			{
				json += temp;
			}
			
			JSONArray array = new JSONArray(json);
			for(int i =0; i< array.length(); i++)
			{
				JSONObject object = (JSONObject) array.get(i);
				
				topics.add((String) object.get("topic") );
			}
			
		}
		catch (Exception e)
		{
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);

				// set title
				alertDialogBuilder.setTitle("Connection Error");

				// set dialog message
				alertDialogBuilder
					.setMessage("Please check your internet connection and try again")
					.setCancelable(false)
					.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
						}
					  });

					AlertDialog alertDialog = alertDialogBuilder.create();

					alertDialog.show();
		}	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage_questions, menu);
		return true;
	}

}
