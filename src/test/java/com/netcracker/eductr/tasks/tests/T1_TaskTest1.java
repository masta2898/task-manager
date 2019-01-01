package com.netcracker.eductr.tasks.tests;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tasks.tests.common.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;

@TestCase("1. Перевірка основних методів класу Task")
public class T1_TaskTest1 
{
	final Task.Constructor Task;
	
	public T1_TaskTest1(ProxyRepository repo) throws ClassNotFoundException, ObsoleteTestException
	{
        repo.obsoleteIfConstructorExists("Task", String.class, java.util.Date.class);
        Task = repo.createTypeProxy("Task", Task.Constructor.class);
	}

	@Test("Перевірка rонструктору Task(title, time)") 
	void testConstructorNonRepeat()
	{
		Task task = Task.create("Task title", 42);
		
		assert "Task title".equals(task.getTitle()) : 
            "Задача має неочікувану назву: "+ describe(task);
		
		assert ! task.isRepeated() : 
            "Задача не повинна повторюватись";
		
		assert ! task.isActive() : 
            "Задача повинна бути неактивною";
		
		assert 42 == task.getTime() : 
            "Задача має неочікуваний час виконання: "+ describe(task);

		assert 42 == task.getStartTime() : 
            "Задача має неочікуваний час початку: "+ describe(task);

		assert 42 == task.getEndTime() : 
            "Задача має неочікуваний час закінчення: "+ describe(task);

		assert 0 == task.getRepeatInterval() : 
            "Задача має неочікуваний інтервал повторення: "+ describe(task);
	}

	@Test("Перевірка конструктору Task(title, start, end, interval)") 
	void testConstructorRepeat()
	{
		Task task = Task.create("Task title", 5, 25, 3);
		
		assert "Task title".equals(task.getTitle()) : 
            "Задача має неочікувану назву: "+ describe(task);
		
		assert task.isRepeated() : 
            "Задача повинна повторюватись";
		
		assert ! task.isActive() : 
            "Задача повинна бути неактивною";

		assert 5 == task.getStartTime() : 
            "Задача має неочікуваний час початку: "+ describe(task);

		assert 25 == task.getEndTime() : 
            "Задача має неочікуваний час закінчення: "+ describe(task);

		assert 3 == task.getRepeatInterval() : 
            "Задача має неочікуваний інтервал повторення: "+ describe(task);

		assert 5 == task.getTime() : 
            "Задача має неочікуваний час виконання: "+ describe(task);
	}
	
	@Test(value = "Перевірка методів setTime",
		depends = {"#testConstructorRepeat","#testConstructorNonRepeat"})
	void testSetTime()
	{
		Task task = Task.create("Task title", 5, 25, 3);
		
		task.setTime(42);

		assert ! task.isRepeated() : 
            "Задача не повинна повторюватись";

		assert 42 == task.getTime() : 
            "Задача має неочікуваний час виконання: "+ describe(task);

		assert 42 == task.getStartTime() : 
            "Задача має неочікуваний час початку: "+ describe(task);

		assert 42 == task.getEndTime() : 
            "Задача має неочікуваний час закінчення: "+ describe(task);

		assert 0 == task.getRepeatInterval() : 
            "Задача має неочікуваний інтервал повторення: "+ describe(task);

		task.setTime(5, 25, 3);

		assert task.isRepeated() : 
            "Задача повинна повторюватись";
		
		assert 5 == task.getStartTime() : 
            "Задача має неочікуваний час початку: "+ describe(task);

		assert 25 == task.getEndTime() : 
            "Задача має неочікуваний час закінчення: "+ describe(task);

		assert 3 == task.getRepeatInterval() : 
            "Задача має неочікуваний інтервал повторення: "+ describe(task);

		assert 5 == task.getTime() : 
            "Задача має неочікуваний час виконання: "+ describe(task);
	}
}
