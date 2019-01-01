package com.netcracker.eductr.tasks.tests;

import static com.netcracker.eductr.tester.TestHelper.describe;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tasks.tests.common.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;

@TestCase("4. Перевірка імплементації Iterable у LinkedTaskList")
public class T4_LinkedIteratorsTest extends IterableTest
{
	public T4_LinkedIteratorsTest(ProxyRepository repo) 
            throws ClassNotFoundException, ObsoleteTestException {
        super(repo);
	}

    @Override
    protected String getImplementation() {
        return "LinkedTaskList";
    }
}
