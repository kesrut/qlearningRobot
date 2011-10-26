package qlearn2;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class map_editor extends JPanel implements MouseListener {
    
    
    int pazymetas ; 
   
    BufferedImage robot;
    BufferedImage wall;
    BufferedImage apple;
    
    public int map[][] ;
   
    public int [][]  editor(int map2[][])
    {
        JFrame frame = new JFrame() ; 
        frame.setTitle("Map editor");
        frame.setSize(500, 400);
        frame.setResizable(false) ;
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE) ;
        Container contentPane = frame.getContentPane() ;
        contentPane.add(this, BorderLayout.CENTER) ; 
        JButton btn = new JButton() ; 
        pazymetas = -1; 
        addMouseListener(this) ; 
        this.map = new int[10][10] ;
        if (map2 != null)
        {
        for (int i=0; i < 10; i++)
        {
            for (int j=0; j < 10; j++)
            {
                map[i][j] = map2[i][j] ; 
            }
        }
        }
        else { this.map = new int[10][10] ; }
        frame.show() ;
        return this.map; 
        
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g ;
        int j=0, i=0 ; 
        int x=0, y=0 ; 
        for (j=0; j < 10; j++)
        {
            x = 0 ; 
            for (i=0; i < 10; i++)
            {
                  
                  Rectangle2D rectangle = new Rectangle2D.Float(x, y, 32, 32) ; 
                  g2d.draw(rectangle) ;
                  x += 32 ;
            }
            y += 32 ; 
        }
        x = 400 ;
        y = 20; 
        for (i = 0; i < 4; i++)
        {
            Rectangle2D rectangle = new Rectangle2D.Float(x, y, 32, 32) ; 
            g2d.draw(rectangle) ;
            y += 64 ; 
        }
        
        File f = new File("images/robot.png") ;
          File f_wall = new File("images/wall.png") ;
          File f_apple = new File("images/apple.png") ;
          robot = null;
          wall = null;
          apple = null;
        try {
            robot = ImageIO.read(f);
            wall = ImageIO.read(f_wall);
            apple = ImageIO.read(f_apple) ; 
        } catch (IOException ex) {
            System.err.println("Error reading file: " + f);
            System.exit(1);
        }
        g2d.drawImage(robot, 400+5, 20, null);
        g2d.drawImage(wall, 400 , 84, null);
        g2d.drawImage(apple, 400, 144, null);
       y=0;
       for (i=0; i < 10; i++ )
          {
              x = 0; 
              for (j=0; j < 10; j++)
              {
                  if (map[i][j] == 1)
                     g2d.drawImage(robot, x+5, y+1, null);
                  if (map[i][j] == 2)
                     g2d.drawImage(wall, x, y, null);
                  if (map[i][j] == 3)
                     g2d.drawImage(apple, x, y, null);
                  x += 32 ;
                
              }
              y += 32 ; 
        }
        
    }
    public void mouseClicked(MouseEvent e) {
      int x = e.getX() ; 
      int y = e.getY() ; 
      Graphics g = this.getGraphics();
      Graphics2D g2d = (Graphics2D)g ;
      
      if (x < 320 && y < 320)
      {
          int x1 = x  / 32 ; 
          int y1 = y /  32 ;
          if (pazymetas >= 0)
          {
             int x2 = x1;
             int y2 = y1 ; 
             System.out.println("labas") ; 
             System.out.println("aaa: " + x2 + " " + y2) ; 
             switch(pazymetas)
             {
                 case 0: { g2d.drawImage(robot, x2*32+5, y2*32, null); map[y2][x2] = 1; break ; }
                 case 1: { g2d.drawImage(wall, x2*32, y2*32, null); map[y2][x2] = 2; break ; }
                 case 2: { g2d.drawImage(apple, x2*32, y2*32-2, null); map[y2][x2] = 3; break ;  }
                 case 3:
                 {
                     Rectangle rectangle = new Rectangle(x2*32, y2*32, 32, 32) ; 
                     g2d.setPaint(getBackground());
                     g2d.fill(rectangle) ;
                     Rectangle2D rectangle2 = new Rectangle2D.Float(x2*32, y2*32, 32, 32) ; 
                     g2d.setPaint(Color.BLACK);
                     g2d.draw(rectangle2) ;
                     map[y2][x2] = 0;
                 }
             }
          }
          
      }
     
      if (x >=400 && x <= 432 && y>= 20 && y <=52)
      {
         // pazymetas = 0 ;  
      }
      if (x >=400 && x <= 432 && y>= 83 && y <=115)
      {
          pazymetas = 1 ;
      }
      if (x >=400 && x <= 432 && y>= 147 && y <=179)
      {
          pazymetas = 2 ;
      }
      if (x > 400 && x < 432 && y >=211 && y <= 243)
      {
          pazymetas = 3; 
      }
    }

    public void mousePressed(MouseEvent e) {
        
    }

    public void mouseReleased(MouseEvent e) {
        
    }

    public void mouseEntered(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {
        
    }

    
   
    
}
