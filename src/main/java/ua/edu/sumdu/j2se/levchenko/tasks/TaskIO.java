package ua.edu.sumdu.j2se.levchenko.tasks;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskIO {
    public static void writeBinary(TaskList tasks, File file) throws IOException {
        write(tasks, new FileOutputStream(file));
    }

    public static void readBinary(TaskList tasks, File file) throws IOException {
        read(tasks, new FileInputStream(file));
    }

    public static void writeText(TaskList tasks, File file) throws IOException {
        write(tasks, new FileWriter(file));
    }

    public static void readText(TaskList tasks, File file) throws IOException, ParseException {
        read(tasks, new FileReader(file));
    }

    public static void write(TaskList tasks, OutputStream out) throws IOException {
        try {
            DataOutputStream dataOutput = new DataOutputStream(out);

            try {
                dataOutput.writeInt(tasks.size());
                Iterator iterator = tasks.iterator();
                Task task;

                while (iterator.hasNext()) {
                    task = (Task) iterator.next();
                    dataOutput.writeInt(task.getTitle().length());
                    dataOutput.writeUTF(task.getTitle());
                    if (task.isActive()) {
                        dataOutput.writeInt(1);
                    } else {
                        dataOutput.writeInt(0);
                    }

                    dataOutput.writeInt(task.getRepeatInterval());

                    if (task.isRepeated()) {
                        dataOutput.writeLong(task.getStartTime().getTime());
                        dataOutput.writeLong(task.getEndTime().getTime());
                    } else {
                        dataOutput.writeLong(task.getTime().getTime());
                    }
                }
            } finally {
                dataOutput.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Невозможно произвести запись в файл.");
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вівода =: " + e);
        }

    }

    public static void write(TaskList tasks, Writer out) throws IOException {
        PrintWriter writer = new PrintWriter(out);

        try {
            Task task;
            String isActive;
            String endLine;
            String title;
            String formatted;
            int interval;
            int intervalDay;
            int intervalHour;
            int intervalMin;
            int intervalSec;
            StringBuilder stringBuilder = new StringBuilder();
            int i = 1;
            int listSize = tasks.size();

            writer.print(listSize);
            writer.println();

            Iterator iterator = tasks.iterator();
            while (iterator.hasNext()) {
                task = (Task) iterator.next();
                title = task.getTitle().replace("\"", "\"\"");

                if (task.isActive()) {
                    isActive = "";
                } else {
                    isActive = " inactive";
                }

                if (i == listSize) {
                    endLine = ".";
                } else {
                    endLine = ";";
                }

                if (!task.isRepeated()) {
                    formatted = String.format("\"%s\" " + "at [%tF %tT.%tL]%s%s",
                            title,
                            task.getTime(),
                            task.getTime(),
                            task.getTime(),
                            isActive,
                            endLine);
                } else {

                    interval = task.getRepeatInterval();
                    intervalDay = ((interval / 60) / 60) / 24;

                    if (intervalDay == 0) {
                        stringBuilder.append("");
                    } else {
                        if (intervalDay > 1) {
                            stringBuilder.append(intervalDay).append(" days");

                        } else {
                            stringBuilder.append(intervalDay).append(" day");
                        }
                    }

                    intervalHour = (interval / 60) / 60;
                    if (intervalHour == 0 || intervalHour == 24) {
                        stringBuilder.append("");
                    } else {
                        if (intervalDay != 0) {
                            stringBuilder.append(" ");
                        }
                        if (intervalHour > 1) {
                            stringBuilder.append(intervalHour).append(" hours");
                        } else {
                            stringBuilder.append(intervalHour).append(" hour");
                        }
                    }

                    intervalMin = (interval / 60) - intervalHour * 60;
                    if (intervalMin == 0 || intervalMin == 60) {
                        stringBuilder.append("");
                    } else {
                        if (intervalHour != 0) {
                            stringBuilder.append(" ");
                        }
                        if (intervalMin > 1) {
                            stringBuilder.append(intervalMin).append(" minutes");

                        } else {
                            stringBuilder.append(intervalMin).append(" minute");
                        }
                    }

                    intervalSec = interval - (intervalHour * 60 * 60
                            + intervalMin * 60);
                    if (intervalSec == 0 || intervalSec == 60) {
                        stringBuilder.append("");
                    } else {
                        if (intervalMin != 0) {
                            stringBuilder.append(" ");
                        }
                        if (intervalSec > 1) {
                            stringBuilder.append(intervalSec).append(" seconds");

                        } else {
                            stringBuilder.append(intervalSec).append(" second");
                        }
                    }

                    formatted = String.format("\"%s\" "
                                    + "from [%tF %tT.%tL] "
                                    + "to [%tF %tT.%tL] "
                                    + "every [%s]%s%s",
                            title,
                            task.getStartTime(),
                            task.getStartTime(),
                            task.getStartTime(),
                            task.getEndTime(),
                            task.getEndTime(),
                            task.getEndTime(),
                            stringBuilder.toString(),
                            isActive,
                            endLine
                    );

                }
                stringBuilder.delete(0, stringBuilder.length());
                writer.print(formatted);
                writer.println();
                i++;
            }
        } finally {
            writer.close();
        }
    }

    public static void read(TaskList tasks, InputStream in) throws IOException {
        try {
            DataInputStream dataInput = new DataInputStream(in);

            try {
                Task task;
                int titleLength;
                int taskActive = 0;
                boolean isActive;
                boolean isRepeated;
                int interval = 0;
                long time = 0;
                long startTime = 0;
                long endTime = 0;
                String title;
                int listSize = dataInput.readInt();
                int i = 0;

                while (i < listSize) {
                    titleLength = dataInput.readInt();
                    title = dataInput.readUTF();
                    taskActive = dataInput.readInt();

                    if (taskActive == 1) {
                        isActive = true;
                    } else {
                        isActive = false;
                    }

                    interval = dataInput.readInt();

                    if (interval == 0) {
                        time = dataInput.readLong();
                        isRepeated = false;
                    } else {
                        startTime = dataInput.readLong();
                        endTime = dataInput.readLong();
                        isRepeated = true;
                    }
                    if (!isRepeated) {
                        task = new Task(title, new Date(time));
                    } else {
                        task = new Task(title, new Date(startTime),
                                new Date(endTime), interval);
                    }
                    task.setActive(isActive);

                    tasks.add(task);
                    i++;
                }
            } finally {
                dataInput.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Невозможно произвести запись в файл.");
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода =: " + e);
        }
    }

    public static void read(TaskList tasks, Reader in) throws IOException, ParseException {
        try {
            BufferedReader reader = new BufferedReader(in);

            try {
                String input;
                Task task;
                String title;
                boolean isActive;
                boolean isRepeated;

                SimpleDateFormat formatter =
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

                String tSize = reader.readLine();
                int size = Integer.parseInt(tSize);

                int i = 1;
                while (i <= size) {
                    input = reader.readLine();
                    title = input.substring(input.indexOf("\"") + 1,
                            input.lastIndexOf("\"")).replace("\"\"", "\"");

                    if (input.contains("] inactive")) {
                        isActive = false;
                    } else {
                        isActive = true;
                    }

                    if (input.contains("from [") & input.contains("to [")) {
                        isRepeated = true;
                    } else {
                        isRepeated = false;
                    }

                    if (!isRepeated) {
                        String timeInString = input.substring(input.indexOf("[") + 1,
                                input.lastIndexOf("]"));

                        Date time;
                        time = formatter.parse(timeInString);
                        task = new Task(title, time);
                    } else {
                        String startTimeInString = input.substring(input.indexOf("[")
                                + 1, input.indexOf("]"));
                        String endTimeInString = input.substring(input.indexOf("[", input.indexOf("]")) + 1,
                                input.indexOf("]", input.indexOf("]") + 1));
                        String intervalInString = input.substring(input.lastIndexOf("[") + 1,
                                input.lastIndexOf("]"));

                        Date start = formatter.parse(startTimeInString);
                        Date end = formatter.parse(endTimeInString);
                        int day;
                        int hour;
                        int minute;
                        int second;
                        int interval;

                        String regDay = "\\d+ day| days";
                        day = parseInterval(intervalInString, regDay);

                        String regHour = "\\d+ hour| hours";
                        hour = parseInterval(intervalInString, regHour);

                        String regMin = "\\d+ minute| minutes";
                        minute = parseInterval(intervalInString, regMin);

                        String regSec = "\\d+ second| seconds";
                        second = parseInterval(intervalInString, regSec);

                        interval = day * 24 * 60 * 60
                                + hour * 60 * 60
                                + minute * 60
                                + second;
                        task = new Task(title, start, end, interval);
                    }

                    task.setActive(isActive);
                    tasks.add(task);
                    i++;
                }
            } finally {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Невозможно произвести запись в файл.");
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода =: " + e);
        }
    }

    private static int parseInterval(String input, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        if (m.find()) {
            String interval = input.substring(m.start(), m.end()).replaceAll("\\D", "");
            return Integer.parseInt(interval);
        }
        return 0;
    }
}
