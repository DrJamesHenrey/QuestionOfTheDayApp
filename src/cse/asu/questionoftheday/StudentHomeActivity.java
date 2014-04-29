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

import com.example.cse.asu.questionoftheday.MainActivity;
import com.example.cse.asu.questionoftheday.R;
import com.example.cse.asu.questionoftheday.R.layout;
import com.example.cse.asu.questionoftheday.R.menu;

import cse.asu.questionoftheday.model.Question;
import cse.asu.questionoftheday.model.User;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.*;


public class StudentHomeActivity extends Activity 
{
	//public User user;
	private Button questionButton;
	private Button statisticsButton;
	private Button menuButton;
	private ImageButton newQuestions;
	private TextView welcomeStudent, numberOfQ;
	private String note = "0";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_home);
		
		questionButton = (Button) findViewById(R.id.QuestionsButton);
		statisticsButton = (Button) findViewById(R.id.StatisticsButton);
		menuButton = (Button) findViewById(R.id.MenuButton);
		newQuestions = (ImageButton) findViewById(R.id.NewImage);
		welcomeStudent = (TextView) findViewById(R.id.welcomeStudent);
		numberOfQ = (TextView) findViewById(R.id.newQs);
		
		final User user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		ArrayList<String> listOfSections = new ArrayList<String>(user.getListOfSections());
		
		
		welcomeStudent.setText("Welcome " + user.getFirstName() +" " + user.getLastName()+ " to \nSection " + listOfSections.get(0) +" Question of the Day");
		
		
		questionButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View currentView) 
			{
				Intent myIntent = new Intent(currentView.getContext(), QuestionsScreenActivity.class);
				myIntent.putExtra("USER_KEY", user);
				startActivity(myIntent);
			}
		});
		
		statisticsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View currentView) 
			{
				Intent myIntent = new Intent(currentView.getContext(), StatisticsScreenActivity.class);
				myIntent.putExtra("USER_KEY", user);
				startActivity(myIntent);
			}
		});
		
		menuButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View currentView) 
			{
				Intent myIntent = new Intent(currentView.getContext(), SettingsScreenActivity.class);
				myIntent.putExtra("USER_KEY", user);
				startActivity(myIntent);
			}
		});
		
		
		
		newQuestions.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View currentView) {
				Intent myIntent = new Intent(currentView.getContext(), NewQuestions.class);
				myIntent.putExtra("USER_KEY", user);
				startActivity(myIntent);
			}
		}); 
		
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			HttpClient defaultClient =  new DefaultHttpClient();
			HttpPost post = new HttpPost();
			
			String temp1 = "http://cse110.courses.asu.edu/index.php/mobile/getNotAnsweredQuestions/" + user.getUsername() + "/" + listOfSections.get(0)+ "/0";
			
			post.setURI(new URI(temp1));
			HttpResponse httpResponse = defaultClient.execute(post);
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
			String json = ""; 
			String temp = "";
			
			while ((temp = reader.readLine()) != null)
			{
				json += temp;
			}
			
			numberOfQ.setText(json+"");
			numberOfQ.setTextColor(Color.WHITE);
			numberOfQ.setTypeface(null, Typeface.BOLD);
			
			if(!json.equals("0"))
			{
				note = json;
			}
			
			
		}
		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(),
                    "Error. Please be sure your device has service or is connected to the internet.",
                    Toast.LENGTH_LONG).show();
		}
		
		
		if(!note.equals("0"))
		{
			try {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
				HttpClient defaultClient =  new DefaultHttpClient();
				HttpPost post = new HttpPost();
				
				String temp1 = "http://cse110.courses.asu.edu/index.php/mobile/notifications/" + user.getUsername();
				
				post.setURI(new URI(temp1));
				HttpResponse httpResponse = defaultClient.execute(post);
				BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
				String json = ""; 
				String temp = "";
				
				while ((temp = reader.readLine()) != null)
				{
					json += temp;
				}

				if(json.equalsIgnoreCase("true"))
				{
					Notify(note + " New Question(s)",
						      "Answer now!");
				}
				
				
			}
			catch (Exception e)
			{
				Toast.makeText(getApplicationContext(),
	                    "Error. Please be sure your device has service or is connected to the internet.",
	                    Toast.LENGTH_LONG).show();
			}
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.student_home, menu);
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
	
	@SuppressWarnings("deprecation")
	  private void Notify(String notificationTitle, String notificationMessage) {
	   NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	   @SuppressWarnings("deprecation")
	   Notification notification = new Notification(R.drawable.ic_launcher,
	     "New Message", System.currentTimeMillis());

	    Intent notificationIntent = new Intent(this, LoginActivity.class);
	   PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
	     notificationIntent, 0);

	    notification.setLatestEventInfo(StudentHomeActivity.this, notificationTitle,
	     notificationMessage, pendingIntent);
	   notificationManager.notify(9999, notification);
	  }

}

