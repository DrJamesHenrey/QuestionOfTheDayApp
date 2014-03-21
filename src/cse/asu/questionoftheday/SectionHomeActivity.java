package cse.asu.questionoftheday;

import java.util.ArrayList;

import com.example.cse.asu.questionoftheday.R;
import com.example.cse.asu.questionoftheday.R.layout;
import com.example.cse.asu.questionoftheday.R.menu;

import cse.asu.questionoftheday.model.Question;
import cse.asu.questionoftheday.model.Section;
import cse.asu.questionoftheday.model.User;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class SectionHomeActivity extends Activity {
	
	TextView sectionHomeText, text1, text2, text3, text4, text5, text6, text7, text8, text9, text10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_section_home);
		
		setTitle("Question of the Day");
		
		RelativeLayout rl = (RelativeLayout)findViewById(R.id.insideLayout);
		rl.setBackgroundColor(Color.LTGRAY);
		Button properties = (Button) findViewById(R.id.editPropButton);
		Button qButton = (Button) findViewById(R.id.QButton);
		Button sButton = (Button) findViewById(R.id.SButton);
		Button rButton = (Button) findViewById(R.id.RButton);
		final Context context = this;
		
		sectionHomeText = (TextView) findViewById(R.id.sectionHomeTest);
		text1 = (TextView) findViewById(R.id.ErrorText);
		text2 = (TextView) findViewById(R.id.textView12);
		text3 = (TextView) findViewById(R.id.textView13);
		text4 = (TextView) findViewById(R.id.textView14);
		text5 = (TextView) findViewById(R.id.textView15);
		text6 = (TextView) findViewById(R.id.textView16);
		text7 = (TextView) findViewById(R.id.textView17);
		text8 = (TextView) findViewById(R.id.textView18);
		text9 = (TextView) findViewById(R.id.textView19);
		text10 = (TextView) findViewById(R.id.textView20);
		
		final User user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		ArrayList<String> listOfSections = new ArrayList<String>(user.getListOfSections());

		final Section section = (Section) getIntent().getExtras().getParcelable("SECTION_KEY");
		
		sectionHomeText.setText("Section " + section.getSectionID());
		sectionHomeText.setTextSize(22);
		
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
		
		
		text1.setText(section.getTitle());
		text2.setText(section.getCourseID());
		text3.setText(section.getSectionID());
		text4.setText(user.getUsername());
		
		String temp="";
		if(section.getMeetMonday() == 1)
			temp +="M";
		if(section.getMeetTuesday() == 1)
			temp += "Tu";
		if(section.getMeetWednesday() == 1)
			temp += "W";
		if(section.getMeetThursday() == 1)
			temp += "Th";
		if(section.getMeetFriday() == 1)
			temp += "F";
		if(section.getMeetSaturday() == 1)
			temp += "Sa";
		if(section.getMeetSunday() == 1)
			temp += "Su";
		
		if(temp.isEmpty())
			temp = "Class does not meet";
		else
				temp += " " + section.getTime();
		
		text5.setText(temp);
		
		text6.setText(section.getSemester());
		if(section.getSendToProf() == 1)
			text7.setText("Yes");
		else
			text7.setText("No");
		text8.setText(section.getTimeLimit() + " day(s)");
		if(section.getSendingQuestions() == 1)
			text9.setText("Yes");
		else
			text9.setText("No");
		if(section.getCanDrop() == 1)
			text10.setText("Yes");
		else
			text10.setText("No");
		
		properties.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), PropertiesActivity.class);
				myIntent.putExtra("USER_KEY", user);
				myIntent.putExtra("SECTION_KEY", section);
				startActivity(myIntent);
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.section_home, menu);
		return true;
	}

}
