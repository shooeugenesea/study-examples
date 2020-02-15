package examples;

public class OS {

	private static final String osname = System.getProperty("os.name").toLowerCase();

	public static boolean isLinux() {
		return osname.contains("linux");
	}

}
