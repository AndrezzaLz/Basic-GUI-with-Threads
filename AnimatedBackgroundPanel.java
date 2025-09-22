import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Graphics2D;

/**
 * A custom panel with a dynamic, animated background that runs on a separate thread.
 */
public class AnimatedBackgroundPanel extends JPanel implements Runnable {

    private volatile boolean running = false;
    private Thread animationThread;
    private final Random random = new Random();
    private Color dynamicColor = Color.BLACK;

    public AnimatedBackgroundPanel() {
        // The constructor can be used for initial setup if needed.
    }

    /**
     * The main loop for the animation thread.
     */
    @Override
    public void run() {
        while (running) {
            // Change the color to a new random value
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            dynamicColor = new Color(r, g, b);

            // Request a repaint, which will eventually call paintComponent
            repaint();

            try {
                // Control the animation speed
                Thread.sleep(500); // Sleeps for 500 milliseconds
            } catch (InterruptedException e) {
                // Restore the interrupted status and exit the loop
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    /**
     * Overridden method to perform custom drawing.
     * @param g The Graphics object to protect.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Fills the entire panel with the current dynamic color
        g2d.setColor(dynamicColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Starts the animation thread.
     */
    public void startAnimation() {
        if (animationThread == null || !running) {
            running = true;
            animationThread = new Thread(this);
            animationThread.start();
        }
    }

    /**
     * Stops the animation thread gracefully.
     */
    public void stopAnimation() {
        running = false;
        if (animationThread != null) {
            animationThread.interrupt();
        }
    }
}