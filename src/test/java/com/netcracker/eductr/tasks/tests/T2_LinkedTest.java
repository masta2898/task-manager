package com.netcracker.eductr.tasks.tests;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tasks.tests.common.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;

@TestCase("2. Перевірка основних методів класу LinkedTaskList")
public class T2_LinkedTest extends TaskListTest
{
	public T2_LinkedTest(ProxyRepository repo) 
            throws ClassNotFoundException, ObsoleteTestException {
        super(repo);
	}
    
    @Override
    protected String getImplementation() {
        return "LinkedTaskList";
    }
}