// HAPY DA BIRDAY TU YU

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
import com.example.cse.asu.questionoftheday.R.id;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class ProfessorHomeActivity extends Activity {

	User user;
	ArrayList<String> listOfSections;
	ArrayList<String> coursesForProfessor;
	final Context context = this;
	private TextView profText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_professor_home);
		
		profText = (TextView) findViewById(R.id.ProfessorText);
		
		profText.setText("My Courses");
		profText.setTextSize(24);
		
		user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		listOfSections = new ArrayList<String>(user.getListOfSections());
		
		initializeCourses();
		pupulateSections();
	}

	private void pupulateSections() {
		TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
		
		if(coursesForProfessor.size() == 0)
		{
			profText.setText("You currently do not teach any courses/sections");
			profText.setGravity(Gravity.CENTER);
		}
		
		for(int index = 0; index < coursesForProfessor.size(); index ++)
		{	
			final int numar = index;
			final ArrayList<Section> sections = new ArrayList<Section>();
			
			TableRow tableRow = new TableRow(this);
			table.addView(tableRow);
			
			TextView text = new TextView(this);
			tableRow.addView(text);
			
			text.setText(coursesForProfessor.get(index));
			text.setTextSize(18);
			text.setGravity(Gravity.LEFT);
			
			try {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
				HttpClient defaultClient =  new DefaultHttpClient();
				HttpPost post = new HttpPost();
				
				String temp1 = "http://cse110.courses.asu.edu/index.php/mobile/getSectionsForCourse/" + coursesForProfessor.get(index);
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
				JSONArray array = new JSONArray(json);
			
				for(int i =0; i< array.length(); i++)
				{
					JSONObject object = (JSONObject) array.get(i);
					String sectionID = (String) object.get("sectionID");
					String courseID = (String) object.get("courseID");
					int professorID = Integer.parseInt((String) object.get("professorID"));
					
					int sendToProf = Integer.parseInt((String) object.get("sendToProf"));
					int timeLimit = Integer.parseInt((String) object.get("timeLimit"));
					String semester = (String) object.get("semester");
					int sendingQuestions = Integer.parseInt((String) object.get("sendingQuestions"));
					int canDrop = Integer.parseInt((String) object.get("canDrop"));
					int meetMonday = Integer.parseInt((String) object.get("meetMonday"));
					int meetTuesday = Integer.parseInt((String) object.get("meetTuesday"));
					int meetWednesday = Integer.parseInt((String) object.get("meetWednesday"));
					int meetThursday = Integer.parseInt((String) object.get("meetThursday"));
					int meetFriday = Integer.parseInt((String) object.get("meetFriday"));
					int meetSaturday = Integer.parseInt((String) object.get("meetSaturday"));
					int meetSunday = Integer.parseInt((String) object.get("meetSunday"));
					String time = (String) object.get("time");
					String title = (String) object.get("title");
					Section section = new Section(sectionID, courseID, professorID, sendToProf, timeLimit, semester, sendingQuestions, canDrop, meetMonday, meetTuesday, meetWednesday, meetThursday, meetFriday, meetSaturday, meetSunday, time, title, "");
					sections.add(section);
					
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
			
			
			for(int t = 0; t<sections.size(); t++)
			{
				final int inex = t;
				TableRow tableRow2 = new TableRow(this);
				table.addView(tableRow2);
				
				Button button = new Button(this);
				tableRow2.addView(button);
				
				button.setTextSize(14);
				button.setText("                  Section " + sections.get(t).getSectionID() + "                    ");
				button.setGravity(Gravity.LEFT);
				button.setTextAppearance(this, R.style.Theme_Cse);
				
				button.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						Intent myIntent = new Intent(v.getContext(), SectionHomeActivity.class);
						myIntent.putExtra("USER_KEY", user);
						myIntent.putExtra("SECTION_KEY", sections.get(inex));
						startActivity(myIntent);
					}
				});
			}
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
	
private void initializeCourses() {
		
		coursesForProfessor = new ArrayList<String>();

		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			HttpClient defaultClient =  new DefaultHttpClient();
			HttpPost post = new HttpPost();
			post.setURI(new URI("http://cse110.courses.asu.edu/index.php/mobile/getCoursesForProfessor/" + user.getID()));
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
				
				coursesForProfessor.add((String) object.get("courseID") );
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

}