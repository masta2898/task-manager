package com.netcracker.eductr.tasks.tests;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tasks.tests.common.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;
import static com.netcracker.eductr.tasks.tests.common.Dates.*;
import java.util.*;

@TestCase("5. Перевірка методів класу Tasks")
public class T5_TasksTest
{
    final Tasks Tasks;
	final Task.Constructor Task;
    
    final ProxyRepository repo;

	public T5_TasksTest(ProxyRepository repo) 
            throws ClassNotFoundException, ObsoleteTestException {
        
        this.repo = repo;
        Tasks = repo.createTypeProxy("Tasks", Tasks.class);
        Task = repo.createTypeProxy("Task", Task.Constructor.class);
        repo.registerWrapType(Iterable.class);
        repo.registerWrapType(Iterator.class);
	}

    protected Task task(String title, int time, boolean active) {
        Task task = Task.create(title, seconds(time));
        task.setActive(active);
        return task;
    }

    protected Task task(String title, int start, int end, int interval, boolean active) {
        Task task = Task.create(title, seconds(start), seconds(end), interval);
        task.setActive(active);
        return task;
    }
    
    private Set<Task> set(Task ... tasks) {
        return new HashSet<Task>(Arrays.asList(tasks));
    }
    
    @Test("Перевірка incoming для неактивних задач")
    public void testIncomingInactive() throws ObsoleteTestException {
        Set<Task> input = set(task("A",0,false), task("B",1,false), task("C",2,false));
        Date start = seconds(0);
        Date end = seconds(1000);
        Iterable<?> res = Tasks.incoming(input, start, end);
        assert ! res.iterator().hasNext() : 
            "incoming("+ input +", "+ start +", "+ end +") має бути пустим, але насправді: "+ res;
    }

    @Test("Перевірка incoming для активних задач")
    public void testIncoming() throws ObsoleteTestException {
        // range: 50 60
        Set<Task> ts = set(
            task("Simple IN", 55, true), 
            task("Simple OUT", 10, true),
            task("Inactive OUT", 0, 1000, 1, false), 
            task("Simple bound OUT", 50, true), 
            task("Simple bound IN", 60, true),
            task("Repeat inside IN", 51, 58, 2, true),
            task("Repeat outside IN", 0, 100, 5, true),
            task("Repeat outside OUT", 0, 100, 22, true),
            task("Repeat left OUT", 0, 40, 1, true),
            task("Repeat right OUT", 65, 100, 1, true),
            task("Repeat left intersect IN 1", 0, 55, 13, true),
            task("Repeat left intersect IN 2", 0, 60, 30, true),
            task("Repeat left intersect OUT", 0, 55, 22, true),
            task("Repeat right intersect IN", 55, 100, 20, true)
        );
        Set<String> incoming = new HashSet<String>();
        for (Task t : ts)
            if (t.getTitle().contains("IN"))
                incoming.add(t.getTitle());
        Date start = seconds(50);
        Date end = seconds(60);
        Iterable<Task> res = Tasks.incoming(ts, start, end);
        String call = "incoming("+ ts +", "+ start +", "+ end +")";
        for (Task t : res) {
            assert incoming.contains(t.getTitle()) : call +" не повинно містити "+ t;
            incoming.remove(t.getTitle());
        }
        assert incoming.isEmpty() : call +" повинно містити "+ incoming;
    }
    
    @Test("Перевірка calendar")
    public void testTimeline() {
        Task daily = Task.create("Daily", YESTERDAY, TOMORROW, DAY);
        Task hourly = Task.create("Hourly", TODAY, TOMORROW, HOUR);
        Task every3h = Task.create("Every 3 hours", TODAY_1H, TOMORROW, 3*HOUR);
        daily.setActive(true);
        hourly.setActive(true);
        every3h.setActive(true);
        
        SortedMap<Date, Set<Task>> timeline = new TreeMap<Date, Set<Task>>();
        timeline.put(TODAY, set(daily, hourly));
        timeline.put(TODAY_1H, set(hourly, every3h));
        timeline.put(TODAY_2H, set(hourly));
        timeline.put(TODAY_3H, set(hourly));
        timeline.put(TODAY_4H, set(hourly, every3h));
        SortedMap<Date, Set<Task>> result = 
            Tasks.calendar(set(daily, hourly, every3h), ALMOST_TODAY, TODAY_4H);
        assertEqual(timeline.keySet(), result.keySet(), "Неочікувані дати у календарі,");
        for (Date date : timeline.keySet()) {
            assertEqual(timeline.get(date), repo.wrapCollection(result.get(date)), "Для "+ describe(date));
        }
    }
    
    private <T> void assertEqual(Set<T> expected, Set<T> actual, String msg) {
        if (! expected.equals(actual)) {
            Set<T> exp = new HashSet<T>(expected);
            Set<T> notexp = new HashSet<T>(actual);
            exp.removeAll(actual);
            notexp.removeAll(expected);
            assert exp.isEmpty() : msg+ " не знайдені "+ exp;
            assert notexp.isEmpty() : msg+ " знайдені зайві: "+ exp;
        }
    }
}


