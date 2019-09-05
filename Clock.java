/**
* Creates a clock that is inspired by the Touhou character, Cirno.
* @author Michelle Chan
* @version 31-5-2017
*
*/

import java.time.LocalTime;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.BasicStroke;
import javax.swing.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.*;

public class Clock {

    BufferedImage img = null;

    //constants for the frame
    public final static double RADIANS = 2*Math.PI;
    public final static int SCALE = 200;
    public final static int CENTRE = SCALE/2;

    //constants for the clock hands
    private final static int NUM_TICKS = 12;
    private final static int NUM_FAT_TICKS = 4;
    private final static int TICK_LENGTH = SCALE / 10;

    //clock face constants
    public final static int CLOCK_FACE_SCALE = SCALE - 16;
    public final static int CLOCK_CENTRE = (CLOCK_FACE_SCALE / 2) + 8;

    //Creates a custom colour based on Cirno's dress and hair
    Color cirnoLightBlue = new Color(0.435f, 0.545f, 0.976f);
    Color cirnoDarkBlue = new Color(0.204f, 0.51f, 0.753f);

    private LocalTime time;

    public Clock() {
        this(LocalTime.now());
    }
    public Clock(LocalTime aTime) {
        time = aTime;
    }


    /**
    * Loads and draws images given its name and position.
    * @param imageName - name of the imageName
    * @param x - x position of the image
    * @param y - y position of the image
    */
    public void drawImage(Graphics g, String imageName, int x, int y) {
        //reads the image
        try {
            img = ImageIO.read(new File(imageName));
        } catch (IOException e) {
        }

        g.drawImage(img, x, y, null);
    }


    /**
    * Draws the clock face
    * @param g - the graphics component
    */

    public void draw(Graphics2D g) {

        //draws the UI border
        g.setColor(cirnoLightBlue);
        RoundRectangle2D.Double border = new RoundRectangle2D.Double(0,0, SCALE - 1, SCALE - 1,0,0);
        g.setStroke(new BasicStroke(2f));
        g.draw(border);

        //Draws the clock face and its border
        Ellipse2D.Double clockFace = new Ellipse2D.Double(8, 8, CLOCK_FACE_SCALE, CLOCK_FACE_SCALE);
        g.setStroke(new BasicStroke(4f));
        g.draw(clockFace);

        //Draws the tick marks
        g.setColor(Color.BLACK);
        for (int i = 1; i <= 60; i++) {
            //draws the hour tick marks
            int endPoint;

            if (i % 5 == 0) {
                //creates the stroke settings for the "cardinal" directions
                if (i % 15 == 0) {
                    g.setStroke(new BasicStroke(3f));
                }
                endPoint = 20;
                g.setColor(Color.BLACK);
            } else {
                //minute/second tick mark settings
                endPoint = 15;

                g.setStroke(new BasicStroke(1f));
                g.setColor(cirnoDarkBlue);
            }
            //regardless of setting, draws the tick marks
            Line2D.Double tick = new Line2D.Double(13, CLOCK_CENTRE, endPoint, CLOCK_CENTRE);
            g.rotate(RADIANS/60, CENTRE, CENTRE);
            g.draw(tick);
        }

        //draws Cirno
        drawImage(g, "cirno.png", CLOCK_CENTRE - 40, CLOCK_CENTRE - 55);

        //draws the clock numbers
        drawImage(g, "3.png", CLOCK_FACE_SCALE - 35, CENTRE - 10);
        drawImage(g, "9.png", CLOCK_CENTRE - 72, CENTRE - 6);
        drawImage(g, "6.png", CLOCK_CENTRE - 30, CENTRE + 60);
        drawImage(g, "12.png", CLOCK_CENTRE - 30, CENTRE - 75);

        //holds the hand objects
        Hand[] arr = new Hand[3];
        arr = getHands();

        //draws the hands
        for (Hand h: arr) {
            h.draw(g);
        }

        Ellipse2D.Double iceFastener = new Ellipse2D.Double(CENTRE - 2, CENTRE - 2, 4, 4);
        g.setColor(new Color(0.56f, .95f, 1f));
        g.fill(iceFastener);
        g.draw(iceFastener);
        g.setColor(cirnoDarkBlue);
        g.setStroke(new BasicStroke(.5f));
        g.draw(new Line2D.Double(CENTRE - 2.5, CENTRE, CENTRE + 2.5, CENTRE));

    }

    //return hand objects
    public Hand[] getHands() {
        LocalTime now = LocalTime.now();
        Hand[] arr = new Hand[3];

        //creates hands for the clock
        arr[0] = new Hand(Hand.TimeUnit.SECOND, now);
        arr[1] = new Hand(Hand.TimeUnit.MINUTE, now, arr[0]);
        arr[2] = new Hand(Hand.TimeUnit.HOUR, now, arr[1]);

        return arr;
    }
}
