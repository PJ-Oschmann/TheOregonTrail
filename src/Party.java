import javax.swing.*;

public class Party extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public Party() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }

    public static void main(String[] args) {
        Party dialog = new Party();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
