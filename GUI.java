import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;

public class GUI implements ActionListener, MouseListener {
  Random rand = new Random();

  JFrame boardWindow = new JFrame("Battleship");
  JPanel boardMain = new JPanel();
  JPanel[][] boardGrid;

  JPanel humanButtons = new JPanel();
  JPanel[][] humanGrid;
  JPanel humanPanelGrid = new JPanel();

  JPanel[][] compGrid;
  int[][] compTracker;
  /*
  0 - water(blue)
  1 - boat placed(gray)
  2 - hit water(white)
  3 - hit boat(black)
  */

  JButton rotate = new JButton("Rotate");

  Boat carrier = new Boat("Car-5", 5);
  JButton carrierButt = new JButton(carrier.getName());
  Boat battleship = new Boat("Bat-4", 4);
  JButton battleshipButt = new JButton(battleship.getName());
  Boat cruiser = new Boat("Cru-3", 3);
  JButton cruiserButt = new JButton(cruiser.getName());
  Boat submarine = new Boat("Sub-3", 3);
  JButton subButt = new JButton(submarine.getName());
  Boat destroyer = new Boat("Des-2", 2);
  JButton destroyerButt = new JButton(destroyer.getName());

  Boat[] compBoats = new Boat[5]; 

  ArrayList<Boat> userBoats = new ArrayList<Boat>(5);

  Boat selected = carrier;

  boolean userTurn = true;
  boolean gameEnd = false;

  public GUI() {
    compBoats[0] = new Boat("Car-5", 5);
    compBoats[1] = new Boat("Bat-4", 4);
    compBoats[2] = new Boat("Cru-3", 3);
    compBoats[3] = new Boat("Sub-3", 3);
    compBoats[4] = new Boat("Des-2", 2);
  
    userBoats.add(carrier);
    userBoats.add(battleship);
    userBoats.add(cruiser);
    userBoats.add(submarine);
    userBoats.add(destroyer);
  
    boardMain.setLayout(new GridLayout(1, 2, 7, 7));
    humanPanelGrid.setLayout(new GridLayout(10, 10, 1, 1));
    boardGrid = new JPanel[1][2];
    humanGrid = new JPanel[10][10];
    compGrid = new JPanel[10][10];
    compTracker = new int[10][10];

    for (int i = 0; i < boardGrid.length; i++) {
		  for (int j = 0; j < boardGrid[0].length; j++) {
				boardGrid[i][j] = new JPanel();
        boardMain.add(boardGrid[i][j]);
        boardGrid[i][j].addMouseListener(this);
      }
    }

    boardGrid[0][0].setLayout(new BorderLayout());

    for (int i = 0; i < humanGrid.length; i++) {
		  for (int j = 0; j < humanGrid[0].length; j++) {
				humanGrid[i][j] = new JPanel();
        humanPanelGrid.add(humanGrid[i][j]);
        humanGrid[i][j].setBackground(Color.BLUE);
        humanGrid[i][j].addMouseListener(this);
      }
    }

    boardGrid[0][1].setLayout(new GridLayout(10, 10, 1, 1));

    for (int i = 0; i < compGrid.length; i++) {
		  for (int j = 0; j < compGrid[0].length; j++) {
				compGrid[i][j] = new JPanel();
        boardGrid[0][1].add(compGrid[i][j]);
        compGrid[i][j].setBackground(Color.BLUE);
        compTracker[i][j] = 0;
        compGrid[i][j].addMouseListener(this);
      }
    }

    boardGrid[0][0].add(humanPanelGrid, BorderLayout.CENTER);
    boardGrid[0][0].add(humanButtons, BorderLayout.SOUTH);

    humanButtons.add(rotate);
    rotate.addActionListener(this);
    humanButtons.add(carrierButt);
    carrierButt.addActionListener(this);
    humanButtons.add(battleshipButt);
    battleshipButt.addActionListener(this);
    humanButtons.add(cruiserButt);
    cruiserButt.addActionListener(this);
    humanButtons.add(subButt);
    subButt.addActionListener(this);
    humanButtons.add(destroyerButt);
    destroyerButt.addActionListener(this);


    boardWindow.add(boardMain);
    boardWindow.setSize(1000, 600);
    boardWindow.setVisible(true);


    //setting comp boats
    for(int i = 0; i < compBoats.length; i++){
      while(!compBoats[i].isPlaced()) {
        boolean intersect = false;
        int randX = rand.nextInt(10);
        int randY = rand.nextInt(10);
        boolean orientation = rand.nextBoolean();

        compBoats[i].setDir(orientation);

        if(compBoats[i].getDir()) {
          //check for loop
          for(int a = 0; a < compBoats[i].getLength(); a++) {
            if(randX+compBoats[i].getLength()<=compGrid.length) {
              if(compTracker[randX+a][randY] == 1) {
                intersect = true;
                break;
              }
            }
          }
          //place for loop
          if(!intersect){
            for(int a = 0; a < compBoats[i].getLength(); a++) {
              if(randX+compBoats[i].getLength()<=compGrid.length) {
                compTracker[randX+a][randY] = 1;
                compBoats[i].placed();
              }
            }
          }
         }
        if(!compBoats[i].getDir()) {
          //check for loop
          for(int b = 0; b < compBoats[i].getLength(); b++) {
            if(randY+compBoats[i].getLength()<=compGrid[0].length) {
              if(compTracker[randX][randY+b] == 1) {
                intersect = true;
                break;
              }
            }
          }
          //place for loop
          if(!intersect){
            for(int b = 0; b < compBoats[i].getLength(); b++) {
              if(randY+compBoats[i].getLength()<=compGrid[0].length) {
                compTracker[randX][randY+b] = 1;
                compBoats[i].placed();
              }
            }
          }
        }
      }
    }//end for loop
  }

