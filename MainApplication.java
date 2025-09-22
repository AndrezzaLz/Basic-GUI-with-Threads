import javax.swing.SwingUtilities;

/**
 * Main class to launch the application.
 * Ensures the GUI is created on the Event Dispatch Thread (EDT).
 */
public class MainApplication {

    public static void main(String[] args) {
        // Use invokeLater to ensure GUI creation is done on the EDT
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}