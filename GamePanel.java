import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    // Screen Settings
    final int originalTileSize = 16; // 16x16 tile final

    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile

    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run() {
         while(gameThread != null) {
              
            long currentTime = System.nanoTime();
            long currentTime2 = System.currentTimeMillis();

            // System.out.println("The game is running");

            // Update: update information such as character positions
            update();
            // Drew: draw the screen with the updated information
            repaint();
         }
    }
    public void update(){
        if(keyH.upPressed){
            playerY -= playerSpeed;
        }
        else if(keyH.downPressed){
            playerY += playerSpeed;
        }
        else if(keyH.leftPressed){
            playerX -= playerSpeed;
        }
        if(keyH.rightPressed){
            playerX += playerSpeed;
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }
}