  public void actionPerformed (ActionEvent e) {
    if(e.getSource() == rotate) {
      selected.setDir(!selected.getDir());
    }
    if(e.getSource() == carrierButt) {
      selected = carrier;
    }
    if(e.getSource() == battleshipButt) {
      selected = battleship;
    }
    if(e.getSource() == cruiserButt) {
      selected = cruiser;
    }
    if(e.getSource() == subButt) {
      selected = submarine;
    }
    if(e.getSource() == destroyerButt) {
      selected = destroyer;
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if(!gameEnd){
      for (int i = 0; i < humanGrid.length; i++) {
        for (int j = 0; j < humanGrid[0].length; j++) {
          if(e.getSource() == humanGrid[i][j]) {
            if(!selected.isPlaced()){
              boolean intersect = false;
              if(selected.getDir()) {
                //check for loop
                for(int a = 0; a < selected.getLength(); a++) {
                  if(humanGrid[i+a][j].getBackground()==Color.GRAY) {
                    intersect = true;
                    break;
                  }
                }
                //place for loop
                if(!intersect){
                  for(int a = 0; a < selected.getLength(); a++) {
                    if(i+selected.getLength()<=humanGrid.length) {
                      humanGrid[i+a][j].setBackground(Color.GRAY);
                      selected.placed();
                    }
                  }
                }
              }
              if(!selected.getDir()) {
                //check for loop
                for(int b = 0; b < selected.getLength(); b++) {
                  if(humanGrid[i][j+b].getBackground()==Color.GRAY) {
                    intersect = true;
                    break;
                  }
                }
                //place for loop
                if(!intersect){
                  for(int b = 0; b < selected.getLength(); b++) {
                    if(j+selected.getLength()<=humanGrid[0].length) {
                        humanGrid[i][j+b].setBackground(Color.GRAY);
                        selected.placed();
                    }
                  }
                }
              }
            }
          }
        }
      }

      //user turn guessing boats
      boolean allPlaced = true;
      for(int i = 0; i< userBoats.size(); i++){
        if(!userBoats.get(i).isPlaced()){
          allPlaced = false;
        }
      }
      if(allPlaced){
        if(userTurn){
          for (int i = 0; i < compGrid.length; i++) {
            for (int j = 0; j < compGrid[0].length; j++) {
              if(e.getSource() == compGrid[i][j]){
                if(compTracker[i][j] == 1){
                  compGrid[i][j].setBackground(Color.BLACK);
                  compTracker[i][j] = 3;
                  userTurn = false;
                }
                if(compTracker[i][j] == 0){
                  compGrid[i][j].setBackground(Color.WHITE);
                  compTracker[i][j] = 2;
                  userTurn = false;
                }
              }
            }
          }
        }
      }

      //computer turn
      if(!userTurn){
        boolean compTurnEnd = false;
        while(!compTurnEnd){
          int randX = rand.nextInt(10);
          int randY = rand.nextInt(10);
          if(humanGrid[randX][randY].getBackground() == Color.GRAY){
            humanGrid[randX][randY].setBackground(Color.BLACK);
            compTurnEnd = true;
          }
          if(humanGrid[randX][randY].getBackground() == Color.BLUE){
            humanGrid[randX][randY].setBackground(Color.WHITE);
            compTurnEnd = true;
          }
        }
        userTurn = true;
      }
      
    
      //checking if won
      int humanHits = 0;
      int compHits = 0;
      for (int i = 0; i < humanGrid.length; i++) {
        for (int j = 0; j < humanGrid[0].length; j++) {
          if(humanGrid[i][j].getBackground() == Color.BLACK){
            compHits++;
          }
        }
      }
      for (int i = 0; i < compGrid.length; i++) {
        for (int j = 0; j < compGrid[0].length; j++) {
          if(compTracker[i][j] == 3){
            humanHits++;
          }
        }
      }
      if(humanHits == 17){
        System.out.println("User Wins");
        gameEnd = true;
      } else if(compHits == 17){
        System.out.println("Computer Wins");
        gameEnd = true;
      }
    }
  }

  @Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
    if(!selected.isPlaced()) {
      for (int i = 0; i < humanGrid.length; i++) {
        for (int j = 0; j < humanGrid[0].length; j++) {
          if(e.getSource() == humanGrid[i][j]) {
            if(selected.getDir()) {
              for(int a = 0; a < selected.getLength(); a++) {
                if(i+a<humanGrid.length) {
                  if(humanGrid[i+a][j].getBackground() == Color.BLUE) {
                    humanGrid[i+a][j].setBackground(Color.RED);
                  }
                }
              }
            }
            if(!selected.getDir()) {
              for(int b = 0; b < selected.getLength(); b++) {
                if(j+b<humanGrid[0].length) {
                  if(humanGrid[i][j+b].getBackground() == Color.BLUE) {
                    humanGrid[i][j+b].setBackground(Color.RED);
                  }
                }
              }
            }
          }
        }
      }
    }
  }

	@Override
	public void mouseExited(MouseEvent e) {
    if(!selected.isPlaced()) {
      for (int i = 0; i < humanGrid.length; i++) {
        for (int j = 0; j < humanGrid[0].length; j++) {
          if(e.getSource() == humanGrid[i][j]) {
            if(selected.getDir()) {
              for(int a = 0; a < selected.getLength(); a++) {
                if(i+a<humanGrid.length) {
                  if(humanGrid[i+a][j].getBackground() == Color.RED) {
                    humanGrid[i+a][j].setBackground(Color.BLUE);
                  }
                }
              }
            }
            if(!selected.getDir()) {
              for(int b = 0; b < selected.getLength(); b++) {
                if(j+b<humanGrid[0].length) {
                  if(humanGrid[i][j+b].getBackground() == Color.RED) {
                    humanGrid[i][j+b].setBackground(Color.BLUE);
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
