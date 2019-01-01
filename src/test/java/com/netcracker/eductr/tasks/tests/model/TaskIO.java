package com.netcracker.eductr.tasks.tests.model;

import java.io.*;

public interface TaskIO
{
    void write(TaskList tasks, OutputStream out);
    
    void read(TaskList tasks, InputStream in);
    
    void write(TaskList tasks, Writer out);
    
    void read(TaskList tasks, Reader in);
}
