package com.netcracker.eductr.tasks.tests;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tasks.tests.common.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;

@TestCase("2. Перевірка основних методів класу ArrayTaskList")
public class T2_ArrayTest extends TaskListTest
{
	public T2_ArrayTest(ProxyRepository repo) 
            throws ClassNotFoundException, ObsoleteTestException {
        super(repo);
	}
    
    @Override
    protected String getImplementation() {
        return "ArrayTaskList";
    }
}