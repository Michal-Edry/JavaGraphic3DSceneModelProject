package unittests;

import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * class of project1
 */
public class project1 {
    /**
     * first part - project
     */
    @Test
    public void picture() {
        Scene scene = new Scene("Test scene");
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0))
                        .setDOF(1).setAperture(5d).setNumOfRays(300).setFocalDistance(100));
        scene.setDistance(1000);

        Material sphereMaterial = new Material(0.5, 0.5, 60, 0.6, 0);
        Color colorPink = new Color(102, 0, 51);
        Color colorBlue = new Color(java.awt.Color.blue);


        scene.addGeometries(
                new Sphere(colorPink, sphereMaterial, 15, new Point3D(140, -80, 500)),
                new Sphere(colorBlue, sphereMaterial, 20, new Point3D(150, -120, 450)),
                new Sphere(colorPink, sphereMaterial, 10, new Point3D(110, -100, 400)),
                new Sphere(colorPink, sphereMaterial, 18, new Point3D(90, -125, 300)),
                new Sphere(colorBlue, sphereMaterial, 13, new Point3D(60, -85, 200)),
                new Sphere(colorPink, sphereMaterial, 15, new Point3D(95, -30, 100)),
                new Sphere(colorPink, sphereMaterial, 12, new Point3D(35, -100, 100)),
                new Sphere(colorBlue, sphereMaterial, 10, new Point3D(50, -100, 400)),
                new Sphere(colorBlue, sphereMaterial, 10, new Point3D(30, -60, 100)),
                new Sphere(colorPink, sphereMaterial, 17, new Point3D(70, -70, 300)),
                new Sphere(colorBlue, sphereMaterial, 10, new Point3D(80, -40, 200)),
                new Sphere(colorPink, sphereMaterial, 19, new Point3D(90, -5, 150)),
                new Sphere(colorPink, sphereMaterial, 14, new Point3D(0, -60, 100)),
                new Sphere(colorBlue, sphereMaterial, 16, new Point3D(40, -20, 100)),
                new Sphere(colorPink, sphereMaterial, 8, new Point3D(10, -25, 100)),
                new Sphere(colorPink, sphereMaterial, 10, new Point3D(-60, -5, 100)),
                new Sphere(colorBlue, sphereMaterial, 20, new Point3D(20, -10, 200)),
                new Sphere(colorBlue, sphereMaterial, 12, new Point3D(25, -40, 100)),
                new Sphere(colorBlue, sphereMaterial, 15, new Point3D(55, 15, 150)),
                new Sphere(colorPink, sphereMaterial, 10, new Point3D(20, 25, 100)),
                new Sphere(colorPink, sphereMaterial, 12, new Point3D(0, -5, 300)),
                new Sphere(colorBlue, sphereMaterial, 17, new Point3D(-40, 20, 200)),
                new Sphere(colorBlue, sphereMaterial, 14, new Point3D(-90, 17, 100)),
                new Sphere(colorPink, sphereMaterial, 13, new Point3D(-5, 15, 100)),
                new Sphere(colorBlue, sphereMaterial, 13, new Point3D(0, -40, 300)),
                new Sphere(colorBlue, sphereMaterial, 20, new Point3D(-40, -25, 100)),
                new Sphere(colorPink, sphereMaterial, 15, new Point3D(-100, 45, 100)),
                new Sphere(colorPink, sphereMaterial, 10, new Point3D(-70, 45, 200)),
                new Sphere(colorBlue, sphereMaterial, 20, new Point3D(-70, 70, 150)),
                new Sphere(colorPink, sphereMaterial, 13, new Point3D(-40, 50, 200)),
                new Sphere(colorPink, sphereMaterial, 26, new Point3D(-90, 95, 70)));

        scene.addGeometries(new Triangle(new Color(java.awt.Color.blue), new Material(0.8, 0.2, 300),
                        new Point3D(20, 60, 150), new Point3D(60, 60, 150), new Point3D(40, 65, 100)),
                new Triangle(new Color(java.awt.Color.blue), new Material(0.8, 0.2, 300),
                        new Point3D(60, 60, 150), new Point3D(40, 65, 100), new Point3D(40, 100, 100)),
                new Triangle(new Color(java.awt.Color.blue), new Material(0.8, 0.2, 300),
                        new Point3D(20, 60, 150), new Point3D(40, 65, 100), new Point3D(40, 100, 100)),
                new Triangle(new Color(java.awt.Color.blue), new Material(0.8, 0.2, 300),
                        new Point3D(20, 60, 150), new Point3D(60, 60, 150), new Point3D(40, 100, 100)));


        scene.addLights(new DirectionalLight(new Color(252, 212, 64), new Vector(0, 0, 1)),
                new PointLight(new Color(30, 30, 30), new Point3D(-40, -25, -100), 1, 0.00001, 0.00000001),
                new PointLight(new Color(30, 30, 30), new Point3D(0, 0, 0), 1, 0.001, 0.0001));


        ImageWriter imageWriter = new ImageWriter("project1", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene).setMultithreading(4).setDebugPrint();
        render.setAdaptiveSampling(1);

        render.renderImage();
        render.writeToImage();
    }
}
