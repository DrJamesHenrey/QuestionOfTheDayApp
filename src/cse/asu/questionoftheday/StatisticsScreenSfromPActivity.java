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

import cse.asu.questionoftheday.model.StatQuestion;
import cse.asu.questionoftheday.model.User;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class StatisticsScreenSfromPActivity extends Activity {
	
	TextView questionTit;
	int userid;
	ArrayList<StatQuestion> questions;
	ArrayList<String> listOfSections;
	private Button questionButton;
	private Button statisticsButton;
	private Button menuButton;
	//this is here because git is dumb

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics_screen_s_from_p);
		
		questionTit = (TextView) findViewById(R.id.questionTit);

		
		final User user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		userid = user.getID();
		listOfSections = new ArrayList<String>(user.getListOfSections());
		
		
		
		////////////////////  code starts /////////////////////////////////////
		
		questionTit.setText("Statistics for Section "+listOfSections.get(0));
		
		initializeStatistics();
		populateButtons(questions, user);
	}
	
	private void initializeStatistics(){
		String prompt= "", answer= "", correct= "";
		int id =0;	
		questions = new ArrayList<StatQuestion>();
		
		try{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			HttpClient defaultClient =  new DefaultHttpClient();
			HttpPost post = new HttpPost();

			post.setURI(new URI("http://cse110.courses.asu.edu/index.php/mobile/getGradesforStudent/" + listOfSections.get(0) + "/"+userid)); 
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
				id = Integer.parseInt((String) object.get("questionID"));
				prompt = (String) object.get("prompt") + "";
				correct = (String) object.get("correct") + ""; 
				answer = (String) object.get("answer") + "";
				
				StatQuestion question = new StatQuestion(id, prompt, correct, answer);
				questions.add(question);		
			}
		}
		catch(Exception e){
			
		}
	}

	
	private void populateButtons(final ArrayList<StatQuestion> questions, final User user) {
		TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
			
		if(questions.size() == 0)
		{
			questionTit.setText("This student has not answered any question for this section.");
			questionTit.setGravity(Gravity.CENTER);
		}
		else{
			int correct = 0;
			for(int i =0; i < questions.size(); i++){
				if(Integer.parseInt(questions.get(i).getCorrect()) == 1){
					correct++;
				}
			}
			float percent = ((float)correct/(float)questions.size()*100);
			questionTit.setText("This student has answered: "+ correct +" out of "+ questions.size() + " questions correctly.\n Their grade is: " + percent +"%" );
			//questionTit.setTextSize(8);
		}
		
		for(int row = 0; row < questions.size(); row++)
		{
			final int index = row;
			
			TableRow tableRow = new TableRow(this);
			table.addView(tableRow);
			
			Button button = new Button(this);
			tableRow.addView(button);
			
			String temp;
			if(questions.get(row).getPrompt().length() >40)
			{
				if(Integer.parseInt(questions.get(row).getCorrect()) == 1){
					temp = "100%   " + questions.get(row).getPrompt().substring(0, 40) + "...";
				}
				else{
					temp = "0%   " + questions.get(row).getPrompt().substring(0, 40) + ".. .";
				}
			}
			else{
				if(Integer.parseInt(questions.get(row).getCorrect()) == 1){
					temp = "100%  " + questions.get(row).getPrompt();
				}
				else{
					temp = "0%  " + questions.get(row).getPrompt();
				}
			}
			
			button.setTextSize(8);
			button.setText(questions.get(row).getID() + ". " + temp);
			button.setGravity(Gravity.LEFT);
			button.setTextAppearance(this, R.style.Theme_Cse);
			
			button.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					Intent myIntent = new Intent(v.getContext(), SingleQuestionStatsActivity.class);
					myIntent.putExtra("USER_KEY", user);
					myIntent.putExtra("QUESTION_KEY", questions.get(index));
					startActivity(myIntent);
				}
			});
		}
		
	}
	

	@Override
	//////////THIS WILL NEED TO BE CHANGED TO BE FOR STATS
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.questions_screen, menu);
		return true;
	}

}
