package unittests;

import elements.*;
import geometries.Plane;
import geometries.Sphere;
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
    public void picture()
    {
        Scene scene = new Scene("Test scene");
        scene.setBackground(new Color(java.awt.Color.WHITE));
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE),0.15));
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setFocal_plane(new Plane(new Point3D(0,0,100),new Vector(0,0,1)));

        scene.addGeometries(
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),15,new Point3D(140,-80,500)),
                new Sphere(new Color(java.awt.Color.blue),new Material(0.5,0.5,60,0.6,0),20,new Point3D(150,-120,450)),
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),10,new Point3D(110,-100,400)),
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),18,new Point3D(90,-125,300)),
                new Sphere(new Color(java.awt.Color.blue),new Material(0.5,0.5,60,0.6,0),13,new Point3D(60,-85,200)),
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),15,new Point3D(95,-30,100)),
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),12,new Point3D(35,-100,100)),
                new Sphere(new Color(java.awt.Color.blue),new Material(0.5,0.5,60,0.6,0),10,new Point3D(50,-100,400)),
                new Sphere(new Color(java.awt.Color.BLUE),new Material(0.5,0.5,60,0.6,0),10,new Point3D(30,-60,100)),
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),17,new Point3D(70,-70,300)),
                new Sphere(new Color(java.awt.Color.blue),new Material(0.5,0.5,60,0.6,0),10,new Point3D(80,-40,200)),
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),19,new Point3D(90,-5,150)),
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),14,new Point3D(0,-60,100)),
                new Sphere(new Color(java.awt.Color.blue),new Material(0.5,0.5,60,0.6,0),16,new Point3D(40,-20,100)),
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),8,new Point3D(10,-25,100)),
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),10,new Point3D(-60,-5,100)),
                new Sphere(new Color(java.awt.Color.blue),new Material(0.5,0.5,60,0.6,0),20,new Point3D(20,-10,200)),
                new Sphere(new Color(java.awt.Color.blue),new Material(0.5,0.5,60,0.6,0),12,new Point3D(25,-40,100)),
                new Sphere(new Color(java.awt.Color.blue),new Material(0.5,0.5,60,0.6,0),15,new Point3D(55,15,150)),
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),10,new Point3D(20,25,100)),
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),12,new Point3D(0,-5,300)),
                new Sphere(new Color(java.awt.Color.blue),new Material(0.5,0.5,60,0.6,0),17,new Point3D(-40,20,200)),
                new Sphere(new Color(java.awt.Color.blue),new Material(0.5,0.5,60,0.6,0),14,new Point3D(-90,17,100)),
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),13,new Point3D(-5,15,100)),
                new Sphere(new Color(java.awt.Color.blue),new Material(0.5,0.5,60,0.6,0),13,new Point3D(0,-40,300)),
                new Sphere(new Color(java.awt.Color.blue),new Material(0.5,0.5,60,0.6,0),20,new Point3D(-40,-25,100)),
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),15,new Point3D(-100,45,100)),
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),10,new Point3D(-70,45,200)),
                new Sphere(new Color(java.awt.Color.blue),new Material(0.5,0.5,60,0.6,0),20,new Point3D(-70,70,150)),
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),13,new Point3D(-40,50,200)),
                new Sphere(new Color(102,0,51),new Material(0.5,0.5,60,0.6,0),26,new Point3D(-90,95,70))
        );

        scene.addLights(new DirectionalLight(new Color(252,212,64),new Vector(0,0,1)));

        ImageWriter imageWriter = new ImageWriter("project1", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);
        render.set_depthOffield(1);

        render.renderImage();
        render.writeToImage();
    }
}

