package com.netcracker.eductr.tasks.tests;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tasks.tests.common.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;
import static com.netcracker.eductr.tasks.tests.model.Task.NEVER;

@TestCase("1. Перевірка методу Task.nextTimeAfter")
public class T1_TaskTest2
{
	final Task.Constructor Task;
	
	public T1_TaskTest2(ProxyRepository repo) throws ClassNotFoundException, ObsoleteTestException
	{
        repo.obsoleteIfConstructorExists("Task", String.class, java.util.Date.class);
        Task = repo.createTypeProxy("Task", Task.Constructor.class);
	}

    @Test("Перевірка для задачі, що не повторюється")
    public void testNextNonRepeative() {
        Task task = Task.create("some", 10);
        task.setActive(true);
        assertEquals(10, task.nextTimeAfter(0), describe(task)+" nextTimeAfter 0");
        assertEquals(10, task.nextTimeAfter(9), describe(task)+" nextTimeAfter 9");
        assertEquals(NEVER, task.nextTimeAfter(10), describe(task)+" nextTimeAfter 10");
        assertEquals(NEVER, task.nextTimeAfter(100), describe(task)+" nextTimeAfter 100");
    }
    @Test("Перевірка для задачі, що повторюється")
    public void testNextRepeative() {
        Task task = Task.create("some", 10, 100, 20);
        task.setActive(true);
        assertEquals(10, task.nextTimeAfter(0), describe(task)+" nextTimeAfter 0");
        assertEquals(10, task.nextTimeAfter(9), describe(task)+" nextTimeAfter 9");
        assertEquals(50, task.nextTimeAfter(30), describe(task)+" nextTimeAfter 30");
        assertEquals(50, task.nextTimeAfter(40), describe(task)+" nextTimeAfter 40");
        assertEquals(30, task.nextTimeAfter(10), describe(task)+" nextTimeAfter 10");
        assertEquals(NEVER, task.nextTimeAfter(95), describe(task)+" nextTimeAfter 95");
        assertEquals(NEVER, task.nextTimeAfter(100), describe(task)+" nextTimeAfter 100");
    }
    @Test("Перевірка для неактивної задачі")
    public void testNextInactive() {
        Task task = Task.create("some", 10);
        task.setActive(false);
        assertEquals(NEVER, task.nextTimeAfter(0), describe(task)+" nextTimeAfter 0");
    }
}
