package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.swing.JButton;

public class FancyRoundButton extends JButton {
	private Color color = Color.blue;
	private int width = 50;
	private int height = 50;
	String text = "Button";
	int stringLocationX, stringLocationY;

	private Font getFontFile() {
		File font_file = new File("font.ttf");
		try {
			if (font_file.exists()) {
				Font font = Font.createFont(Font.PLAIN, font_file).deriveFont(30f);
				return font;
			} else {
				String urlLink = "https://raw.githubusercontent.com/google/fonts/master/apache/rancho/Rancho-Regular.ttf";
				URL website;
				try {
					website = new URL(urlLink);
					ReadableByteChannel rbc = Channels.newChannel(website.openStream());
					FileOutputStream fos = new FileOutputStream("font.ttf");
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
					Font font = Font.createFont(Font.PLAIN, font_file).deriveFont(30f);
					System.out.println("Downloaded the font from "+urlLink);
					return font;

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return null;

	}

	public void SetParameters(int width, int height, Color color, String text, int stringLocationX,
			int stringLocationY) {
		this.color = color;
		this.height = height;
		this.width = width;
		this.text = text;
		this.stringLocationX = stringLocationX;
		this.stringLocationY = stringLocationY;
	}

	@Override
	protected void paintComponent(Graphics g1) {


		Graphics2D g = (Graphics2D) g1;

		g.setColor(color);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, this.width, this.height);
		g.fill(circle);

		g.setColor(Color.white);
		Font font = getFontFile();
		g.setFont(font);
		g.drawString(this.text, stringLocationX, stringLocationY);

	}

	@Override
	protected void paintBorder(Graphics g) {

	}

	@Override
	public Dimension getPreferredSize() {
		Dimension size = super.getPreferredSize();
		size.width = this.width;
		size.height = this.height;
		return size;
	}

}
