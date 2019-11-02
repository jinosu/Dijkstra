
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.java.swing.plaf.motif.MotifButtonListener;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import processing.core.PApplet;

/**
 *
 * @author vento
 */
public class GraphDrawing extends JFrame implements MouseListener, MouseMotionListener, KeyListener {

    public static void main(String[] args) {
        try {
            GraphDrawing gui = new GraphDrawing();
        } catch (Exception ex) {

        }
    }
    //Data of graph
    ArrayList<Vertex> Vertexs = new ArrayList<>();
    ArrayList<Edge_> Edge_s = new ArrayList<>();

    Object selected = null;
    TempEdge TempEdge = null; //TempEdge edge

    //UI 
    Canvas c;
    String mode = "Vertex"; //Vertex,Edge_

    //set font 
    Font sanSerifFont = new Font("SanSerif", Font.PLAIN, 24);

    //find size monitor
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    ShortestPath z;
    JFrame frameHelp = new JFrame("Help");

    JPanel boxSave = new JPanel();
    JPanel boxOpen = new JPanel();
    JPanel boxHelp = new JPanel();

    JButton saveButt = new JButton();
    JButton openButt = new JButton();
    JButton helpButt = new JButton();
    JButton run = new JButton();
    JLabel s = new JLabel("start");
    JLabel e = new JLabel("end");    
    JTextField start = new JTextField();
    JTextField end = new JTextField();
    JFileChooser pathSave = new JFileChooser();
    JFileChooser pathOpen = new JFileChooser();

    JLabel helpString = new JLabel();

    //-----###e
    JPanel menubar = new JPanel();
    int shift = 50;

    //////////////////////////////// Backup ////////////////////////////////
    class Backup {

        ArrayList<Vertex> VertexsBackup;
        ArrayList<Edge_> Edge_sBackup;

        public Backup() {
            this.VertexsBackup = Vertexs;
            this.Edge_sBackup = Edge_s;
        }

    }

