import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

/**
 * A custom panel with a dynamic, animated background that runs on a separate thread.
 * Now includes controls for speed, color schemes, and drawing patterns.
 */
public class AnimatedBackgroundPanel extends JPanel implements Runnable {

    // Animation control flags and variables
    private volatile boolean running = false;
    private Thread animationThread;
    private int animationDelay = 500; // Default speed: 500ms

    // Enum for different color modes
    public enum ColorMode {
        RANDOM, BLUES, GREENS, GRAYSCALE
    }

    // Enum for different drawing patterns
    public enum DrawPattern {
        SOLID_FILL, CIRCLES
    }

    private ColorMode currentColorMode = ColorMode.RANDOM;
    private DrawPattern currentDrawPattern = DrawPattern.SOLID_FILL;
    private final Random random = new Random();
    private final List<Circle> circles = new ArrayList<>();

    // A simple record to hold circle data
    private record Circle(int x, int y, int radius, Color color) {}

    @Override
    public void run() {
        while (running) {
            updateAnimationState();
            repaint();

            try {
                Thread.sleep(animationDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    private void updateAnimationState() {
        if (currentDrawPattern == DrawPattern.SOLID_FILL) {
            // For solid fill, we just need one color for the whole panel
            Color newColor = generateColor();
            setBackground(newColor);
        } else if (currentDrawPattern == DrawPattern.CIRCLES) {
            // For circles, we generate multiple objects
            circles.clear();
            for (int i = 0; i < 20; i++) {
                int x = random.nextInt(getWidth());
                int y = random.nextInt(getHeight());
                int radius = random.nextInt(40) + 10;
                circles.add(new Circle(x, y, radius, generateColor()));
            }
        }
    }

    private Color generateColor() {
        return switch (currentColorMode) {
            case RANDOM -> new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            case BLUES -> new Color(0, 0, random.nextInt(128) + 127);
            case GREENS -> new Color(0, random.nextInt(128) + 127, 0);
            case GRAYSCALE -> {
                int gray = random.nextInt(256);
                yield new Color(gray, gray, gray);
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentDrawPattern == DrawPattern.CIRCLES) {
            Graphics2D g2d = (Graphics2D) g;
            for (Circle circle : circles) {
                g2d.setColor(circle.color());
                g2d.fillOval(circle.x() - circle.radius(), circle.y() - circle.radius(), circle.radius() * 2, circle.radius() * 2);
            }
        }
    }

    public void startAnimation() {
        if (animationThread == null || !running) {
            running = true;
            animationThread = new Thread(this);
            animationThread.start();
        }
    }

    public void stopAnimation() {
        running = false;
        if (animationThread != null) {
            animationThread.interrupt();
        }
    }

    // --- Public setters for control from MainFrame ---

    public void setAnimationDelay(int delay) {
        this.animationDelay = Math.max(50, delay); // Set a minimum delay of 50ms
    }

    public void setColorMode(ColorMode mode) {
        this.currentColorMode = mode;
    }

    public void setDrawPattern(DrawPattern pattern) {
        // When changing patterns, clear the old one
        if (this.currentDrawPattern == DrawPattern.CIRCLES) {
            circles.clear();
        }
        this.currentDrawPattern = pattern;
    }
}