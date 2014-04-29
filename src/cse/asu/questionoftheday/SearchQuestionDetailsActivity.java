package cse.asu.questionoftheday;

import java.net.URI; 
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

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
import android.text.TextUtils.TruncateAt;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SearchQuestionDetailsActivity extends Activity {

	User user;
	Question question;
	Section section;
	Button add;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_question_details);
		
		setTitle("Question of the Day");
		
		Button qButton = (Button) findViewById(R.id.QButton);
		Button sButton = (Button) findViewById(R.id.SButton);
		Button rButton = (Button) findViewById(R.id.RButton);
		add = (Button) findViewById(R.id.Remove);
		TextView questionDetails = (TextView) findViewById(R.id.QuestionDetailsText);
		TextView topicText = (TextView) findViewById(R.id.topicText);
		TextView promptText = (TextView) findViewById(R.id.promptText);
		TextView aText = (TextView) findViewById(R.id.aText);
		TextView bText = (TextView) findViewById(R.id.bText);
		TextView cText = (TextView) findViewById(R.id.cText);
		TextView dText = (TextView) findViewById(R.id.dText);
		TextView correctText = (TextView) findViewById(R.id.correctText);
		TextView hintsText = (TextView) findViewById(R.id.hintsText);
		TextView explanationText = (TextView) findViewById(R.id.explanationText);
		final Context context = this;
		
		final User user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		ArrayList<String> listOfSections = new ArrayList<String>(user.getListOfSections());

		question = (Question) getIntent().getExtras().getParcelable("QUESTION_KEY");
		section = (Section) getIntent().getExtras().getParcelable("SECTION_KEY");
		
		questionDetails.setText("Question #" + question.getID());
		questionDetails.setTextSize(20);
		
		qButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View currentView) 
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Questions Menu");
				builder.setItems(new CharSequence[] {"Create New Question", "Manage Questions", "Send Questions By Topic", "Search Questions"}, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which){
						case 0:
							Intent myIntent1 = new Intent(context, CreateQuestionActivity.class);
							myIntent1.putExtra("USER_KEY", user);
							myIntent1.putExtra("SECTION_KEY", section);
							startActivity(myIntent1);
							break;
						case 1:
							Intent myIntent2 = new Intent(context, ManageQuestionsActivity.class);
							myIntent2.putExtra("USER_KEY", user);
							myIntent2.putExtra("SECTION_KEY", section);
							startActivity(myIntent2);
							break;
						case 2:
							Intent myIntent3 = new Intent(context, SendByTopicActivity.class);
							myIntent3.putExtra("USER_KEY", user);
							myIntent3.putExtra("SECTION_KEY", section);
							startActivity(myIntent3);
							break;
						case 3:
							Intent myIntent4 = new Intent(context, SearchQuestionActivity.class);
							myIntent4.putExtra("USER_KEY", user);
							myIntent4.putExtra("SECTION_KEY", section);
							startActivity(myIntent4);
							break;
						}
						
					}
				});
				builder.create().show();
			}
		});
		
		sButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View currentView) 
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Statistics Menu");
				builder.setItems(new CharSequence[] {"Statistics By Student Name", "Statistics By Topic", "Statistical Graphs"}, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which){
						case 0:
							Intent myIntent1 = new Intent(context, StatsByNameActivity.class);
							myIntent1.putExtra("USER_KEY", user);
							myIntent1.putExtra("SECTION_KEY", section);
							startActivity(myIntent1);
							break;
						case 1:
							Intent myIntent2 = new Intent(context, StatsByTopicActivity.class);
							myIntent2.putExtra("USER_KEY", user);
							myIntent2.putExtra("SECTION_KEY", section);
							startActivity(myIntent2);
							break;
						case 2:
							Intent myIntent3 = new Intent(context, GraphsActivity.class);
							myIntent3.putExtra("USER_KEY", user);
							myIntent3.putExtra("SECTION_KEY", section);
							startActivity(myIntent3);
							break;
						}
						
					}
				});
				builder.create().show();
			}
		});
		
		rButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View currentView) 
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Roster Menu");
				builder.setItems(new CharSequence[] {"Show Student Roster", "Show TAs"}, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which){
						case 0:
							Intent myIntent1 = new Intent(context, StudentRosterActivity.class);
							myIntent1.putExtra("USER_KEY", user);
							myIntent1.putExtra("SECTION_KEY", section);
							myIntent1.putExtra("RELOADED_KEY", false);
							startActivity(myIntent1);
							break;
						case 1:
							Intent myIntent2 = new Intent(context, TAActivity.class);
							myIntent2.putExtra("USER_KEY", user);
							myIntent2.putExtra("SECTION_KEY", section);
							startActivity(myIntent2);
							break;
						}
						
					}
				});
				builder.create().show();
			}
		});
		
		
		topicText.setText(question.getTopic());
		topicText.setTextSize(12);
		topicText.setSingleLine();
		topicText.setEllipsize(TruncateAt.END);
		
		promptText.setText(question.getPrompt());
		promptText.setTextSize(12);
		promptText.setSingleLine();
		promptText.setEllipsize(TruncateAt.END);
		
		aText.setText(question.getA());
		aText.setTextSize(12);
		aText.setSingleLine();
		aText.setEllipsize(TruncateAt.END);
		
		bText.setText(question.getB());
		bText.setTextSize(12);
		bText.setSingleLine();
		bText.setEllipsize(TruncateAt.END);
		
		cText.setText(question.getC());
		cText.setTextSize(12);
		cText.setSingleLine();
		cText.setEllipsize(TruncateAt.END);
		
		dText.setText(question.getD());
		dText.setTextSize(12);
		dText.setSingleLine();
		dText.setEllipsize(TruncateAt.END);
		
		correctText.setText(question.getCorrect().toUpperCase());
		correctText.setTextSize(12);
		
		hintsText.setText(question.getHint());
		hintsText.setTextSize(12);
		hintsText.setSingleLine();
		hintsText.setEllipsize(TruncateAt.END);
		
		explanationText.setText(question.getExplanation());
		explanationText.setTextSize(12);
		explanationText.setSingleLine();
		explanationText.setEllipsize(TruncateAt.END);
		
		add.setOnClickListener(new View.OnClickListener() 
        {
			
			@Override
			public void onClick(View currentView) 
			{

				try {
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy);
					HttpClient defaultClient =  new DefaultHttpClient();
					HttpPost post = new HttpPost();
					
					String temp = "http://cse110.courses.asu.edu/index.php/mobile/searchAdd/" + section.getSectionID() + "/" + question.getID() + "/" + question.getTopic();
					temp = temp.replaceAll(" ", "%20");
					post.setURI(new URI(temp));
					defaultClient.execute(post);
				
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
									
								}
							  });

							AlertDialog alertDialog = alertDialogBuilder.create();

							alertDialog.show();
				}	

				Toast.makeText(getApplicationContext(),
	                    "Question added to section " + section.getSectionID(),
	                    Toast.LENGTH_LONG).show();
			}
        });
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.qestion_details, menu);
		return true;
	}
}

