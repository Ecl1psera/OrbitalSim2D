package orbitalSim.Interface;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import orbitalSim.Bodies.Body;

import org.lwjgl.system.*;

import java.nio.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class VulWindow {

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

	public VulWindow(Body[] bodies) {
		this.bodies = bodies;

		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();
		glfwInitHint(GLFW_PLATFORM, GLFW_PLATFORM_X11);

        if(!glfwInit()) throw new RuntimeException("Unable to initialise GLFW");

		if(!GLFWVulkan.glfwVulkanSupported()) {
			throw new RuntimeException("Vulkan is not Supported");
		}
        

        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        window = glfwCreateWindow(300, 300, "Orbital Simulator", NULL, NULL);

		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
	}

	public Boolean paint() {
		if (!glfwWindowShouldClose(window)) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			glfwGetWindowSize(window, width, height);

			int centerX = width.get(0)/ 2;
        	int centerY = height.get(0) / 2;

			System.out.println(centerX + " | " + centerY);

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
			glfwSetErrorCallback(null).free();

			return false;
		}
	}

	private void drawCircle(double x, double y, double radius) {
		int segments = 64;
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glVertex2d(x, y);
		for (int i = 0; i <= segments; i++) {
			double angle = 2 * Math.PI * i / segments;
			GL11.glVertex2d((float) Math.cos(angle) * radius, 
							(float) Math.sin(angle) * radius);
		}
		GL11.glEnd();
	}

}
