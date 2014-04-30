package cse.asu.questionoftheday;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
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

import cse.asu.questionoftheday.model.Section;
import cse.asu.questionoftheday.model.User;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.util.Log;
import android.view.*;
import android.widget.*;

public class RegisterUserActivity extends Activity {
	
	// Declare all controls
	private Button cancelButton;
	private Button registerButton;
	private EditText firstNameField;
	private EditText userNameField;
	private EditText lastNameField;
	private EditText emailField;
	private EditText passwordField;
	private EditText confirmField;
	private Spinner sectionSpinner;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_user);

		// Initialize all controls
		cancelButton = (Button) findViewById(R.id.btnCancel);
		registerButton = (Button) findViewById(R.id.btnRegister);
		firstNameField = (EditText) findViewById(R.id.txtFName);
		lastNameField = (EditText) findViewById(R.id.txtLName);
		userNameField = (EditText) findViewById(R.id.txtUsername);
		emailField = (EditText) findViewById(R.id.txtEmail);
		passwordField = (EditText) findViewById(R.id.txtPassword);
		confirmField = (EditText) findViewById(R.id.txtConfirm);
		sectionSpinner = (Spinner) findViewById(R.id.spnSection);
		
		// Arraylist of sections
		final ArrayList<Section> sections = new ArrayList<Section>();
		
		// Retrieve sections that are available
		try
		{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			HttpClient defaultClient =  new DefaultHttpClient();
			HttpPost post = new HttpPost();
			
			String temp1 = "http://cse110.courses.asu.edu/index.php/mobile/getSections/";
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
			
			Log.w("json", json);
			
			JSONArray array = new JSONArray(json);
			
			for(int i =0; i < array.length(); i++)
			{
				JSONObject object = (JSONObject) array.get(i);
				String sectionID = (String) object.get("sectionID");
				String courseID = (String) object.get("courseID");
				//int professorID = Integer.parseInt((String) object.get("professorID"));
				int professorID = 6;
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
				String title = (String) object.get("name");
				Section section = new Section(sectionID, courseID, professorID, sendToProf, timeLimit, semester, sendingQuestions, canDrop, meetMonday, meetTuesday, meetWednesday, meetThursday, meetFriday, meetSaturday, meetSunday, time, title);
				sections.add(section);
			}
			
			// Populate spinner
			ArrayList<String> spinnerArray = new ArrayList<String>();
			
			for(int i=0; i < sections.size(); i++)
			{
				String str = "";
				
				if(sections.get(i).getMeetMonday() == 1)
				{
					str += "M";
				}
				if(sections.get(i).getMeetTuesday() == 1)
				{
					str += "T";
				}
				if(sections.get(i).getMeetWednesday() == 1)
				{
					str +="W";
				}
				if(sections.get(i).getMeetThursday() == 1)
				{
					str += "Th";
				}
				if(sections.get(i).getMeetFriday() == 1)
				{
					str += "F";
				}
				if(sections.get(i).getMeetSaturday() == 1)
				{
					str += "S";
				}
				if(sections.get(i).getMeetSunday() == 1)
				{
					str += "Su";
				}
				
				//str += " " + sections.get(i).getTime() + "(" + sections.get(i).getProfessorID() + ")";
				str += " " + sections.get(i).getTime() + " (navabi)";
				
				spinnerArray.add(str);
			}
			
			ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, 
					android.R.layout.simple_spinner_dropdown_item, spinnerArray);
			
			sectionSpinner.setAdapter(spinnerArrayAdapter);
		}
		
		catch (Exception e)
		{
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
			 
			// set title
			alertDialogBuilder.setTitle("Connection Error");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Please check your internet connection and try again")
				.setCancelable(false)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						
					}
				  });
 
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				alertDialog.show();
		}
		
		
		
		
		
		cancelButton.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View currentView)
        	{
        		// Go back to main login activity
        		Intent myIntent = new Intent(currentView.getContext(), LoginActivity.class);
				startActivity(myIntent);
				finish();
        	}
        });
		
		registerButton.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View currentView)
        	{
        		// Make sure no fields are blank..
        		if((firstNameField.getText().equals("")) || (lastNameField.getText().equals("")||
        				emailField.getText().equals("")) || (passwordField.getText().equals(""))||
        				confirmField.getText().equals(""))
        		{
        			Toast toast = Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT);
					toast.show();
        		}
        		else
        		{
        			try
        			{
        				// Register stuff!
            			String username = userNameField.getText().toString();
            			String firstname = firstNameField.getText().toString();
            			String lastname = lastNameField.getText().toString();
            			String email = emailField.getText().toString().replaceAll("@", "%90");
            			String password = passwordField.getText().toString();
            			String confirmpass = confirmField.getText().toString();
            			String section = sections.get(sectionSpinner.getSelectedItemPosition()).getSectionID();
            			
            			
                		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        				StrictMode.setThreadPolicy(policy);
        				HttpClient defaultClient =  new DefaultHttpClient();
        				HttpPost post = new HttpPost();
        				
        				// Need to add section to the end
        				String URL = "http://cse110.courses.asu.edu/index.php/mobile/createUser/" + username + "/" + firstname + 
        						"/" + lastname + "/" + email + "/" + password + "/" + confirmpass  + "/" + section;
        				Log.w("url", URL);
        				
        				post.setURI(new URI(URL));
        				HttpResponse httpResponse = defaultClient.execute(post);
        				BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
        				String json = ""; 
        				String temp = "";
        				
        				while ((temp = reader.readLine()) != null)
        				{
        					json += temp;
        				}
        				
        				// If it was created, go to login screen
        				if(json.equalsIgnoreCase("account created"))
        				{
        					Toast toast = Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_LONG);
        					toast.show();
        					
        	        		Intent myIntent = new Intent(currentView.getContext(), LoginActivity.class);
        					startActivity(myIntent);
        					finish();	
        				}
        				// Display whatever error that was returned
        				else
        				{
        					Toast toast = Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT);
        					toast.show();
        				}
        			}
        			catch (Exception e)
        			{
        				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(currentView.getContext());
				 
							// set title
							alertDialogBuilder.setTitle("Connection Error");
				 
							// set dialog message
							alertDialogBuilder
								.setMessage("Please check your internet connection and try again")
								.setCancelable(false)
								.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										
									}
								  });
				 
								AlertDialog alertDialog = alertDialogBuilder.create();
				 
								alertDialog.show();
        			}
        		
        		}
        		
        	}
        });
		
	}

}
