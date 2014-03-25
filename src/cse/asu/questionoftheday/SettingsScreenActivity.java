package cse.asu.questionoftheday;

import java.util.ArrayList;

import com.example.cse.asu.questionoftheday.R;
import com.example.cse.asu.questionoftheday.R.layout;
import com.example.cse.asu.questionoftheday.R.menu;

import cse.asu.questionoftheday.model.User;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class SettingsScreenActivity extends Activity {
	
	private Button questionButton;
	private Button statisticsButton;
	private Button menuButton;
	private Button changePassword;
	private CheckBox notifications;
	private CheckBox emails;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_screen);
		
		questionButton = (Button) findViewById(R.id.QuestionsButton);
		statisticsButton = (Button) findViewById(R.id.StatisticsButton);
		menuButton = (Button) findViewById(R.id.MenuButton);
		changePassword = (Button) findViewById(R.id.password);
		notifications = (CheckBox) findViewById(R.id.note);
		emails = (CheckBox) findViewById(R.id.email);
		
		final User user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		ArrayList<String> listOfSections = new ArrayList<String>(user.getListOfSections());
		
		changePassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View currentView) 
			{
				Intent myIntent = new Intent(currentView.getContext(), ChangePassWordActivity.class);
				myIntent.putExtra("USER_KEY", user);
				startActivity(myIntent);
			}
		});
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_screen, menu);
		return true;
	}

}
