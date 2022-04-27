import javax.swing.*;

public class UsefullPanels {
    public static void showLongTextMessageInDialog(String longMessage) {
        JTextArea textArea = new JTextArea(6, 24);
        textArea.setText(longMessage);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane);
    }

    public static String showAndGetLongTextMessageInDialog(String longMessage) {
        JTextArea textArea = new JTextArea(6, 24);
        textArea.setText(longMessage);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        String res = JOptionPane.showInputDialog(null, scrollPane);
        return res;
    }
}
