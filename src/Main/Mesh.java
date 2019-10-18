package Main;

import java.util.ArrayList;

public class Mesh {

    enum ObjectShape {
        CUBE,
        TERRAIN

    }

    static ArrayList<Triangle> basicShape = new ArrayList<>();
    static ArrayList<Triangle> rotatedShape = new ArrayList<>();
    static ArrayList<Triangle> projectedShape = new ArrayList<>();

    static Points [][] grid = new Points[5][5];

    public static void init(ObjectShape objectShape) {
        if (objectShape == ObjectShape.CUBE) {
            Points p1 = new Points(0, 0, 0, 1);
            Points p2 = new Points(0, 1, 0, 1);
            Points p3 = new Points(1, 1, 0, 1);
            Points p4 = new Points(1, 0, 0, 1);
            Points p5 = new Points(0, 0, 1, 1);
            Points p6 = new Points(0, 1, 1, 1);
            Points p7 = new Points(1, 1, 1, 1);
            Points p8 = new Points(1, 0, 1, 1);

            /** FRONT **/
            Triangle t1 = new Triangle(p1, p2, p3);
            Triangle t2 = new Triangle(p1, p3, p4);
            Mesh.basicShape.add(t1);
            Mesh.basicShape.add(t2);
            /** TOP **/
            Triangle t3 = new Triangle(p2, p6, p7);
            Triangle t4 = new Triangle(p7, p3, p2);
            Mesh.basicShape.add(t3);
            Mesh.basicShape.add(t4);
            /** BOTTOM **/
            Triangle t5 = new Triangle(p1, p5, p8);
            Triangle t6 = new Triangle(p8, p4, p1);
            Mesh.basicShape.add(t5);
            Mesh.basicShape.add(t6);
            /** RIGHT **/
            Triangle t7 = new Triangle(p4, p3, p7);
            Triangle t8 = new Triangle(p7, p8, p4);
            Mesh.basicShape.add(t7);
            Mesh.basicShape.add(t8);
            /** LEFT **/
            Triangle t9 = new Triangle(p1, p2, p6);
            Triangle t10 = new Triangle(p6, p5, p1);
            Mesh.basicShape.add(t9);
            Mesh.basicShape.add(t10);
            /** BACK **/
            Triangle t11 = new Triangle(p5, p6, p8);
            Triangle t12 = new Triangle(p8, p6, p7);
            Mesh.basicShape.add(t11);
            Mesh.basicShape.add(t12);
        }
        if (objectShape == ObjectShape.TERRAIN){
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    grid[i][j] = new Points(i, 1, j, 1);

                }
            }
            grid[1][1].y -= .25f;
            grid[2][2].y -= .25f;
            grid[3][3].y -= .25f;
            grid[4][4].y -= .25f;
            for (int i = 0; i < grid.length-1; i++){
                for (int j = 0; j < grid[i].length-1; j++){
                    Triangle t1 = new Triangle(grid[i][j], grid[i+1][j], grid[i+1][j+1]);
                    Triangle t2 = new Triangle(grid[i][j], grid[i+1][j+1], grid[i][j+1]);
                    basicShape.add(t1);
                    basicShape.add(t2);
                }
            }
        }
        //grid[1][2].y = 0.25f;
        //grid[2][3].y = 0;
        //grid[3][4].y = 0.5f;
    }

    public static void rotateIt(float[][] rotateMatrix, ArrayList<Triangle> arr) {
        for (int i = 0; i < arr.size(); i++) {
                Triangle rotated = new Triangle(Matrix.matrixMul(rotateMatrix, arr.get(i).points[0]), Matrix.matrixMul(rotateMatrix, arr.get(i).points[1]), Matrix.matrixMul(rotateMatrix, arr.get(i).points[2]));
                rotatedShape.add(rotated);

        }
    }

    public static void rotateItAgain(float[][] rotateMatrix, ArrayList<Triangle> arr) {
        for (int i = 0; i < arr.size(); i++) {
            Triangle rotated = new Triangle(Matrix.matrixMul(rotateMatrix, arr.get(i).points[0]), Matrix.matrixMul(rotateMatrix, arr.get(i).points[1]), Matrix.matrixMul(rotateMatrix, arr.get(i).points[2]));
            rotatedShape.set(i, rotated);
        }
    }

    public static void addNormalVector(Triangle t){
        float x1 = t.points[1].x - t.points[0].x;
        float y1 = t.points[1].y - t.points[0].y;
        float z1 = t.points[1].z - t.points[0].z;

        float x2 = t.points[2].x - t.points[0].x;
        float y2 = t.points[2].y - t.points[0].y;
        float z2 = t.points[2].z - t.points[0].z;

        float nx = y1 * z2 - z1 * y2;
        float ny = z1 * x2 - x1 * z2;
        float nz = x1 * y2 - y1 * x2;

        t.normal = new Points(nx, ny, nz, 1);
    }

    public static void projectIt(float[][] projectionMatrix, ArrayList<Triangle> arr) {
            for (int i = 0; i < arr.size(); i++) {
                    Triangle projected = new Triangle(Matrix.matrixMul(projectionMatrix, arr.get(i).points[0]), Matrix.matrixMul(projectionMatrix, arr.get(i).points[1]), Matrix.matrixMul(projectionMatrix, arr.get(i).points[2]));
                    projectedShape.add(projected);

            }
    }

    public static void scaleIt(ArrayList<Triangle> arr) {
        for (Triangle t : arr) {
            for (Points p : t.points) {
                p.x += 1f;
                p.y -= 1f;
                p.x *= 600f;
                p.y *= 400f;
            }

        }
    }

    public static void makeMountains(ArrayList<Triangle> arr){

    }

}
