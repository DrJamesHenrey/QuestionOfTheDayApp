package cse.asu.questionoftheday.model;

import java.util.*;

import android.os.Parcel;
import android.os.Parcelable;

public class StatQuestion implements Parcelable{
	
	private String topic, prompt, answer, points, correct, explanation;
	private int id;
	
	@Override
	public int describeContents() {
		return this.hashCode();
	}
	
	public StatQuestion(int id, String prompt, String correct, String answer)
	{
		this.id = id;
		this.prompt = prompt;
		this.correct = correct;
		this.answer = answer;
	}
	
	public StatQuestion()
	{
		super();
	}
	
	public StatQuestion(Parcel parcel)
	{
		this();
		this.setID(parcel.readInt());
		this.setPrompt(parcel.readString());
		this.setCorrect(parcel.readString());
		this.setAnswer(parcel.readString());
		
	}//a
	
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(getID());
		dest.writeString(getPrompt());
		dest.writeString(getCorrect());
		dest.writeString(getAnswer());
	}
	
	public static final Parcelable.Creator<StatQuestion> CREATOR = new Parcelable.Creator<StatQuestion>()
	{
		public StatQuestion createFromParcel(Parcel parcel)
		{
			return new StatQuestion(parcel);
		}
		
		public StatQuestion[] newArray(int size)
		{
			return new StatQuestion[size];
		}
	};
	
	public int getID()
	{
		return id;
	}
	
	
	public String getPrompt()
	{
		return prompt;
	}
	
	public String getAnswer()
	{
		return answer;
	}

	public String getCorrect()
	{
		return correct;
	}
	


	
	public void setPrompt(String prom)
	{
		prompt = prom;
	}
	
	public void setAnswer(String aa)
	{
		answer = aa;
	}
	
	
	public void setID(int questionID)
	{
		id = questionID;
	}
	
	
	public void setCorrect(String cor)
	{
		correct = cor;
	}


}

