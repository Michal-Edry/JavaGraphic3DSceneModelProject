package unittests;

import org.junit.Test;
import renderer.ImageWriter;

import java.awt.*;

/**
 * Unit tests for render.ImageWriter class
 */
public class ImageWriterTest {

    /**
     * tests write to image
     * builds the first picture
     */
    @Test
    public void writeToImage() {
        String imagename = "first test";
        int width = 1600;
        int height = 1000;
        int nx =800;
        int ny =500;
        ImageWriter imageWriter = new ImageWriter(imagename, width, height, nx, ny);
        for (int col = 0; col < ny; col++) {
            for (int row = 0; row < nx; row++) {
                if (col % 50 == 0 || row % 50 == 0) {
                    imageWriter.writePixel(row, col, new Color(50,20,220));
                }
                else
                    imageWriter.writePixel(row, col, new Color(255,100,150));
            }
        }
        imageWriter.writeToImage();
    }
}
