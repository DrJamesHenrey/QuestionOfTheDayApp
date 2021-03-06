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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassWordActivity extends Activity {

	private Button questionButton;
	private Button statisticsButton;
	private Button menuButton;
	private Button save;
	private String cPassword, newPassword1, newPassword2;
	private EditText cPassw, newPassw1, newPassw2;
	private boolean correctCurrentPword;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pass_word);
		
		questionButton = (Button) findViewById(R.id.QuestionsButton);
		statisticsButton = (Button) findViewById(R.id.StatisticsButton);
		menuButton = (Button) findViewById(R.id.MenuButton);
		save = (Button) findViewById(R.id.save);
		
		cPassw = (EditText) findViewById(R.id.UserNameTextField);
		newPassw1 = (EditText) findViewById(R.id.PasswordTextField);
		newPassw2 = (EditText) findViewById(R.id.txtEmail);
		
		final User user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		ArrayList<String> listOfSections = new ArrayList<String>(user.getListOfSections());
		
		
		
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View currentView) 
			{
				cPassword = cPassw.getText().toString();
				newPassword1 = newPassw1.getText().toString();
				newPassword2 = newPassw2.getText().toString();

				
				try {
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy);
					HttpClient defaultClient =  new DefaultHttpClient();
					HttpPost post = new HttpPost();
					
					String temp1 = "http://cse110.courses.asu.edu/index.php/mobile/getPassword/" + user.getUsername()+ "/" + cPassword;
					
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
						correctCurrentPword = false;
						
					}
					else
					{
						correctCurrentPword= true;
					}
					
					if(newPassword1.equals(newPassword2))
					{
						if(correctCurrentPword == false)
						{
							Toast.makeText(getApplicationContext(),
			                        "Current password incorrect. Please re-enter current password and try again.",
			                        Toast.LENGTH_LONG).show();
						}
						else if(newPassword1.equals(cPassword))
						{
							Toast.makeText(getApplicationContext(),
			                        "Current password is the same as new password. Changes not saved.",
			                        Toast.LENGTH_LONG).show();
						}
						else
						{
							temp1 = "http://cse110.courses.asu.edu/index.php/mobile/updatePassword/" + user.getUsername()+ "/" + newPassword1;
							
							post.setURI(new URI(temp1));
							httpResponse = defaultClient.execute(post);
							reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
							json = ""; 
							temp = "";
							
							while ((temp = reader.readLine()) != null)
							{
								json += temp;
							}
							
							if(json.equalsIgnoreCase("true"))
							{
								Toast.makeText(getApplicationContext(),
				                        "Password changed.",
				                        Toast.LENGTH_LONG).show();
								
							}
							
							
						}
						
					}
					else
					{
						Toast.makeText(getApplicationContext(),
		                        "New passwords do not match. Change not saved. Please re-enter new password and try again.",
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
		getMenuInflater().inflate(R.menu.change_pass_word, menu);
		return true;
	}

}
