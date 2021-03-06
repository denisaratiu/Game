package summativegame;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author ratid6445
 */
public class summativeGame extends JComponent {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    //Title of the window
    String title = "My Game";
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    // YOUR GAME VARIABLES WOULD GO HERE
    // make the mushroom into a rectangle
    Rectangle mushroom = new Rectangle(350, 480, 70, 70);
    // make the clouds into a rectangle
    Rectangle[] clouds = new Rectangle[10];
    // make colors for variables used in game
    Color grassGreen = new Color(144, 212, 145);
    Color lightBlue = new Color(151, 227, 252);
    // mushroom movement
    boolean right = false;
    boolean left = false;
    boolean jump = false;
    // mushroom score
    int mushroomScore = 0;
    // create a counter in the corner for how high the mushroom is
    Font myFont = new Font("Arial", Font.BOLD, 45);
    // insert image from web to represent clouds
    BufferedImage cloud = loadImage("summativegame/cloud.png");
    // insert image from web to represent mushroom
    BufferedImage mushroomImage = loadImage("summativegame/mushroom.png");
    // variable to add speed
    int add = 3;
    int camY = 0;
    int camSpeed = 0;
    // displacement of y - how much you move up/down each time
    int dy = 0;
    // displacement of x - how much you move left/right each frame
    int dx = 0;
    // how much the dx should decrease by if not "moving". This gives the decelerating effect
    double decay = 0.8;
    // gravity (down)
    int gravity = 1;
    // is the mushroom in the air or not? Prevents "air jumping"
    boolean inAir = false;
    // how hard the character jumps up. 
    int JUMP_VELOCITY = -40;
    // maximum speed the dy can be
    int MAX_Y_VELOCITY = 20;
    // maximum speed the dx can be
    int MAX_X_VELOCITY = 6;
    // if user wants to play again
    String restartGame = "Press y to play again";
    boolean end = false;
    int level = 0;

