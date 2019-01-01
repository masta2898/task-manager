package com.netcracker.eductr.tasks.tests;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tasks.tests.common.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;

@TestCase("4. Перевірка імплементації Iterable у ArrayTaskList")
public class T4_ArrayIteratorsTest extends IterableTest
{
	public T4_ArrayIteratorsTest(ProxyRepository repo) 
            throws ClassNotFoundException, ObsoleteTestException {
        super(repo);
	}

    @Override
    protected String getImplementation() {
        return "ArrayTaskList";
    }
}
