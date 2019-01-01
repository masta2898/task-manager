package com.netcracker.eductr.tasks.tests;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tasks.tests.common.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;
import static com.netcracker.eductr.tasks.tests.common.Dates.*;
import java.util.Date;

@TestCase("4. Перевірка реалізації клонування задач")
public class T4_TaskCloneTest {
	final Task.Constructor Task;
    final boolean oldTask;
    final ProxyRepository repo;

    public T4_TaskCloneTest(ProxyRepository repo)  
        throws ClassNotFoundException, ObsoleteTestException 
    {
        this.repo = repo;
        oldTask = ! repo.constructorExists("Task", String.class, Date.class);
        Task = repo.createTypeProxy("Task", Task.Constructor.class);
        repo.obsoleteIfMethodNotDeclared("Task", "clone");
    }
    
    @Test(value = "Перевірка клонування задач", 
        depends = "com.netcracker.eductr.tasks.tests.T4_TaskEqualsTest#testEqualsChanged")
    public void testClone() {
        Task original = oldTask ? Task.create("Original", 0) : Task.create("Original", TODAY);
        Task copy = original.clone();
        
        Object origRef = repo.unwrap(original)[0];
        Object cloneRef = repo.unwrap(copy)[0];
        
        assert origRef != cloneRef :
            "{ x.clone() != x } не виконується";
            
        assert origRef.getClass() == cloneRef.getClass() :
            "{ x.clone().getClass() == x.getClass() } не виконується";
            
        assert origRef.equals(cloneRef) :
            "{ x.clone().equals(x) } не виконується";
    }
    
    @Test(value = "Перевірка незалежності клону та оригіналу",
        depends = "#testClone")
    public void testCloneIndependence() {
        Task original = oldTask ? Task.create("Original", 0) : Task.create("Original", TODAY);
        Task copy = original.clone();
        
        copy.setTitle("Copy");
        assert ! original.equals(copy) : "Після зміни title копії оригінал також змінився";

        if (! oldTask) {
            copy = original.clone();
            copy.setTime(TOMORROW);
            assert ! original.equals(copy) : "Після зміни time копії оригінал також змінився";
        }
    }
}





