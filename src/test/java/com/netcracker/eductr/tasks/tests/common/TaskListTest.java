package com.netcracker.eductr.tasks.tests.common;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;
import java.util.*;

public abstract class TaskListTest extends AbstractListTest
{
	public TaskListTest(ProxyRepository repo) 
            throws ClassNotFoundException, ObsoleteTestException {
        super(repo);
	}
    
    @Test("Перевірка методів size, add та getTask")
    public void testSizeAddGet() {
        TaskList tasks = TaskList.create();
        assertEquals(0, tasks.size(), describe(tasks) +".size()");
        Task[] ts = {task("A"), task("B"), task("C")};
        for (Task t : ts)
            tasks.add(t);
        assertEquals(ts.length, tasks.size(), describe(tasks) +".size()");
        for (Task t : ts) {
            assertContains(tasks, t);
        }
    }    
    
    @Test("Перевірка видалення задачі")
    public void testRemove() {
        TaskList tasks = TaskList.create();
        Task[] ts = {task("A"),task("B"),task("C"),task("D"),task("E")};
        for (Task t : ts)
            tasks.add(t);
            
        // remove first
        assert tasks.remove(ts[0]) : describe(tasks) +".remove(A) повинно бути істинним";
        assertEquals(4, tasks.size(), describe(tasks) +".size()");
        assertContains(tasks, ts[1], ts[2], ts[3], ts[4]);
        
        // remove last
        assert tasks.remove(ts[4]) : describe(tasks) +".remove(E) повинно бути істинним";
        assertEquals(3, tasks.size(), describe(tasks) +".size()");
        assertContains(tasks, ts[1], ts[2], ts[3]);

        // remove middle
        assert tasks.remove(ts[2]) : describe(tasks) +".remove(C) повинно бути істинним";
        assertEquals(2, tasks.size(), describe(tasks) +".size()");
        assertContains(tasks, ts[1], ts[3]);
        
        // remove unexistent
        assert !tasks.remove(task("F")) : describe(tasks) +".remove(D) не повинно бути істинним";
        
        // test multiple remove
        tasks.add(ts[0]);
        tasks.add(ts[0]);
        assertEquals(4, tasks.size(), describe(tasks) +".size()");
        assertContains(tasks, ts[1], ts[0], ts[3]);
        assert tasks.remove(ts[0]) : describe(tasks) +".remove(A) повинно бути істинним";
        assertEquals(3, tasks.size(), describe(tasks) +".size()");
        assertContains(tasks, ts[0], ts[1], ts[3]);
        assert tasks.remove(ts[0]) : describe(tasks) +".remove(A) повинно бути істинним";
        assertEquals(2, tasks.size(), describe(tasks) +".size()");
        assertContains(tasks, ts[1], ts[3]);
    }
    
    @Test("Перевірка incoming для неактивних задач")
    public void testIncomingInactive() throws ObsoleteTestException {
        repo.obsoleteIfMethodNotExists(getImplementation(), "incoming", Integer.TYPE, Integer.TYPE);
        TaskList tasks = TaskList.create();
        Task[] ts = {task("A",0,false), task("B",1,false), task("C",2,false)};
        for (Task t : ts) tasks.add(t);
        TaskList res = tasks.incoming(0, 1000);
        assert res.size() == 0 : "incoming(0, 1000) має бути пустим, але насправді: "+ describe(res);
    }
    @Test("Перевірка incoming для активних задач")
    public void testIncoming() throws ObsoleteTestException {
        repo.obsoleteIfMethodNotExists(getImplementation(), "incoming", Integer.TYPE, Integer.TYPE);
        // range: 50 60
        Task[] ts = {
            task("Simple IN", 55, true), 
            task("Simple OUT", 10, true),
            task("Inactive OUT", 0, 1000, 1, false), 
            task("Simple bound OUT", 50, true), 
            task("Simple bound IN", 60, true),
            task("Repeat inside IN", 51, 58, 2, true),
            task("Repeat outside IN", 0, 100, 5, true),
            task("Repeat outside OUT", 0, 100, 22, true),
            task("Repeat left OUT", 0, 40, 1, true),
            task("Repeat right OUT", 65, 100, 1, true),
            task("Repeat left intersect IN 1", 0, 55, 13, true),
            task("Repeat left intersect IN 2", 0, 60, 30, true),
            task("Repeat left intersect OUT", 0, 55, 22, true),
            task("Repeat right intersect IN", 55, 100, 20, true)
        };
        TaskList tasks = TaskList.create();
        for (Task t : ts) tasks.add(t);
        Set<String> incoming = new HashSet<String>();
        for (Task t : ts)
            if (t.getTitle().contains("IN"))
                incoming.add(t.getTitle());
        TaskList res = tasks.incoming(50, 60);
        for (int i = 0; i < res.size(); i++) {
            assert incoming.contains(res.getTask(i).getTitle()) : "incoming(50,60) не повинно містити "+ describe(res.getTask(i));
            incoming.remove(res.getTask(i).getTitle());
        }
        assert incoming.isEmpty() : "incoming(50,60) повинно містити "+ describe(incoming.toArray());
    }
}
