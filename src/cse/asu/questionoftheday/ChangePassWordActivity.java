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
import android.widget.Toast;

public class ChangePassWordActivity extends Activity {

	private Button questionButton;
	private Button statisticsButton;
	private Button menuButton;
	private Button save;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pass_word);
		
		questionButton = (Button) findViewById(R.id.QuestionsButton);
		statisticsButton = (Button) findViewById(R.id.StatisticsButton);
		menuButton = (Button) findViewById(R.id.MenuButton);
		save = (Button) findViewById(R.id.save);
		
		final User user = (User) getIntent().getExtras().getParcelable("USER_KEY");
		ArrayList<String> listOfSections = new ArrayList<String>(user.getListOfSections());
		
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View currentView) 
			{
				Toast.makeText(getApplicationContext(),
                        "Password Changed.",
                        Toast.LENGTH_LONG).show();
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
		getMenuInflater().inflate(R.menu.change_pass_word, menu);
		return true;
	}

}
