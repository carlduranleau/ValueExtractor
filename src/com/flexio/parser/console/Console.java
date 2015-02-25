package com.flexio.parser.console;

import java.util.Date;

import com.flexio.parser.Extractor;

public class Console implements Runnable {

	private final String CONSOLE_PROMPT = "\n>";
	
	private final String COMMAND_QUIT = "quit";
	private final String COMMAND_VERSION = "version";
	private final String COMMAND_CONFIG = "config";
	private final String COMMAND_HELP = "help";
	
	private boolean consoleEnabled = false;
	private Extractor serverThread = null;
	
	private java.io.Console javaConsole;
	
	public Console (Extractor pServerThread) {
		this.serverThread = pServerThread;
	}
	
	@Override
	public void run() {
        try {
        	javaConsole = System.console();
        	String command;
            consoleEnabled = true;
        	while (consoleEnabled) {
	        	javaConsole.flush();
        		command = javaConsole.readLine(CONSOLE_PROMPT);
				if (!command.equals("")) {
					doCommand(command);
				}
			}
        } catch (Exception e) {
        	print (e.getMessage());
        	doCommand(COMMAND_QUIT);
        }
	}
	
	public void print (String content) {
		System.out.printf("\n%s: " + content + "\n", (new Date()).toString());
	}
	
	private boolean doCommand(String command) {
		if (command == null) return false;
		javaConsole.printf("\n");
		if (command.equalsIgnoreCase(COMMAND_QUIT)) {
			consoleEnabled = false;
			this.serverThread.shutdown();
		} else if (command.equalsIgnoreCase(COMMAND_VERSION)) {
			javaConsole.printf("\n" + serverThread.getVersion() + "\n");
		} else if (command.equalsIgnoreCase(COMMAND_CONFIG)) {
			javaConsole.printf("\n" + serverThread.getConfiguration() + "\n");
		} else if (command.equalsIgnoreCase(COMMAND_HELP)) {
			javaConsole.printf("\n" + this.doHelp() + "\n");
		} else {
			if (!command.equals("")) {
				javaConsole.printf("Unknown command '%s'", command);
			}
		}
		javaConsole.printf("\n");
		return true;
	}
	
	private String doHelp () {
		StringBuilder sb = new StringBuilder();
		sb.append("\nAvailable commands:\n\n");
		sb.append("VERSION:\tShow the server version.\n");
		sb.append("CONFIG:\tDisplay the current configuration.\n");
		sb.append("HELP:\tDisplay this content.\n");
		sb.append("QUIT:\tShutdown the server.\n");
		return sb.toString();
    }
}
