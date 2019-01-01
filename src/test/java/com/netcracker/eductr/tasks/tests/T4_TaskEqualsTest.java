package com.netcracker.eductr.tasks.tests;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tasks.tests.common.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;
import java.util.Date;

@TestCase("4. Перевірка методів equals & hashCode в Task")
public class T4_TaskEqualsTest extends EqualsTest<Task>
{
	final Task.Constructor Task;
    final boolean isOld;
	
	public T4_TaskEqualsTest(ProxyRepository repo) throws ClassNotFoundException, ObsoleteTestException
	{
        isOld = ! repo.constructorExists("Task", String.class, Date.class);
        Task = repo.createTypeProxy("Task", Task.Constructor.class);
        repo.obsoleteIfMethodNotDeclared("Task", "equals", Object.class);
	}
    
    protected Task createA() {
        Task t = isOld ?
            Task.create("Task A", 10) :
            Task.create("Task A", new Date(10));
        t.setActive(true);
        return t;
    }
    protected Task createB() {
        return isOld ?
            Task.create("Task B", 20, 30, 5) :
            Task.create("Task B", new Date(20), new Date(30), 5);
    }
    protected void modify(Task t) {
        t.setTitle("Some task");
        if (isOld)
            t.setTime(42);
        else
            t.setTime(new Date(42));
        t.setActive(true);
    }
}