package other;

import student.PriorityQueue;
import student.PriorityQueueImpl;

/**
 * Created by Sam on 28/03/2016.
 */
public class PriorityQueueTest {

    public static void main(String[] args) {
        PriorityQueue<String> pqs = new PriorityQueueImpl<>();
        pqs.add("First", 5.00);
        pqs.add("Second", 1.00);
        pqs.add("Third", 3.00);
        System.out.println(pqs.toString());
        System.out.println(pqs.peek());
        pqs.poll();
        System.out.println(pqs.toString());
        pqs.updatePriority("First", 2.00);
        System.out.println(pqs.toString());
        pqs.add("Fourth", 3.00);
        pqs.add("Fifth", 3.00);
        pqs.add("Sixth", 3.00);
        System.out.println(pqs.toString());
    }
}
