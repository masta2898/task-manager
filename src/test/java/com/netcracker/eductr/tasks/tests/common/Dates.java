package com.netcracker.eductr.tasks.tests.common;

import java.util.Date;

public class Dates
{
    public static final int MINUTE = 60;
    public static final int HOUR = 60 * MINUTE;
    public static final int DAY = 24 * HOUR;
    public static final int MONTH = 30 * DAY;
    public static final int YEAR = 365 * DAY;

    public static Date seconds(int secs) {
        return new Date(secs * 1000);
    }
    
    public static class ImmutableDate extends Date {
        public ImmutableDate(long time) {
            super(time);
        }
        public ImmutableDate() {
            super();
        }
        @Override
        public void setTime(long time) {
            throw new UnsupportedOperationException("Date should not be changed");
        }
        @Override
        public Date clone() {
            return new Date(getTime());
        }
    }
    
    public static final Date TODAY              = new ImmutableDate(); 
    public static final Date TOMORROW           = new ImmutableDate(TODAY.getTime() + DAY * 1000); 
    public static final Date NEVER              = null; 
    public static final Date YESTERDAY          = new ImmutableDate(TODAY.getTime() - DAY * 1000); 
    public static final Date ALMOST_TODAY       = new ImmutableDate(TODAY.getTime() - 1); 
    public static final Date TODAY_1H           = new ImmutableDate(TODAY.getTime() + HOUR * 1000); 
    public static final Date TODAY_2H           = new ImmutableDate(TODAY.getTime() + 2 * HOUR * 1000); 
    public static final Date TODAY_3H           = new ImmutableDate(TODAY.getTime() + 3 * HOUR * 1000); 
    public static final Date TODAY_4H           = new ImmutableDate(TODAY.getTime() + 4 * HOUR * 1000); 
    public static final Date TODAY_5H           = new ImmutableDate(TODAY.getTime() + 5 * HOUR * 1000); 
    public static final Date TODAY_6H           = new ImmutableDate(TODAY.getTime() + 6 * HOUR * 1000); 
    public static final Date TODAY_7H           = new ImmutableDate(TODAY.getTime() + 7 * HOUR * 1000); 
    public static final Date TODAY_8H           = new ImmutableDate(TODAY.getTime() + 8 * HOUR * 1000); 
    public static final Date TODAY_9H           = new ImmutableDate(TODAY.getTime() + 9 * HOUR * 1000); 
    public static final Date ALMOST_TOMORROW    = new ImmutableDate(TOMORROW.getTime() - 1); 
}
