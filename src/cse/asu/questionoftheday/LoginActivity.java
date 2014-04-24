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

public class LoginActivity extends Activity {

	private Button loginButton;
	private Button newUserButton;
	private Button forgotPasswordButton;
	private EditText usernameTextField;
	private EditText passwordTextField;
	final Context context = this;
	public User user;
	ArrayList<String> listOfSections;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        loginButton = (Button) findViewById(R.id.LoginButton);
        newUserButton = (Button) findViewById(R.id.NewUserButton);
        forgotPasswordButton = (Button) findViewById(R.id.ForgotPasswordButton);
        usernameTextField = (EditText) findViewById(R.id.UserNameTextField);
        passwordTextField = (EditText) findViewById(R.id.PasswordTextField);
        
        //final TextView text = (TextView) findViewById(R.id.Text); Killed the "hello world label on the top" - jabach
        
        forgotPasswordButton.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View currentView)
        	{
        		
        	}
        });
        
        
        newUserButton.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View currentView)
        	{
        		
        	}
        });
        
        
        loginButton.setOnClickListener(new View.OnClickListener() 
        {
			
			@Override
			public void onClick(View currentView) {
				
				String username = usernameTextField.getText().toString(), firstName = "", lastName = "", eMail = "";
				String password = passwordTextField.getText().toString();
				
				int userType=0, id=0;
				listOfSections = new ArrayList<String>();
				
				if((username.equals("")) || (password.equals("")))
				{
					Toast toast = Toast.makeText(getApplicationContext(), "Please enter both fields", Toast.LENGTH_SHORT);
					toast.show();	
				}
				else
				{
				
					// Verify the password entered
					try 
					{
						StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
						StrictMode.setThreadPolicy(policy);
						HttpClient defaultClient =  new DefaultHttpClient();
						HttpPost post = new HttpPost();					
						post.setURI(new URI("http://199.180.255.173/index.php/mobile/getPassword/" + username + "/" + password));
						HttpResponse httpResponse = defaultClient.execute(post);
						BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
						String json = ""; 
						String temp = "";
						
						while ((temp = reader.readLine()) != null)
						{
							json += temp;
						}
						
						// If password doesn't match..
						if(json.equalsIgnoreCase("false"))
						{
							Toast.makeText(getApplicationContext(),
			                        "Username or Password is incorrect.", Toast.LENGTH_LONG).show();	
						}
						
						else // It matched, now grab all the user info, and jump into the next screen
						{
							
								policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
							
								StrictMode.setThreadPolicy(policy);
								defaultClient =  new DefaultHttpClient();
								post = new HttpPost();					
								post.setURI(new URI("http://199.180.255.173/index.php/mobile/getUserInfo/" + username));
								httpResponse = defaultClient.execute(post);
								reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
								json = ""; 
								temp = "";
								
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
								
								post.setURI(new URI("http://199.180.255.173/index.php/mobile/isProfessor/" + username));
								HttpResponse htpR = defaultClient.execute(post);
								BufferedReader read = new BufferedReader(new InputStreamReader(htpR.getEntity().getContent(), "UTF-8"));
								json = ""; 
								temp = "";
								
								while ((temp = read.readLine()) != null)
								{
									json += temp;
								}
								if(json.equalsIgnoreCase("false"))
								{
									userType = 1;
									
								}
								if(json.equalsIgnoreCase("true"))
									userType = 2;
						
							
							User user = new User(id, username, firstName, lastName, eMail, userType, listOfSections);
							
							if(user.getUserType() == 1)
							{
								Intent myIntent = new Intent(currentView.getContext(), StudentHomeActivity.class);
								myIntent.putExtra("USER_KEY", user);
								startActivity(myIntent);
								finish(); //JON DONT REMOVE THIS. THIS WONT ALLOW STUDENT TO GO BACK TO LOGIN
							}
							if(user.getUserType() == 2)
							{
								Intent myIntent = new Intent(currentView.getContext(), ProfessorHomeActivity.class);
								myIntent.putExtra("USER_KEY", user);
								startActivity(myIntent);
								finish(); //JON DONT REMOVE THIS. THIS WONT ALLOW STUDENT TO GO BACK TO LOGIN
							}
							
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
										
									}
								  });
				 
								AlertDialog alertDialog = alertDialogBuilder.create();
				 
								alertDialog.show();
					}
				}
								
			}
		});
    }
   


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }

}
