package com.netcracker.eductr.tasks.tests;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tasks.tests.common.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;
import java.util.Date;

@TestCase("4. Перевірка методів equals & hashCode в ArrayTaskList")
public class T4_ArrayEqualsTest extends ListEqualsTest
{
	public T4_ArrayEqualsTest(ProxyRepository repo) throws ClassNotFoundException, ObsoleteTestException
	{
        super(repo);
	}
    
    protected String getImplementation() {
        return "ArrayTaskList";
    }
}