    // GAME VARIABLES END HERE   
    // Constructor to create the Frame and place the panel in
    // You will learn more about this in Grade 12 :)
    public summativeGame() {
        // creates a windows to show my game
        JFrame frame = new JFrame(title);

        // sets the size of my game
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(this);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        // add listeners for keyboard and mouse
        frame.addKeyListener(new Keyboard());
        Mouse m = new Mouse();

        this.addMouseMotionListener(m);
        this.addMouseWheelListener(m);
        this.addMouseListener(m);
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE

        // make background colour blue
        g.setColor(lightBlue);
        // fill in the sky
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // make ground green and mushroom start on ground
        g.setColor(grassGreen);
        // grass
        g.fillRect(0, 540 - camY, WIDTH, 100);

        // use a for loop to go through the array of clouds
        for (int i = 0; i < clouds.length; i++) {
            if (clouds[i] != null) {
                g.drawImage(cloud, clouds[i].x, clouds[i].y - camY, clouds[i].width, clouds[i].height, this);
            }
        }

        // insert the mushroom as player and put him on grass
        g.drawImage(mushroomImage, mushroom.x, mushroom.y - camY, mushroom.width, mushroom.height, this);

        //sets font
        g.setFont(myFont);
        // sets color
        g.setColor(Color.BLUE);
        // score
        g.drawString("" + mushroomScore, 40, 50);

        if (level == 1) {
            //sets fonts
            g.setFont(myFont);
            // set color
            g.setColor(Color.DARK_GRAY);
            g.drawString("" + restartGame, 22, 588);
        }

        // GAME DRAWING ENDS HERE
    }

    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void preSetup() {
        // Any of your pre setup before the loop starts should go here

        // clouds in different places
        clouds[0] = new Rectangle(300, 300, 90, 50); // cloud 1
        clouds[1] = new Rectangle(42, 50, 90, 50); // cloud 2
        clouds[2] = new Rectangle(100, 200, 90, 50); // cloud 3
        clouds[3] = new Rectangle(600, 150, 90, 50); // cloud 4
        clouds[4] = new Rectangle(500, 100, 90, 50); // cloud 5
        clouds[5] = new Rectangle(200, 400, 90, 50); // cloud 1
        clouds[6] = new Rectangle(20, -39, 90, 50); // cloud 2
        clouds[7] = new Rectangle(100, -300, 90, 50); // cloud 3
        clouds[8] = new Rectangle(600, -150, 90, 50); // cloud 4
        clouds[9] = new Rectangle(500, - 100, 90, 50); // cloud 5
        // make x coordinate random
        clouds[0].x = (int) (Math.random() * (WIDTH - clouds[0].width - 0 + 1)) + 1;
        clouds[1].x = (int) (Math.random() * (WIDTH - clouds[1].width - 0 + 1)) + 1;
        clouds[2].x = (int) (Math.random() * (WIDTH - clouds[2].width - 0 + 1)) + 1;
        clouds[3].x = (int) (Math.random() * (WIDTH - clouds[3].width - 0 + 1)) + 1;
        clouds[4].x = (int) (Math.random() * (WIDTH - clouds[4].width - 0 + 1)) + 1;
        clouds[5].x = (int) (Math.random() * (WIDTH - clouds[5].width - 0 + 1)) + 1;
        clouds[6].x = (int) (Math.random() * (WIDTH - clouds[6].width - 0 + 1)) + 1;
        clouds[7].x = (int) (Math.random() * (WIDTH - clouds[7].width - 0 + 1)) + 1;
        clouds[8].x = (int) (Math.random() * (WIDTH - clouds[8].width - 0 + 1)) + 1;
        clouds[9].x = (int) (Math.random() * (WIDTH - clouds[9].width - 0 + 1)) + 1;
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        preSetup();

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 

            // if mushroom is too slow for the camera
            if (mushroom.y >= camY + HEIGHT && level == 0) {

                end = true;
                level = 1;

            }

            if (level == 2) {

                // reset game
                done = false;
                end = false;
                right = false;
                left = false;
                jump = false;

                // mushroom score
                mushroomScore = 0;
                add = 3;
                camY = 0;
                camSpeed = 0;

                // mushroom position
                mushroom.x = 480;
                mushroom.y = 350;

                // reset gravity
                dy = 0;
                dx = 0;
                decay = 0.8;
                gravity = 1;

                // reset velocity
                inAir = false;
                JUMP_VELOCITY = -40;
                MAX_Y_VELOCITY = 20;
                MAX_X_VELOCITY = 6;

                // reset the cloud positions
                clouds[0].x = 300;
                clouds[0].y = 300;
                clouds[1].x = 42;
                clouds[1].x = 50;
                clouds[2].x = 100;
                clouds[2].y = 200;
                clouds[3].x = 600;
                clouds[3].y = 150;
                clouds[4].x = 500;
                clouds[4].y = 100;
                clouds[5].y = 200;
                clouds[5].y = 400;
                clouds[6].y = 20;
                clouds[6].y = -39;
                clouds[7].y = 100;
                clouds[7].y = -300;
                clouds[8].y = 600;
                clouds[8].y = -150;
                clouds[9].x = 500;
                clouds[9].y = -100;

                // reset cloud heights and widths
                for (int i = 0; i < clouds.length; i++) {
                    clouds[i].width = 90;
                    clouds[i].height = 50;
                }
            }

            System.out.println("Level " + level);
            if (level == 0) {
                //move mushroom horizontally until mushroom hits space bar
                mushroom.x = mushroom.x + add;
                if (mushroom.x <= 10 || mushroom.x >= 740 && mushroom.y == 480) {
                    add = add * -1;
                }
                if (mushroom.y != 480) {
                    mushroom.x = mushroom.x - add;
                }
                // move clouds up as camY moves
                for (int i = 0; i < clouds.length; i++) {
                    if (camY + HEIGHT <= clouds[i].y) {
                        clouds[i].y = clouds[i].y - 750;
                        randomGenerate(i);
                    }
                }
                // make the mushroom come from the sides of the game width
                if (mushroom.x >= 800) {
                    mushroom.x = mushroom.x - 800;
                }
                if (mushroom.x <= 0) {
                    mushroom.x = mushroom.x + 800;
                }
                // adjuct score to mushroom placement
                mushroomScore = mushroom.y * -1 + 480;

                // apply gravity!
                dy = dy + gravity; // gravity always pulls down!
                // clamp maximum down force
                if (dy > MAX_Y_VELOCITY) {
                    dy = MAX_Y_VELOCITY; // biggest positive dy
                } else if (dy < -MAX_Y_VELOCITY) {
                    dy = -MAX_Y_VELOCITY; // biggest negative dy
                }


                // look at keys for left/right movement
                if (right) {
                    dx = dx + 1; // start ramping up my movement
                    // cap my max speed
                    if (dx > MAX_X_VELOCITY) {
                        dx = MAX_X_VELOCITY;
                    }
                } else if (left) {
                    dx = dx - 1; // start ramping up my movement
                    // cap my max speed
                    if (dx < -MAX_X_VELOCITY) {
                        dx = -MAX_X_VELOCITY;
                    }
                } else {
                    // need to start slowing down
                    dx = (int) (dx * decay); // takes a percentage of what dx was... needs to be an int
                }

                // is jump being pressed and are you standing on something?
                if (jump && !inAir) {
                    inAir = false; // I'm going to be jumping... not on the ground :)
                    dy = JUMP_VELOCITY; // start moving up!
                }


                // apply the forces to x and y
                mushroom.x = mushroom.x + dx;
                mushroom.y = mushroom.y + dy;

                // hit the ground
                if (mushroom.y > 480) {
                    mushroom.y = 480;
                    dy = 0;
                    inAir = false;
                }



                // check for any collisions and fix them
                // see the method below
                checkCollisions();

                // move camera
                if (mushroom.y < 300) {
                    // speed of camera 
                    camSpeed = 9 / 9;
                    camY = camY - camSpeed;
                }
                //make the camera move faster when reached a certain height
                if (camY <= -1000) {
                    camSpeed = 10 / 9;
                    camY = camY - camSpeed;
                }
                
                // if camera at -3000
                if (camY <= -3000) {
                    camSpeed = 11 / 9;
                    camY = camY - camSpeed;
                }

                // if camera at -5500
                if (camY <= -5500) {
                    camSpeed = 12 / 9;
                    camY = camY - camSpeed;
                }

                // if camera at -7000
                if (camY <= -7000) {
                    camSpeed = 13 / 9;
                    camY = camY - camSpeed;
                }

                // if camera at -9000
                if (camY <= -9000) {
                    camSpeed = 14 / 9;
                    camY = camY - camSpeed;
                }

                // if camera at -10000
                if (camY <= -10000) {
                    camSpeed = 2;
                    camY = camY - camSpeed;
                }

                // if camera at -15000
                if (camY <= -15000) {
                    camSpeed = 3;
                    camY = camY - camSpeed;
                }

                // if camera at -17000
                if (camY <= -17000) {
                    camSpeed = 5;
                    camY = camY - camSpeed;
                }

                // if camera at -21000
                if (camY <= -21000) {
                    camSpeed = 8;
                    camY = camY - camSpeed;
                }
            }

            // GAME LOGIC ENDS HERE 
            // update the drawing (calls paintComponent)
            repaint();

            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            try {
                if (deltaTime > desiredTime) {
                    //took too much time, don't wait
                    Thread.sleep(1);
                } else {
                    // sleep to make up the extra time
                    Thread.sleep(desiredTime - deltaTime);
                }
            } catch (Exception e) {
            };
        }

    }

    // Used to implement any of the Mouse Actions
    private class Mouse extends MouseAdapter {
        // if a mouse button has been pressed down

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println(" X: " + e.getX() + " Y: " + e.getY());
        }

        // if a mouse button has been released
        @Override
        public void mouseReleased(MouseEvent e) {
        }

        // if the scroll wheel has been moved
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
        }

        // if the mouse has moved positions
        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }

    // Used to implements any of the Keyboard Actions
    private class Keyboard extends KeyAdapter {
        // if a key has been pressed down

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            // right pressed move right
            if (key == KeyEvent.VK_RIGHT) {
                right = true;
            }
            // left pressed move left
            if (key == KeyEvent.VK_LEFT) {
                left = true;
            }
            // if space pressed to jump 
            if (key == KeyEvent.VK_SPACE) {
                jump = true;
            }
            // if y is pressed to restart game
            if (key == KeyEvent.VK_Y) {
                level = 2;
                System.out.println(level);

            }
        }

        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_RIGHT) {
                right = false;
            } else if (key == KeyEvent.VK_LEFT) {
                left = false;
            } else if (key == KeyEvent.VK_SPACE) {
                jump = false;
            }
            // reset level to 0 if y pressed
            if (key == KeyEvent.VK_Y) {
                level = 0;
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates an instance of my game
        summativeGame game = new summativeGame();

        // starts the game loop
        game.run();
    }

    // A method used to load in an image
    // The filname is used to pass in the EXACT full name of the image from the src folder
    // i.e.  images/picture.png
    public BufferedImage loadImage(String filename) {

        BufferedImage img = null;

        try {
            // use ImageIO to load in an Image
            // ClassLoader is used to go into a folder in the directory and grab the file
            img = ImageIO.read(ClassLoader.getSystemResourceAsStream(filename));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        return img;
    }

    public void checkCollisions() {
        // use a loop to go through each block
        // see if we are hitting any block... if we are not, we should be falling
        boolean colliding = false;
        for (int i = 0; i < clouds.length; i++) {
            // if the mushroom is hitting a block at position i
            if (mushroom.intersects(clouds[i])) {
                // handle the collision with the block at position i
                handleCollision(i);
                colliding = true;
            }
        }

        // if no collision seen, I'm in the air!
        if (mushroom.y != 480 && !colliding) {
            inAir = true;
        }
    }

    // method to fix any collisions that happen
    // the position integer is which block it is colliding with in the array of clouds
    // since all of our clouds are "axis-aligned" it is easier
    // we will determine how much of an overlap we have, and fix the smaller one (x or y)
    public void handleCollision(int position) {
        // set my overlap as a number - -1 means not set
        int overlapX = -1;
        // mushroom is on the left
        if (mushroom.x <= clouds[position].x) {
            // right corner of mushroom subtract left corner of cloud
            overlapX = mushroom.x + mushroom.width - clouds[position].x;
        } else {
            // right corner of cloud subtract left corner of mushroom
            overlapX = clouds[position].x + clouds[position].width - mushroom.x;
        }

        // do the same but for the y values
        // set my overlap as a number - -1 means not set
        int overlapY = -1;
        // mushroom is above the block
        if (mushroom.y <= clouds[position].y) {
            // bottom of mushroom subtract top of block
            overlapY = mushroom.y + mushroom.height - clouds[position].y;
        } else {
            // bottom of block subtract top of mushroom
            overlapX = clouds[position].y + clouds[position].height - mushroom.y;
        }

        // now check which overlap is smaller
        // fix the x overlapping
        // move the mushrooms x position so the no longer hit the block
        // fix the dx so that we are no longer changing that
        if (overlapX < overlapY) {
            dx = 0; // not moving left or right any more 
        } else {
            // fixing the y overlap in the same way
            // above the block
            if (mushroom.y <= clouds[position].y && dy > 0) {
                // no more y collision
                mushroom.y = clouds[position].y - mushroom.height;
                // on the cloud
                inAir = false;
                dy = 0; // not moving up or down anymore 
            }

        }
    }

    // generate clouds at random x coordinates
    public void randomGenerate(int cloudNumber) {
        clouds[cloudNumber].x = (int) (Math.random() * (WIDTH - clouds[0].width - 0 + 1)) + 1;
    }
}
