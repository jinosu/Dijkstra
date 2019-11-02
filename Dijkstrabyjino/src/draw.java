
import com.sun.java.swing.plaf.windows.resources.windows;
import java.util.ArrayList;
import processing.core.*;

public class draw extends PApplet {

    static ArrayList<ArrayList<dataxy>> position = new ArrayList<>();
    public static ArrayList<ArrayList<dataxy>> position2 = new ArrayList<>();
    public ArrayList<dataxy> beaseposition = new ArrayList<>();
    public ArrayList<dataxy> beasepositionv = new ArrayList<>();
    public ArrayList<Integer> v = new ArrayList<>();
    public static ArrayList<Integer> vec = new ArrayList<>();
    ArrayList<String> level = new ArrayList<>();
    ArrayList<String> level1 = new ArrayList<>();
    ArrayList<String> level2 = new ArrayList<>();
    String[] sum;
    int[] dist;
    String end;
    String start;
    public draw(ArrayList<ArrayList<dataxy>> position) {
        this.position = position;
    }

    public void setBeaseposition(ArrayList<dataxy> beaseposition) {
        this.beaseposition = beaseposition;
    }

    @Override
    public void settings() {
        size(1900, 1080);
        for (int k = 0; k < sum.length; k++) {
            String t = "";
           t += sum[k].charAt(0);
            for (int l = 1; l < sum[k].length(); l++) {
              t +=","+sum[k].charAt(l);
            }
            sum[k]= t;
        }
        for (ArrayList<dataxy> arrayList : position2) {
            for (dataxy object : arrayList) {
                System.out.println(object.namee);
            }
        }
    }
    int m = 0;
    int i = 0;
    int j = 0;
    int x = 1, y = 1;
    boolean e = false;

    @Override
    public void draw() {
        textSize((float) (25));
        background(255);
        strokeWeight((float) (3));
        stroke(0, 0, 0);
        for (dataxy position : beaseposition) {
            fill(255);
            line(position.sx, position.sy, position.ex, position.ey);
            ellipse(position.sx, position.sy, 50, 50);
            ellipse(position.ex, position.ey, 50, 50);
            fill(50);
            text(position.names, position.sx, position.sy - 50);
            text(position.namee, position.ex, position.ey - 50);
            text(position.num, (position.ex - position.sx) / 2 + position.sx + 10,
                    (position.ey - position.sy) / 2 + position.sy + 10);
           fill(200, 255, 255);
            rect(1500, 200 + t, 1880, 1000 - t);
        } fill(50);
        for (dataxy position : beasepositionv) {
             text(position.base, position.ex - 20, position.ey + 10);
            text(position.base, position.sx - 20, position.sy + 10);
        } 
        String s = "(V)";
        fill(50);
        text(s, 1540, 270 + t, 1880, 1000);
        s = "dist(V)";
        text(s, 1640, 270 + t, 1880, 1000);
        s = "(prev(V))";
        text(s, 1790, 270 + t, 1880, 1000);
        s = level.get(0);
        fill(50);
        text(s, 1550, 310 + t, 1880, 1000);
        s = level1.get(0);
        text(s, 1650, 310 + t, 1880, 1000);
        s = level2.get(0);
        text(s, 1800, 310 + t, 1880, 1000);
        line(1500, 305 + t, 1900, 305 + t);
        line(1635, 200 + t, 1635, 1000);
        line(1785, 200 + t, 1785, 1000);
        fill(50);
//text(s, 1550, 270,1880, 1000);
        fill(244, 230, 251);
        rect(1500, 0, 1880, 200);
        if (m == 1) {
            partv2();
            if (c) {
                j++;
            }
            if (j == last) {
                last--;
                c = false;
            }

        }
        if (m == 2) {
            for (dataxy position : beaseposition) {

                fill(255);
                line(position.sx, position.sy, position.ex, position.ey);

                ellipse(position.sx, position.sy, 50, 50);
                ellipse(position.ex, position.ey, 50, 50);
                fill(50);

                text(position.num, (position.ex - position.sx) / 2 + position.sx + 10, 
                        (position.ey - position.sy) / 2 + position.sy + 10);
            }
            partv();
        }

    }

    void randomcolor() {
        stroke(random(255), random(255), random(255));
        fill(random(255), random(255), random(255));

    }

