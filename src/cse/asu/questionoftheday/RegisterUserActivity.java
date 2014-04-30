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
		
		// Retrieve sections that are available
		// .......
		
		
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
            			//String section = ** ADD CODE TO GET SECTION ID ** 
            			
            			
                		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        				StrictMode.setThreadPolicy(policy);
        				HttpClient defaultClient =  new DefaultHttpClient();
        				HttpPost post = new HttpPost();
        				
        				// Need to add section to the end
        				String URL = "http://cse110.courses.asu.edu/index.php/mobile/createUser" + username + "/" + firstname + 
        						"/" + lastname + "/" + email + "/" + password + "/" + confirmpass /* + "/" + section*/;
        				
        				/*post.setURI(new URI(URL));
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
        				}*/
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
