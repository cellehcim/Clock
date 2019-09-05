import java.awt.*;
import java.awt.geom.Line2D;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class Hand {

    public final static double RADIANS = Clock.RADIANS;
    public final static double CLOCK_CENTRE = Clock.CLOCK_CENTRE;
    public final static double CENTRE = Clock.CENTRE;

    public enum TimeUnit {
        MILLS(ChronoField.MILLI_OF_SECOND, 0 , 0),
        SECOND(ChronoField.SECOND_OF_MINUTE, 80, 0.5f, Color.RED),
        MINUTE(ChronoField.MINUTE_OF_HOUR, 75, 1),
        HOUR(ChronoField.HOUR_OF_AMPM, 50, 2);

        public final ChronoField cf;
        public final int length;
        public final BasicStroke stroke;
        public final Color color;
        TimeUnit(ChronoField aCF, int aLength, float aWidth) {
            this(aCF, aLength, aWidth, Color.BLACK);
        }
        TimeUnit(ChronoField aCF, int aLength, float aWidth, Color aColor) {
            cf = aCF;
            length = (Clock.SCALE * aLength) / (100 * 2);
            stroke = new BasicStroke(aWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            color = aColor;
        }
    }

    //hand object attributes.
    private final TimeUnit unit; // ie: hour, minute, second, etc
    public final double angle; // the rotation of the hand in radians from 12 o'clock

    //Creates a hand.
    public Hand(TimeUnit aUnit, LocalTime aTime) {
            this(aUnit, aTime, null);
    }

    /**
    * Creates a clock hand that uses angles.
    * @param aUnit - unit used in the hand
    * @param aTime - the value of the unit
    * @param prevHand - the previous hand
    */

    public Hand(TimeUnit aUnit, LocalTime aTime, Hand prevHand) {
        //time-obtaining variables
        unit = aUnit;
        int value = aTime.get(unit.cf);
        long maxValue = unit.cf.range().getMaximum() + 1;

        //calculates the angle
        if (prevHand == null) {
            angle = (RADIANS/maxValue * value);
        } else {
            angle = RADIANS/maxValue * value + (prevHand.angle/maxValue);
        }
    }

    /**
    * Draws the clock hands.
    */

    public void draw(Graphics2D g) {
        // TODO FIXME draw the hand
        Line2D.Double tick = new Line2D.Double(CLOCK_CENTRE, CLOCK_CENTRE, CLOCK_CENTRE, CLOCK_CENTRE - unit.length);
        g.setColor(unit.color);
        g.setStroke(unit.stroke);
        g.rotate(angle, CENTRE, CENTRE);
        g.draw(tick);
        g.rotate(RADIANS - angle, CENTRE, CENTRE);
    }
}
