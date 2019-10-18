package Main;

public class Matrix {

    public static double angle = 180f / 8f;
    static float fNear = 1f;
    static float fFar = 1000.0f;
    static float fFov = 90.0f;
    static float fAspectRation = Main.width / Main.height;
    static float fFovRad = 1.0f / (float) Math.tan(Math.toDegrees(fFov));

    public static Points matrixMul(float[][] arr, Points p){
        float tempX = (arr[0][0] * p.x) + (arr [1][0] * p.y) + (arr[2][0] * p.z + (arr[3][0]));
        float tempY = (arr[0][1] * p.x) + (arr [1][1] * p.y) + (arr[2][1] * p.z + (arr[3][1]));
        float tempZ = (arr[0][2] * p.x) + (arr [1][2] * p.y) + (arr[2][2] * p.z + (arr[3][2]));
        float tempW = p.x * arr[0][3] + p.y * arr[1][3] + p.z * arr[2][3] + arr[3][3];
        if (tempW != 0){
            tempX /= tempW;
            tempY /= tempW;
            tempZ /= tempW;
        }

        return new Points(tempX, tempY,tempZ,tempW);

    }

    public static final float[][] projection = {{fAspectRation * fFovRad, 0, 0, 0},
                                                 {0, fFovRad, 0, 0 },
                                                 {0, 0, fFar / (fFar - fNear), 1.0f },
                                                 {0, 0, (-fFar * fNear) / (fFar - fNear), 0.0f}};


    public static final float[][] rotateX = {{1, 0, 0, 0},
                                              {0, (float)Math.cos(Math.toDegrees(angle)), (float)Math.sin(Math.toDegrees(angle)), 0},
                                              {0, (float)-Math.sin(Math.toDegrees(angle)), (float)Math.cos(Math.toDegrees(angle)), 0},
                                              {0, 0, 0, 1}};


    public static final float[][] rotateY = {{(float) Math.cos(Math.toRadians(angle)), 0, 0, (float)Math.sin(Math.toRadians(angle))},
                                              {0, 1, 0, 0},
                                              {-(float)Math.sin(Math.toRadians(angle)), 0, 0, (float)Math.cos(Math.toRadians(angle))},
                                              {0, 0, 0, 1}};


    public static final float[][] rotateZ = {{ (float)Math.cos(Math.toRadians(angle)), (float)Math.sin(Math.toRadians(angle)), 0, 0},
                                              {(float)-Math.sin(Math.toRadians(angle)), (float)Math.cos(Math.toRadians(angle)), 0, 0},
                                              {0, 0, 1, 0},
                                              {0, 0, 0, 1}};



}
