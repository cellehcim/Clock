import java.time.LocalTime;

public class HandTester {

    private final static double RADIANS = Math.PI*2;
    private final static double EPSILON = 1E-12;

    public static void main(String[] args) {
        test_0();
        testSeconds();
        testMinutes();
        testHours();
        testHands();
        System.err.println("PASS");
    }

    private static void test_0() {
        LocalTime time = LocalTime.MIN;
        Hand hours = new Hand(Hand.TimeUnit.HOUR, time);
        assert 0 == hours.angle;
    }

    private static void testSeconds() {
        //Boundary - test 0
        LocalTime time = LocalTime.MIN;
        Hand b = new Hand(Hand.TimeUnit.SECOND, time);
        assert 0 == b.angle;

        //Boundary -test 59
        time = LocalTime.MAX;
        Hand bMax = new Hand(Hand.TimeUnit.SECOND, time);
        assert (RADIANS/60 * 59) == bMax.angle;

        //Typical - test 2
        time = LocalTime.of(0,0,2);
        Hand t = new Hand(Hand.TimeUnit.SECOND, time);
        assert (RADIANS/60 * 2) == t.angle;

    }

    private static void testMinutes() {
        //Boundary
        LocalTime time = LocalTime.MIN;
        Hand b = new Hand(Hand.TimeUnit.MINUTE, time);
        assert 0 == b.angle;

        //Boundary
        time = LocalTime.MAX;
        Hand bMaxS = new Hand(Hand.TimeUnit.SECOND, time);
        Hand bMaxM = new Hand(Hand.TimeUnit.MINUTE, time, bMaxS);
        assert ((RADIANS/60 * 59) + (bMaxS.angle/60)) == bMaxM.angle;

        //Typical - test 4
        time = LocalTime.of(0,4,0);
        Hand t = new Hand(Hand.TimeUnit.MINUTE, time);
        assert (RADIANS/60 * 4) == t.angle;

        //Special - test 5:49
        time = LocalTime.of(0,5,49);
        Hand ss = new Hand(Hand.TimeUnit.SECOND, time);
        Hand sm = new Hand(Hand.TimeUnit.MINUTE, time, ss);
        assert ((RADIANS/60 * 5) + (ss.angle/60)) == sm.angle;
    }

    private static void testHours() {
        //Boundary - test 0
        LocalTime time = LocalTime.MIN;
        Hand b = new Hand(Hand.TimeUnit.HOUR, time);
        assert 0 == b.angle;

        //Typical - test 2
        time = LocalTime.of(2,0,0);
        Hand t = new Hand(Hand.TimeUnit.HOUR, time);
        assert (RADIANS/12 * 2) == t.angle;

        //Special - test 2:57
        time = LocalTime.of(2,57,0);
        Hand sm = new Hand(Hand.TimeUnit.MINUTE, time);
        Hand sh = new Hand(Hand.TimeUnit.HOUR, time, sm);
        assert ((RADIANS/12 * 2) + (sm.angle/12)) == sh.angle;
    }

    private static void testHands() {
        LocalTime time = LocalTime.MIN;

        //Boundary - test 0:00:00;
        Hand bs = new Hand(Hand.TimeUnit.SECOND, time);
        Hand bm = new Hand(Hand.TimeUnit.MINUTE, time, bs);
        Hand bh = new Hand(Hand.TimeUnit.HOUR, time, bm);
        assert 0 == bs.angle;
        assert 0 == bm.angle;
        assert 0 == bh.angle;

        //Typical - test 3:14:15;
        time = LocalTime.of(3,14,15);
        Hand ts = new Hand(Hand.TimeUnit.SECOND, time);
        Hand tm = new Hand(Hand.TimeUnit.MINUTE, time, ts);
        Hand th = new Hand(Hand.TimeUnit.HOUR, time, tm);
        assert (RADIANS/60 * 15) == ts.angle;
        assert (RADIANS/60 * 14 + (ts.angle/60)) == tm.angle;
        assert (RADIANS/12 * 3 + (tm.angle/12)) == th.angle;
    }

    // ** Do Not Change Below this line ** //

    private static void assertEquals(double exp, double act) {
        assert Math.abs(exp - act) <= EPSILON :
            "\nexp: " + exp + "\nact: " + act + "\ndif: " + (exp-act);
    }

    static {
        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (!assertsEnabled) {
            throw new RuntimeException("Asserts must be enabled!!! java -ea");
        }
    }
}
