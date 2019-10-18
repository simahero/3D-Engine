package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main implements Runnable {

    JFrame frame;
    Canvas canvas;

    static int width = 1200;
    static int height = 800;
    int scale = 4;
    int offsetX = 10;
    int offsetY = 600;
    boolean clear = false;

    public Main() {
        frame = new JFrame("TERRAIN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas = new Canvas());
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new Thread(this).start();
    }

    public void update() {
        Mesh.rotateIt(Matrix.rotateY, Mesh.basicShape);
        //Mesh.rotateItAgain(Matrix.rotateY, Mesh.basicShape);
        for (Triangle t : Mesh.rotatedShape) {
            float x1 = t.points[1].x - t.points[0].x;
            float y1 = t.points[1].y - t.points[0].y;
            float z1 = t.points[1].z - t.points[0].z;

            float x2 = t.points[2].x - t.points[0].x;
            float y2 = t.points[2].y - t.points[0].y;
            float z2 = t.points[2].z - t.points[0].z;

            float nx = y1 * z2 - z1 * y2;
            float ny = z1 * x2 - x1 * z2;
            float nz = x1 * y2 - y1 * x2;
            System.out.println(nz);
            float l = (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
            nx /= l;
            ny /= l;
            nz /= l;
            System.out.println(nz);
            t.normal = new Points(nx, ny, nz, 1);
            if (t.normal.z < 0) {
                Mesh.projectIt(Matrix.projection, Mesh.rotatedShape);
                Mesh.scaleIt(Mesh.projectedShape);
                BufferStrategy bs = canvas.getBufferStrategy();
                if (bs == null) {
                    canvas.createBufferStrategy(2);
                    return;
                }
                Graphics g = bs.getDrawGraphics();
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, width, height);
                g.setColor(Color.CYAN);
                g.translate(offsetX, offsetY);

                for (Triangle ti : Mesh.projectedShape) {
                    g.drawLine((int) ti.points[0].x, (int) ti.points[0].y, (int) ti.points[1].x, (int) ti.points[1].y);
                    g.drawLine((int) ti.points[1].x, (int) ti.points[1].y, (int) ti.points[2].x, (int) ti.points[2].y);
                    g.drawLine((int) ti.points[2].x, (int) ti.points[2].y, (int) ti.points[0].x, (int) ti.points[0].y);
                }

                g.dispose();
                bs.show();
            }

        }

    }

    public void render() {
        /*
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.CYAN);
        g.translate(offsetX, offsetY);

        for (Triangle t : Mesh.projectedShape) {
            g.drawLine((int) t.points[0].x, (int) t.points[0].y, (int) t.points[1].x, (int) t.points[1].y);
            g.drawLine((int) t.points[1].x, (int) t.points[1].y, (int) t.points[2].x, (int) t.points[2].y);
            g.drawLine((int) t.points[2].x, (int) t.points[2].y, (int) t.points[0].x, (int) t.points[0].y);
        }

        g.dispose();
        bs.show();

         */
    }



    @Override
    public void run() {
        BasicTimer timer = new BasicTimer(6000);

        Mesh.init(Mesh.ObjectShape.TERRAIN);
        /*
        Mesh.rotateIt(Matrix.rotateY, Mesh.basicShape);
        Mesh.projectIt(Matrix.projection, Mesh.rotatedShape);
        Mesh.scaleIt(Mesh.projectedShape);

         */


        while (true) {
            timer.sync();
            update();
            render();
        }

    }

    public static void main(String[] args) {
        new Main();
    }
}
