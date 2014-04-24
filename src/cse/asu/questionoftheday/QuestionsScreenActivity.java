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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class QuestionsScreenActivity extends Activity {
	
	TextView questionTit;
	ArrayList<Question> questions;
	ArrayList<String> listOfSections;
	private Button questionButton;
	private Button statisticsButton;
	private Button menuButton;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions_screen);
		
		questionTit = (TextView) findViewById(R.id.questionTit);
		questionButton = (Button) findViewById(R.id.QuestionsButton);
		statisticsButton = (Button) findViewById(R.id.StatisticsButton);
		menuButton = (Button) findViewById(R.id.MenuButton);
		
		final User user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		listOfSections = new ArrayList<String>(user.getListOfSections());
		
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
		
		questionTit.setText("Section "+listOfSections.get(0) + " Questions:");
		
		initializeQuestions();
		populateButtons(questions, user);
	}

	private void initializeQuestions() {
		
		String topic= "", prompt= "", a= "", b= "", c= "", d= "", correct= "", hint= "", explanation= "";
		int id =0;	
		questions = new ArrayList<Question>();

		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			HttpClient defaultClient =  new DefaultHttpClient();
			HttpPost post = new HttpPost();			
			post.setURI(new URI("http://199.180.255.173/index.php/mobile/getSentQuestions/" + listOfSections.get(0)));
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
				id = Integer.parseInt((String) object.get("id"));
				topic = (String) object.get("topic") + "";
				prompt = (String) object.get("prompt") + "";
				a = (String) object.get("a") + "";	
				b = (String) object.get("b") + "";
				c = (String) object.get("c") + "";
				d = (String) object.get("d") + "";
				
				correct = (String) object.get("correct") + "";
				hint = (String) object.get("hint") + "";
				explanation = (String) object.get("explanation") + "";
				
				Question question = new Question(id, topic, prompt, a, b, c, d, correct, hint, explanation);
				questions.add(question);
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

	private void populateButtons(final ArrayList<Question> questions, final User user) {
		TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
			
		if(questions.size() == 0)
		{
			questionTit.setText("There aren't any questions that have been sent for this class. Please review later.");
			questionTit.setGravity(Gravity.CENTER);
		}
		
		for(int row = 0; row < questions.size(); row++)
		{
			final int index = row;
			
			TableRow tableRow = new TableRow(this);
			table.addView(tableRow);
			
			Button button = new Button(this);
			tableRow.addView(button);
			
			String temp;
			if(questions.get(row).getPrompt().length() >55)
			{
				temp = questions.get(row).getPrompt().substring(0, 55) + "...";
			}
			else
				temp = questions.get(row).getPrompt();
			
			button.setTextSize(8);
			button.setText(questions.get(row).getID() + ". " + temp);
			button.setGravity(Gravity.LEFT);
			button.setTextAppearance(this, R.style.Theme_Cse);
			
			button.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					Intent myIntent = new Intent(v.getContext(), AnswerPageActivity.class);
					myIntent.putExtra("USER_KEY", user);
					myIntent.putExtra("QUESTION_KEY", questions.get(index));
					startActivity(myIntent);
				}
			});
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.questions_screen, menu);
		return true;
	}

}
