import java.util.ArrayList;
public class PointQuadtree {

    enum Quad {
        NW,
        NE,
        SW,
        SE
    }

    public PointQuadtreeNode root;

    public PointQuadtree() {
        this.root = null;
    }

    public boolean insert(CellTower a) {
        // TO be completed by students
        //this function assumes that object 'a' is complete
        if (this.root==null) root = new PointQuadtreeNode(a);
        else{
            PointQuadtreeNode cur = this.root;
            while (true){
                if (cur.celltower.y<=a.y && cur.celltower.x>a.x){   //going to NW
                    if (cur.quadrants[0]==null){    //leaf node, so add as it's child
                        cur.quadrants[0] = new PointQuadtreeNode(a);break;
                    }
                    else{cur = cur.quadrants[0];continue;}
                }
                
                if (cur.celltower.y<a.y && cur.celltower.x<=a.x){   //going to NE
                    if (cur.quadrants[1]==null){    //leaf node, so add as it's child
                        cur.quadrants[1] = new PointQuadtreeNode(a);break;
                    }
                    else{cur = cur.quadrants[1];continue;}
                }
                
                if (cur.celltower.y>=a.y && cur.celltower.x<a.x){   //going to SE
                    if (cur.quadrants[3]==null){    //leaf node, so add as it's child
                        cur.quadrants[3] = new PointQuadtreeNode(a);break;
                    }
                    else{cur = cur.quadrants[3];continue;}
                }
                
                if (cur.celltower.y>a.y && cur.celltower.x>=a.x){   //going to SW
                    if (cur.quadrants[2]==null){    //leaf node, so add as it's child
                        cur.quadrants[2] = new PointQuadtreeNode(a);break;
                    }
                    else{cur = cur.quadrants[2];continue;}
                }
                // if there's a cell tower at the specific location
                return false;
            }
        }
        return true;
    }

    public boolean cellTowerAt(int x, int y) {
        // TO be completed by students
        if (this.root!=null){
            PointQuadtreeNode cur = this.root;
            while (true){
                if (cur.celltower.y<=y && cur.celltower.x>x){   //going to NW
                    if (cur.quadrants[0]==null){    //leaf node, so not found
                        break;
                    }
                    else{cur = cur.quadrants[0];continue;}
                }
                
                if (cur.celltower.y<y && cur.celltower.x<=x){   //going to NE
                    if (cur.quadrants[1]==null){    //leaf node, so not found
                        break;
                    }
                    else{cur = cur.quadrants[1];continue;}
                }
                
                if (cur.celltower.y>=y && cur.celltower.x<x){   //going to SE
                    if (cur.quadrants[3]==null){    //leaf node, so not found
                        break;
                    }
                    else{cur = cur.quadrants[3];continue;}
                }
                
                if (cur.celltower.y>y && cur.celltower.x>=x){   //going to SW
                    if (cur.quadrants[2]==null){    //leaf node, so not found
                        break;
                    }
                    else{cur = cur.quadrants[2];continue;}
                }
                // if there's a cell tower at the specific location
                return true;
            }
        }
        return false;
    }

    
    public CellTower chooseCellTower(int x,int y,int r) {
        // TO be completed by students
        CellTower minTower = null;
        if (this.root!=null){
            ArrayList<PointQuadtreeNode> nodes_queue = new ArrayList<PointQuadtreeNode>();
            nodes_queue.add(this.root);
            while (!nodes_queue.isEmpty()){
                PointQuadtreeNode cur = nodes_queue.remove(0);if (cur==null) continue;
                if (cur.celltower.distance(x,y)<=r){    //if current tower is in required range of (x,y)
                    if(minTower==null) minTower = cur.celltower;
                    else{
                        if (minTower.cost>cur.celltower.cost) minTower = cur.celltower;
                    }
                }
                int[] intersecting_quadrants = intersecting_quadrants(x, y, r, cur);
                for (int i=0;i<4;i++){      //adding all intersecting quadrants to the queue
                    if (intersecting_quadrants[i]==1){
                        nodes_queue.add(cur.quadrants[i]);
                    }
                }
            }
        }
        return minTower;
    }

    //helper function
    private int[] intersecting_quadrants(int x,int y,int r,PointQuadtreeNode curr_cell){
        int[] intersecting_quadrants = {0,0,0,0};
        if (curr_cell.celltower.distance(x, y)<r){ //if splitting point is in range, then all quadrants will intersect
            intersecting_quadrants[0]=1;intersecting_quadrants[1]=1;intersecting_quadrants[2]=1;intersecting_quadrants[3]=1;
        }
        else{
            if (x<curr_cell.celltower.x && y>=curr_cell.celltower.y){   //(x,y) is NW of curr cell splitting point
                intersecting_quadrants[0]=1;    //quadrant having the circle center is definitely intersecting with the circle
                if(y-curr_cell.celltower.y<r) intersecting_quadrants[2]=1;  //if circle enters SW quadrant
                if (curr_cell.celltower.x-x<=r) intersecting_quadrants[1]=1;    //if circle enters NE quadrant
            }
            else if(x>=curr_cell.celltower.x && y>curr_cell.celltower.y){   //(x,y) is NE of curr cell splitting point
                intersecting_quadrants[1]=1;    //quadrant having the circle center is definitely intersecting with the circle
                if(y-curr_cell.celltower.y<=r) intersecting_quadrants[3]=1;  //if circle enters SE quadrant
                if (x-curr_cell.celltower.x<r) intersecting_quadrants[0]=1;    //if circle enters NW quadrant
            }
            else if(x<=curr_cell.celltower.x && y<curr_cell.celltower.y){   //(x,y) is SW of curr cell splitting point
                intersecting_quadrants[2]=1;    //quadrant having the circle center is definitely intersecting with the circle
                if(curr_cell.celltower.y-y<=r) intersecting_quadrants[0]=1;  //if circle enters NW quadrant
                if (curr_cell.celltower.x-x<r) intersecting_quadrants[3]=1;    //if circle enters SE quadrant
            }
            else if(x>curr_cell.celltower.x && y<=curr_cell.celltower.y){   //(x,y) is SE of curr cell splitting point
                intersecting_quadrants[3]=1;    //quadrant having the circle center is definitely intersecting with the circle
                if(curr_cell.celltower.y-y<r) intersecting_quadrants[1]=1;  //if circle enters NE quadrant
                if (x-curr_cell.celltower.x<=r) intersecting_quadrants[2]=1;    //if circle enters SW quadrant
            }
            //else here includes the situation when r=0 and x,y coincides with curr.celltower
        }
        return intersecting_quadrants;
    }
}
