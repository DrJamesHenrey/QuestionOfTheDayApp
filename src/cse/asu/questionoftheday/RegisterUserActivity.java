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
	
	private Button cancelButton;
	private Button registerButton;
	private EditText firstNameField;
	private EditText lastNameField;
	private EditText emailField;
	private EditText passwordField;
	private EditText confirmField;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_user);

		cancelButton = (Button) findViewById(R.id.btnCancel);
		registerButton = (Button) findViewById(R.id.btnRegister);
		firstNameField = (EditText) findViewById(R.id.txtFName);
		lastNameField = (EditText) findViewById(R.id.txtLName);
		emailField = (EditText) findViewById(R.id.txtEmail);
		passwordField = (EditText) findViewById(R.id.txtPassword);
		confirmField = (EditText) findViewById(R.id.txtConfirm);
		
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
        		// Register stuff!
        	}
        });
		
	}

}
