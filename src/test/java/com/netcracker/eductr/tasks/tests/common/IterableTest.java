package com.netcracker.eductr.tasks.tests.common;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;
import java.util.*;

public abstract class IterableTest extends AbstractListTest
{
	public IterableTest(ProxyRepository repo) 
            throws ClassNotFoundException, ObsoleteTestException {
        super(repo);
        repo.obsoleteIfMethodNotExists("TaskList", "iterator");
        repo.registerWrapType(Iterator.class);
	}
    
    @Test("Перевірка ітерації по списку")
    public void testIteration() {
        Task[] array = {task("A"), task("B"), task("C"), task("D")};
        TaskList tasks = TaskList.create();
        Set<String> titles = new HashSet<String>();
        for (Task t : array) {
            tasks.add(t);
            titles.add(t.getTitle());
        }
        for (Task task : tasks) {
            assert titles.contains(task.getTitle()) : 
                "При ітерації знайдено невідому задачу: "+ describe(task);
            titles.remove(task.getTitle());
        }
        assert titles.isEmpty() : 
            "При ітерації не знайдено наступні задачі: "+ describe(titles.toArray());
    }
    
    @Test("Перевірка видалення задач")
    public void testIteratorRemove() {
        Task[] array = {task("A"), task("B"), task("C"), task("D")};
        TaskList tasks = TaskList.create();
        for (Task t : array) tasks.add(t);
        
        // saving order
        List<String> etalon = new ArrayList<String>(array.length);
        for (Task t : tasks)
            etalon.add(t.getTitle());
            
        Iterator<Task> it = tasks.iterator();
        Iterator<String> etalonIt = etalon.iterator();
        try {
            it.remove();
            assert false : "Виклик Iterator.remove без next повинен призводити до помилки";
        }
        catch (IllegalStateException e) {
            // OK
        }
        
        assertEquals(etalonIt.next(), it.next().getTitle(), "it.next().getTitle()");
        it.remove(); etalonIt.remove();
        assertEquals(etalonIt.next(), it.next().getTitle(), "it.next().getTitle()");
        it.next(); etalonIt.next();
        it.remove(); etalonIt.remove();
        it.next(); etalonIt.next();
        
        it = tasks.iterator(); 
        etalonIt = etalon.iterator();
        assertEquals(etalonIt.next(), it.next().getTitle(), "Перший елемент після видалення");
        assertEquals(etalonIt.next(), it.next().getTitle(), "Другий елемент після видалення");
        assert etalonIt.hasNext() == it.hasNext() : "Після видалення у списку має бути два елемента";
    }
    
    
    @Test("Перевірка паралельної ітерації")
    public void testParallelIteration() {
        Task[] array = {task("A"), task("B"), task("C")};
        TaskList tasks = TaskList.create();
        for (Task t : array) tasks.add(t);
        Set<String> pairs = new HashSet<String>(
            Arrays.asList( "AA", "AB", "AC", "BA", "BB", "BC", "CA", "CB", "CC" ));
        for (Task a : tasks)
            for (Task b : tasks) {
                assert pairs.contains(a.getTitle() + b.getTitle()) :
                    "Паралельна ітерація не повинна призводити до "+ a.getTitle() + b.getTitle();
                pairs.remove(a.getTitle() + b.getTitle());
            }
        assert pairs.isEmpty() : "Паралельна ітерація повинна призводити до "+ 
            describe(pairs.toArray());
    }
}