import javax.swing.JOptionPane;

public class Main {
  public static void main(String[] args) {
    String gridSize = JOptionPane.showInputDialog(null, "Enter Grid Size: ");
    int size = Integer.parseInt(gridSize);
    GUI g = new GUI(size);
  }
}
