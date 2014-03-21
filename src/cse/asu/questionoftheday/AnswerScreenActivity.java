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
import com.example.cse.asu.questionoftheday.R.layout;
import com.example.cse.asu.questionoftheday.R.menu;

import cse.asu.questionoftheday.model.Question;
import cse.asu.questionoftheday.model.User;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AnswerScreenActivity extends Activity {

	TextView answerText, explanation;
	String answer;
	User user;
	Question question;
	ArrayList<String> listOfSections;
	Button hButton, rButton;
	int correct;
	ToggleButton showExplanation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answer_screen);

		explanation = (TextView) findViewById(R.id.expText);
		answerText = (TextView) findViewById(R.id.AnswerText);
		rButton = (Button) findViewById(R.id.retryB);
		hButton = (Button) findViewById(R.id.RemoveButton);
		showExplanation = (ToggleButton) findViewById(R.id.explanationButton);
		
		user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		listOfSections = new ArrayList<String>(user.getListOfSections());

		question = (Question) getIntent().getExtras().getParcelable("QUESTION_KEY");
		answer = (String) getIntent().getExtras().getString("ANSWER");
		
		if(answer.equalsIgnoreCase(question.getCorrect()))
		{
			correct = 1;
		}
		else
			correct = 0;
		
		dealWithAnswer();
		
		showExplanation.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if(isChecked)
				{
					explanation.setText(question.getExplanation());
				}
				else
				{
					explanation.setText("");
				}
			}
		});
		
	}

	private void dealWithAnswer() {
		
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			HttpClient defaultClient =  new DefaultHttpClient();
			HttpPost post = new HttpPost();
			String temp = "http://199.180.255.173/index.php/mobile/dealWithAnswer/" + user.getID() + "/" + listOfSections.get(0) + "/" + question.getID() + "/" + answer + "/" + correct;
			
			post.setURI(new URI(temp));
			HttpResponse httpResponse = defaultClient.execute(post);
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
			String json = ""; 
			temp = "";
			
			while ((temp = reader.readLine()) != null)
			{
				json += temp;
			}
			
			json = json.substring(1, json.length()-1);
			answerText.setText(json);
			
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}	
		
		
		if(answerText.getText().charAt(0) == 'Y')
		{
			rButton.setVisibility(View.INVISIBLE);
		}
		if(answerText.getText().charAt(0) == 'A')
		{
			if(answer.equalsIgnoreCase(question.getCorrect()))
			{
				answerText.setText("You answered Correctly!");
				rButton.setVisibility(View.INVISIBLE);
			}
			else
			{
				answerText.setText("You answered incorrectly! You may retry.");
				
			}
		}
		if(answerText.getText().charAt(0) == 'E')
		{
			rButton.setVisibility(View.INVISIBLE);
			showExplanation.setVisibility(View.INVISIBLE);
		}
		if(answerText.getText().charAt(0) == 'T')
		{
			showExplanation.setVisibility(View.INVISIBLE);
			rButton.setVisibility(View.INVISIBLE);
		}
		
		rButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View currentView) 
			{
				Intent myIntent = new Intent(currentView.getContext(), AnswerPageActivity.class);
				myIntent.putExtra("USER_KEY", user);
				myIntent.putExtra("QUESTION_KEY", question);
				startActivity(myIntent);
				finish();
			}
		});
		
		hButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View currentView) 
			{
				Intent myIntent = new Intent(currentView.getContext(), StudentHomeActivity.class);
				myIntent.putExtra("USER_KEY", user);
				startActivity(myIntent);
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.answer_screen, menu);
		return true;
	}

}
