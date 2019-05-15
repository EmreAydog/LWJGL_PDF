import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    // The title of the window
    private final String title;

    // The width of the window in pixels
    private int width;

    // The height of the window in pixels
    private int height;

    // TODO
    private long windowHandle;

    // Has the window been resized
    private boolean resized;

    // Should update be tied to refresh rate
    private boolean vSync;

    public Window(String title, int width, int height, boolean vSync) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        this.resized = false;
    }

    public void init() {
        // Construct an error callback to the default System.err and set it
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW itself, this is needed before using additional GLFW functions
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        /**
         * The following window hints are used to give GLFW some
         * parameters as to how it should operate. Think GLFW version or whether
         * it should run in compatibility mode
         */
        // Hide the window on creation
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        // Allow for resizing of the window
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        // The version of OpenGl this program requires, in this case OpenGL 3.2
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);

        // OpenGL profile hint and attribute
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        // The program should be forwards compatible by setting this flag
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        /** End of setting the window hints */

        // Create the window
        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);

        // And check if window creation was successfull
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create a window");
        }

        // Set up a callback whenever the window gets resized
        glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {

            // Get the new width and height of the window
            this.width = width;
            this.height = height;

            // Indicate that a resize operation was performed
            this.setResized(true);
        });

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        // Center the window on the monitor
        glfwSetWindowPos(windowHandle,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowHandle);

        // Check whether we need to enale vSync
        if (vSync) {
            glfwSwapInterval(1);
        }

        // Make the window visible AFTER having initialized all this
        glfwShowWindow(windowHandle);

        GL.createCapabilities();

        // Set the window to have a clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    // Reset the color
    public void setClearColor (float r, float g, float b, float alpha) {
        glClearColor(r, g, b, alpha);
    }

    // Checks if there is ANY key pressed at all and simply returns true or false
    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }

    // Should the current window be closed?
    public boolean windowShouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }

    // Update the window state
    public void update() {
        glfwSwapBuffers(windowHandle);
        glfwPollEvents();
    }

    /**
     * Some small helper methods
     **/
    // Return the window title
    public String getTitle() {
        return title;
    }

    // Return the window width
    public int getWidth() {
        return width;
    }

    // Return the window height
    public int getHeight() {
        return height;
    }

    // Return whether the window was resized
    public boolean isResized() {
        return resized;
    }

    // Set the resized boolean
    public void setResized(boolean resized) {
        this.resized = resized;
    }

    // Return whether vSync is enabled
    public boolean isvSync() {
        return vSync;
    }

    // Set the vSync boolean
    public void setvSync(boolean vSync) {
        this.vSync = vSync;
    }
}
