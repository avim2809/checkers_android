package com.example.san4o.checkers;

import android.util.Log;

public class Location {

    private int col;
    private int row;
    private Location eatsLocation=null;

    public boolean isEatenLocation() {
        return eatenLocation;
    }

    public void setEatenLocation(boolean eatenLocation) {
        this.eatenLocation = eatenLocation;
    }

    private boolean eatenLocation;

    public Location getEatsLocation() {
        return eatsLocation;
    }

    public void setEatsLocation(Location eatsLocation) {
        this.eatsLocation = eatsLocation;
    }

    // the location that its stone is being eaten by moving to this location




    public Location(int col, int row) {
        this.col = col;
        this.row = row;
        eatsLocation = null;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean isBetween(final Location location1, final Location location2) {
        // checks if this location is in between 2 other locations
        final int maxX = Math.max(location1.getCol(), location2.getCol());
        final int maxY = Math.max(location1.getRow(), location2.getRow());
        final int minX = Math.min(location1.getCol(), location2.getCol());
        final int minY = Math.min(location1.getRow(), location2.getRow());

        return this.col == minX + 1
                && this.col == maxX - 1
                && this.row == minY + 1
                && this.row == maxY - 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj ==null || obj.getClass() != getClass()) return false;

        Location location = (Location) obj;
        if (col != location.col) return false;
        if (row != location.row) return false;
        return true;

    }

    @Override
    public String toString() {
        String str = "";
        str = String.valueOf(col) + "," + String.valueOf(row);
        return str;
    }

    public int hashCode() {
        int result = col;
        result = 31 * result + row;
        return result;
    }

    public static Location getLocationfromString(String location_str)
    {
        Location location = new Location(0,0);
        if (location_str.contains(":"))
        {
            location_str = location_str.split(":")[1];
        }
        //parse string coordinates
        String[] cords = location_str.split(",");
        if ((cords.length != 2)) throw new AssertionError();
        int x = Integer.parseInt(cords[0]);
        int y = Integer.parseInt(cords[1]);
        location.setCol(x);
        location.setRow(y);
        Log.i("loc = ", location.toString());
        return location;
    }

    public static double getDistance(Location p1,Location p2)
    {
        /**
         * d=√((x1-x2)^2+(y1-y2)^2)

         */

        double res;
        if (p1.equals(p2))
            res = 0;
        double d1,d2;
        d1 = (p1.getCol()-p2.getCol())*(p1.getCol()-p2.getCol());
        d2 = (p1.getRow()-p2.getRow())*(p1.getRow()-p2.getRow());
        res = Math.sqrt(d1+d2);
        return res;
    }
}
