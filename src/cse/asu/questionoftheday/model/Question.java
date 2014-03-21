package cse.asu.questionoftheday.model;

import java.util.*;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable{
	
	private String topic, prompt, a, b, c, d, correct, hint, explanation;
	private int id;
	
	@Override
	public int describeContents() {
		return this.hashCode();
	}
	
	public Question(int id, String topic, String prompt, String a, String b, String c, String d, String correct, String hint, String explanation)
	{
		this.id = id;
		this.topic = topic;
		this.prompt = prompt;
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.correct = correct;
		this.hint = hint;
		this.explanation = explanation;
	}
	
	public Question()
	{
		super();
	}
	
	public Question(Parcel parcel)
	{
		this();
		this.setID(parcel.readInt());
		this.setTopic(parcel.readString());
		this.setPrompt(parcel.readString());
		this.setA(parcel.readString());
		this.setB(parcel.readString());
		this.setC(parcel.readString());
		this.setD(parcel.readString());
		this.setCorrect(parcel.readString());
		this.setHint(parcel.readString());
		this.setExplanation(parcel.readString());
	}//a
	
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(getID());
		dest.writeString(getTopic());
		dest.writeString(getPrompt());
		dest.writeString(getA());
		dest.writeString(getB());
		dest.writeString(getC());
		dest.writeString(getD());
		dest.writeString(getCorrect());
		dest.writeString(getHint());
		dest.writeString(getExplanation());
	}
	
	public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>()
	{
		public Question createFromParcel(Parcel parcel)
		{
			return new Question(parcel);
		}
		
		public Question[] newArray(int size)
		{
			return new Question[size];
		}
	};
	
	public int getID()
	{
		return id;
	}
	
	public String getTopic()
	{
		return topic;
	}
	
	public String getPrompt()
	{
		return prompt;
	}
	
	public String getA()
	{
		return a;
	}

	public String getB()
	{
		return b;
	}

	public String getC()
	{
		return c;
	}
	
	public String getD()
	{
		return d;
	}
	
	public String getCorrect()
	{
		return correct;
	}
	
	public String getHint()
	{
		return hint;
	}
	
	public String getExplanation()
	{
		return explanation;
	}


	public void setTopic(String top)
	{
		topic = top;
	}
	
	public void setPrompt(String prom)
	{
		prompt = prom;
	}
	
	public void setA(String aa)
	{
		a = aa;
	}
	
	public void setB(String bb)
	{
		b = bb;
	}
	
	public void setID(int questionID)
	{
		id = questionID;
	}
	
	public void setD(String dd)
	{
		d = dd;
	}
	
	public void setC(String cc)
	{
		c = cc;
	}
	
	public void setCorrect(String cor)
	{
		correct = cor;
	}
	
	public void setHint(String hin)
	{
		hint = hin;
	}
	
	public void setExplanation(String expl)
	{
		explanation= expl;
	}

}

