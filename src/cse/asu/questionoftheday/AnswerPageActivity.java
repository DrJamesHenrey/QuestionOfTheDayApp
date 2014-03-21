package cse.asu.questionoftheday;

import java.util.ArrayList;

import com.example.cse.asu.questionoftheday.R;
import com.example.cse.asu.questionoftheday.R.layout;
import com.example.cse.asu.questionoftheday.R.menu;

import cse.asu.questionoftheday.model.Question;
import cse.asu.questionoftheday.model.User;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AnswerPageActivity extends Activity {
	
	RadioButton a, b, c, d;
	TextView answerText, hint;
	ToggleButton showHint;
	Button submit;
	RadioGroup answersGroup;
	String answer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answer_page);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		answerText = (TextView) findViewById(R.id.AnswerText);
		hint = (TextView) findViewById(R.id.hintText);
		showHint = (ToggleButton) findViewById(R.id.toggleHint);
		submit = (Button) findViewById(R.id.submit);
		answersGroup = (RadioGroup) findViewById(R.id.answersGroup);
		a = (RadioButton) findViewById(R.id.a);
		b = (RadioButton) findViewById(R.id.b);
		c = (RadioButton) findViewById(R.id.c);
		d = (RadioButton) findViewById(R.id.d);
		answer = "a";
		
		final User user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		ArrayList<String> listOfSections = new ArrayList<String>(user.getListOfSections());

		final Question question = (Question) getIntent().getExtras().getParcelable("QUESTION_KEY");
		setTitle("Question " + question.getID());
		
		answerText.setText(Html.fromHtml(question.getPrompt()));
		hint.setText("");
		
		a.setText(question.getA());
		b.setText(question.getB());
		c.setText(question.getC());
		d.setText(question.getD());
		
		showHint.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if(isChecked)
				{
					hint.setText(question.getHint());
				}
				else
				{
					hint.setText("");
				}
			}
		});
		
        submit.setOnClickListener(new View.OnClickListener() 
        {
			
			@Override
			public void onClick(View currentView) {
				
				int selectedId = answersGroup.getCheckedRadioButtonId();
				if(selectedId == R.id.a)
					answer = "a";
				if(selectedId == R.id.b)
					answer = "b";
				if(selectedId == R.id.c)
					answer = "c";
				if(selectedId == R.id.d)
					answer = "d";
				
				
				Intent myIntent = new Intent(currentView.getContext(), AnswerScreenActivity.class);
				myIntent.putExtra("USER_KEY", user);
				myIntent.putExtra("QUESTION_KEY", question);
				myIntent.putExtra("ANSWER", answer);
				startActivity(myIntent);
			}
        });
				
		
		getMenuInflater().inflate(R.menu.answer_page, menu);
		return true;
	
	}

}
