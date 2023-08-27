package com.app;

import com.sun.jna.Native;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.LoadLibs;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws Exception {

        File tmpFolder = LoadLibs.extractTessResources("win32-x86-64"); // Change this argument, depending on your platform
        System.setProperty("java.library.path", tmpFolder.getPath());
        System.out.println("native libraries extracted to: " + tmpFolder.getPath());

        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("eng");
        tesseract.setOcrEngineMode(1);

        tesseract.setTessVariable("user_defined_dpi", "96");

        Path dataDirectory = Paths.get("D:\\Tess4J\\tessdata\\data");
        tesseract.setDatapath(dataDirectory.toString());

        //File file = new File("D:\\Tess4J\\tessdata\\partySS.jpg");
        //BufferedImage image = ImageIO.read(file);
        //BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        //ImageIO.write(image, "png", new File("D:\\Tess4J\\tessdata\\partySS.png"));


        int hWnd = Windows.User32.instance.FindWindowA(null, "Knight OnLine Client");
        Windows.WindowInfo w = getWindowInfo(hWnd);
        Windows.User32.instance.SetForegroundWindow(w.hwnd);
        BufferedImage image = new Robot().createScreenCapture(new Rectangle(w.rect.left, w.rect.top, w.rect.right - w.rect.left, w.rect.bottom - w.rect.top));
        ImageIO.write(image, "png", new File("D:\\Tess4J\\tessdata\\partySSS.png"));

        String result = tesseract.doOCR(image);

        System.out.println(result);
    }

    public static Windows.WindowInfo getWindowInfo(int hWnd) {
        Windows.RECT r = new Windows.RECT();
        Windows.User32.instance.GetWindowRect(hWnd, r);
        byte[] buffer = new byte[1024];
        Windows.User32.instance.GetWindowTextA(hWnd, buffer, buffer.length);
        String title = Native.toString(buffer);
        Windows.WindowInfo info = new Windows.WindowInfo(hWnd, r, title);
        return info;
    }
}
