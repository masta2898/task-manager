package com.netcracker.eductr.tasks.tests.common;

import com.netcracker.eductr.tasks.tests.model.*;
import com.netcracker.eductr.tester.*;
import com.netcracker.eductr.tester.proxy.*;
import static com.netcracker.eductr.tester.TestHelper.*;

public abstract class EqualsTest<T>
{
    protected abstract T createA();
    protected abstract T createB();
    protected abstract void modify(T t);
    
    @Test("a.equals(a), a != null")
    public void testEqualsToItself() {
        T a = createA();
        assert a.equals(a) : "не виконується для "+ describe(a);
    }

    @Test("a = b <=> b = a")
    public void testEquals() {
        T a = createA();
        T b = createA();
        assert a.equals(b) : "Об'єкти мають бути рівні: "+
            describe(a) +" та "+ describe(b);
        assert b.equals(a) : "не виконується для "+
            describe(a) +" та "+ describe(b);
    }

    @Test("Перевірка рівності після зміни параметрів")
    public void testEqualsChanged() {
        T a = createA();
        T b = createB();
        modify(a);
        modify(b);
        assert a.equals(b) :
            "Об'єкти повинні бути рівними: "+
            describe(a) +" та "+ describe(b);
    }

    @Test("x.equals(null) == false")
    public void testEqualsNull() {
        T a = createA();
        assert ! a.equals(null) : "Не виконується";
    }

    @Test("Порівняння задачі із іншими об'єктами")
    public void testEqualsToString() {
        T a = createA();
        assert ! a.equals("some string") : "Об'єкти не повинні дорівнювати об'єктам зовсім іншого типу";
    }

    @Test(value = "Перевірка hashCode", depends = "#testEqualsChanged")
    public void testHashCode() {
        T a = createA();
        T b = createB();
        modify(a);
        modify(b);
        
        assert a.hashCode() == b.hashCode()
            : "Хеш-коди для "+ describe(a) +" ("+ a.hashCode() +") та "+
                describe(b) +" ("+ b.hashCode() +") мають бути рівні";
    }    
}