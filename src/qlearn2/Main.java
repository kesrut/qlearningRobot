package qlearn2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Main {
    
    public static void main(String[] args) throws InterruptedException {
      //  Qlearn q = new Qlearn() ; 
       // q.q();
        learn q = new learn() ;
        q.create();
    }

}
class Qlearn
{ 
    /*
    public int[][] map =
        {{0,0,0,0,0,0,0,0,0,0},
         {2,2,2,2,2,2,2,0,0,0},
         {0,0,2,2,0,0,0,0,0,0},
         {0,0,2,2,0,0,2,0,2,0},
         {0,2,0,0,0,2,0,0,2,0},
         {0,2,0,2,2,2,0,0,2,0},
         {0,0,0,2,0,2,0,0,2,0},
         {0,0,2,0,0,2,0,0,2,0},
         {3,2,0,0,0,2,0,0,2,0},
         {2,0,0,0,0,0,0,0,2,0}
        }; */ 
        
     public int [][]map ; 
     
     public Vector <Object> qmatrix ;
     public Vector <Object> learn ;
     public generate_Q_learn learning ; 
    

    public void q(float rate, int map_[][])
    {
        map = map_ ; 
        learning = new generate_Q_learn() ; 
        learn = learning.generate() ; 
        qmatrix = learning.get_q()  ;
        Vector <Object> row1 = null ; 
        int j=0, i =0, reward =0  ;
        int state = 0, state_next = 0 ; int size= 0, count= 0, max=0 ; int rand=0 ; 
        Random gen = new Random() ;
        int x=0, y=0, x1=0, y1=0 ; 
        int t = 0 ; 
        
        int k=0 ; 
        while (k < 2)
        {
        while (j < 100000)
        {
            Vector <Object> row = (Vector<Object>) learn.elementAt(state) ; 
            size = row.size() ; 
            count  = ((Integer)row.elementAt(size-1)) ;
            rand = gen.nextInt(size-2) ; // paimamke atsitiktini
            x  = ((qwrapper)row.elementAt(rand+1)).return_x() ;
            y = ((qwrapper)row.elementAt(rand+1)).return_y() ; 
            i = 0 ; 
            t = 0 ; 
            while (i < 100)
            {
                row1 = (Vector<Object>) learn.elementAt(i) ; 
                x1 = ((qwrapper)row1.elementAt(0)).return_x() ;
                y1 = ((qwrapper)row1.elementAt(0)).return_y() ;
                if (x == x1 && y == y1) { t = 1; state_next = i;  break ; }
                i++ ;
            }
            if (t==1)
            {
                i = 1 ;
                Vector <Object> rowq = (Vector<Object>) qmatrix.elementAt(state_next) ;
                max = ((Integer)(rowq.elementAt(0))); 
                while (i < row1.size()-2)
                {
                    if (((Integer)(rowq.elementAt(i))) > max) max = ((Integer)(rowq.elementAt(i))) ;
                    i++ ; 
                }
                reward = 0 ;
                if (map[y][x] == 0) { reward = 100 ; } 
                if (map[y][x] == 2) reward = -1000  ;
                if (map[y][x] == 3) { reward = 500;  state_next = gen.nextInt(100) ;  } 
                //}
                int calc = (int) (reward + max * rate) ; 
                ((Vector)qmatrix.elementAt(state)).setElementAt(calc, rand);
                state = state_next ; 
            }
            j++ ; 
        }
       k++;  
    }
        
    }
}     
    
    
    
