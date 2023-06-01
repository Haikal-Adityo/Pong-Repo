import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class testing extends Rectangle {
    public static void main(String[] args) {
        Rectangle rect1 = new Rectangle(0, 0, 100, 100);
        Rectangle rect2 = new Rectangle(50, 50, 100, 100);

        if (rect1.intersects(rect2)) {
            System.out.println("Rectangles intersect");
        } else {
            System.out.println("Rectangles do not intersect");
        }

    }

}
