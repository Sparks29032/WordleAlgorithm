import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class Reader implements MouseListener {
	Robot rb;
	Boolean clicked;
	
	public static void main(String[] args) throws AWTException {
		Reader rd = new Reader();
		double[] data = rd.find();
		char[] res = rd.getRow(data);
		for (int i = 0; i < res.length; i++) {
			System.out.println(res[i]);
		}
	}
	
	public Reader() throws AWTException {
		rb = new Robot();
		clicked = false;
	}
	
	public double[] find() {
		Point p;
		double[] data;
		int[] i = new int[2];
		int[] f = new int[2];
		int[] b = new int[2];
		int[] x = new int[2];
		
		while (!clicked) {}
		p = MouseInfo.getPointerInfo().getLocation();
		System.out.println("hi");
		i[0] = p.x;
		i[1] = p.y;
		
		while (clicked) {}
		while (!clicked) {}
		p = MouseInfo.getPointerInfo().getLocation();
		f[0] = p.x;
		f[1] = p.y;
		
		while (clicked) {}
		while (!clicked) {}
		p = MouseInfo.getPointerInfo().getLocation();
		b[0] = p.x;
		b[1] = p.y;
		
		while (clicked) {}
		while (!clicked) {}
		p = MouseInfo.getPointerInfo().getLocation();
		x[0] = p.x;
		x[1] = p.y;
		
		data = new double[]{i[0], i[1], (f[0] - i[0]), (f[1] - i[0]), (b[0] - f[0]), (b[1] - f[1]), ((double) Math.min(x[0] - i[0], x[1] - i[1]) / Math.max(f[0] - i[0], f[1] - i[1]))};
		return data;
	}
	
	public char[] getRow(double[] data) {
		double ix = data[0], iy = data[1];
		double dx = data[2] + data[4];
		double dy = data[3];
		double p = data[6];
		double cx = ix, cy = iy;
		
		char[] results = new char[5];
		
		BufferedImage pic = rb.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		results[0] = getColor(pic.getRGB((int) (cx + p * dx), (int) (cy + p * dy)));
		
		for (int i = 1; i < 5; i++) {
			cx += dx;
			results[i] = getColor(pic.getRGB((int) (cx + p * dx), (int) (cy + p * dy)));
		}
		
		return results;
	}
	
	public char getColor(int color) {
		int[] gArr = {108, 169, 102};
		int[] yArr = {203, 179, 93};
		int[] bArr = {120, 124, 127};
		
		int g = convert(gArr);
		int y = convert(yArr);
		int b = convert(bArr);
		
		if (color == g) {
			return 'G';
		}
		
		if (color == y) {
			return 'Y';
		}
		
		if (color == b) {
			return 'B';
		}
		
		return '\0';
	}
	
	public int convert(int[] rgb) {
		return 65536 * rgb[0] + 256 * rgb[1] + rgb[2] - 256 * 256 * 256;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		clicked = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		clicked = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
