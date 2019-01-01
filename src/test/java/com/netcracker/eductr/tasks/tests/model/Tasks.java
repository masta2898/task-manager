package com.netcracker.eductr.tasks.tests.model;

import java.util.*;

public interface Tasks
{
    Iterable<Task> incoming(Iterable<Task> tasks, Date start, Date end);
    
    SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end);
}