class generate_Q_learn
{
    private Vector <Object> qmatrix  ;
    public Vector <Object> generate()
    {
      int i=0, j=0 ; 
      int c= 0 ;
      Vector <Object> p = new Vector();
      qmatrix = new Vector() ;
      Vector <Object> row ; 
      Vector <Object> qrow; 
      for (i=0;i < 10; i++)
      {
          for (j=0; j < 10; j++)
          {
              row = new Vector() ;
              qrow = new Vector() ; 
              c = 0 ;
              qwrapper key = new qwrapper() ;
              key.set_values(j, i);
              row.add(key) ; 
              if (((j+1)>=0 && ((j+1)<10)) && ((i>=0) && (i <10))) { qwrapper wrapp = new qwrapper() ; wrapp.set_values(j+1, i); row.add(wrapp); ++c; qrow.add(0);  }
              if ((((j-1)>=0) && ((j-1)<10)) && (i>=0) && (i<10)) { qwrapper wrapp = new qwrapper() ; wrapp.set_values(j-1, i); row.add(wrapp);  ++c; qrow.add(0); }
              if (((j>=0) && (j<10)) && (i+1)>=0 && ((i+1)<10)) { qwrapper wrapp = new qwrapper() ; wrapp.set_values(j, i+1); row.add(wrapp);  ++c; qrow.add(0); }
              if (((j>=0) && (j<10)) && (((i-1)>=0) && ((i-1)<10))) { qwrapper wrapp = new qwrapper() ; wrapp.set_values(j, i-1); row.add(wrapp);  ++c; qrow.add(0); }
              if ( c > 0 ) row.add(c) ;
              p.add(row) ; 
              qmatrix.add(qrow) ; 
          }
      }
      return p ; 
    }
    
    public Vector <Object> get_q()
    {
        return qmatrix ; 
    }
    
    public void print_q(Vector <Object> matrix)
    {
        for (int i=0; i < 100; i++)
        {
            Vector <Object> row = (Vector<Object>) matrix.elementAt(i) ;
            for (int j=0; j < row.size(); j++)
            {
                System.out.print(row.elementAt(j) + " " ) ;
            }
            System.out.println() ; 
        }
    }
}

class qwrapper
{
  private int x ; 
  private int y;
  
  public void set_values(int x, int y)
  {
      this.x = x ;
      this.y = y ;
  }
  
  public int return_x()
  {
      return x ;  
  }
  public int return_y()
  {
      return y ; 
  }
}
class learn extends JPanel implements ActionListener
{
    /* public int[][] map =
        {{0,0,0,0,0,0,0,0,0,0},
         {2,2,2,2,2,2,2,0,0,0},
         {0,0,2,2,0,0,0,0,0,0},
         {0,0,2,2,0,0,2,0,2,0},
         {0,2,0,0,0,2,0,0,2,0},
         {0,2,0,2,2,2,0,0,2,0},
         {0,0,0,2,0,2,0,0,2,0},
         {0,0,2,0,0,2,0,0,2,0},
         {3,2,0,0,0,2,0,0,2,0},
         {2,0,0,0,0,0,0,0,2,0}} ;*/
    int map[][] ; 
    int map_start[][];
    Qlearn q ;
    JTextField rate ;
    Thread thread ; 
    int pause ; 
    map_editor editor ; 
    
