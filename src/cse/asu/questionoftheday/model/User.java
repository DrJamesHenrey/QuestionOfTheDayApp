package cse.asu.questionoftheday.model;

//package cse.asu.questionoftheday.model;

import java.util.*;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
	
	public static final int STUDENT =1;
	public static final int PROFESSOR=2;
	private String username, firstName, lastName, eMail;
	private int userType, id;
	ArrayList<String> listOfSections;
	
	@Override
	public int describeContents() {
		return this.hashCode();
	}
	
	public User(int id, String userName, String firstName, String lastName, String eMail, int userType, ArrayList<String> listOfSections)
	{
		this.id = id;
		this.username = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.eMail = eMail;
		if(userType == 1)
		{
			this.userType = STUDENT;
		}
		if(userType == 2)
		{
			this.userType = PROFESSOR;
		}
		
		this.listOfSections = new ArrayList<String>(listOfSections);
		
	}
	
	public User()
	{
		super();
	}
	
	public User(Parcel parcel)
	{
		this();
		this.setID(parcel.readInt());
		this.setUsername(parcel.readString());
		this.setFirstName(parcel.readString());
		this.setLastName(parcel.readString());
		this.setEMail(parcel.readString());
		this.setUserType(parcel.readInt());
		listOfSections = (ArrayList<String>) parcel.readSerializable();
	}
	
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(getID());
		dest.writeString(getUsername());
		dest.writeString(getFirstName());
		dest.writeString(getLastName());
		dest.writeString(getEMail());
		dest.writeInt(getUserType());
		dest.writeSerializable(listOfSections);
	}
	
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>()
	{
		public User createFromParcel(Parcel parcel)
		{
			return new User(parcel);
		}
		
		public User[] newArray(int size)
		{
			return new User[size];
		}
	};
	
	public int getID()
	{
		return id;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}

	public String getEMail()
	{
		return eMail;
	}

	public int getUserType()
	{
		return userType;
	}

	public void setFirstName(String fName)
	{
		firstName = fName;
	}
	
	public void setLastName(String lName)
	{
		lastName = lName;
	}
	
	public void setEMail(String mail)
	{
		eMail = mail;
	}
	
	public void setUsername(String name)
	{
		username = name;
	}
	
	public void setID(int userID)
	{
		id = userID;
	}
	
	public void setUserType(int type)
	{
		if(type == 1)
			userType = STUDENT;
		if(type == 2)
			userType = PROFESSOR;
	}
	
	public ArrayList<String> getListOfSections()
	{
		return listOfSections;
	}

}
