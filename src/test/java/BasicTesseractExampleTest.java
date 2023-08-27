
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


public class BasicTesseractExampleTest {
    
    @Test
    public void givenTessBaseApi_whenImageOcrd_thenTextDisplayed() throws Exception {
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("deu");
        tesseract.setOcrEngineMode(1);

        Path dataDirectory = Paths.get(ClassLoader.getSystemResource("data").toURI());
        tesseract.setDatapath(dataDirectory.toString());

        BufferedImage image = ImageIO.read(BasicTesseractExampleTest.class.getResourceAsStream("/ocrexample.jpg"));
        String result = tesseract.doOCR(image);
        System.out.println(result);
    }
}
