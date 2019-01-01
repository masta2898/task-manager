package com.netcracker.eductr.tasks.tests;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tasks.tests.common.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;

@TestCase("4. Перевірка реалізації клонування LinkedTaskList")
public class T4_LinkedCloneTest extends TaskListCloneTest {

    public T4_LinkedCloneTest(ProxyRepository repo)  
        throws ClassNotFoundException, ObsoleteTestException 
    {
        super(repo);
    }
    
    protected String getImplementation() {
        return "LinkedTaskList";
    }

    @Test(value = "Перевірка клонування списку задач", 
        depends = "com.netcracker.eductr.tasks.tests.T4_LinkedEqualsTest#testEqualsChanged")
    public void testClone() {
        super.testClone();
    }
}