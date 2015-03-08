package no.Strohm.game2D.world;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.state.State;
import no.Strohm.game2D.util.FPS;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

/**
 * Created by Ole on 08/03/2015.
 */
public class Launcher extends Canvas implements Runnable {

	public static final String TITLE = "Ole's World Launcher";
	public static int SCALE = 4;
	public static int WIDTH = 1280 / SCALE;
	public static int HEIGHT = (WIDTH / 16) * 10;
	private static boolean running = false;
	private Dimension d = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
	private BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private JFrame frame;
	private InputHandler input;
	private Screen screen;
	private int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

	public static void main(String[] args) {
		Launcher launcher = new Launcher();
		launcher.setPreferredSize(launcher.d);
		launcher.setMinimumSize(launcher.d);
		launcher.setMaximumSize(launcher.d);

		launcher.frame = new JFrame(Game.TITLE);
		launcher.frame.setUndecorated(false);
		launcher.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		launcher.frame.setResizable(false);
		launcher.frame.add(launcher);
		launcher.frame.pack();
		launcher.frame.setLocationRelativeTo(null);
		launcher.frame.setVisible(true);

		try {
			launcher.frame.setIconImage(ImageIO.read(Game.class.getResourceAsStream("/textures/icon2.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		launcher.start();
	}

	public synchronized void start() {
		running = true;
		input = new InputHandler();
		screen = new Screen(WIDTH, HEIGHT);

		State.init(input);

		addKeyListener(input);
		new Thread(this).start();
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double ns = 1000000000.0 / 60.0;
		double unprocessed = 0;
		int ticks = 0;
		int frames = 0;

		requestFocus();
		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / ns;
			lastTime = now;
			while (unprocessed >= 1) {
				tick();
				ticks++;
				unprocessed--;
			}
			{
				render();
				frames++;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				FPS.ticks = ticks;
				FPS.frames = frames;
				ticks = 0;
				frames = 0;
			}
		}

		screen.renderArea(0x00A9FF, 0, screen.w, 0, screen.h, false);
		screen.renderText("Exiting Game!", 50, (HEIGHT - 8) / 2, 0, false);
		screen.copy(pixels);
		getGraphics().drawImage(img, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);

	}

	private void tick() {
		input.tick();
		State.getCurState().tick();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}

		State.getCurState().render(screen);

		screen.copy(pixels);

		Graphics g;
		g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.dispose();
		bs.show();
	}

}
