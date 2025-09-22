import java.io.File;
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JMenuBar;
import java.awt.BorderLayout;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.border.BevelBorder;

/**
 * The main window (JFrame) of the application.
 * It sets up the GUI, menus, listeners, and integrates all components.
 */
public class MainFrame extends JFrame {

    private JTextArea contentTextArea;
    private JLabel statusBarLabel;
    private AnimatedBackgroundPanel backgroundPanel;
    private JFileChooser fileChooser;

    public MainFrame() {
        super("Basic GUI with Threads");

        // --- Basic Frame Setup ---
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Custom close operation
        setSize(800, 600);
        setLocationRelativeTo(null); // Center on screen
        
        // --- Component Initialization ---
        this.fileChooser = new JFileChooser();
        setupComponents();
        setupLayout();
        setupListeners();
        
        // Start the background animation
        this.backgroundPanel.startAnimation();
    }

    private void setupComponents() {
        // --- Menu Bar ---
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);

        // --- Animated Background ---
        this.backgroundPanel = new AnimatedBackgroundPanel();
        
        // --- Text Area for File Content ---
        this.contentTextArea = new JTextArea();
        this.contentTextArea.setEditable(false);
        this.contentTextArea.setOpaque(false); // Makes text area transparent to see background
        
        // --- Scroll Pane ---
        JScrollPane scrollPane = new JScrollPane(this.contentTextArea);
        scrollPane.setOpaque(false); // Makes scroll pane transparent
        scrollPane.getViewport().setOpaque(false); // Makes the viewport transparent

        // --- Status Bar ---
        this.statusBarLabel = new JLabel("Pronto.");
        this.statusBarLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        
        // Add components to the background panel which acts as the main container
        this.backgroundPanel.setLayout(new BorderLayout());
        this.backgroundPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void setupLayout() {
        // The main content pane is the animated panel itself
        setContentPane(this.backgroundPanel);
        // Add status bar to the frame's layout
        add(this.statusBarLabel, BorderLayout.SOUTH);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // --- File Menu ---
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem openFileItem = new JMenuItem("Abrir Arquivo");
        openFileItem.addActionListener(e -> openFile());
        
        JMenuItem closeFileItem = new JMenuItem("Fechar Arquivo");
        closeFileItem.addActionListener(e -> closeFile());

        JMenuItem exitItem = new JMenuItem("Sair");
        exitItem.addActionListener(e -> exitApplication());
        
        fileMenu.add(openFileItem);
        fileMenu.add(closeFileItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // --- Configuration Menu ---
        JMenu configMenu = new JMenu("Configuração");
        JMenuItem patternsItem = new JMenuItem("Padrões");
        patternsItem.addActionListener(e -> showNotImplementedDialog());
        JMenuItem colorsItem = new JMenuItem("Cores");
        colorsItem.addActionListener(e -> showNotImplementedDialog());
        JMenuItem speedItem = new JMenuItem("Velocidade");
        speedItem.addActionListener(e -> showNotImplementedDialog());

        configMenu.add(patternsItem);
        configMenu.add(colorsItem);
        configMenu.add(speedItem);
        
        // --- Help Menu ---
        JMenu helpMenu = new JMenu("Ajuda");
        JMenuItem helpItem = new JMenuItem("Ajuda");
        helpItem.addActionListener(e -> showHelpDialog());

        JMenuItem aboutItem = new JMenuItem("Sobre");
        aboutItem.addActionListener(e -> showAboutDialog());

        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(configMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private void setupListeners() {
        // Add a window listener to stop the animation thread on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });
    }

    // --- Action Methods ---

    private void openFile() {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                String content = Files.readString(selectedFile.toPath());
                contentTextArea.setText(content);
                contentTextArea.setCaretPosition(0); // Scroll to top
                statusBarLabel.setText("Arquivo aberto: " + selectedFile.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao ler o arquivo: " + ex.getMessage(),
                    "Erro de Arquivo",
                    JOptionPane.ERROR_MESSAGE);
                statusBarLabel.setText("Falha ao abrir o arquivo.");
            }
        }
    }

    private void closeFile() {
        contentTextArea.setText("");
        statusBarLabel.setText("Arquivo fechado.");
    }

    private void exitApplication() {
        backgroundPanel.stopAnimation();
        dispose();
        System.exit(0);
    }

    private void showHelpDialog() {
        HelpDialog helpDialog = new HelpDialog(this);
        helpDialog.setVisible(true);
    }

private void showAboutDialog() {
    String aboutMessage = "Aplicação: Basic GUI with Threads\nVersão: " +
                          "2025.a" + // From ver. in PDF
                          "\nAutores: [Seu Nome/Grupo Aqui]";
    JOptionPane.showMessageDialog(this, aboutMessage, "Sobre", JOptionPane.INFORMATION_MESSAGE);
}
    
    private void showNotImplementedDialog() {
        JOptionPane.showMessageDialog(this,
            "Funcionalidade não implementada.",
            "Informação",
            JOptionPane.INFORMATION_MESSAGE);
    }
}