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

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
 
    // World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;
    
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound music = new Sound();
    Sound sound = new Sound();
    CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // Entity and Object
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];





    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        playSound(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    // Sleep method is not accurate, so we will use delta method to calculate the time
    // public void run() {
    // double drawInterval = 1000000000/FPS; // 0.01666 seconds
    // double nextDrawTime = System.nanoTime() + drawInterval;

    // while(gameThread != null) {
    // // Update: update information such as character positions
    // update();
    // // Drew: draw the screen with the updated information
    // repaint();

    // try {
    // double remainingTime = nextDrawTime - System.nanoTime();
    // remainingTime = remainingTime/1000000; // Convert to milliseconds
    // if(remainingTime < 0) {
    // remainingTime = 0;
    // }
    // Thread.sleep((long) remainingTime);

    // nextDrawTime += drawInterval;
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    // }
    // Delta method is more accurate than sleep method, so we will use delta method to calculate the time
    public void run() {
        double drawInterval = 1000000000 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                // Update: update information such as character positions
                update();
                // Drew: draw the screen with the updated information
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        
        // Draw the tiles
        tileM.draw(g2);
        // Draw the objects
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        // Draw the player
        player.draw(g2);

        // Draw the UI
        ui.draw(g2);

        g2.dispose();
    }
    // Sound Methods
    public void playSound(int i) {
        music.setFile(i); // Set the sound file to play
        music.play(); // Play the sound
        music.loop(); // Loop the sound continuously
    }
    // Stop the sound
    public void stopSound() {
        music.stop();
    }
    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
}
