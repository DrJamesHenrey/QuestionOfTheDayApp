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
import com.example.cse.asu.questionoftheday.R.layout;
import com.example.cse.asu.questionoftheday.R.menu;

import cse.asu.questionoftheday.model.Question;
import cse.asu.questionoftheday.model.StatQuestion;
import cse.asu.questionoftheday.model.User;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SingleQuestionStatsTopicActivity extends Activity {
	
	TextView a, b, c, d;
	String qa, qb, qc, qd, qprompt;
	TextView answers, questionattempt, corrects;
	//Button submit;
	RadioGroup answersGroup;
	String answer, iscorrect;
	ArrayList<String> attempts;
	ArrayList<String> correct;
	ArrayList<String> listOfSections;
	int userid, questionid;

	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_question_topic);
		
		attempts = new ArrayList<String>();
		correct = new ArrayList<String>();
		
		questionattempt = (TextView) findViewById(R.id.questionprompt3);
		a = (TextView) findViewById(R.id.answerA2);
		b = (TextView) findViewById(R.id.answerB2);
		c = (TextView) findViewById(R.id.answerC2);
		d = (TextView) findViewById(R.id.answerD2);
		answers = (TextView) findViewById(R.id.answers);
		corrects = (TextView) findViewById(R.id.correct);
		
		final User user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		final StatQuestion question = (StatQuestion) getIntent().getExtras().getParcelable("QUESTION_KEY");
		listOfSections = new ArrayList<String>(user.getListOfSections());
		

		try{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			HttpClient defaultClient =  new DefaultHttpClient();
			HttpPost post = new HttpPost();
			userid = user.getID();

			post.setURI(new URI("http://cse110.courses.asu.edu/index.php/mobile/getAttemptsAsStudent/" + userid + "/"+  listOfSections.get(0) +"/" + question.getID())); 
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
				String att = (String) object.get("answer") + "";
				String cor = (String) object.get("correct") + "";
				
				attempts.add(att);
				correct.add(cor);
			}
			
			post.setURI(new URI("http://cse110.courses.asu.edu/index.php/mobile/getQuestion/" + question.getID())); 
			httpResponse = defaultClient.execute(post);
			reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
			
			String json2 = ""; 
			String temp2 = "";
			
			while ((temp2 = reader.readLine()) != null)
			{
				json2 += temp2;
			}
			JSONArray array2 = new JSONArray(json2);
			JSONObject object2 = (JSONObject) array2.get(0);
			qprompt = (String) object2.get("prompt")+"";
			qa = (String) "a:  " + object2.get("a")+"";
			qb = (String) "b:  " + object2.get("b")+"";
			qc = (String) "c:  " + object2.get("c")+"";
			qd = (String) "d:  " + object2.get("d")+"";
			
			}
			catch(Exception ex){
				System.out.println(ex);
			}
		answer = "";
		iscorrect ="";

		for(int i = 0; i < attempts.size(); i++){
			answer += attempts.get(i) + "\n";
			iscorrect += correct.get(i) + "\n";
		}
		setTitle("Question " + question.getID());

		
		questionattempt.setText(Html.fromHtml(qprompt));
		
		a.setText(qa);
		b.setText(qb);
		c.setText(qc);
		d.setText(qd);
		answers.setText(answer);
		corrects.setText(iscorrect);
		

	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.answer_page, menu);
		return true;
	}

}
