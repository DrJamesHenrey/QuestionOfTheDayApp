package cse.asu.questionoftheday.model;

import java.util.*;

import android.os.Parcel;
import android.os.Parcelable;

public class Section implements Parcelable{
	
	private String sectionID, courseID, semester, time, title, profName;
	private int professorID, sendToProf, timeLimit, sendingQuestions, canDrop, meetMonday, meetTuesday, meetWednesday, meetThursday, meetFriday, meetSaturday, meetSunday;
	
	@Override
	public int describeContents() {
		return this.hashCode();
	}
	
	public Section(String sectionID, String courseID, int professorID, int sendToProf, int timeLimit, String semester, int sendingQuestions, int canDrop, int meetMonday, int meetTuesday, int meetWednesday, int meetThursday, int meetFriday, int meetSaturday, int meetSunday, String time, String title, String name)
	{
		this.sectionID = sectionID;
		this.courseID = courseID;
		this.professorID = professorID;
		this.sendToProf = sendToProf;
		this.timeLimit = timeLimit;
		this.semester = semester;
		this.sendingQuestions = sendingQuestions;
		this.canDrop = canDrop;
		this.meetMonday = meetMonday;
		this.meetTuesday = meetTuesday;
		this.meetWednesday = meetWednesday;
		this.meetThursday = meetThursday;
		this.meetFriday = meetFriday;
		this.meetSaturday = meetSaturday;
		this.meetSunday = meetSunday;
		this.time = time;
		this.title = title;
		this.profName = name;
	}
	
	public Section()
	{
		super();
	}
	
	public Section(Parcel parcel)
	{
		this();
		this.setSectionID(parcel.readString());
		this.setCourseID(parcel.readString());
		this.setProfessorID(parcel.readInt());
		this.setSendToProf(parcel.readInt());
		this.setTimeLimit(parcel.readInt());
		this.setSemester(parcel.readString());
		this.setSendingQestions(parcel.readInt());
		this.setCanDrop(parcel.readInt());
		this.setMeetMonday(parcel.readInt());
		this.setMeetTuesday(parcel.readInt());
		this.setMeetWednesday(parcel.readInt());
		this.setMeetThursday(parcel.readInt());
		this.setMeetFriday(parcel.readInt());
		this.setMeetSaturday(parcel.readInt());
		this.setMeetSunday(parcel.readInt());
		this.setTime(parcel.readString());
		this.setTitle(parcel.readString());
	}//a
	
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getSectionID());
		dest.writeString(getCourseID());
		dest.writeInt(getProfessorID());
		dest.writeInt(getSendToProf());
		dest.writeInt(getTimeLimit());
		dest.writeString(getSemester());
		dest.writeInt(getSendingQuestions());
		dest.writeInt(getCanDrop());
		dest.writeInt(getMeetMonday());
		dest.writeInt(getMeetTuesday());
		dest.writeInt(getMeetWednesday());
		dest.writeInt(getMeetThursday());
		dest.writeInt(getMeetFriday());
		dest.writeInt(getMeetSaturday());
		dest.writeInt(getMeetSunday());
		dest.writeString(getTime());
		dest.writeString(getTitle());
	}
	
	public static final Parcelable.Creator<Section> CREATOR = new Parcelable.Creator<Section>()
	{
		public Section createFromParcel(Parcel parcel)
		{
			return new Section(parcel);
		}
		
		public Section[] newArray(int size)
		{
			return new Section[size];
		}
	};
	
	public String getSectionID()
	{
		return sectionID;
	}
	
	public String getCourseID()
	{
		return courseID;
	}
	
	public int getProfessorID()
	{
		return professorID;
	}
	
	public int getSendToProf()
	{
		return sendToProf;
	}
	
	public int getTimeLimit()
	{
		return timeLimit;
	}
	
	public String getSemester()
	{
		return semester;
	}
	
	public int getSendingQuestions()
	{
		return sendingQuestions;
	}
	
	public int getCanDrop()
	{
		return canDrop;
	}
	public String getProfName()
	{
		return profName;
	}
	
	public int getMeetMonday()
	{
		return meetMonday;
	}
	
	public int getMeetTuesday()
	{
		return meetTuesday;
	}
	
	public int getMeetWednesday()
	{
		return meetWednesday;
	}
	
	public int getMeetThursday()
	{
		return meetThursday;
	}
	
	public int getMeetFriday()
	{
		return meetFriday;
	}
	
	public int getMeetSaturday()
	{
		return meetSaturday;
	}
	
	public int getMeetSunday()
	{
		return meetSunday;
	}
	
	public String getTime()
	{
		return time;
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setSectionID(String section)
	{
		sectionID = section;
	}
	
	public void setCourseID(String course)
	{
		courseID = course;
	}
	
	public void setProfessorID(int professor)
	{
		professorID = professor;
	}
	
	public void setSendToProf(int sendTo)
	{
		sendToProf = sendTo;
	}
	
	public void setTimeLimit(int timelim)
	{
		timeLimit = timelim;
	}
	
	public void setSemester(String sem)
	{
		semester = sem;
	}
	
	public void setSendingQestions(int send)
	{
		sendingQuestions = send;
	}
	
	public void setCanDrop(int can)
	{
		canDrop = can;
	}
	
	public void setMeetMonday(int day)
	{
		meetMonday = day;
	}
	
	public void setMeetTuesday(int day)
	{
		meetTuesday = day;
	}
	
	public void setMeetWednesday(int day)
	{
		meetWednesday = day;
	}
	
	public void setMeetThursday(int day)
	{
		meetThursday = day;
	}
	
	public void setMeetFriday(int day)
	{
		meetFriday = day;
	}
	
	public void setMeetSaturday(int day)
	{
		meetSaturday = day;
	}
	
	public void setMeetSunday(int sunday)
	{
		meetSunday = sunday;
	}

	public void setTime(String tim)
	{
		time = tim;
	}
	
	public void setTitle(String tit)
	{
		title = tit;
	}
}

