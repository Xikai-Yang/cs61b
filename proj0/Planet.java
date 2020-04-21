public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public static final double G = 6.67e-11;
    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        imgFileName = img;
    }
    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.imgFileName = p.imgFileName;
    }
    public double calcDistance(Planet p) {
        double xdelta = (p.xxPos - this.xxPos);
        double ydelta = (p.yyPos - this.yyPos);
        return xdelta * xdelta + ydelta * ydelta;
    }
    public double calcForceExertedBy(Planet p) {
        return ((G * this.mass * p.mass) / (this.calcDistance(p)));
    }
    public double calcForceExertedByX(Planet p) {
        double force = this.calcForceExertedBy(p);
        return (force * (p.xxPos - this.xxPos) / Math.sqrt(this.calcDistance(p)));
    }
    public double calcForceExertedByY(Planet p) {
        double force = this.calcForceExertedBy(p);
        return (force * (p.yyPos - this.yyPos) / Math.sqrt(this.calcDistance(p)));
    }
    public double calcNetForceExertedByX(Planet[] parray) {
        double ans = 0;
        for (int i = 0; i < parray.length; i++) {
            if(!parray[i].equals(this)) {
                ans += this.calcForceExertedByX(parray[i]);
            }
        }
        return ans;
    }

    public double calcNetForceExertedByY(Planet[] parray) {
        double ans = 0;
        for (int i = 0; i < parray.length; i++) {
            if(!parray[i].equals(this)) {
                ans += this.calcForceExertedByY(parray[i]);
            }
        }
        return ans;
    }
    public void update(double dt, double fX, double fY) {
        double accelerationX = fX/this.mass;
        double accelerationY = fY/this.mass;
        this.xxVel += accelerationX * dt;
        this.yyVel += accelerationY * dt;
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }
    public void draw() {
        String newfilename = "./images/"+this.imgFileName;
        StdDraw.picture(this.xxPos,this.yyPos,newfilename);

    }
}
