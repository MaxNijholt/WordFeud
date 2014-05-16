package Core;

public class Main {

	@SuppressWarnings("unused")
	private Application application;

	public static void main(String[] args) {
		Main program = new Main();
		program.initialize();
	}

	private void initialize() {
		this.application = new Application();
				
	}

}
