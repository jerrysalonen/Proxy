package proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Image {
    public void displayImage();
    public void showData();
}

// On System A
class RealImage implements Image {
    private final String filename;

    /**
     * Constructor
     * @param filename
     */
    public RealImage(String filename) {
        this.filename = filename;
        loadImageFromDisk();
    }

    /**
     * Loads the image from the disk
     */
    private void loadImageFromDisk() {
        System.out.println("Loading   " + filename);
    }

    public void showData() {
        System.out.println(filename);
    }

    /**
     * Displays the image
     */
    public void displayImage() {
        System.out.println("Displaying " + filename);
    }
}

// On System B
class ProxyImage implements Image {
    private final String filename;
    private RealImage image;

    /**
     * Constructor
     * @param filename
     */
    public ProxyImage(String filename) {
        this.filename = filename;
    }

    public void showData() {
        System.out.println(filename);
    }

    /**
     * Displays the image
     */
    public void displayImage() {
        if (image == null) {
            image = new RealImage(filename);
        }
        image.displayImage();
    }
}

class Main {
    /**
     * Test method
     */
    public static void main(final String[] arguments) {
        Image image1 = new ProxyImage("HiRes_10MB_Photo1");
        Image image2 = new ProxyImage("HiRes_10MB_Photo2");
        Image image3 = new ProxyImage("HiRes_10MB_Photo3");

        List<Image> images = new ArrayList<>();
        images.add(image1);
        images.add(image2);
        images.add(image3);

        Scanner sc = new Scanner(System.in);
        int select = -1;

        while (select != 0) {
            System.out.println("Select a file to open. Select 0 to quit.");

            for (int i = 0; i < images.size(); i++) {
                System.out.print(i+1 + "\t");
                images.get(i).showData();
            }

            try {
                select = Integer.parseInt(sc.nextLine());
                if (select != 0) {
                    images.get(select - 1).displayImage();
                    System.out.println("");
                }
            } catch (IndexOutOfBoundsException iobe) {
                select = -1;
                System.out.println("Image not found\n");
            } catch (Exception e) {
                select = -1;
                System.out.println("Invalid selection\n");
            }
        }
        System.out.println("Bye");
    }
}