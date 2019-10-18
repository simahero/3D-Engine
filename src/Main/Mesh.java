package Main;

import java.util.ArrayList;
import java.util.Random;

public class Mesh {

    enum ObjectShape {
        CUBE,
        TERRAIN
    }

    static int rows = 15;
    static int cols = 15;
    static Points [][] grid = new Points[rows][cols];

    static ArrayList<Triangle> basicShape = new ArrayList<>();
    static ArrayList<Triangle> rotatedShape = new ArrayList<>();
    static ArrayList<Triangle> projectedShape = new ArrayList<>();

    public static void makeGrid(){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Points(i, 0.5f, j, 1);
            }
        }
    }

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
            for (int i = 0; i < grid.length-1; i++){
                for (int j = 0; j < grid[i].length-1; j++){
                    Triangle t1 = new Triangle(grid[i][j], grid[i+1][j], grid[i+1][j+1]);
                    Triangle t2 = new Triangle(grid[i][j], grid[i+1][j+1], grid[i][j+1]);
                    basicShape.add(t1);
                    basicShape.add(t2);
                }
            }
        }
    }

    public static void rotateItX(ArrayList<Triangle> arr, float angle) {
        float[][] rotateX = {{1, 0, 0, 0},
                {0, (float)Math.cos(Math.toDegrees(angle)), (float)-Math.sin(Math.toDegrees(angle)), 0},
                {0, (float)Math.sin(Math.toDegrees(angle)), (float)Math.cos(Math.toDegrees(angle)), 0},
                {0, 0, 0, 1}};
        for (int i = 0; i < arr.size(); i++) {
                Triangle rotated = new Triangle(Matrix.matrixMul(rotateX, arr.get(i).points[0]), Matrix.matrixMul(rotateX, arr.get(i).points[1]), Matrix.matrixMul(rotateX, arr.get(i).points[2]));
                rotatedShape.add(rotated);
        }
    }

    public static void rotateItY(ArrayList<Triangle> arr, float angle) {
        float[][] rotateY = {{(float) Math.cos(Math.toRadians(angle)), 0, (float)Math.sin(Math.toRadians(angle)), 0},
                {0, 1, 0, 0},
                {-(float)Math.sin(Math.toRadians(angle)), 0, (float)Math.cos(Math.toRadians(angle)), 0},
                {0, 0, 0, 1}};
        for (int i = 0; i < arr.size(); i++) {
            Triangle rotated = new Triangle(Matrix.matrixMul(rotateY, arr.get(i).points[0]), Matrix.matrixMul(rotateY, arr.get(i).points[1]), Matrix.matrixMul(rotateY, arr.get(i).points[2]));
            rotatedShape.add(rotated);
        }
    }

    public static void rotateItZ(ArrayList<Triangle> arr, float angle) {
        float[][] rotateZ = {{ (float)Math.cos(Math.toRadians(angle)), (float)-Math.sin(Math.toRadians(angle)), 0, 0},
                {(float)Math.sin(Math.toRadians(angle)), (float)Math.cos(Math.toRadians(angle)), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}};
        for (int i = 0; i < arr.size(); i++) {
            Triangle rotated = new Triangle(Matrix.matrixMul(rotateZ, arr.get(i).points[0]), Matrix.matrixMul(rotateZ, arr.get(i).points[1]), Matrix.matrixMul(rotateZ, arr.get(i).points[2]));
            rotatedShape.add(rotated);
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

    public static void addMountains(){
        Random r = new Random();
        for (int k = 0; k < grid[0].length; k++){
            grid[rows - 1][k].y = 1;
            grid[rows - 1][k].y += (r.nextFloat());
            grid[rows - 1][k].y -= (r.nextFloat());
        }

        for (int i = grid.length-2; i > -1; i--){
            for (int j = grid[i].length-2; j > -1; j--){
                grid[i][j].y = grid[i+1][j+1].y;
            }
        }

    }

}
