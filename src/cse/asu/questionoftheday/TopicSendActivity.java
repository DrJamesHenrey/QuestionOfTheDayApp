package cse.asu.questionoftheday;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.cse.asu.questionoftheday.R;
import com.example.cse.asu.questionoftheday.R.layout;
import com.example.cse.asu.questionoftheday.R.menu;

import cse.asu.questionoftheday.model.Question;
import cse.asu.questionoftheday.model.Section;
import cse.asu.questionoftheday.model.User;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TopicSendActivity extends Activity {

	String topic;
	User user;
	Section section;
	TextView topicText;
	final Context context = this;
	Button save;
	CheckBox m;
	CheckBox t;
	CheckBox w;
	CheckBox th;
	CheckBox f;
	CheckBox s;
	CheckBox su;
	EditText startDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic_send);
		setTitle("Question of the Day");
		
		topicText = (TextView) findViewById(R.id.TopicHomeText);
		Button qButton = (Button) findViewById(R.id.QButton);
		Button sButton = (Button) findViewById(R.id.SButton);
		Button rButton = (Button) findViewById(R.id.RButton);
		save = (Button) findViewById(R.id.btnNewUser);
		m  = (CheckBox) findViewById(R.id.monday);
		t  = (CheckBox) findViewById(R.id.tuesday);
		w  = (CheckBox) findViewById(R.id.wednesday);
		th  = (CheckBox) findViewById(R.id.thursday);
		f  = (CheckBox) findViewById(R.id.friday);
		s  = (CheckBox) findViewById(R.id.saturday);
		su  = (CheckBox) findViewById(R.id.sunday);
		startDate = (EditText) findViewById(R.id.txtResetEmail);
		final Context context = this;

		
		user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		ArrayList<String> listOfSections = new ArrayList<String>(user.getListOfSections());

		section = (Section) getIntent().getExtras().getParcelable("SECTION_KEY");
		topic = (String) getIntent().getExtras().getString("TOPIC_KEY");
		
		topicText.setText("Topic: " + topic);
		topicText.setTextSize(18);
		
		save.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View currentView) 
			{
				String date = startDate.getText().toString();
				int mo = 0;
				int tu = 0;
				int wed = 0;
				int thur = 0;
				int fri = 0;
				int sat = 0;
				int sun = 0;
				if(!date.equals(""))
				{
					CheckBox checkBox1 = (CheckBox) findViewById(R.id.monday);
					if (checkBox1.isChecked()) 
					{
						mo = 1;
					}
					CheckBox checkBox2 = (CheckBox) findViewById(R.id.tuesday);
					if (checkBox2.isChecked()) 
					{
						tu = 1;
					}
					CheckBox checkBox3 = (CheckBox) findViewById(R.id.wednesday);
					if (checkBox3.isChecked()) 
					{
						wed = 1;
					}
					CheckBox checkBox4 = (CheckBox) findViewById(R.id.thursday);
					if (checkBox4.isChecked()) 
					{
						thur = 1;
					}
					CheckBox checkBox5 = (CheckBox) findViewById(R.id.friday);
					if (checkBox5.isChecked()) 
					{
						fri = 1;
					}
					CheckBox checkBox6 = (CheckBox) findViewById(R.id.saturday);
					if (checkBox6.isChecked()) 
					{
						sat = 1;
					}
					CheckBox checkBox7 = (CheckBox) findViewById(R.id.sunday);
					if (checkBox7.isChecked()) 
					{
						sun = 1;
					}
					
					try {
							StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
							StrictMode.setThreadPolicy(policy);
							HttpClient defaultClient =  new DefaultHttpClient();
							HttpPost post = new HttpPost();
							
							String temp1 = "http://199.180.255.173/index.php/mobile/sendTopic/" + topic + "/" + section.getSectionID() + "/" + date + "/" + mo + "/" + tu + "/" + wed + "/" + thur + "/" + fri + "/" + sat + "/" + sun;
							//String temp1 = "http://199.180.255.173/index.php/mobile/sendTopic/" + section + "/" + section + "/" + section + "/" + section + "/" + section + "/" + section + "/" + section + "/" + section + "/" + section + "/" + section;
							temp1 = temp1.replaceAll(" ", "%20");
							post.setURI(new URI(temp1));
							HttpResponse httpResponse = defaultClient.execute(post);
							BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
							String json = ""; 
							String temp = "";
							
							while ((temp = reader.readLine()) != null)
							{
								json += temp;
							}
		
							if(json.equalsIgnoreCase("false"))
							{
								Toast.makeText(getApplicationContext(),
				                        "Error.",
				                        Toast.LENGTH_LONG).show();
								
							}
							else if(json.equalsIgnoreCase("true"))
							{
								Toast.makeText(getApplicationContext(),
				                        "Changes saved!",
				                        Toast.LENGTH_LONG).show();
							}
							
							Toast.makeText(getApplicationContext(),
			                        json,
			                        Toast.LENGTH_LONG).show();
						
						
					}
					catch (Exception e)
					{
						Toast.makeText(getApplicationContext(),
			                    "Error. Please be sure your device has service or is connected to the internet.",
			                    Toast.LENGTH_LONG).show();
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(),
	                        "Please enter a start date and try again.",
	                        Toast.LENGTH_LONG).show();
					
				}
				
				
			

				
			}
		});
		
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
		

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.topic_home, menu);
		return true;
	}

}
