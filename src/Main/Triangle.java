package Main;

public class Triangle{

    Points[] points;
    Points normal;

    public Triangle(Points p1, Points p2, Points p3){
        this.points = new Points[3];
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;

    }

    public Triangle(Points p1, Points p2, Points p3, Points normal){
        this.points = new Points[3];
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
        this.normal = normal;

    }
}