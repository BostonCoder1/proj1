// Grid.java, for pa3
// originally by Scott Madin
package pa2;
import java.util.*;

/**
 * Class to demonstrate greedy algorithms. Skeleton.
 */
public class Grid {
    private boolean[][] grid = null;
    private ArrayList<Set<Spot>> allGroups; // All groups
    private int size;
    private HashSet<Spot> st;
    /**
     * Very simple constructor
     *
     * @param ingrid
     *            a two-dimensional array of boolean to be used as the grid to
     *            search
     */
    public Grid(boolean[][] ingrid) {
        grid = ingrid;

        allGroups = new ArrayList<Set<Spot>>();
        size = grid.length;
        st = new HashSet<>();
        calcAllGroups();
    }

    /**
     * Main method, creates a Grid, then asks it for the size of the group
     * containing the given point.
     */
    public static void main(String[] args) {
        // Check arguments here.
        int i = 0, j=0;

        String string = "";
         if (args.length == 2) {
            i = Integer.parseInt(args[0]);
            j = Integer.parseInt(args[1]);
        }
          else if (args.length > 2) {
            Grid.printUsage();
            return;
        }  else if (args.length == 1) {
            if (!args[0].equals("-all")) {
                Grid.printUsage();
                return;
            }
            string = "-all";
        } else {
            Grid.printUsage();
            return;
        }

        // Usage: java Grid 3 7 to search from (3, 7), top occupied square
        // of L-shaped group.
        // -all to print all sets.

        boolean[][] gridData = {
                { false, false, false, false, false, false, false, false,
                        false, true },
                { false, false, false, true, true, false, false, false, false,
                        true },
                { false, false, false, false, false, false, false, false,
                        false, false },
                { false, false, false, false, true, false, false, true, false,
                        false },
                { false, false, false, true, false, false, false, true, false,
                        false },
                { false, false, false, false, false, false, false, true, true,
                        false },
                { false, false, false, false, true, true, false, false, false,
                        false },
                { false, false, false, false, false, false, false, false,
                        false, false },
                { false, false, false, false, false, false, false, false,
                        false, false },
                { false, false, false, false, false, false, false, false,
                        false, false } };

        // Other stuff here.
        Grid grid = new Grid(gridData);
        if (string.equals("-all")) {
            grid.printAllGroups(); return;
        }
        System.out.println(grid.groupSize(i,j));
        grid.st = new HashSet<>();
    }

    public void printAllGroups()
    {
        for(Set<Spot> g:allGroups) {
            for(Spot s:g)
                System.out.println(s);
            System.out.println("");
        }
    }
    // The best way IMO to calculate the number of groups is to set up a matrix of integers and
    // for each non-0 entry calculate the group it's in.
    public ArrayList<Set<Spot>> calcAllGroups() {
        // Write code here.
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                Spot placeHolder = new Spot(i,j);
                if (grid[i][j] && !st.contains(placeHolder)) {
                    HashSet<Spot> group = new HashSet<Spot>();
                    calcGroup(i, j, group);
                    allGroups.add(group);
                }
            }
        }
        st = new HashSet<>();
        return allGroups;
    }

    private void calcGroup(int i, int j, HashSet<Spot> group) {
        if (!grid[i][j]) return;
        if (group.contains(new Spot(i, j))) return;
        Spot placeHolder = new Spot(i, j);
        group.add(new Spot(i, j));

        st.add(new Spot(i,j));
        if (i+1 < size) calcGroup(i+1, j, group);
        if (i-1 >= 0) calcGroup(i-1, j, group);
        if (j+1 < size) calcGroup(i, j+1, group);
        if (j-1 >= 0) calcGroup(i, j-1, group);
    }

    public ArrayList<Set<Spot>> getAllGroups() {
        return allGroups;
    }
    public int getSize() {
        return size;
    }

    /**
     * Prints out a usage message
     */
    private static void printUsage() {
        System.out.println("Usage: java Grid <i> <j>");
        System.out.println("Find the size of the cluster of spots including position i,j");
        System.out.println("Usage: java Grid -all");
        System.out.println("Print all spots.");
    }

    /**
     * This calls the recursive method to find the group size
     *
     * @param i
     *            the first index into grid (i.e. the row number)
     * @param j
     *            the second index into grid (i.e. the col number)
     * @return the size of the group
     */
    public int groupSize(int i, int j) {
        Spot spot = new Spot(i,j);
        for (Set<Spot> set : allGroups) {
            for (Spot s: set) {
                if (spot.equals(s)) {
                    return set.size();
                }
            }
        }
        return -1;
    }


    /**
     * Nested class to represent a filled spot in the grid
     */
    public static class Spot {
        int i;
        int j;
        int group;
        /**
         * Constructor for a Spot
         *
         * @param i
         *            the i-coordinate of this Spot
         * @param j
         *            the j-coordinate of this Spot
         */
        public Spot(int i, int j) {
            this.i = i;
            this.j = j;
            this.group = 0; // Default. Will be set later.
        }

        /**
         * Tests whether this Spot is equal (i.e. has the same coordinates) to
         * another
         *
         * @param o
         *            an Object
         * @return true if o is a Spot with the same coordinates
         */
        public boolean equals(Object o) {
            if (o == null)
                return false;
            if (o.getClass() != getClass())
                return false;
            Spot other = (Spot) o;
            return (other.i == i) && (other.j == j);

        }

        /**
         * Returns an int based on Spot's contents
         * another way: (new Integer(i)).hashCode()^(new Integer(j)).hashCode();
         */
        public int hashCode() {
            return i << 16 + j; // combine i and j two halves of int
        }

        public void setGroup(int g) {group = g;}

        public int getI() {return i;}
        public int getJ() {return j;}
        public int getGroup() {return group;}
        /**
         * Returns a String representing this Spot, just the coordinates. You can add group if you want.
         */
        public String toString() {
            return "(" + i + " , " + j + ")";
        }
    }
}
