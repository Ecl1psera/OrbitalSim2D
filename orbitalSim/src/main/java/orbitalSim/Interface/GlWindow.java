package orbitalSim.Interface;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import orbitalSim.Bodies.Body;

import java.io.PrintStream;
import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GlWindow {

	// The window handle
	private long window;

	private Body[] bodies;
    public double scale = 1e6;
    public double camX = 0;
    public double camY = 0;
    private double camSpeed = -10*scale;
    private boolean camLock = true;
	private IntBuffer width = BufferUtils.createIntBuffer(1);
	private IntBuffer height = BufferUtils.createIntBuffer(1);


    public boolean pause = true;

	public GlWindow(Body[] bodies) {
		this.bodies = bodies;

		// Setup an error callback. The default implementation
		// will print the error message in System.err.

		PrintStream stream = System.err;
		if(stream == null) throw new NullPointerException("System.err is set to null");
		GLFWErrorCallback.createPrint(stream).set();
		glfwInitHint(GLFW_PLATFORM, GLFW_PLATFORM_X11);

        if(!glfwInit()) throw new RuntimeException("Unable to initialise GLFW");
        

		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);


        window = glfwCreateWindow(300, 300, "Orbital Simulator", NULL, NULL);

		glfwMakeContextCurrent(window);
		GL.createCapabilities();

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 300, 300, 0, -1, 1); // initial size

		GL11.glMatrixMode(GL11.GL_MODELVIEW);


		glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {
				key_callback(win, key, scancode, action, mods);
		});


		glfwSetMouseButtonCallback(window, (win, button, action, mods) -> {
			if (button == GLFW_MOUSE_BUTTON_RIGHT && action == GLFW_PRESS) {
				createBodyMenu();
			}
		});

		glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
			GL11.glViewport(0, 0, w, h);

			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, w, h, 0, -1, 1);

			GL11.glMatrixMode(GL11.GL_MODELVIEW);
		});

		
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
	}

	public Boolean paint() {
		if (!glfwWindowShouldClose(window)) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			glfwGetWindowSize(window, width, height);

			int w = width.get(0);
			int h = height.get(0);

			int centerX = w / 2;
        	int centerY = h / 2;


			if(camLock) {
				Body focus = bodies[0];
				camX = focus.pos[0];
				camY = focus.pos[1];
			}

			for (Body i : bodies) {
				double relX = i.pos[0] - camX;
				double relY = i.pos[1] - camY;

				double x = centerX + (relX / scale);
				double y = centerY + (relY / scale);

				double r = Math.max(3, (i.radius / scale));

				drawCircle(x - r, y - r, r * 2);
			}

			glfwSwapBuffers(window);
			glfwPollEvents();

			return true;
		} else {
			glfwFreeCallbacks(window);
			glfwDestroyWindow(window);

			glfwTerminate();

			return false;
		}
	}

	private void key_callback(long window, int key, int scancode, int action, int mods)
	{
		boolean keyPressed = (action == GLFW_PRESS);
		if(keyPressed) {	
			if (key == GLFW_KEY_DOWN)
				scale *= 1.25;
			if (key == GLFW_KEY_UP)
				scale *= 0.8;
			if (key == GLFW_KEY_A) 
				camX += camSpeed;
			if (key == GLFW_KEY_D) 
				camX -= camSpeed;
			if (key == GLFW_KEY_W) 
				camY += camSpeed;
			if (key == GLFW_KEY_S) 
				camY -= camSpeed;
			if (key == GLFW_KEY_E) 
				camLock = !camLock;
			if (key == GLFW_KEY_SPACE) 
				pause = !pause;
		}
	}

	public void createBodyMenu() {
		DoubleBuffer cursorX = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer cursorY = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(window, cursorX, cursorY);

		

		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex2d(cursorX.get(0), cursorY.get(0));
		GL11.glVertex2d(cursorX.get(0) + 20, cursorY.get(0));
		GL11.glVertex2d(cursorX.get(0) + 20, cursorY.get(0) + 20);
		GL11.glVertex2d(cursorX.get(0), cursorY.get(0)+20);
		GL11.glEnd();
	}

	private void drawCircle(double x, double y, double radius) {
		int segments = 64;
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glVertex2d(x, y);
		for (int i = 0; i <= segments; i++) {
			double angle = 2 * Math.PI * i / segments;
			GL11.glVertex2d(
				x + (float) Math.cos(angle) * radius, 
				y + (float) Math.sin(angle) * radius
			);
		}
		GL11.glEnd();
	}

}
