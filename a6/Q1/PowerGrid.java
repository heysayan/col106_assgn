import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class PowerLine {
    String cityA;
    String cityB;

    public PowerLine(String cityA, String cityB) {
        this.cityA = cityA;
        this.cityB = cityB;
    }
}

// Students can define new classes here
class CityNode{
    String name;
    ArrayList<PowerLine> nonDFStreelines;
    ArrayList<PowerLine> DFStreelines;
    PowerLine parentline;   // PowerLine connecting this city to the parent city in DFS tree(null for root)
    int visited;    //if the node is visited in the DFS traversal
    int treelevel;  //level in the BFS tree
    int HighPoint;  //Highest Point of dfs tree rooted at this node
    int index;  //index same as that of the city in cityNames array
    int noofbridgesabove;    //no of bridges between ancestors of this node
    CityNode(String name,int index){
        this.name = name;
        nonDFStreelines = new ArrayList<PowerLine>();
        DFStreelines = new ArrayList<PowerLine>();
        visited = 0;
        parentline = null;
        this.index=index;
    }
}

public class PowerGrid {
    int numCities;
    int numLines;
    String[] cityNames;
    PowerLine[] powerLines;
    // Students can define private variables here
    private ArrayList<PowerLine> criticallines;    //arraylist of critical lines
    private int[][] LCAoracle;
    private HashMap<String,CityNode> CityNodeHashMap;
    private CityNode DFStreeroot;
    private int logn;   //value of log(base2)n where n is number of cities

    public PowerGrid(String filename) throws Exception {
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        numCities = Integer.parseInt(br.readLine());

        CityNodeHashMap = new HashMap<String,CityNode>(numCities,1);   //student defined variable

        numLines = Integer.parseInt(br.readLine());
        cityNames = new String[numCities];
        for (int i = 0; i < numCities; i++) {
            cityNames[i] = br.readLine();
            CityNode citynode = new CityNode(cityNames[i],i); //student defined var
            CityNodeHashMap.put(cityNames[i],citynode);     //student defined variable
        }
        powerLines = new PowerLine[numLines];
        for (int i = 0; i < numLines; i++) {
            String[] line = br.readLine().split(" ");
            powerLines[i] = new PowerLine(line[0], line[1]);
            CityNodeHashMap.get(line[0]).nonDFStreelines.add(powerLines[i]);    //adding the powerline to respective cityNodes
            CityNodeHashMap.get(line[1]).nonDFStreelines.add(powerLines[i]);
        }

        // TO be completed by students
        //DFS tree construction
        DFStreeroot=CityNodeHashMap.get(cityNames[0]);
        DFStraverse(DFStreeroot, 0);
        //setting highpoint of the cities
        set_highpoints(DFStreeroot);
        //figuring out the critical lines
        criticallines = new ArrayList<PowerLine>();
        figure_out_bridges(DFStreeroot, 0);
    }
    //student defined helper functions starts here
    private void DFStraverse(CityNode city,int level){
        //setting current level as the level of the city in tree
        city.treelevel = level;
        //setting current city as visited
        city.visited = 1;
        for (int i = city.nonDFStreelines.size()-1;i>-1;i--){
            PowerLine line = city.nonDFStreelines.get(i);
            String neighbour = return_connected_city(line, city.name);
            if(CityNodeHashMap.get(neighbour).visited==0){
                city.DFStreelines.add(city.nonDFStreelines.remove(i));
                CityNodeHashMap.get(neighbour).parentline = line;
                DFStraverse(CityNodeHashMap.get(neighbour), level+1);
            }
            else if (line == city.parentline){  //if this nonDFStreeline is the parentline
                city.nonDFStreelines.remove(i);
            }
        }
    }
    
    private void set_highpoints(CityNode city){
        //sets the highpoint of sub-tree rooted at this city
        int hp; //highpoints
        //computing Highpoint(hp) of city
        if (city.DFStreelines.size()==0){   //for the leaf nodes of DFS tree
            hp = city.treelevel; 
            for (int i=0;i<city.nonDFStreelines.size();i++){    //comparing levels of the non-tree adjacent cities
                PowerLine line = city.nonDFStreelines.get(i);
                String neighbour = return_connected_city(line, city.name);  //city adjacent by non-tree edge
                int neighbour_level = CityNodeHashMap.get(neighbour).treelevel;
                if (neighbour_level<hp) hp = neighbour_level;
            }
        }
        else{
            hp = city.treelevel;
            for (int i=0;i<city.nonDFStreelines.size();i++){    //comparing levels of the non-tree adjacent cities
                PowerLine line = city.nonDFStreelines.get(i);
                String neighbour = return_connected_city(line, city.name);  //city adjacent by non-tree edge
                int neighbour_level = CityNodeHashMap.get(neighbour).treelevel;
                if (neighbour_level<hp) hp = neighbour_level;
            }
            for(int i=0;i<city.DFStreelines.size();i++){    //comparing levels of cities which child of current city in DFS tree
                PowerLine line = city.DFStreelines.get(i);
                CityNode neighbour = CityNodeHashMap.get(return_connected_city(line, city.name));  //city adjacent by non-tree edge
                set_highpoints(neighbour);  //recursive call to setting highpoint of the current child node
                if (neighbour.HighPoint<hp) hp = neighbour.HighPoint;
            }
        }
        //setting the city's highpoint
        city.HighPoint = hp;
    }
    private void figure_out_bridges(CityNode city,int bridges_count){
        //figures out the bridge edges in the tree and modifies the critical lines array
        //it also sets the no of bridges that are there between ancestors of the city

        //setting the no of bridges above this node
        city.noofbridgesabove = bridges_count;

        for (int i = 0;i<city.DFStreelines.size();i++){ //for all child , checking if the edge is a bridge edge
            PowerLine line = city.DFStreelines.get(i);  //adjacent edge
            CityNode neighbour = CityNodeHashMap.get(return_connected_city(line, city.name));      //neighbour city ,child in tree
            if (neighbour.HighPoint>=neighbour.treelevel){  //this line is a critical line
                criticallines.add(line);
                figure_out_bridges(neighbour, bridges_count+1);
            }
            else figure_out_bridges(neighbour, bridges_count);
        }
    }

