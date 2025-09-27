import java.io.File;
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.border.BevelBorder;

/**
 * The main window (JFrame) of the application.
 * Sets up the GUI, menus, listeners, and integrates all components.
 */
public class MainFrame extends JFrame {

    private JTextArea contentTextArea;
    private JLabel statusBarLabel;
    private AnimatedBackgroundPanel backgroundPanel;
    private JFileChooser fileChooser;

    public MainFrame() {
        super("Basic GUI with Threads");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        setupComponents();
        setupLayout();
        setupListeners();
        
        this.backgroundPanel.startAnimation();
    }

    private void setupComponents() {
        setJMenuBar(createMenuBar());
        this.fileChooser = new JFileChooser();
        this.backgroundPanel = new AnimatedBackgroundPanel();
        this.contentTextArea = new JTextArea();
        this.contentTextArea.setEditable(false);
        this.contentTextArea.setOpaque(false);
        
        JScrollPane scrollPane = new JScrollPane(this.contentTextArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        this.statusBarLabel = new JLabel("Pronto.");
        this.statusBarLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        
        this.backgroundPanel.setLayout(new BorderLayout());
        this.backgroundPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void setupLayout() {
        setContentPane(this.backgroundPanel);
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
        patternsItem.addActionListener(e -> changeDrawPattern());
        JMenuItem colorsItem = new JMenuItem("Cores");
        colorsItem.addActionListener(e -> changeColorMode());
        JMenuItem speedItem = new JMenuItem("Velocidade");
        speedItem.addActionListener(e -> changeAnimationSpeed());

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
                contentTextArea.setCaretPosition(0);
                statusBarLabel.setText("Arquivo aberto: " + selectedFile.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo: " + ex.getMessage(), "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
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
        new HelpDialog(this).setVisible(true);
    }

    private void showAboutDialog() {
        String aboutMessage = "Aplicação: Basic GUI with Threads\n" +
                              "Versão: 2025\n" +
                              "Autores:\n" + 
                              "Andrezza L. Z.\nSidinei Junior\nAdriano De Jesus\nMirella Dos Santos\nDandara Melissa\nYasmin Inoue";
        JOptionPane.showMessageDialog(this, aboutMessage, "Sobre", JOptionPane.INFORMATION_MESSAGE);
    }

    // --- Configuration Action Methods ---

    private void changeDrawPattern() {
        Object[] options = {"Cor Sólida", "Círculos"};
        int choice = JOptionPane.showOptionDialog(this,
            "Escolha o padrão de desenho para o fundo:",
            "Mudar Padrão",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);

        if (choice == 0) {
            backgroundPanel.setDrawPattern(AnimatedBackgroundPanel.DrawPattern.SOLID_FILL);
        } else if (choice == 1) {
            backgroundPanel.setDrawPattern(AnimatedBackgroundPanel.DrawPattern.CIRCLES);
        }
    }

    private void changeColorMode() {
        Object[] options = {"Aleatório", "Tons de Azul", "Tons de Verde", "Escala de Cinza", "Tons de Rosa"};
        int choice = JOptionPane.showOptionDialog(this,
            "Escolha o esquema de cores:",
            "Mudar Cores",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);

        switch (choice) {
            case 0 -> backgroundPanel.setColorMode(AnimatedBackgroundPanel.ColorMode.RANDOM);
            case 1 -> backgroundPanel.setColorMode(AnimatedBackgroundPanel.ColorMode.BLUES);
            case 2 -> backgroundPanel.setColorMode(AnimatedBackgroundPanel.ColorMode.GREENS);
            case 3 -> backgroundPanel.setColorMode(AnimatedBackgroundPanel.ColorMode.GRAYSCALE);
            case 4 -> backgroundPanel.setColorMode(AnimatedBackgroundPanel.ColorMode.PINK);

        }
    }

    private void changeAnimationSpeed() {
        String result = JOptionPane.showInputDialog(this, "Digite o novo intervalo em milissegundos (ex: 500):");
        if (result != null && !result.isBlank()) {
            try {
                int delay = Integer.parseInt(result);
                backgroundPanel.setAnimationDelay(delay);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, digite um número válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
