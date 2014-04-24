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


public class ForgotPasswordActivity extends Activity
{
	private Button resetButton;
	private EditText emailAddress;
	
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        
        resetButton = (Button) findViewById(R.id.btnResetPassword);
        emailAddress = (EditText) findViewById(R.id.txtResetEmail);		
        
        resetButton.setOnClickListener(new View.OnClickListener() 
        {
			
			@Override
			public void onClick(View v) 
			{
				// Check if email address field was blank
				String email = emailAddress.getText().toString();
				
				if(email.equals(""))
				{
					Toast toast = Toast.makeText(getApplicationContext(), "Please enter an email address", Toast.LENGTH_SHORT);
					toast.show();
				}
				else
				{
					try 
					{
						StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
						StrictMode.setThreadPolicy(policy);
						HttpClient defaultClient =  new DefaultHttpClient();
						HttpPost post = new HttpPost();					
						post.setURI(new URI("http://199.180.255.173/index.php/mobile/requestReset/" + email));
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
			                        "User with Email does not exist.", Toast.LENGTH_LONG).show();	
						}
						else // It matched!
						{
							Toast.makeText(getApplicationContext(),
			                        "Sent email with temporary password", Toast.LENGTH_LONG).show();
							Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
							//myIntent.putExtra("USER_KEY", user);
							startActivity(myIntent);
							finish();
						}
					}
					catch (Exception e)
					{
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
				 
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
