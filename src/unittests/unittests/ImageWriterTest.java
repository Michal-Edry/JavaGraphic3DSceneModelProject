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
        int width = 1000;
        int height = 1600;
        int nx =500;
        int ny =800;
        ImageWriter imageWriter = new ImageWriter(imagename, width, height, nx, ny);
        for (int col = 0; col < ny; col++) {
            for (int row = 0; row < nx; row++) {
                if (col % 50 == 0 || row % 50 == 0) {
                    imageWriter.writePixel(row, col, new Color(255,0,0));
                }
                else
                    imageWriter.writePixel(row, col, new Color(150,150,200));
            }
        }
        imageWriter.writeToImage();
    }
}
