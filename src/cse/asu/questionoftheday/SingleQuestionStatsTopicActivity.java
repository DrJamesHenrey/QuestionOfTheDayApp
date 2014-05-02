package cse.asu.questionoftheday;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
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
import cse.asu.questionoftheday.model.Section;
import cse.asu.questionoftheday.model.StatQuestion;
import cse.asu.questionoftheday.model.User;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
	ImageView iv;
	Bitmap bitmap;
	Section section;

	


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
		
		RelativeLayout r1 = (RelativeLayout)findViewById(R.id.relativeLayout2);
		r1.setBackgroundColor(Color.LTGRAY);
		RelativeLayout r2 = (RelativeLayout)findViewById(R.id.relativeLayout4);
		r2.setBackgroundColor(Color.LTGRAY);
		RelativeLayout r3 = (RelativeLayout)findViewById(R.id.relativeLayout1);
		r3.setBackgroundColor(Color.LTGRAY);
		

		
		final User user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		final String topic =  getIntent().getExtras().getString("TOPIC_KEY");
		section = (Section) getIntent().getExtras().getParcelable("SECTION_KEY");
		final StatQuestion question = (StatQuestion) getIntent().getExtras().getParcelable("QUESTION_KEY");
		listOfSections = new ArrayList<String>(user.getListOfSections());
		questionid = question.getID();
		


		try{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			HttpClient defaultClient =  new DefaultHttpClient();
			HttpPost post = new HttpPost();
			userid = user.getID();
			
			post.setURI(new URI("https://cse110.courses.asu.edu/index.php/mobile/getSingleQuestionTopicGrades/" + section.getSectionID() + "/"+  topic +"/" + question.getID())); 
			HttpResponse httpResponse = defaultClient.execute(post);
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
			String json = ""; 
			String temp = "";
			
			
			while ((temp = reader.readLine()) != null)
			{
				json += temp;
			}
			JSONObject jsonResponse = new JSONObject(json);
			JSONArray usernames =  jsonResponse.getJSONArray("usernames");
			JSONArray questionCorrect =  jsonResponse.getJSONArray("isCorrect");
			for(int i =0; i< usernames.length(); i++)
			{
				String att = usernames.getString(i);
				String cor = questionCorrect.getString(i);
				
				attempts.add(att);
				correct.add(cor);
			}

			
			post.setURI(new URI("https://cse110.courses.asu.edu/index.php/mobile/getQuestion/" + question.getID())); 
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
			
			if(Integer.parseInt(correct.get(i))== 1 ){
				iscorrect += "YES" + "\n";
			}
			else{
				iscorrect += "NO" + "\n";
			}
			answer += attempts.get(i) + "\n";
		}
		setTitle("Question " + question.getID());

		
		questionattempt.setText(Html.fromHtml(qprompt));
		
		a.setText(qa);
		b.setText(qb);
		c.setText(qc);
		d.setText(qd);
		answers.setText(answer);
		corrects.setText(iscorrect);
		
		iv = (ImageView) findViewById(R.id.imageFromServer4);
		//bitmap = getBitmapFromURL("https://cse110.courses.asu.edu/index.php/mobile/transfereGraph");
		bitmap = getBitmapFromURL("https://cse110.courses.asu.edu/jGraph/Pictures/topic_breakdown.png");
		iv.setImageBitmap(bitmap);
		

	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.answer_page, menu);
		return true;
	}
	
	public Bitmap getBitmapFromURL(String src){
		try{
			//
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			HttpClient defaultClient =  new DefaultHttpClient();
			HttpPost post = new HttpPost();
			post.setURI(new URI("https://cse110.courses.asu.edu/index.php/mobile/transfereGraphProfessor" +"/"+ questionid + "/"+ section.getSectionID())); 
			HttpResponse httpResponse = defaultClient.execute(post);
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
			//
			
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		}
		catch(Exception e){
			return null;
		}
	}

}
