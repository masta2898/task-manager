package com.netcracker.eductr.tasks.tests;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tasks.tests.common.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;
import static com.netcracker.eductr.tasks.tests.common.Dates.*;
import java.io.*;

@TestCase("6. Перевірка методів класу TaskIO")
public class T6_TaskIOTest
{
    final TaskIO TaskIO;
	final Task.Constructor Task;
	final TaskList.Constructor TaskList;
    
	public T6_TaskIOTest(ProxyRepository repo) 
            throws ClassNotFoundException, ObsoleteTestException {
        
        TaskIO = repo.createTypeProxy("TaskIO", TaskIO.class);
        Task = repo.createTypeProxy("Task", Task.Constructor.class);
        TaskList = repo.createTypeProxy("ArrayTaskList", TaskList.Constructor.class);
        repo.registerUnwrapType(TaskList.class, "TaskList");
	}

    private TaskList createList() {
        TaskList tasks = TaskList.create();
        tasks.add(Task.create("A", TODAY));
        tasks.add(Task.create("B", TODAY, TOMORROW, HOUR));
        Task t = Task.create("C", TODAY);
        t.setActive(true);
        tasks.add(t);
        t = Task.create("B", TODAY, TOMORROW, HOUR);
        t.setActive(true);
        tasks.add(t);
        return tasks;
    }
    
    @Test("Перевірка бінарного считування/запису")
    public void testBinary() throws IOException {
        TaskList tasks = createList();
        
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);

        TaskIO.write(tasks, out);
        TaskList result = TaskList.create();
        TaskIO.read(result, in);
        
        assertEquals(tasks, result, describe(result));
    }

    @Test("Перевірка текстового считування/запису")
    public void testText() throws IOException {
        TaskList tasks = createList();
        
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);

        TaskIO.write(tasks, new OutputStreamWriter(out));
        TaskList result = TaskList.create();
        TaskIO.read(result, new InputStreamReader(in));
        
        assertEquals(tasks, result, describe(result));
    }
}


