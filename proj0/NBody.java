public class NBody {
    public static double readRadius(String filename) {
        In in = new In(filename);
        int number = in.readInt();
        double radius = in.readDouble();
        return radius;
    }
    public static Planet[] readPlanets(String filename) {
        In in = new In(filename);
        int totalnumber = in.readInt();
        double radius = in.readDouble();
        Planet[] parray = new Planet[totalnumber];
        for (int i = 0; i < totalnumber; ++i) {
            parray[i] = new Planet(in.readDouble(),in.readDouble(),
                    in.readDouble(),in.readDouble(),in.readDouble(),in.readString());
        }
        return parray;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] parray = NBody.readPlanets(filename);
        double radius = NBody.readRadius(filename);
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(radius*(-1),radius);
        for (int i = 0; i < parray.length; i++) {
            parray[i].draw();
        }
        StdDraw.show();
        double time = 0;
        double[] xforces = new double[parray.length];
        double[] yforces = new double[parray.length];
        while(true) {
            for (int i = 0; i < parray.length; i++) {
                xforces[i] = parray[i].calcNetForceExertedByX(parray);
                yforces[i] = parray[i].calcNetForceExertedByY(parray);
            }
            for (int i = 0; i < parray.length; i++) {
                parray[i].update(dt,xforces[i],yforces[i]);
            }
            StdDraw.clear();
            StdDraw.picture(0,0,"./images/starfield.jpg");

            for (int i = 0; i < parray.length; i++) {
                parray[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
            if(time >= T) {
                break;
            }
        }

        StdOut.printf("%d\n",parray.length);
        StdOut.printf("%.2e\n",radius);
        for (int i = 0;i < parray.length;++i) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",parray[i].xxPos,parray[i].yyPos,
                    parray[i].xxVel,parray[i].yyVel,parray[i].mass,parray[i].imgFileName);
        }

    }

}
