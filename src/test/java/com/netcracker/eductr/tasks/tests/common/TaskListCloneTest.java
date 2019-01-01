package com.netcracker.eductr.tasks.tests.common;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;
import static com.netcracker.eductr.tasks.tests.common.Dates.*;

public abstract class TaskListCloneTest extends AbstractListTest {

    public TaskListCloneTest(ProxyRepository repo)  
        throws ClassNotFoundException, ObsoleteTestException 
    {
        super(repo);
        boolean cloneInAbstract = repo.methodExists("TaskList", "clone", false);
        boolean cloneInImpl = repo.methodExists(getImplementation(), "clone", false);
        if (! cloneInAbstract && ! cloneInImpl)
            throw new ObsoleteTestException();
    }
    
    private TaskList createList() {
        TaskList tasks = TaskList.create();
        tasks.add(task("A"));
        tasks.add(task("B"));
        tasks.add(task("C"));
        return tasks;
    }
    
    public void testClone() {
        TaskList original = createList();
        TaskList copy = original.clone();
        
        Object origRef = repo.unwrap(original)[0];
        Object cloneRef = repo.unwrap(copy)[0];
        
        assert origRef != cloneRef :
            "{ x.clone() != x } не виконується";
            
        assert origRef.getClass() == cloneRef.getClass() :
            "{ x.clone().getClass() == x.getClass() } не виконується";
            
        assert origRef.equals(cloneRef) :
            "{ x.clone().equals(x) } не виконується";
    }
    
    @Test(value = "Перевірка незалежності клону та оригіналу при видаленні",
        depends = "#testClone")
    public void testCloneIndependenceRemove() {
        TaskList original = createList();
        TaskList etalon = createList();
        TaskList copy = original.clone();
        
        copy.remove(original.getTask(0));
        assert original.equals(etalon) : "Після зміни копії оригінал також змінився";
    }

    @Test(value = "Перевірка незалежності клону та оригіналу при додаванні",
        depends = "#testClone")
    public void testCloneIndependenceAdd() {
        TaskList original = createList();
        TaskList etalon = createList();
        TaskList copy = original.clone();
        
        copy.add(task("D"));
        assert original.equals(etalon) : "Після зміни копії оригінал також змінився";
    }
}





