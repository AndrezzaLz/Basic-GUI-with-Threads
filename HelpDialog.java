import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

/**
 * A custom dialog for displaying help information.
 * It includes an image, scrollable text, and a closing button.
 */
public class HelpDialog extends JDialog {

    public HelpDialog(JFrame parent) {
        super(parent, "Ajuda", true);

        ImageIcon helpIcon = new ImageIcon(getClass().getResource("/help-icon.png"));
        JLabel imageLabel = new JLabel(helpIcon);
        imageLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String helpText = """
            Esta é a tela de ajuda da aplicação 'Basic GUI with Threads'.

            Funcionalidades:
            - Menu Arquivo:
              - Abrir Arquivo: Carrega um arquivo .txt e exibe seu conteúdo.
              - Fechar Arquivo: Limpa o conteúdo exibido.
              - Sair: Encerra a aplicação.

            - Menu Configuração:
              - Padrões: Altera o padrão visual da animação (Cor Sólida ou Círculos).
              - Cores: Altera o esquema de cores da animação.
              - Velocidade: Ajusta a velocidade da animação em milissegundos.

            A animação de fundo é executada em uma thread separada para não
            impactar a performance da interface gráfica.
            """;
        JTextArea textArea = new JTextArea(helpText);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okButton);

        setLayout(new BorderLayout());
        add(imageLabel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(550, 400);
        setLocationRelativeTo(parent);
    }
}