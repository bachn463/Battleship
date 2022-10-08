public class Boat {
  private String name;
  private boolean direction;
  private int length; 
  private boolean placed = false;

  public Boat(String name, int len) {
    this.name = name;
    this.direction = true;
    this.length = len;
  }

  //getters
  public int getLength() {
    return length;
  }
  public String getName() {
    return name;
  }
  public boolean getDir() {
    return direction;
  }
  public boolean isPlaced() {
    return placed;
  }
  
  //setters
  public void setLength(int temp) {
    length = temp;
  }
  public void setName(String temp) {
    name = temp;
  }
  public void setDir(boolean temp) {
    direction = temp;
  }
  public void placed(){
    placed = true;
  }
}
