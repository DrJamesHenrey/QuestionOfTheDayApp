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
import com.example.cse.asu.questionoftheday.R.id;
import com.example.cse.asu.questionoftheday.R.layout;
import com.example.cse.asu.questionoftheday.R.menu;

import cse.asu.questionoftheday.GraphsActivity;
import cse.asu.questionoftheday.SearchQuestionActivity;
import cse.asu.questionoftheday.StatsByNameActivity;
import cse.asu.questionoftheday.StatsByTopicActivity;
import cse.asu.questionoftheday.StudentRosterActivity;
import cse.asu.questionoftheday.TAActivity;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchQuestionActivity extends Activity {

	LinearLayout topicLayout;
	ArrayList<String> topics;
	Section section;
	User user;
	TextView topicText;
	final Context context = this;
	ArrayList<String> studentlist;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_question);
		
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
		
		topicText.setText("All Topics");
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
				builder.setItems(new CharSequence[] {"Statistics By Student Name", "Statistics By Topic"}, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which){
						case 0:
							statByStudent(user, section);

							break;
						case 1:
							
							statByTopic(user, section);

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
					Intent myIntent = new Intent(v.getContext(), SearchTopicQuestions.class);
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
			post.setURI(new URI("http://cse110.courses.asu.edu/index.php/mobile/getAllTopics"));
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
		getMenuInflater().inflate(R.menu.professor_home, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.menulogout:
				
				Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
				//myIntent.putExtra("USER_KEY", user);
				startActivity(myIntent);
				finish(); //JON DONT REMOVE THIS. THIS WONT ALLOW STUDENT TO GO BACK TO LOGIN
				break;
			
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void statByStudent( User user, Section section){
///////////////////////////////////////


final Context context = this;
final User user2 = user;
final Section section2 = section;




try{
studentlist = new ArrayList<String>();
StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
StrictMode.setThreadPolicy(policy);
HttpClient defaultClient =  new DefaultHttpClient();
HttpPost post = new HttpPost();

post.setURI(new URI("http://cse110.courses.asu.edu/index.php/mobile/getRoster/" +  section2.getSectionID())); 
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
String s ="";
JSONObject object = (JSONObject) array.get(i);
s = (String) object.get("username") + "";


studentlist.add(s);		
}
}
catch(Exception e){

}
AlertDialog.Builder builder = new AlertDialog.Builder(context);
builder.setTitle("Students in Course");
final CharSequence[] users = studentlist.toArray(new CharSequence[studentlist.size()]);


builder.setItems(users, new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {


Intent myIntent1 = new Intent(context, StatisticsScreenSfromPActivity.class);
//myIntent1.putExtra("USER_KEY", user2);
myIntent1.putExtra("SECTION_KEY", section2);

User student = new User();
String username = "", firstName = "", lastName = "", eMail ="";
int id = 0;
ArrayList<String> listOfSections = new ArrayList<String>();

try
{

StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
StrictMode.setThreadPolicy(policy);
HttpClient defaultClient =  new DefaultHttpClient();
HttpPost post = new HttpPost();
System.out.println(studentlist.get(which));
post.setURI(new URI("http://cse110.courses.asu.edu/index.php/mobile/getUserInfo/" + studentlist.get(which)));
HttpResponse httpResponse = defaultClient.execute(post);
BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
String json = ""; 
String temp = "";

while ((temp = reader.readLine()) != null)
{
json += temp;
}

json = json.substring(1, json.length()-1);

JSONArray array = new JSONArray(json);

for(int i =0; i< array.length(); i++)
{
JSONObject object = (JSONObject) array.get(i);

listOfSections.add((String) object.get("sectionID"));
if(i==0)
{
	firstName = (String) object.get("firstName");
	lastName = (String) object.get("lastName");
	username = (String) object.get("username");
	eMail = (String) object.get("email");
	id = Integer.parseInt((String) object.get("userID"));
}
}
student = new User(id, username, firstName, lastName, eMail, 1, listOfSections);						


}

catch (Exception e)
{
System.out.println(e.getMessage());
}


myIntent1.putExtra("USER_KEY", student);
startActivity(myIntent1);

}
});
builder.create().show();


//////////////////////////////////////////////;
}

public void statByTopic( User user, Section section){
///////////////////////////////////////


///////////////////////////////////////


final Context context = this;
final Section section2 = section;
final User user2 = user;
final ArrayList<String> topiclist;
topiclist = new ArrayList<String>();



try{

StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
StrictMode.setThreadPolicy(policy);
HttpClient defaultClient =  new DefaultHttpClient();
HttpPost post = new HttpPost();


post.setURI(new URI("http://cse110.courses.asu.edu/index.php/mobile/getTopics/" +  section2.getSectionID())); 
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
String s ="";
JSONObject object = (JSONObject) array.get(i);
s = (String) object.get("topic") + "";


topiclist.add(s);		
}
}
catch(Exception e){

}
AlertDialog.Builder builder = new AlertDialog.Builder(context);
builder.setTitle("Topics in Course");

final CharSequence[] topicss = topiclist.toArray(new CharSequence[topiclist.size()]);


builder.setItems(topicss, new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {


Intent myIntent1 = new Intent(context, StatsByTopicActivity.class);
myIntent1.putExtra("USER_KEY", user2);
myIntent1.putExtra("SECTION_KEY", section2);
System.out.println(topiclist.get(which));
myIntent1.putExtra("TOPIC_KEY", topiclist.get(which));
startActivity(myIntent1);

}
});
builder.create().show();


//////////////////////////////////////////////;
}

}

