package com.flexio.parser.console;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

import com.flexio.parser.Extractor;

public class Console
    implements Runnable {

	private final String LOG_FILE ="extractor.log";
	
    private final String CONSOLE_PROMPT = "\n>";

    private final String COMMAND_QUIT = "quit";

    private final String COMMAND_VERSION = "version";

    private final String COMMAND_CONFIG = "config";

    private final String COMMAND_HELP = "help";

    private boolean consoleEnabled = false;

    private Extractor application = null;

    private java.io.Console javaConsole;

    private Thread thisThread;

    private PrintWriter logFile;
    
    public Console(final Extractor pApplication) {

        this.application = pApplication;
    }

    public void start() {

        this.thisThread = new Thread(this);
        this.thisThread.start();
    }

    @Override
    public void run() {

        try {
            this.javaConsole = System.console();
            String command;
            this.consoleEnabled = true;
            while (this.consoleEnabled) {
                this.javaConsole.flush();
                command = this.javaConsole.readLine(this.CONSOLE_PROMPT);
                if (!command.equals("")) {
                    doCommand(command);
                }
            }
        } catch (final Exception e) {
            shutdown();
        } finally {
        	closeLogFile();
        }
    }

    public void print(final String content, boolean toConsole) {
    	if (toConsole) {
    		this.javaConsole.printf(content);
    	}
    	print(content);
    }
    
    public void print(
        final String content) {
    	
    	String logLine = "\n" + (new Date().toString()) + ": " + content;
    	
        System.out.println(logLine);
        
        writeToLog(logLine);
        
        System.out.printf(this.CONSOLE_PROMPT);
    }
    
    private void writeToLog(String content) {
    	try {
    		openLogFile();
    		String[]lines = content.split("\n");
    		for(String line : lines) {
    			this.logFile.println(line);
    		}
    	} catch (Exception e) {}
    }
    
    private void openLogFile() {
    	if (this.logFile == null) {
    		try {
    			this.logFile = new PrintWriter(new BufferedWriter(new FileWriter(LOG_FILE, true)));
    		} catch (Exception e) {}
    	}
    }

    private void closeLogFile() {
    	if (this.logFile != null) {
    		try {
    			this.logFile.close();
    		} catch (Exception e) {}
    	}
    }
    
    private boolean doCommand(
        final String command) {

        if (command == null) {
            return false;
        }
        print(command + "\n");
        if (command.equalsIgnoreCase(this.COMMAND_QUIT)) {
            this.consoleEnabled = false;
            this.application.shutdown();
        } else if (command.equalsIgnoreCase(this.COMMAND_VERSION)) {
            print("\n" + this.application.getVersion() + "\n", true);
        } else if (command.equalsIgnoreCase(this.COMMAND_CONFIG)) {
            print("\n" + this.application.getConfiguration() + "\n", true);
        } else if (command.equalsIgnoreCase(this.COMMAND_HELP)) {
            print("\n" + doHelp() + "\n", true);
        } else {
            if (!command.equals("")) {
                print("Unknown command '" + command + "'", true);
            }
        }
        return true;
    }

    private String doHelp() {

        final StringBuilder sb = new StringBuilder();
        sb.append("\nAvailable commands:\n\n");
        sb.append("VERSION:\tShow the server version.\n");
        sb.append("CONFIG:\tDisplay the current configuration.\n");
        sb.append("HELP:\tDisplay this content.\n");
        sb.append("QUIT:\tShutdown the server.\n");
        return sb.toString();
    }

    public void shutdown() {

        // print("Shutting down console...");
        this.consoleEnabled = false;
        this.thisThread.interrupt();
    }
}