    GraphDrawing() {
        super("canvas");

        // create a empty canvas 
        c = new Canvas() {
            @Override
            public void paint(Graphics g) {
            }
        };
        c.setBackground(Color.white);

        // add mouse listener 
        c.addMouseListener(this);
        c.addMouseMotionListener(this);

        // add keyboard listener 
        c.addKeyListener(this);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });

        saveButt.setFont(sanSerifFont);
        openButt.setFont(sanSerifFont);
        helpButt.setFont(sanSerifFont);
        s.setFont(sanSerifFont);
        e.setFont(sanSerifFont);
        run.setFont(sanSerifFont);
        start.setFont(sanSerifFont);
        end.setFont(sanSerifFont);
        helpString.setFont(sanSerifFont);

        boxSave.setBackground(Color.white);
        boxOpen.setBackground(Color.white);

        boxHelp.setBackground(Color.white);
        frameHelp.add(boxHelp);

        //button
        saveButt.setText("save");
        saveButt.setBounds((screenSize.width - getWidth()) - 400 + shift, 100, 150, 23);
        getContentPane().add(saveButt);
        saveButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    saveButtAction(e);
                } catch (IOException ex) {
                    Logger.getLogger(GraphDrawing.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        openButt.setText("open");
        openButt.setBounds((screenSize.width - getWidth()) - 400 + 150 + shift, 100, 150, 23);
        getContentPane().add(openButt);
        openButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    openButtAction(e);
                } catch (IOException ex) {
                    Logger.getLogger(GraphDrawing.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        start.setBounds((screenSize.width - getWidth()) - 400 + shift, 200, 50, 23);
        getContentPane().add(start);
        s.setBounds((screenSize.width - getWidth()) - 400 + shift, 170, 50, 23);
        getContentPane().add(s);
        end.setBounds((screenSize.width - getWidth()) - 400 + 75 + shift, 200, 50, 23);
        getContentPane().add(end);
        e.setBounds((screenSize.width - getWidth()) - 400 + 75 + shift, 170, 50, 23);
        getContentPane().add(e);
        run.setText("run");
        run.setBounds((screenSize.width - getWidth()) - 400 + 150 + shift, 200, 150, 23);
        getContentPane().add(run);
        start.setText("A");
        start.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                start.setText("");
            }
        });
        end.setText("A");
        end.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                end.setText("");
            }
        });
        final JPanel panel = new JPanel();

        run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String star = start.getText();
                String en = end.getText();
            
                boolean pp = false;
                boolean pp2 = false;
              
                for (Edge_ Vertex1 : Edge_s) {
                    if (Vertex1.vertexA.name.equals(star) || Vertex1.vertexB.name.equals(star)) {
                        pp = true;
                    }
                    if (Vertex1.vertexA.name.equals(en) || Vertex1.vertexB.name.equals(en)) {
                        pp2 = true;
                    }
                }
                if (Edge_s.size() != 0) {
                   if (Vertexs.size() + 65 > start.getText().charAt(0) && start.getText().charAt(0) > 64 && start.getText().length() == 1
                            && Vertexs.size() + 65 > end.getText().charAt(0) && end.getText().charAt(0) > 64 && end.getText().length() == 1) {
                        if (pp && pp2) {
                            for (Edge_ Edge_1 : Edge_s) {

                                //System.out.println(Edge_1.vertexA.name + "  " + Edge_1.vertexB.name);
                            }
                            int[][] m = new int[Vertexs.size()][Vertexs.size()];
                            for (int i = 0; i < Edge_s.size(); i++) {
                                m[(int) Edge_s.get(i).vertexA.name.charAt(0) - 65][(int) Edge_s.get(i).vertexB.name.charAt(0) - 65] = Integer.parseInt(Edge_s.get(i).weight);
                                m[(int) Edge_s.get(i).vertexB.name.charAt(0) - 65][(int) Edge_s.get(i).vertexA.name.charAt(0) - 65] = Integer.parseInt(Edge_s.get(i).weight);
                                // System.out.println(Edge_s.get(i).vertexA.name + "   " + Edge_s.get(i).vertexB.name + "    " + Edge_s.get(i).weight);
                            }
                            z = new ShortestPath(m);
                            z.dijkstra(m, star.charAt(0) - 65);
                            String s, r;
                            ArrayList<dataxy> beaseposition = new ArrayList<>();
                            ArrayList<dataxy> beasepositionv = new ArrayList<>();
                            ArrayList<dataxy> tn = new ArrayList<>();
                            ArrayList<ArrayList<dataxy>> position = new ArrayList<>();
                            dataxy p;
                            for (String string : z.sum) {
                                //   System.out.println(string);
                            }

                            for (int i = 0; i < z.sum.length; i++) {
                                tn = new ArrayList<>();
                                 for (Vertex Vertex1 : Vertexs) {
                                     p = new dataxy(Vertex1.x, Vertex1.y);
                                     if(Vertex1.name.equals(star))
                                         p.base="0";
                                     beasepositionv.add(p);
                                }
                                for (Edge_ object : Edge_s) {
                                      //  System.out.println(Vertexs.get(Vertexs.indexOf(object.vertexA)).name);
                                   // System.out.println(Vertexs.get(Vertexs.indexOf(object.vertexB)).name);
                                    //System.out.print(Vertexs.get(Vertexs.indexOf(object.vertexA)).x+"    ");
                                    //System.out.println(Vertexs.get(Vertexs.indexOf(object.vertexA)).y);
                                    // System.out.print(Vertexs.get(Vertexs.indexOf(object.vertexB)).x+"    ");
                                    //System.out.println(Vertexs.get(Vertexs.indexOf(object.vertexB)).y);
                                    //       System.out.println(z.sum[i]);nnn
                                    //  System.err.println("/////////////////////////////");
                                    //  System.out.println(object.vertexA.name+"   "+object.vertexB.name);
                                    System.out.println(object.vertexA.x+"---"+object.vertexA.y);
                                    System.out.println(object.vertexB.x+"---"+object.vertexB.y);
                                    p = new dataxy(object.vertexA.x, object.vertexA.y, object.vertexB.x,
                                            object.vertexB.y, object.vertexA.name, object.vertexB.name, object.weight);
                                      System.out.println(object.vertexA.name+"???"+object.vertexB.name);
                               
                                
                                    beaseposition.add(p);
                                    for (int j = 0; j < z.sum[i].length() - 1; j++) {
                                        s = String.valueOf(z.sum[i].charAt(j));
                                        r = String.valueOf(z.sum[i].charAt(j + 1));
                                        // System.out.println(s+"   "+r);System.out.println(object.vertexA.x+"   "+object.vertexA.y);
                                        // System.out.println(object.vertexA.name+"   "+object.vertexB.name+"   "+z.sum[i].charAt(z.sum[i].length()-1));
                                        if (object.vertexA.name.equals(s) && object.vertexB.name.equals(r)) {
                                            // System.out.println(object.vertexA.x+"   "+object.vertexA.y);

                                            p = new dataxy(object.vertexA.x, object.vertexA.y, object.vertexB.x, object.vertexB.y, object.vertexA.name, object.vertexB.name, object.weight);

                                            tn.add(p);

                                        } else if (object.vertexB.name.equals(s) && object.vertexA.name.equals(r)) {
                                            p = new dataxy(object.vertexB.x, object.vertexB.y, object.vertexA.x, object.vertexA.y, object.vertexB.name, object.vertexA.name, object.weight);

                                            tn.add(p);
                                        }
                                    }

                                }
                                position.add(tn);
                                //      System.out.println(position.size());
                            }
                            for (String string : z.sum) {
                                //     System.out.println(string);
                            }
                            ArrayList<dataxy> tn2 = new ArrayList<>();
                            ArrayList<ArrayList<dataxy>> position2 = new ArrayList<>();
                            for (int i = 0; i < z.rq.size(); i++) {
                                tn2 = new ArrayList<>();
                                for (Edge_ object : Edge_s) {

                                    //       System.out.println(z.sum[i]);
                                    for (int j = 0; j < z.rq.get(i).length() - 1; j++) {
                                        s = String.valueOf(z.rq.get(i).charAt(j));
                                        r = String.valueOf(z.rq.get(i).charAt(j + 1));

                                        if (object.vertexA.name.equals(s) && object.vertexB.name.equals(r)) {
                                            // System.out.println(object.vertexA.x+"   "+object.vertexA.y);
                                            //System.out.println(s+"   "+r);System.out.println(object.vertexA.x+"   "+object.vertexA.y);

                                            p = new dataxy(object.vertexA.x, object.vertexA.y, object.vertexB.x, object.vertexB.y, object.vertexA.name, object.vertexB.name, object.weight);
                                            if (j == z.rq.get(i).length() - 2) {
                                                p.vec = z.vec.get(i);
                                            }
                                            tn2.add(p);

                                        } else if (object.vertexB.name.equals(s) && object.vertexA.name.equals(r)) {

                                            //   System.out.println(s+"   "+r);System.out.println(object.vertexA.x+"   "+object.vertexA.y);
                                            p = new dataxy(object.vertexB.x, object.vertexB.y, object.vertexA.x, object.vertexA.y, object.vertexB.name, object.vertexA.name, object.weight);
                                            if (j == z.rq.get(i).length() - 2) {
                                                p.vec = z.vec.get(i);
                                            }
                                            tn2.add(p);
                                        }
                                    }

                                }
                                position2.add(tn2);
                                //    System.out.println(position2.size());           
                            }
                            for (ArrayList<dataxy> arrayList : position2) {
                                //      System.out.println(arrayList.size());

                            }
                            draw draw = new draw(position);
                            draw.setBeaseposition(beaseposition);
                            draw.beasepositionv=beasepositionv;
                            draw.position2 = position2;
                            draw.v = z.cq;
                            draw.vec = z.vec;
                            draw.sum = z.sum;
                            draw.level = z.level;
                            draw.level1 = z.level1;
                            draw.level2 = z.level2;
                            draw.end = en;
                              draw.start = star;
                            draw.dist = z.dist;
                            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                            PApplet.runSketch(new String[]{"ProcessingTest"}, draw);

                        } else {
                            JOptionPane.showMessageDialog(panel, "กรุณาใส่ตัวอักษรที่จุดยอดมีเส้นเชื่อม", "เกิดข้อผิดพลาด", JOptionPane.ERROR_MESSAGE);

                        }

                    } else {
                        JOptionPane.showMessageDialog(panel, "กรุณาใส่ตัวอักษรที่มีอยู่ในกราฟ", "เกิดข้อผิดพลาด", JOptionPane.ERROR_MESSAGE);

                    }

                } else {
                    JOptionPane.showMessageDialog(panel, "กรุณาสร้างกราฟหรือเปิดไฟล์กราฟ", "เกิดข้อผิดพลาด", JOptionPane.ERROR_MESSAGE);

                }

            }
        });
        helpButt.setText("Help");
        helpButt.setBounds((screenSize.width - getWidth()) - 400 + shift, 40, 300, 23);
        getContentPane().add(helpButt);
        helpButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                helpButtAction(e);
            }
        });

        //-----###e     
        menubar.setBackground(Color.cyan);
        menubar.setBounds((screenSize.width - getWidth()) - 400, 0, 400, (screenSize.height - getHeight()));
        c.setBounds(0, 0, (screenSize.width - getWidth()) - 400, (screenSize.height - getHeight()));
        setBounds(0, 0, (screenSize.width - getWidth()), (screenSize.height - getHeight()));
        //setUndecorated(true);
        //setVisible(true);
        add(c);
        add(menubar);
        // setSize(1500, 1000);
        show();
    }

    //
    void helpButtAction(ActionEvent e) {
        String help = "<html>";
        help += "Double click for create Vertex<br>";
        help += "Click on Vertex then type for rename<br>";
        help += "Click on Vertex or center of edge then it is blue you can edit etc move , rename , delete <br>";
        help += "Click on Vertex then press delete for remove Vertex<br>";
        help += "Press and hold spacebar with drag mouse for create edge<br>";
        help += "Click on character on edge then type for rename<br>";
        help += "Click on character on edge then drag mouse for move edge<br>";
        help += "Click on character on edge then press delete for remove edge<br>";
        help += "Press Button save for save Graph on canvas to json file<br>";
        help += "Press Button open for open Graph json file to canvas<br>";
         help += "<br>";
         help += "                              SHOW OUTPUT<br>";
        help += "Press button 'A' on keyboard for show answer<br>";
         help += "Press button 'N' on keyboard for next step<br>";
          help += "Press button 'B' on keyboard for back step<br>";
           help += "Press button 'M' on keyboard for change display mode<br>";
            help += "<br>";
        helpString.setText(help);
        boxHelp.add(helpString);
        boxHelp.setAutoscrolls(true);

        frameHelp.setBounds(screenSize.width / 2 - 500, screenSize.height / 2 - 200, 1000, 600);
        frameHelp.setVisible(true);
    }

    void saveButtAction(ActionEvent e) throws IOException {
        pathSave.setBounds(60, 120, 750, 450);
        boxSave.add(pathSave);

        int ret = pathSave.showDialog(null, "save");
        String path = "";

        if (ret == JFileChooser.APPROVE_OPTION) {
            File filePath = pathSave.getSelectedFile();
            path = filePath.getPath();
            save(path);
        }
    }

    void openButtAction(ActionEvent e) throws IOException {
        pathOpen.setBounds(60, 120, 750, 450);
        boxOpen.add(pathOpen);

        int ret = pathOpen.showDialog(null, "open");
        String path = "";

        if (ret == JFileChooser.APPROVE_OPTION) {
            File filePath = pathOpen.getSelectedFile();
            path = filePath.getPath();
            open(path);
            draw();
        }
    }

    public void save(String path) throws IOException {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        FileWriter writer = new FileWriter(path);

        Backup backup = new Backup();
        writer.write(gson.toJson(backup));
        writer.close();
    }

    public void open(String path) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        Backup backup = gson.fromJson(bufferedReader, Backup.class);

        Vertexs = backup.VertexsBackup;
        Edge_s = backup.Edge_sBackup;

        //bind object reference
        for (Edge_ e : Edge_s) {
            if (e.vertexA != null) {
                int id = e.vertexA.id;
                for (Vertex v : Vertexs) {
                    if (v.id == id) {
                        e.vertexA = v;
                        break;
                    }
                }
            }
            if (e.vertexB != null) {
                int id = e.vertexB.id;
                for (Vertex v : Vertexs) {
                    if (v.id == id) {
                        e.vertexB = v;
                        break;
                    }
                }
            }
        }
    }

    //set canvas is white
    public void clear() {
        Graphics2D g = (Graphics2D) c.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void selected(int x, int y) {
        Object obj = null;
        for (Vertex s : Vertexs) {
            if (s.inCircle(x, y)) {
                s.isSelect = true;
                obj = s;
                break;
            }
        }
        if (obj == null) {
            for (Edge_ t : Edge_s) {
                if (t.inLine(x, y)) {
                    t.isSelect = true;
                    obj = t;
                    break;
                }
            }
        }
        if (obj == null) {
            if (selected == null) {
                return;
            } else {
                if (selected instanceof Vertex) {
                    Vertex s = (Vertex) selected;
                    s.isSelect = false;
                } else {
                    Edge_ t = (Edge_) selected;
                    t.isSelect = false;
                }
                selected = null;
            }
        } else {
            if (selected == null) {
                selected = obj;
            } else {
                if (obj == selected) {
                    return;
                } else {
                    if (selected instanceof Vertex) {
                        Vertex s = (Vertex) selected;
                        s.isSelect = false;
                    } else {
                        Edge_ t = (Edge_) selected;
                        t.isSelect = false;
                    }
                    selected = obj;
                }
            }
        }
    }

    public void draw() {
        clear();

        Graphics2D g = (Graphics2D) c.getGraphics();
        g.setFont(sanSerifFont);
        for (int j = 0; j < Vertexs.size(); j++) {
            Vertexs.get(j).name = String.valueOf((char) (j + 65));
        }
        for (Edge_ Edge_1 : Edge_s) {
            for (Vertex Vertex1 : Vertexs) {
                if (Edge_1.vertexA.x == Vertex1.x && Edge_1.vertexA.y == Vertex1.y) {
                    System.out.println(Edge_1.vertexA.x + "  " + Vertex1.x + "  " + Edge_1.vertexA.y + "  " + Vertex1.y);
                    Edge_1.vertexA.name = Vertex1.name;
                }
                if (Edge_1.vertexB.x == Vertex1.x && Edge_1.vertexB.y == Vertex1.y) {
                    System.out.println(Edge_1.vertexA.x + "  " + Vertex1.x + "  " + Edge_1.vertexA.y + "  " + Vertex1.y);

                    Edge_1.vertexB.name = Vertex1.name;
                }
            }
        }
        for (Edge_ t : Edge_s) {
            t.draw(g);
        }
        if (TempEdge != null) {
            TempEdge.line(g);
        }
        for (Vertex s : Vertexs) {
            s.draw(g);
        }
    }

////////////////////////////////  UI EVENT  ////////////////////////////////
    // 3.1 mouse listener and mouse motion listener mehods 
    // keyboard listener and keyboard motion listener mehods 
    @Override
    public void keyTyped(KeyEvent ke) {
        //System.out.println("key " + ke.getKeyChar() + " = " + (int) ke.getKeyChar());
        if ((int) ke.getKeyChar() == 19) {
            try {
                //ctrl + S
                save("backup.json");
            } catch (IOException ex) {

            }
        } else if ((int) ke.getKeyChar() == 15) {
            try {
                //ctrl + O 
                open("backup.json");
            } catch (IOException ex) {

            }
        } else if ((int) ke.getKeyChar() == 14) {
            //ctrl + N 

        } else if ((int) ke.getKeyChar() == 12) {
            //ctrl + L

        } else if ((int) ke.getKeyChar() == 18) {
            //ctrl + R 

        } else if ((int) ke.getKeyChar() == 9) {

        } else if ((int) ke.getKeyChar() == 1) {
            //ctrl + A 

        } else if ((int) ke.getKeyChar() == (int) 'v') {
            //ctrl + A 

        }

        if (selected instanceof Vertex) {
            Vertex s = (Vertex) selected;
            int status = (int) ke.getKeyChar();
            if (status == 8) { //delete
                if (s.name.length() > 1) {
                    s.name = s.name.substring(0, s.name.length() - 1).trim();
                } else {
                    s.name = "".trim();
                }

            } else if (status == 127) { // space
                ArrayList<Edge_> TempEdge = new ArrayList<>();
                for (Edge_ t : Edge_s) {
                    if (t.vertexA == selected || t.vertexB == selected) {
                        TempEdge.add(t);
                    }
                }
                for (Edge_ t : TempEdge) {
                    Edge_s.remove(t);
                }
                Vertexs.remove(selected);
                selected = null;

            } else {
                s.name += ke.getKeyChar();
                s.name = s.name.trim();
            }

        } else if (selected instanceof Edge_) {
            Edge_ t = (Edge_) selected;
            int status = (int) ke.getKeyChar();
            if (status == 8) {
                if (t.weight.length() > 1) {
                    t.weight = t.weight.substring(0, t.weight.length() - 1).trim();
                } else {
                    t.weight = "".trim();
                }
            } else if (status == 127) {
                Edge_s.remove(selected);
                selected = null;

            } else {
                if (ke.getKeyChar() == ' ') {
                    return;
                }
                t.weight += ke.getKeyChar();
                t.weight = t.weight.trim();
            }

        }
        draw();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if ((int) ke.getKeyChar() == 32) {
            //press space bar
            mode = "Edge_";
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //release space bar
        mode = "Vertex";
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        selected(x, y);
        if (e.getClickCount() == 2 && !e.isConsumed()) {
            e.consume();
            if (!Vertexs.contains(selected)) {
                Vertex TempVertex = new Vertex(x, y);
                Vertexs.add(TempVertex);
            }
        }
        draw();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (mode.equals("Vertex")) {
            if (selected != null) {
                if (selected instanceof Vertex) {
                    Vertex s = (Vertex) selected;
                    for (Edge_ t : Edge_s) {
                        if (t.vertexA == s || t.vertexB == s) {
                            int difx = x - s.x;
                            int dify = y - s.y;
                            if (t.vertexA != t.vertexB) {
                                if (t.vertexA != null) {
                                    t.x_center = (t.vertexA.x + t.vertexB.x) / 2;
                                    t.y_center = (t.vertexA.y + t.vertexB.y) / 2;
                                }
                            } else {
                                t.x_center += difx;
                                t.y_center += dify;
                            }

                        }
                    }
                    s.x = x;
                    s.y = y;
                } else {
                    Edge_ t = (Edge_) selected;
                    t.x_center = x;
                    t.y_center = y;
                }
            }

        } else if (mode.equals("Edge_")) {
            try {
                Vertex Vertex = null;
                for (Vertex s : Vertexs) {
                    if (s.inCircle(x, y)) {
                        Vertex = s;
                    }
                }
                if (Vertex != null) {
                    if (Vertex != TempEdge.vertexA) {
                        double angle = Math.atan2(TempEdge.vertexA.y - Vertex.y, TempEdge.vertexA.x - Vertex.x);
                        double dx = Math.cos(angle);
                        double dy = Math.sin(angle);
                        TempEdge.x1 = Vertex.x + (int) (Vertex.r * dx);
                        TempEdge.y1 = Vertex.y + (int) (Vertex.r * dy);
                    } else {
                        TempEdge.x1 = x;
                        TempEdge.y1 = y;
                    }
                } else {
                    TempEdge.x1 = x;
                    TempEdge.y1 = y;
                }
            } catch (Exception ex) {

            }
        }
        draw();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (mode.equals("Vertex")) {
            TempEdge = null;
        } else if (mode.equals("Edge_")) {
            try {
                TempEdge.x1 = x;
                TempEdge.y1 = y;
                Vertex vertexB = null;
                for (Vertex s : Vertexs) {
                    if (s.inCircle(x, y)) {
                        TempEdge.x1 = s.x;
                        TempEdge.y1 = s.y;
                        vertexB = s;
                        Edge_ edge = new Edge_(TempEdge.vertexA, vertexB);

                        if (s != TempEdge.vertexA) {
                            edge.x_center = (TempEdge.vertexA.x + s.x) / 2;
                            edge.y_center = (TempEdge.vertexA.y + s.y) / 2;
                        } else {
                            double angle = Math.atan2(y - TempEdge.vertexA.y, x - TempEdge.vertexA.x);
                            double dx = Math.cos(angle);
                            double dy = Math.sin(angle);

                            int rc = 3 * TempEdge.vertexA.r;

                            edge.x_center = TempEdge.vertexA.x + (int) (dx * rc);
                            edge.y_center = TempEdge.vertexA.y + (int) (dy * rc);

                        }

                        Edge_s.add(edge);
                        break;
                    }
                }
                TempEdge = null;
            } catch (Exception ex) {
            }
        }

        draw();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (mode.equals("Edge_")) {
            TempEdge = new TempEdge(x, y);
            for (Vertex s : Vertexs) {
                if (s.inCircle(x, y)) {
                    TempEdge.setA(s);
                    break;
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }
}
