package com.netcracker.eductr.tasks.tests.model;

import java.util.Date;

public interface Task 
{
	interface Constructor
	{
		Task create(String title, int time);
		Task create(String title, int start, int end, int interval);

		Task create(String title, Date time);
		Task create(String title, Date start, Date end, int interval);
    }
	
	String getTitle();
	void setTitle(String title);
	
	boolean isActive();
	void setActive(boolean value);
	
	int getTime();
	void setTime(int time);
	void setTime(Date time);
	
	int getStartTime();
	int getEndTime();
	int getRepeatInterval();
	void setTime(int start, int end, int interval);
	void setTime(Date start, Date end, int interval);
	
	boolean isRepeated();
	
	int NEVER = -1;
	
	int nextTimeAfter(int now);
	Date nextTimeAfter(Date now);
    
    Task clone();
}
