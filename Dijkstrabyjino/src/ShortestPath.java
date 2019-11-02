import java.util.*; 
import java.lang.*; 
import java.io.*; 
  
class ShortestPath { 
   static int V;
    static int graph[][];
   String[] num;
   int dist[];
public  String[] s ,e,sum;
    ArrayList<String> level  = new ArrayList<>();
     ArrayList<String> level1  = new ArrayList<>();
      ArrayList<String> level2  = new ArrayList<>();
    ArrayList<String> rq  = new ArrayList<>();
    ArrayList<Integer> vec  = new ArrayList<>();
      ArrayList<Integer> cq  = new ArrayList<>();
    public ShortestPath(int[][] graph) {
        this.graph = graph;
        V = ShortestPath.graph.length;
        s= new String[V];
        e = new String[V];
        sum = new String[V];
        num = new String[V];
        for (int i = 0; i < V; i++) {
            e[i] = "null";
            s[i] = String.valueOf((char) (65+i));
            sum[i] = "";
            num[i] = "Infinity"; 
        }
    }  
    int minDistance(int dist[], Boolean sptSet[]) 
    { 
        System.out.println("/////////////////////");
        int min = Integer.MAX_VALUE, min_index = -1; 
        for (int v = 0; v < V; v++) {
            System.out.println(dist[v]+"   <  "+min);
            if (sptSet[v] == false && dist[v] <= min) { 
                min = dist[v]; 
                min_index = v; 
            }} 
        return min_index; 
    }
    void printSolution(int dist[], int n) 
    { 
     System.out.println("Vertex   Distance from Source"); 
        for (int i = 0; i < V; i++) 
            
            System.out.println(i + " tt " + dist[i]); 
    } 
    void dijkstra(int graph[][], int src) 
    { 
        dist = new int[V];  
        Boolean sptSet[] = new Boolean[V]; 
        for (int i = 0; i < V; i++) { 
            dist[i] = Integer.MAX_VALUE; 
            sptSet[i] = false; 
        } 
        System.out.println((char) (src+65)+" = 0");
        dist[src] = 0;
        sum[src] += (char)(src+65);
        num[src] = "0";
         String k = "";
          String k1 = "";
           String k2 = "";
         int b = 0;
            for (int i = 0; i < V; i++) {
                k+=s[i]+"\n";
                System.out.println(s[i]+"  "+num[i]+"  "+e[i]);
                k1+=num[i]+"\n";
                k2+=e[i]+"\n";
            }
            System.out.println("/////////////////////////");
            level.add(k);
            level1.add(k1);
             level2.add(k2);
            int numc=0;
        for (int count = 0; count < V - 1; count++) {
            int v = minDistance(dist, sptSet); 
           System.out.println((char) (v+65));
            sptSet[v] = true;  
            for (int u = 0; u < V; u++)
                if (!sptSet[u] && graph[v][u] != 0 &&dist[v] != Integer.MAX_VALUE && dist[v] + graph[v][u] < dist[u]){ 
                   
                    System.out.println(((char) (v+65))+"==="+(dist[v] + graph[v][u])+"===="+((char) (u+65)));
                    sum[u] = sum[v]+(char) (u+65);
                    rq.add(sum[u]);
                    dist[u] = dist[v] + graph[v][u];
                    num[u] = String.valueOf(dist[u])+"      ";
                    vec.add(dist[u]);
                    e[u] = String.valueOf((char) (v+65));
                    cq.add(b);
           }
          k = "";
          k1 = "";
          k2 = "";
            for (int i = 0; i < V; i++) {
                k+=s[i]+"\n";
                  k1+=num[i]+"\n";
                k2+=e[i]+"\n";
                System.out.println(s[i]+"  "+num[i]+"  "+e[i]);
                
            }
            System.out.println("/////////////////////////");
            level.add(k);
            level1.add(k1);
             level2.add(k2);
        b++;                    
        } 
      
        printSolution(dist, V); 
        for (int i = 0; i <V; i++) {
            System.out.println(sum[i]);
        }
        for (String string : level) {
            System.out.println(string);
        }
        for (int i = 0; i < rq.size(); i++) {
            System.out.println(rq.get(i)+"="+vec.get(i));
        }
    } 
    
    
} 