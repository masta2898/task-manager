package com.netcracker.eductr.tasks.tests.common;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;
import java.util.Date;

public abstract class ListEqualsTest extends EqualsTest<TaskList>
{
	final Task.Constructor Task;
	final TaskList.Constructor TaskList;
    final boolean isOld;
	
	public ListEqualsTest(ProxyRepository repo) throws ClassNotFoundException, ObsoleteTestException
	{
        isOld = ! repo.constructorExists("Task", String.class, Date.class);
        Task = repo.createTypeProxy("Task", Task.Constructor.class);
        TaskList = repo.createTypeProxy(getImplementation(), TaskList.Constructor.class);
        boolean equalsInAbstract = repo.classExists("TaskList") 
            && repo.methodExists("TaskList", "equals", false, Object.class);
        boolean equalsInImpl = repo.methodExists(getImplementation(), "equals", false, Object.class);
        if (! equalsInAbstract && ! equalsInImpl)
            throw new ObsoleteTestException();
	}
    
    protected abstract String getImplementation();
    
    protected TaskList createA() {
        Task t = isOld ?
            Task.create("Task A", 10) :
            Task.create("Task A", new Date(10));
        t.setActive(true);
        TaskList a = TaskList.create();
        for (int i = 0; i < 10; i++)
            a.add(t);
        return a;
    }
    protected TaskList createB() {
        Task t = isOld ?
            Task.create("Task B", 20, 30, 5) :
            Task.create("Task B", new Date(20), new Date(30), 5);
        TaskList b = TaskList.create();
        for (int i = 0; i < 5; i++)
            b.add(t);
        return b;
    }
    protected void modify(TaskList t) {
        while (t.size() > 0)
            t.remove(t.getTask(t.size() - 1));
        Task task = isOld ?
            Task.create("Task A", 10) :
            Task.create("Task A", new Date(10));
        for (int i = 0; i < 10; i++)
            t.add(task);
    }
}