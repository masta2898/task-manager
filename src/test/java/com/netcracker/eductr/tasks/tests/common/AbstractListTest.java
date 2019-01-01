package com.netcracker.eductr.tasks.tests.common;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;
import java.util.*;

public abstract class AbstractListTest
{
	protected final Task.Constructor Task;
	protected final TaskList.Constructor TaskList;
    protected final boolean oldTask;
    protected final ProxyRepository repo;
	
	public AbstractListTest(ProxyRepository repo) 
            throws ClassNotFoundException, ObsoleteTestException {
        oldTask = ! repo.constructorExists("Task", String.class, Date.class);
        this.repo = repo;
        Task = repo.createTypeProxy("Task", Task.Constructor.class);
        TaskList = repo.createTypeProxy(getImplementation(), TaskList.Constructor.class);
        if (repo.classExists("TaskList"))
            repo.createTypeProxy("TaskList", TaskList.Constructor.class);
        if (repo.classExists("ArrayTaskList"))
            repo.createTypeProxy("ArrayTaskList", TaskList.Constructor.class);
        if (repo.classExists("LinkedTaskList"))
            repo.createTypeProxy("LinkedTaskList", TaskList.Constructor.class);
	}
    
    protected abstract String getImplementation();
    
    protected Task task(String title) {
        return oldTask ? Task.create(title, 0) : Task.create(title, new Date(0));
    }
    
    protected Task task(String title, int time, boolean active) {
        Task task = oldTask ? Task.create(title, time) : Task.create(title, new Date(time));
        task.setActive(active);
        return task;
    }

    protected Task task(String title, int start, int end, int interval, boolean active) {
        Task task = oldTask ? 
            Task.create(title, start, end, interval) : 
            Task.create(title, new Date(start), new Date(end), interval);
        task.setActive(active);
        return task;
    }
    
    protected void assertContains(TaskList tasks, Task ... task) {
        boolean[] contains = new boolean[task.length];
        for (int i = 0; i < tasks.size(); i++) {
            for (int j = 0; j < task.length; j++)
                if (task[j].equals(tasks.getTask(i)))
                    contains[j] = true;
        }
        int notFound = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contains.length; i++)
            if (! contains[i])
                sb.append(notFound++ > 0 ? ", " : "").append(describe(task[i]));
        
        assert notFound == 0 : describe(tasks) +" повинен містити у собі "+ sb;
    }
}