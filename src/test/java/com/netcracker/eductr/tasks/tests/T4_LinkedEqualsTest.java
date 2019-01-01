package com.netcracker.eductr.tasks.tests;

import static com.netcracker.eductr.tester.TestHelper.describe;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tasks.tests.common.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;
import java.util.Date;

@TestCase("4. Перевірка методів equals & hashCode в LinkedTaskList")
public class T4_LinkedEqualsTest extends ListEqualsTest
{
	public T4_LinkedEqualsTest(ProxyRepository repo) throws ClassNotFoundException, ObsoleteTestException
	{
        super(repo);
	}
    
    protected String getImplementation() {
        return "LinkedTaskList";
    }
}
