package cse.asu.questionoftheday;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
import com.example.cse.asu.questionoftheday.R.layout;
import com.example.cse.asu.questionoftheday.R.menu;

import cse.asu.questionoftheday.model.Question;
import cse.asu.questionoftheday.model.Section;
import cse.asu.questionoftheday.model.User;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CreateQuestionActivity extends Activity {
	
	LinearLayout createLayout;
	Button createQ;
	EditText topicField, promptField, aField, bField, cField, dField, hintField, explanationField;
	RadioGroup correctGroup;
	RadioButton a, b, c, d;
	String correctAnswer;
	TextView errorText;
	boolean edit;
	Question question;
	String result;
	final Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_question);
		
		setTitle("Question of the Day");
		final Context context = this;
		errorText = (TextView) findViewById(R.id.ErrorText);
		createQ = (Button) findViewById(R.id.createQ);
		correctGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		a = (RadioButton) findViewById(R.id.aRadio);
		b = (RadioButton) findViewById(R.id.bRadio);
		c = (RadioButton) findViewById(R.id.cRadio);
		d = (RadioButton) findViewById(R.id.dRadio);
		topicField = (EditText) findViewById(R.id.topicField);
		promptField = (EditText) findViewById(R.id.PromptField);
		aField = (EditText) findViewById(R.id.aField);
		bField = (EditText) findViewById(R.id.bField);
		cField = (EditText) findViewById(R.id.cField);
		dField = (EditText) findViewById(R.id.dField);
		hintField = (EditText) findViewById(R.id.hintField);
		explanationField = (EditText) findViewById(R.id.explanationField);
		
		edit = false;
		final User user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		ArrayList<String> listOfSections = new ArrayList<String>(user.getListOfSections());

		final Section section = (Section) getIntent().getExtras().getParcelable("SECTION_KEY");
		edit = (boolean) getIntent().getExtras().getBoolean("EDIT_KEY");
		
		correctAnswer = "a";
		
		if(edit)
		{
			question = (Question) getIntent().getExtras().getParcelable("QUESTION_KEY");
			correctAnswer = question.getCorrect();
			if(correctAnswer.equals("a"))
			{
				correctGroup.check(R.id.aRadio);
			}
			else if(correctAnswer.equals("b"))
			{
				correctGroup.check(R.id.bRadio);
			}
			else if(correctAnswer.equals("c"))
			{
				correctGroup.check(R.id.cRadio);
			}
			else
			{
				correctGroup.check(R.id.dRadio);
			}
			
			topicField.setText(question.getTopic());
			promptField.setText(question.getPrompt());
			aField.setText(question.getA());
			bField.setText(question.getB());
			cField.setText(question.getC());
			dField.setText(question.getD());
			hintField.setText(question.getHint());
			explanationField.setText(question.getExplanation());
			createQ.setText("Save");
		}
		
	    createQ.setOnClickListener(new View.OnClickListener() 
	        {
				
				@Override
				public void onClick(View currentView) 
				{
					
					int selectedId = correctGroup.getCheckedRadioButtonId();
					if(selectedId == R.id.aRadio)
						correctAnswer = "a";
					if(selectedId == R.id.bRadio)
						correctAnswer = "b";
					if(selectedId == R.id.cRadio)
						correctAnswer = "c";
					if(selectedId == R.id.dRadio)
						correctAnswer = "d";
					
					if(topicField.getText().length() == 0)
					{
						errorText.setText("Please enter a topic for your question");
					}
					else if(promptField.getText().length() == 0)
					{
						errorText.setText("Please enter a prompt for your question");
					}
					else if(aField.getText().length() == 0)
					{
						errorText.setText("Please enter choice A for your question");
					}
					else if(bField.getText().length() == 0)
					{
						errorText.setText("Please enter choice B for your question");
					}
					else if(cField.getText().length() == 0)
					{
						errorText.setText("Please enter choice C for your question");
					}
					else if(dField.getText().length() == 0)
					{
						errorText.setText("Please enter choice D for your question");
					}
					else if(hintField.getText().length() == 0)
					{
						errorText.setText("Please enter a hint for your question");
					}
					else if(explanationField.getText().length() == 0)
					{
						errorText.setText("Please enter an explanation for your question");
					}
					else
					{	
						
						try {
							String top = topicField.getText()+ "";
							top = top.replaceAll("/", "hhhhhhhhhh");
							top = top.replaceAll("\\*", "yyyyyyyy");
							top = URLEncoder.encode(top, "UTF-8");
							
							String prom = promptField.getText()+ "";
							prom = prom.replaceAll("/", "hhhhhhhhhh");
							prom = prom.replaceAll("\\*", "yyyyyyyy");
							prom = URLEncoder.encode(prom, "UTF-8");
							
							String a = aField.getText()+ "";
							a = a.replaceAll("/", "hhhhhhhhhh");
							a = a.replaceAll("\\*", "yyyyyyyy");
							a = URLEncoder.encode(a, "UTF-8");

							String b = bField.getText()+ "";
							b = b.replaceAll("/", "hhhhhhhhhh");
							b = b.replaceAll("\\*", "yyyyyyyy");
							b = URLEncoder.encode(b, "UTF-8");

							String c = cField.getText()+ "";
							c = c.replaceAll("/", "hhhhhhhhhh");
							c = c.replaceAll("\\*", "yyyyyyyy");
							c = URLEncoder.encode(c, "UTF-8");

							String d = dField.getText()+ "";
							d = d.replaceAll("/", "hhhhhhhhhh");
							c = c.replaceAll("\\*", "yyyyyyyy");
							d = URLEncoder.encode(d, "UTF-8");

							String hin = hintField.getText()+ "";
							hin = hin.replaceAll("/", "hhhhhhhhhh");
							hin = hin.replaceAll("\\*", "yyyyyyyy");
							hin = URLEncoder.encode(hin, "UTF-8");

							String exp = explanationField.getText()+ "";
							exp = exp.replaceAll("/", "hhhhhhhhhh");
							exp = exp.replaceAll("\\*", "yyyyyyyy");
							exp = URLEncoder.encode(exp, "UTF-8");
							
							
							StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
							StrictMode.setThreadPolicy(policy);
							HttpClient defaultClient =  new DefaultHttpClient();
							HttpPost post = new HttpPost();
							String temp ="";
							if(edit)
							{								
								temp = question.getID() + "/" + top + "/" + prom + "/" + a + "/" + b + "/" + c + "/" + d + "/" + correctAnswer + "/" + hin + "/" + exp + "/" + section.getSectionID();
								temp = temp.replaceAll("\\+", "%20");
								System.out.println(temp);
								post.setURI(new URI("http://cse110.courses.asu.edu/index.php/mobile/editQuestion/" + temp));
								defaultClient.execute(post);
							}
							else
							{
								temp = top + "/" + prom + "/" + a + "/" + b + "/" + c + "/" + d + "/" + correctAnswer + "/" + hin + "/" + exp + "/" + section.getSectionID();
								temp = temp.replaceAll("\\+", "%20");
								post.setURI(new URI("http://cse110.courses.asu.edu/index.php/mobile/createQuestion/" + temp));
								defaultClient.execute(post);
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

						Intent myIntent = new Intent(currentView.getContext(), TransitionActivity.class);
						myIntent.putExtra("USER_KEY", user);
						myIntent.putExtra("SECTION_KEY", section);
						
						String result = "";
						if(edit)
							result = "You have successfuly edited the question!";
						else
							result = "You have successfuly created the question!";
						myIntent.putExtra("RESULT_KEY", result);
						startActivity(myIntent);
						finish();
					}
				}
	        });
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_question, menu);
		return true;
	}

}