    private int[] cities_at_same_level(String cityA,String cityB){
        //returns the index of cities which are as same level as cityB and one is ancestor of A and other is ancestor of B
        //Note:this function assumes that preprocessing function is already called or has already begun
        int[] cities = {CityNodeHashMap.get(cityA).index,CityNodeHashMap.get(cityB).index};
        int levelB = CityNodeHashMap.get(cityB).treelevel;
        for (int i=this.logn-1;i>-1;i--){
            int levelA = CityNodeHashMap.get(cityNames[LCAoracle[cities[0]][i]]).treelevel;
            if (  levelA >= levelB  ){
                cities[0] = LCAoracle[cities[0]][i];
                if (levelA==levelB) break;
            }
        }
        return cities;
    }

    private String return_connected_city(PowerLine line,String city){
        //returns the name of city connected to the given city by the givrn line
        if (line.cityA.equals(city)) return line.cityB;
        else return line.cityA;
    }

    //student defined helper functions end here


    public ArrayList<PowerLine> criticalLines() {
        /*
         * Implement an efficient algorithm to compute the critical transmission lines
         * in the power grid.
         
         * Expected running time: O(m + n), where n is the number of cities and m is the
         * number of transmission lines.
         */

        return criticallines;
    }

    public void preprocessImportantLines() {
        /*
         * Implement an efficient algorithm to preprocess the power grid and build
         * required data structures which you will use for the numImportantLines()
         * method. This function is called once before calling the numImportantLines()
         * method. You might want to define new classes and data structures for this
         * method.
         
         * Expected running time: O(n * logn), where n is the number of cities.
         */
        this.logn = 0;int n=numCities;
        while(n>1){n=n/2;this.logn+=1;};
        //initialising the oracle datastructure
        LCAoracle = new int[numCities][this.logn];
        for (int i=0;i<this.logn;i++){
            for (int j = 1;j<numCities;j++){
                if (i==0){
                    CityNode city = CityNodeHashMap.get(cityNames[j]);
                    CityNode parent = CityNodeHashMap.get(return_connected_city(city.parentline, city.name));
                    LCAoracle[j][i] = parent.index;
                }
                else{
                    LCAoracle[j][i] = LCAoracle[LCAoracle[j][i-1]][i-1];
                }
            }
        }
    }
    
    public int numImportantLines(String cityA, String cityB) {
        /*
         * Implement an efficient algorithm to compute the number of important
         * transmission lines between two cities. Calls to numImportantLines will be
         * made only after calling the preprocessImportantLines() method once.
         
         * Expected running time: O(logn), where n is the number of cities.
         */
        //figuring out the LCA of cityA and cityB
    //figuring out the city that is at the same level as the lowest level between citA and cityB
        int[] same_level_cities ;     //city at same level as one of the cities and ancestor of other
        
        int levelA = CityNodeHashMap.get(cityA).treelevel;int levelB = CityNodeHashMap.get(cityB).treelevel;
        if (levelA>=levelB) same_level_cities = cities_at_same_level(cityA, cityB);
        else same_level_cities = cities_at_same_level(cityB, cityA);

    //figuring out LCA
        for (int i =this.logn-1;i>-1;i--){
            if (LCAoracle[same_level_cities[0]][i]!=LCAoracle[same_level_cities[1]][i]){
                same_level_cities[0] = LCAoracle[same_level_cities[0]][i];
                same_level_cities[1] = LCAoracle[same_level_cities[1]][i];
            }
        }
        String LCA = cityNames[LCAoracle[same_level_cities[0]][0]];
        CityNode LCAnode = CityNodeHashMap.get(LCA);
        //figuring out the no of bridge edges between LCA and the cities
        //sum of them is the number of important lines between cities
        CityNode A = CityNodeHashMap.get(cityA);CityNode B = CityNodeHashMap.get(cityB);
        int noofImportantLines = (A.noofbridgesabove-LCAnode.noofbridgesabove)+(B.noofbridgesabove-LCAnode.noofbridgesabove);
        return noofImportantLines;
    }
}