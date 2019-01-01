package com.netcracker.eductr.tasks.tests;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tasks.tests.common.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;

@TestCase("4. Перевірка реалізації клонування ArrayTaskList")
public class T4_ArrayCloneTest extends TaskListCloneTest {

    public T4_ArrayCloneTest(ProxyRepository repo)  
        throws ClassNotFoundException, ObsoleteTestException 
    {
        super(repo);
    }
    
    protected String getImplementation() {
        return "ArrayTaskList";
    }

    @Test(value = "Перевірка клонування списку задач", 
        depends = "com.netcracker.eductr.tasks.tests.T4_ArrayEqualsTest#testEqualsChanged")
    public void testClone() {
        super.testClone();
    }
}