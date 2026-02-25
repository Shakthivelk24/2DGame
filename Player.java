import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldx = gp.tileSize * 23;
        worldy = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/wakingSprite/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/wakingSprite/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/wakingSprite/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/wakingSprite/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/wakingSprite/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/wakingSprite/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/wakingSprite/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/wakingSprite/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            if (keyH.upPressed) direction = "up";
            else if (keyH.downPressed) direction = "down";
            else if (keyH.leftPressed) direction = "left";
            if (keyH.rightPressed) direction = "right";

            collisionOn = false;
            gp.cChecker.checlTilte(this);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            if (collisionOn == false) {
                switch (direction) {
                    case "up": worldy -= speed; break;
                    case "down": worldy += speed; break;
                    case "left": worldx -= speed; break;
                    case "right": worldx += speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }

    // ---------------- OBJECT INTERACTION ----------------
    public void pickUpObject(int index) {

        if (index == 999) return;

        SuperObject object = gp.objList.get(index);
        ObjectType type = object.type; 

        switch (type) {

            case KEY:
                hasKey++;
                gp.objList.remove(index);      
                gp.ui.showMessage("You got a key!");
                gp.playSE(1);
                break;

            case DOOR:
                if (hasKey > 0) {
                    hasKey--;
                    gp.objList.remove(index);
                    gp.ui.showMessage("You opened the door!");
                    gp.playSE(3);
                } else {
                    gp.ui.showMessage("You need a key to open the door!");
                }
                break;

            case BOOTS:
                speed += 2;
                gp.objList.remove(index);
                gp.ui.showMessage("You got the boots! Speed increased!");
                gp.playSE(2);
                break;

            case CHEAT:
                gp.ui.gameFinished = true;
                gp.stopSound();
                gp.playSE(4);
                break;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up": image = (spriteNum == 1) ? up1 : up2; break;
            case "down": image = (spriteNum == 1) ? down1 : down2; break;
            case "left": image = (spriteNum == 1) ? left1 : left2; break;
            case "right": image = (spriteNum == 1) ? right1 : right2; break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}