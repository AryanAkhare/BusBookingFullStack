import ui.BookingUI;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookingUI ui = new BookingUI();
            ui.setVisible(true);
        });
    }
}
