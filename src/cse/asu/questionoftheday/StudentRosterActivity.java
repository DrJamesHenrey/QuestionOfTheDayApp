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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StudentRosterActivity extends Activity {
	
	User user;
	Section section;
	LinearLayout linearLayout;
	ArrayList<String> userNames;
	Button remove;
	List<Integer> selectedStudents;
	boolean error, reloaded;
	TextView rosterView, messageText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_roster);
		
		setTitle("Question of the Day");
		
		rosterView = (TextView) findViewById(R.id.RosterButton);
		messageText = (TextView) findViewById(R.id.messageText);
		Button qButton = (Button) findViewById(R.id.QButton);
		Button sButton = (Button) findViewById(R.id.SButton);
		Button rButton = (Button) findViewById(R.id.RButton);
		remove = (Button) findViewById(R.id.RemoveButton);
		final Context context = this;
		
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
		
		user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		ArrayList<String> listOfSections = new ArrayList<String>(user.getListOfSections());

		section = (Section) getIntent().getExtras().getParcelable("SECTION_KEY");
		rosterView.setText("Roster for Section " + section.getSectionID());
		rosterView.setTextSize(22);
		
		userNames = new ArrayList<String>();
		selectedStudents = new ArrayList<Integer>();
		
		reloaded = (boolean) getIntent().getExtras().getBoolean("RELOADED_KEY");
		if(!reloaded)
		{
			messageText.setText("");
		}
		else
		{
			error = (boolean) getIntent().getExtras().getBoolean("ERROR_KEY");
			if(error)
			{
				messageText.setText("An error has occured while trying to delete");
			}
			else
				messageText.setText("Student(s) deleted successfuly");
		}
		
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
		
		addStudents();
		
	}

	private void addStudents() {
		
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			HttpClient defaultClient =  new DefaultHttpClient();
			HttpPost post = new HttpPost();
			
			String temp = "http://199.180.255.173/index.php/mobile/getRoster/" + section.getSectionID();
			
			post.setURI(new URI(temp));
			HttpResponse httpResponse = defaultClient.execute(post);
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
			String json = ""; 
			temp = "";
			
			while ((temp = reader.readLine()) != null)
			{
				json += temp;
			}
			JSONArray array = new JSONArray(json);
			
			if(array.length() == 0)
			{
				rosterView.setText("There are no students currently enrolled in this section");
				rosterView.setGravity(Gravity.CENTER);
				remove.setVisibility(View.INVISIBLE);
			}
			
			for(int i =0; i< array.length(); i++)
			{
				final int index = i;
				JSONObject object = (JSONObject) array.get(i);
				userNames.add((String) object.get("username"));
				selectedStudents.add(0);
				CheckBox check = new CheckBox(this);
				check.setText((String) object.get("firstName") + " " + (String) object.get("lastName") + " (" + userNames.get(i) + ")");
				check.setHeight(35);
				check.setTextSize(12);
				linearLayout.addView(check, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				
				

				check.setOnCheckedChangeListener(new OnCheckedChangeListener()
				{

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {
						if(arg1)
			            {
			                selectedStudents.set(index, 1);
			            }
			            else
			            {
			            	selectedStudents.set(index, 0);
			            }
						
					}
				});

			}
			
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		remove.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View currentView) 
			{
				error = false;
				boolean selected = false;
				for(int i =0; i<userNames.size(); i++)
				{
					if(selectedStudents.get(i)==1)
						selected = true;
				}
				
				if(!selected)
				{
					messageText.setText("No students selected!");
				}
				for(int i = 0; i<userNames.size(); i++)
				{
					final int index = i;
					
					if(selectedStudents.get(i)==1)
					{

						try {
							StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
							StrictMode.setThreadPolicy(policy);
							HttpClient defaultClient =  new DefaultHttpClient();
							HttpPost post = new HttpPost();
							
							String temp = "http://199.180.255.173/index.php/mobile/removeStudent/" + userNames.get(index) +"/" + section.getSectionID();
							
							post.setURI(new URI(temp));
							HttpResponse httpResponse = defaultClient.execute(post);
							BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
							String json = ""; 
							temp = "";
							
							while ((temp = reader.readLine()) != null)
							{
								json += temp;
							}
							
							if(json.equalsIgnoreCase("false"))
							{
															
							}
							if(json.equalsIgnoreCase("true"))
								error = true;
							
						}
						catch (Exception e)
						{
							System.out.println(e.getMessage());
						}
						
						Intent myIntent = new Intent(currentView.getContext(), StudentRosterActivity.class);
						myIntent.putExtra("USER_KEY", user);
						myIntent.putExtra("SECTION_KEY", section);
						myIntent.putExtra("RELOADED_KEY", true);
						myIntent.putExtra("ERROR_KEY", error);
						myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(myIntent);
						finish();
						
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.student_roster, menu);
		return true;
	}

}
