package application.models;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;

public class TestRangeQuestion {

    private RangeQuestion q1;
    private RangeQuestion q2;
    private RangeQuestion q3;
    ArrayList<Integer> answer1 = new ArrayList<>(Arrays.asList(21, 23, 33));
    ArrayList<Integer> answer2 = new ArrayList<>(Arrays.asList(10, 10, 10));

    @Before
    public void setup(){

        q1 = new RangeQuestion("How old are you?", 0, 117); //Fun fact: The oldest person alive today is 117 in Japan;)
        q2 = new RangeQuestion("How likely is it that this group will ace the project?", 1, 10);
        q3 = new RangeQuestion();
    }

    @Test
    public void TestGetAndSetMin(){
        q3.setMin(2);
        assertEquals(q1.getMin(), 0);
        assertEquals(q2.getMin(), 1);
        assertEquals(q3.getMin(), 2);
    }

    @Test
    public void TestGetAndSetMax(){
        q3.setMax(77);
        assertEquals(q1.getMax(), 117);
        assertEquals(q2.getMax(), 10);
        assertEquals(q3.getMax(), 77);
    }

    @Test
    public void TestGetAndSetAnswer(){
        q1.setAnswer(answer1);
        q2.setAnswer(answer2);

        assertEquals(q1.getAnswer(), new ArrayList<>(Arrays.asList(21, 23, 33)));
        assertEquals(q2.getAnswer(), new ArrayList<>(Arrays.asList(10, 10, 10)));
    }
    @Test
    public void TestGetAndSetQuestion(){

        assertTrue(q1.addAnswer(32));
        assertTrue(q1.addAnswer(5));
        assertTrue(q1.getAnswer().contains(32));
        assertEquals(2, q1.getAnswer().size());
    }
}
