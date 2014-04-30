package cse.asu.questionoftheday; 

import java.io.BufferedReader; 
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.cse.asu.questionoftheday.R;
import com.example.cse.asu.questionoftheday.R.layout;
import com.example.cse.asu.questionoftheday.R.menu;

import cse.asu.questionoftheday.model.User;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;




public class SettingsScreenActivity extends Activity {
	
	private Button questionButton;
	private Button statisticsButton;
	private Button menuButton;
	private Button changePassword;
	private CheckBox notifications;
	private CheckBox emails;
	//private boolean getsEmails;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_screen);
		
		
		
		questionButton = (Button) findViewById(R.id.QuestionsButton);
		statisticsButton = (Button) findViewById(R.id.StatisticsButton);
		menuButton = (Button) findViewById(R.id.MenuButton);
		changePassword = (Button) findViewById(R.id.password);
		notifications = (CheckBox) findViewById(R.id.note);
		emails = (CheckBox) findViewById(R.id.email);
		
		final User user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		ArrayList<String> listOfSections = new ArrayList<String>(user.getListOfSections());
		
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			HttpClient defaultClient =  new DefaultHttpClient();
			HttpPost post = new HttpPost();
			
			String temp1 = "http://cse110.courses.asu.edu/index.php/mobile/emailss/" + user.getUsername();
			
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
				//getsEmails = false;
				emails.setChecked(false);
				
			}
			else if(json.equalsIgnoreCase("true"))
			{
				//getsEmails = true;
				emails.setChecked(true);
			}
			
			
		}
		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(),
                    "Error. Please be sure your device has service or is connected to the internet.",
                    Toast.LENGTH_LONG).show();
		}
		
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

			if(json.equalsIgnoreCase("false"))
			{
				//getsEmails = false;
				notifications.setChecked(false);
				
			}
			else if(json.equalsIgnoreCase("true"))
			{
				//getsEmails = true;
				notifications.setChecked(true);
			}
			
			
		}
		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(),
                    "Error. Please be sure your device has service or is connected to the internet.",
                    Toast.LENGTH_LONG).show();
		}
		
		

			notifications.setOnClickListener(new OnClickListener() {

			      @Override
			      public void onClick(View v) {
			                
			        if (((CheckBox) v).isChecked()) 
			        {
			        	
			        	try {
			    			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			    			StrictMode.setThreadPolicy(policy);
			    			HttpClient defaultClient =  new DefaultHttpClient();
			    			HttpPost post = new HttpPost();
			    			int n = 1;
			    			String temp1 = "http://cse110.courses.asu.edu/index.php/mobile/changeNoteSettings/" + user.getUsername() + "/" + n;
			    			
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
			    			else if (json.equalsIgnoreCase("true"))
			    			{
			    				
			    				Toast.makeText(getApplicationContext(),
				                        "You will recieve notifications when a new question is sent.",
				                        Toast.LENGTH_LONG).show();
			    			}


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
			        	try {
			    			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			    			StrictMode.setThreadPolicy(policy);
			    			HttpClient defaultClient =  new DefaultHttpClient();
			    			HttpPost post = new HttpPost();
			    			int n = 0;
			    			String temp1 = "http://cse110.courses.asu.edu/index.php/mobile/changeNoteSettings/" + user.getUsername() + "/" + n;
			    			
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
				                        "You will not recieve notifications when a new question is sent.",
				                        Toast.LENGTH_LONG).show();
			    				
			    			}
			    			else if(json.equalsIgnoreCase("true"))
			    			{
			    				Toast.makeText(getApplicationContext(),
				                        "Error",
				                        Toast.LENGTH_LONG).show();
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
			    });
		
		emails.setOnClickListener(new OnClickListener() {

		      @Override
		      public void onClick(View v) {
		                
		        if (((CheckBox) v).isChecked()) 
		        {
		        	
		        	try {
		    			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    			StrictMode.setThreadPolicy(policy);
		    			HttpClient defaultClient =  new DefaultHttpClient();
		    			HttpPost post = new HttpPost();
		    			int n = 1;
		    			String temp1 = "http://cse110.courses.asu.edu/index.php/mobile/changeEmailSettings/" + user.getUsername() + "/" + n;
		    			
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
		    			else if (json.equalsIgnoreCase("true"))
		    			{
		    				
		    				Toast.makeText(getApplicationContext(),
			                        "You will recieve emails when a new question is sent.",
			                        Toast.LENGTH_LONG).show();
		    			}


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
		        	try {
		    			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    			StrictMode.setThreadPolicy(policy);
		    			HttpClient defaultClient =  new DefaultHttpClient();
		    			HttpPost post = new HttpPost();
		    			int n = 0;
		    			String temp1 = "http://cse110.courses.asu.edu/index.php/mobile/changeEmailSettings/" + user.getUsername() + "/" + n;
		    			
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
			                        "You will not recieve emails when a new question is sent.",
			                        Toast.LENGTH_LONG).show();
		    				
		    			}
		    			else if(json.equalsIgnoreCase("true"))
		    			{
		    				Toast.makeText(getApplicationContext(),
			                        "Error",
			                        Toast.LENGTH_LONG).show();
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
		    });
		
		changePassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View currentView) 
			{
				Intent myIntent = new Intent(currentView.getContext(), ChangePassWordActivity.class);
				myIntent.putExtra("USER_KEY", user);
				startActivity(myIntent);
			}
		});
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
	


}

