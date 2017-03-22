/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

/**
 *
 * @author Jason
 */
public class DungeonTile implements Comparable
{
    private String tileType;
    private final int x;
    private final int y;
    private float cost = 1;
    private DungeonTile parent;
    private float heuristic;
    private int depth;
    
    public DungeonTile(String tileType, int x, int y)
    {
        this.tileType = tileType;
        this.x = x;
        this.y = y;
    }

    public String getTileType() 
    {
        return tileType;
    }

    public void setTileType(String tileType)
    {
        this.tileType = tileType;
    }
    
    public int setParent(DungeonTile parent)
    {
        setDepth(parent.getDepth() + 1);
        this.parent = parent;
        
        return getDepth();
    }

    @Override
    public int compareTo(Object other) 
    {
        DungeonTile o = (DungeonTile) other;
        
        float f = getHeuristic() + cost;
        float of = o.getHeuristic() + o.cost;

        if (f < of) {
                return -1;
        } else if (f > of) {
                return 1;
        } else {
                return 0;
        }
    }

    /**
     * @return the parent
     */
    public DungeonTile getParent() {
        return parent;
    }

    /**
     * @return the heuristic
     */
    public float getHeuristic() {
        return heuristic;
    }

    /**
     * @param heuristic the heuristic to set
     */
    public void setHeuristic(float heuristic) {
        this.heuristic = heuristic;
    }

    /**
     * @return the depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }
}
