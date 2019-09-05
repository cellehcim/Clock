import javax.swing.JFrame;

public class ClockFrame {

  private final static int WIDTH = 500;
  private final static int HEIGHT = 500;

  public static void main(String[] args) {
    JFrame frame = new JFrame();

    frame.setSize(WIDTH, HEIGHT);
    frame.setTitle("Cirno\'s Perfect CPSC 1181 Clock");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.add(new ClockComponent());

    frame.setVisible(true);
  }
}
