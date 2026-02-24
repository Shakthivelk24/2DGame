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
            if (keyH.upPressed) {  
                direction = "up";
            } else if (keyH.downPressed) {  
                direction = "down";
            } else if (keyH.leftPressed) {     
                direction = "left";
            }
            if (keyH.rightPressed) {
                direction = "right";
            }
            // Check tile collision
            collisionOn = false;
            gp.cChecker.checlTilte(this);
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // If collision is false, player can move
            if(collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldy -= speed;
                        break;
                    case "down":
                        worldy += speed;
                        break;
                    case "left":
                        worldx -= speed;
                        break;
                    case "right":
                        worldx += speed;
                        break;
                    default:
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void pickUpObject(int index){
        if(index != 999){
            String objectName = gp.obj[index].name;

            switch (objectName) {
                case "Key":
                        
                    hasKey++;
                    gp.obj[index] = null;
                    gp.ui.showMessage("You got a key!");
                    gp.playSE(1);
                    break;
                case "Door":
                    if(hasKey > 0){
                        gp.ui.showMessage("You opened the door!");
                        gp.playSE(3);
                        gp.obj[index] = null;
                        hasKey--;
                    } else {
                        gp.ui.showMessage("You need a key to open the door!");
                    }
                    break;
                case "Boots":
                    gp.playSE(2);
                    speed += 2;
                    gp.obj[index] = null;
                    gp.ui.showMessage("You got the boots! Speed increased!");
                    break;
                case "Cheat":
                    gp.ui.gameFinished = true;
                    gp.stopSound();
                    gp.playSE(4);
                    break;
            }
            
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                }
                break;
            default:
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

}