    public void create() throws InterruptedException 
    {
        pause = 0 ; 
        JFrame frame = new JFrame() ; 
        frame.setTitle("Q-Learning Demo");
        frame.setSize(500, 400);
        frame.setResizable(false) ;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ; 
        Container contentPane = frame.getContentPane() ;
        contentPane.setLayout( new BorderLayout()) ;
        
         map_start = new int[10][10] ; 
        map = map_start ;
        JPanel options = new JPanel() ; 
       // JPanel slide = new JPanel() ; 
        JButton start = new JButton("Start") ; 
        JButton map_editor_btn = new JButton("Map editor") ; 
        contentPane.add(this, BorderLayout.CENTER) ;
         // map = q.map ; 
          repaint() ;
            JLabel slider = new JLabel("Speed") ;
        rate = new JTextField("0.9", 4) ; 
        options.add(start, BorderLayout.PAGE_START)  ;
        JButton stop = new JButton("Pause") ;
        options.add(stop, BorderLayout.PAGE_START)  ;
        options.add(map_editor_btn, BorderLayout.PAGE_START) ;
        editor = new map_editor() ; 
        map_editor_btn.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent e) {
           // editor = new map_editor() ; 
            map = editor.editor(map) ; 
            }
    }
);
        stop.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if (pause == 0 )
               {
                   thread.suspend();
                   pause = 1 ;       
               }
               else
               {
                   thread.resume() ;
                   pause = 0 ; 
               }
            }
    }
);
       options.add(new JLabel("Learning rate:"), BorderLayout.PAGE_START)  ; 
       options.add(rate, BorderLayout.PAGE_START)  ; 
       // JSlider fps = new JSlider(JSlider.HORIZONTAL, 0, 30, 5) ;
        //options.add(fps, BorderLayout.PAGE_START)  ;
        contentPane.add(options, BorderLayout.PAGE_START) ;
        frame.show() ;
        start.addActionListener(this);
        
    }
    public void actionPerformed(ActionEvent e)
    {
       if (editor.map != null)
        {
         for (int i=0; i < 10; i++)
         {
             for (int j=0; j< 10; j++)
             {
                 if (map[i][j] == 1) map[i][j] = 0 ; 
             }
         }
        create_map();
        }
    }
    
    
    public void create_map()
    {
        
        if (thread != null)
        {
            thread.stop() ; 
            thread = null ; 
        }
        if (thread == null )
        {
             thread = new Thread(new Runnable() {
        
        public void run() {
         //map = map_start ;
       if (editor.map != null)
         {
         map = editor.map ; 
         q = new Qlearn() ; 
         q.q(Float.valueOf(rate.getText().trim()).floatValue(), map);
         map = q.map ;
        int state = 0 ; 
        int next_state = 0 ;
        int x2=0, y2 = 0 ; 
        for (int i=0; i < 100; i++)
        {
            map[y2][x2] = 0 ;
            Vector <Object> row = ((Vector)q.qmatrix.elementAt(state)) ; 
            int max = ((Integer)row.elementAt(0)) ; 
            int k = 0 ;
            for (int j=1; j < row.size(); j++)
            {
                if (((Integer)row.elementAt(j)) > max )  { k = j ; max = ((Integer)row.elementAt(j)) ;}
            }
            Vector <Object> row_1 = ((Vector)q.learn.elementAt(state)) ;
            int x  = ((qwrapper)row_1.elementAt(k+1)).return_x() ;
            int y = ((qwrapper)row_1.elementAt(k+1)).return_y() ;
            x2 = x; y2 = y; 
            if (map[y][x] != 3) 
            {
                map[y][x] = 1 ; 
                 repaint();

               try {
                  Thread.sleep(500);
               }
               catch (InterruptedException evt) {}
                for (int p=0; p < 100; p++)
                {
                   Vector <Object> row_2 = ((Vector)q.learn.elementAt(p)) ; 
                   int  x1  = ((Integer) ((qwrapper)row_2.elementAt(0)).return_x() );
                   int  y1 = ((Integer)((qwrapper)row_2.elementAt(0)).return_y() );
                   if ( x == x1 && y == y1) { next_state = p; state = next_state;   break ; }
                }
            } else break ; 
         }
        }
        }
        });
        
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.start() ;
        }
       // else { thread.stop(); thread = null ; }
    }
    
     @Override
      public void paintComponent(Graphics g)
      {
         
          super.paintComponent(g);
          Graphics2D g2d = (Graphics2D)g ;
          int x=5 ; 
          int y = 5 ; 
          File f = new File("images/robot.png") ;
          File f_wall = new File("images/wall.png") ;
          File f_apple = new File("images/apple.png") ;
          BufferedImage robot = null;
          BufferedImage wall = null;
          BufferedImage apple = null;
        try {
            robot = ImageIO.read(f);
            wall = ImageIO.read(f_wall);
            apple = ImageIO.read(f_apple) ; 
        } catch (IOException ex) {
            System.err.println("Error reading file: " + f);
            System.exit(1);
        }
          for (int i=0; i < 10; i++ )
          {
              x = 0 ; 
              for (int j=0; j < 10; j++)
              {
                  if (map[i][j] == 1)
                     g2d.drawImage(robot, x+5, y+1, null);
                  if (map[i][j] == 2)
                     g2d.drawImage(wall, x, y, null);
                  if (map[i][j] == 3)
                     g2d.drawImage(apple, x, y, null);
                  Rectangle2D rectangle = new Rectangle2D.Float(x, y, 32, 32) ; 
                  g2d.draw(rectangle) ;
                  x += 32 ;
                
              }
              y += 32 ;  
          }
      } 
     
      public void paint()
      {
          repaint() ; 
      }
}
