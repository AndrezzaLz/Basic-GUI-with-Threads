import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

/**
 * A custom dialog for displaying help information.
 * It includes an image, scrollable text, and a closing button.
 */
public class HelpDialog extends JDialog {

    public HelpDialog(JFrame parent) {
        super(parent, "Ajuda", true); // true for modal

        // --- Image ---
        // Note: You must have an 'help-icon.png' file (e.g., 64x64) in your project's root or classpath.
        // If you don't have an image, this will be blank.
        ImageIcon helpIcon = new ImageIcon(getClass().getResource("/help-icon.png"));
        JLabel imageLabel = new JLabel(helpIcon);
        imageLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Scrollable Text ---
        String helpText = """
            Esta é a tela de ajuda da aplicação 'Basic GUI with Threads'.

            Funcionalidades:
            - Menu Arquivo:
              - Abrir Arquivo: Carrega um arquivo .txt e exibe seu conteúdo na tela.
              - Fechar Arquivo: Limpa o conteúdo exibido.
              - Sair: Encerra a aplicação.

            - Menu Configuração:
              - Permite customizar a animação do plano de fundo (cores, velocidade, etc.).

            - Menu Ajuda:
              - Ajuda: Exibe esta janela.
              - Sobre: Mostra informações sobre o projeto e seus autores.

            A animação de fundo é executada em uma thread separada para não
            impactar a performance da interface gráfica.
            """;
        JTextArea textArea = new JTextArea(helpText);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textArea.setOpaque(false); // Makes it transparent
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Buttons ---
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose()); // Closes the dialog

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okButton);

        // --- Layout ---
        setLayout(new BorderLayout());
        add(imageLabel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(550, 400);
        setLocationRelativeTo(parent); // Center relative to the main window
    }
}