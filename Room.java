import java.awt.Color;
import java.util.*;
public class Room {
    public static final int north = 0;
    public static final int east = 1;
    public static final int south = 2;
    public static final int west = 3;
    public static final int up = 4;
    public static final int down = 5;
    private boolean[] doors = new boolean[6];
    private Color wallColor;
    private boolean[] outside = new boolean[6];
    private static HashMap<Color, Color> usedColors;
    public Room () {
        this(null, null);
    }
    public Room (boolean[] doors, boolean[] outside) {
        if (doors != null)
            this.doors = doors;
        if (outside != null)
            this.outside = outside;
        if (usedColors == null) {
            usedColors = new HashMap<Color, Color>();
        }
        Random rand = new Random();
        int r=0;
        int g=0;
        int b=0;
        while (r <= 128 || g <= 128 || b <= 128 || colorUsed(new Color(r, g, b))) {
            r = rand.nextInt(255);
            g = rand.nextInt(255);
            b = rand.nextInt(255);
        }
        wallColor = new Color(r, g, b);
        usedColors.put(wallColor, wallColor);
    }
    public void setDoor(int direction, boolean isOpen) {
        doors[direction] = isOpen;
    }
    public boolean getDoor(int orientation) {
        return doors[orientation];
    }
    public Color getColor() {
        return wallColor;
    }
    public boolean colorUsed(Color color) {
        return usedColors.containsKey(color);
    }
    public static void resetColors() {
        usedColors.clear();
    }
    public boolean leadsOutside(int orientation) {
        return doors[orientation] && outside[orientation];
    }
}