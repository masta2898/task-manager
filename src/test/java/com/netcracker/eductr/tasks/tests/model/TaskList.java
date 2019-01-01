package com.netcracker.eductr.tasks.tests.model;

public interface TaskList extends Iterable<Task>
{
    interface Constructor
    {
        TaskList create();
    }
    
    void add(Task task);
    
    boolean remove(Task task);
    
    int size();
    
    Task getTask(int index);
    
    TaskList incoming(int from, int to);
    
    TaskList clone();
}