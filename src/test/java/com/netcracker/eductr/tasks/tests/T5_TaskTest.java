package com.netcracker.eductr.tasks.tests;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tasks.tests.common.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;
import static com.netcracker.eductr.tasks.tests.common.Dates.*;
import java.util.Date;

@TestCase("5. Перевірка методу Task.nextTimeAfter")
public class T5_TaskTest
{
	final Task.Constructor Task;
	
	public T5_TaskTest(ProxyRepository repo) throws ClassNotFoundException, ObsoleteTestException
	{
        repo.obsoleteIfConstructorExists("Task", String.class, Integer.TYPE);
        Task = repo.createTypeProxy("Task", Task.Constructor.class);
	}

    private void assertNextTimeAfter(Date exp, Task task, Date time) {
        assertEquals(exp, task.nextTimeAfter(time), describe(task) +" nextTimeAfter "+ describe(time));
    }
    
    @Test("Перевірка задачі, що не повторюється")
    public void testNextNonRepeative() {
        Task task = Task.create("some", TODAY);
        task.setActive(true);
        assertNextTimeAfter(TODAY, task, YESTERDAY);
        assertNextTimeAfter(TODAY, task, ALMOST_TODAY);
        assertNextTimeAfter(NEVER, task, TODAY);
        assertNextTimeAfter(NEVER, task, TOMORROW);
    }
    @Test("Перевірка задачі, що повторюється")
    public void testNextRepeative() {
        Task task = Task.create("some", TODAY, TOMORROW, HOUR);
        task.setActive(true);
        assertNextTimeAfter(TODAY, task, YESTERDAY);
        assertNextTimeAfter(TODAY, task, ALMOST_TODAY);
        assertNextTimeAfter(TODAY_1H, task, TODAY);
        assertNextTimeAfter(TODAY_2H, task, TODAY_1H);
        assertNextTimeAfter(TODAY_3H, task, TODAY_2H);
        assertNextTimeAfter(TOMORROW, task, ALMOST_TOMORROW);
        assertNextTimeAfter(NEVER, task, TOMORROW);
    }
    @Test("Перевірка неактивної задачі")
    public void testNextInactive() {
        Task task = Task.create("some", TODAY);
        task.setActive(false);
        assertNextTimeAfter(NEVER, task, YESTERDAY);
    }
}
