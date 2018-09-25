package com.example.san4o.checkers;

public class Location {

    private int x;
    private int y;
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




    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        eatsLocation = null;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isBetween(final Location location1, final Location location2) {
        // checks if this location is in between 2 other locations
        final int maxX = Math.max(location1.getX(), location2.getX());
        final int maxY = Math.max(location1.getY(), location2.getY());
        final int minX = Math.min(location1.getX(), location2.getX());
        final int minY = Math.min(location1.getY(), location2.getY());

        return this.x == minX + 1
                && this.x == maxX - 1
                && this.y == minY + 1
                && this.y == maxY - 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj ==null || obj.getClass() != getClass()) return false;

        Location location = (Location) obj;
        if (x != location.x) return false;
        if (y != location.y) return false;
        return true;

    }

    @Override
    public String toString() {
        String str = "";
        str = String.valueOf(x) + "," + String.valueOf(y);
        return str;
    }

    public int hashCode() {
        int result = x;
        result = 31 * result + y;
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
        location.setX(x);
        location.setY(y);
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
        d1 = (p1.getX()-p2.getX())*(p1.getX()-p2.getX());
        d2 = (p1.getY()-p2.getY())*(p1.getY()-p2.getY());
        res = Math.sqrt(d1+d2);
        return res;

    }
}