    void partv() {
        strokeWeight((float) (3));
        for (dataxy position : position.get(i)) {
            stroke(255, 0, 0);
            if (e) {
              stroke(255, 0, 0);
            }
            line(position.sx, position.sy, position.ex, position.ey);
            stroke(0);
            fill(255);
            if (e) {
                randomcolor();
            }
            ellipse(position.sx, position.sy, 50, 50);
            if (e) {
                randomcolor();
            }
            ellipse(position.ex, position.ey, 50, 50);
            fill(50);

        }
      fill(200, 255, 255);
        rect(1500, 200, 1880, 1000);
        String s = "path : " + sum[i] + "\n" + "weight : " + dist[i];
        fill(50);
        text(s, 1550, 270, 1880, 1000);
        if (dist[i] == Integer.MAX_VALUE) {
          fill(200, 255, 255);
            rect(1500, 200, 1880, 1000);

            s = "path : " + "not have" + "\n" + "weight : " + "impossibleF";
            fill(50);
            text(s, 1550, 270, 1880, 1000);
        }
        fill(244, 230, 251);
        rect(1500, 0, 1880, 200);
        fill(50);
        textSize((float) (59));
        s = "ShortestPath";
        randomcolor();
        if (!e) {
            text(s, 1520, 70, 1880, 190);
        }
        if (e) {
            s = "ANSWER";
            randomcolor();
               textSize((float) (69));
            text(s, 1570, 70, 1880, 190);
        }

    }
    dataxy p;
    ArrayList<dataxy> lk = new ArrayList<>();
    boolean set = true;

    void partv2() {
        strokeWeight((float) (3));
        int x = 0;
        for (dataxy position : position2.get(j)) {

            stroke(0, 215, 50);
            line(position.sx, position.sy, position.ex, position.ey);
            stroke(0);
            fill(255);
            ellipse(position.sx, position.sy, 50, 50);

            ellipse(position.ex, position.ey, 50, 50);
            fill(50);
            if (position.vec != 0) {
                text(position.vec, position.ex - 20, position.ey + 10);
                if (j == 0) {
                    p = new dataxy();
                    p.vec = 0;
                    p.ex = position.sx;
                    p.ey = position.sy;
                    lk.add(p);
                }
                if (re(position.ex - 20, position.ey, position.vec)) {
                    p = new dataxy();
                    p.vec = position.vec;
                    p.ex = position.ex;
                    p.ey = position.ey;
                    lk.add(p);
                }
            }

        }

        call();
     fill(200, 255, 255);
        rect(1500, 200 + t, 1880, 1000 - t);
        String s = "(V)";

        fill(50);
        text(s, 1540, 270 + t, 1880, 1000);
        s = "dist(V)";

        text(s, 1640, 270 + t, 1880, 1000);
        s = "(prev(V))";
        text(s, 1790, 270 + t, 1880, 1000);
        s = level.get(v.get(j) + 1);
        fill(50);
        text(s, 1550, 310 + t, 1880, 1000);
        s = level1.get(v.get(j) + 1);

        text(s, 1650, 310 + t, 1880, 1000);
        s = level2.get(v.get(j) + 1);

        text(s, 1800, 310 + t, 1880, 1000);
        line(1500, 305 + t, 1900, 305 + t);
        line(1635, 200 + t, 1635, 1000);
        line(1785, 200 + t, 1785, 1000);
        x++;
        fill(244, 230, 251);
        rect(1500, 0, 1880, 200);
        textSize((float) (52));
          s = "Step of Search";
        randomcolor();
     
            text(s, 1520, 70, 1880, 190);
        
    }

    boolean re(int x, int y, int num) {
        boolean t = true;
        for (dataxy position : lk) {
            if (position.ex == x && position.ey == y) {
                position.vec = num;
                t = false;
            }
        }

        return t;
    }

    void call() {
        for (dataxy position : lk) {
            fill(255);
            ellipse(position.ex, position.ey, 50, 50);
            fill(50);
            text(position.vec, position.ex - 20, position.ey + 10);

        }
    }
    int last;
    boolean c = false;
    int t = 0;

    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == UP) {
                t -= 5;
            } else if  (keyCode == DOWN) {
                if (t + 5 <= 0) {
                    t += 5;
                }
            }
        }

        if (key == 'n' || key == 'N' || key == 'ื' || key == '์'||e) {
            delay(150);
            last = j;
            System.out.println(last);
            j++;
            i++;
            if (i >= position.size()) {
                i = 0;
            }
            if (j >= position2.size()) {
                j = 0;
                lk = new ArrayList<>();
            }
        }
        if (key == 'b' || key == 'B' || key == 'ิ' || key == 'ฺ'||e) {
            j--;
            i--;
            if (i < 0) {
                i = 0;
            }
            if (j < 0) {
                j = 0;
            }
            if (m == 1 && last >= 0) {
                j = 0;
                lk = new ArrayList<>();
                c = true;
                if (last == 0) {
                    c = false;
                }
            }
        }
        if (key == 'm' || key == 'M' || key == 'ท' || key == '?') {
            set = false;
            lk = new ArrayList<>();
            delay(150);
            j = 0;
            i = 0;
            e = false;
            if (m == 3) {
                m = 0;
            }
            m++;
        }
        if (key == 'a' || key == 'A' || key == 'ฟ' || key == 'ฤ') {
            i = (int) end.charAt(0) - 65;
            m = 2;

            e = true;

        }
       
    }
   
    public static void main(String... args) {

        draw pt = new draw(position);
        PApplet.runSketch(new String[]{"ShortestPath"}, pt);

    }

}
