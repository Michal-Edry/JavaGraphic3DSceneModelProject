package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test rendering abasic image
 *
 * @author Dan
 */
public class RenderTests {

    /**
     * Produce a scene with basic 3D model and render it into a jpeg image with a grid
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(100);
        scene.setBackground(new Color(75, 127, 90));
        scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1));

        scene.addGeometries(new Sphere(50, new Point3D(0, 0, 100)));

        scene.addGeometries(
                new Triangle(new Point3D(100, 0, 100), new Point3D(0, 100, 100), new Point3D(100, 100, 100)),
                new Triangle(new Point3D(100, 0, 100), new Point3D(0, -100, 100), new Point3D(100, -100, 100)),
                new Triangle(new Point3D(-100, 0, 100), new Point3D(0, 100, 100), new Point3D(-100, 100, 100)),
                new Triangle(new Point3D(-100, 0, 100), new Point3D(0, -100, 100), new Point3D(-100, -100, 100)));

        ImageWriter imageWriter = new ImageWriter("base render test", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.printGrid(50, java.awt.Color.YELLOW);
        render.writeToImage();
    }

//    /**
//     * tests getClosestPoint function
//     * private function, change the function to public for test
//     */
//    @Test
//    public void getClosestPointTest(){
//        Scene scene = new Scene("Test scene");
//        scene.setCamera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
//        scene.setDistance(100);
//        scene.setBackground(new Color(75, 127, 90));
//        scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1));
//
//        ImageWriter imageWriter = new ImageWriter("base render test", 500, 500, 500, 500);
//        Render render = new Render(imageWriter, scene);
//
//        List<Point3D> intersectionPoints;
//
//        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));
//        Ray ray = new Ray(new Point3D(3,0,0),new Vector(-10,0,0));
//        intersectionPoints = sphere.findIntersections(ray);
//        Point3D result = render.getClosestPoint(intersectionPoints);
//        assertEquals("closest intersection point",new Point3D(0 ,0 ,0),result);
//
//
//        Triangle triangle = new Triangle(new Point3D(1,0,0),new Point3D(0,1,0),new Point3D(0,0,1));
//        ray = new Ray(new Point3D(-1,-3,-2),new Vector(1,3,2));
//        intersectionPoints = triangle.findIntersections(ray);
//        result = render.getClosestPoint(intersectionPoints);
//        assertEquals("Inside triangle",new Point3D(0.16666666666666652,0.5,(double)1/3), result);
//
//    }

}
