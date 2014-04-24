package cse.asu.questionoftheday;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.cse.asu.questionoftheday.R;
import com.example.cse.asu.questionoftheday.R.id;
import com.example.cse.asu.questionoftheday.R.layout;
import com.example.cse.asu.questionoftheday.R.menu;

import cse.asu.questionoftheday.GraphsActivity;
import cse.asu.questionoftheday.StatsByTopicActivity;
import cse.asu.questionoftheday.StudentRosterActivity;
import cse.asu.questionoftheday.TAActivity;
import cse.asu.questionoftheday.model.Section;
import cse.asu.questionoftheday.model.StatQuestion;
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

public class StatsByTopicActivity extends Activity {
	TextView questionTit;
	ArrayList<StatQuestion> questions;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats_by_topic);
		setTitle("Question of the Day");
		questionTit = (TextView) findViewById(R.id.questionTit);
		
		

		final Context context = this;
	
		final Section section = (Section) getIntent().getExtras().getParcelable("SECTION_KEY");
		final String topic =  getIntent().getExtras().getString("TOPIC_KEY");
		final User user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		ArrayList<String> listOfSections = new ArrayList<String>(user.getListOfSections());

		questionTit.setText("Questions in Section: " + section.getCourseID() + " \n In Topic: " + topic);
		


		
			
	
	
	initializeStatistics();
	populateButtons(questions, user);
}

private void initializeStatistics(){
	
	final Section section = (Section) getIntent().getExtras().getParcelable("SECTION_KEY");
	final String topic =  getIntent().getExtras().getString("TOPIC_KEY");
	final User user = (User) getIntent().getExtras().getParcelable("USER_KEY");
	ArrayList<String> listOfSections = new ArrayList<String>(user.getListOfSections());
	

	
	String prompt= "", dateSent ="", answer= "", correct= "";
	int id =0;	
	questions = new ArrayList<StatQuestion>();
	ArrayList<String> dateSentArray = new ArrayList<String>();
	
	try{
		System.out.println("1");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		HttpClient defaultClient =  new DefaultHttpClient();
		HttpPost post = new HttpPost();
		
		System.out.println("2");

		System.out.println("http://199.180.255.173/index.php/mobile/getQuestionStatsByTopic/" + topic + "/"+section.getSectionID() + "/" + user.getID());
		post.setURI(new URI("http://199.180.255.173/index.php/mobile/getQuestionStatsByTopic/" + topic + "/"+section.getSectionID() + "/" + user.getID())); 
		HttpResponse httpResponse = defaultClient.execute(post);
		BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
		String json = ""; 
		String temp = "";
		
		
		while ((temp = reader.readLine()) != null)
		{
			json += temp;
		}
		
		System.out.println("3");
		System.out.println(json);
		JSONArray array = new JSONArray(json);
		System.out.println("behind json");
		System.out.println(array.length());
		for(int i =0; i< array.length(); i++)
		{
			System.out.println("3.1");
			JSONObject object = (JSONObject) array.get(i);
			System.out.println("3.2");
			id = Integer.parseInt((String) object.get("id"));
			System.out.println("3.3");
			prompt = (String) object.get("prompt") + "";
			System.out.println("3.4");
			dateSent = (String) object.get("dateSent") + "";
			System.out.println("3.5");
			
			System.out.println(id);

			
			StatQuestion question = new StatQuestion(id, prompt, correct, answer);
			questions.add(question);	
			dateSentArray.add(dateSent);
		}
		System.out.println("4");
	}
	catch(Exception e){
		
	}
}


private void populateButtons(final ArrayList<StatQuestion> questions, final User user) {
	
	TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
		
	System.out.println("5");
	if(questions.size() == 0)
	{
		questionTit.setText("There are no sent questions for this topic at the current time. Please try again later.");
		questionTit.setGravity(Gravity.CENTER);
	}

	System.out.println("6");
	
	for(int row = 0; row < questions.size(); row++)
	{
		final int index = row;
	
		System.out.println("7");
		TableRow tableRow = new TableRow(this);
		table.addView(tableRow);
		
		System.out.println("8");
		Button button = new Button(this);
		tableRow.addView(button);
		System.out.println("9");
		String temp;
		if(questions.get(row).getPrompt().length() >40)
		{
			
				temp =  questions.get(row).getPrompt().substring(0, 40) + "...";
			
		}
		else{
			
				temp =  questions.get(row).getPrompt();
			
		}
		System.out.println("10");
		
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats_by_topic, menu);
		return true;
	}

}
