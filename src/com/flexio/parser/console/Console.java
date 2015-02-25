package com.flexio.parser.console;

import java.util.Date;

import com.flexio.parser.Extractor;

public class Console
    implements Runnable {

    private final String CONSOLE_PROMPT = "\n>";

    private final String COMMAND_QUIT = "quit";

    private final String COMMAND_VERSION = "version";

    private final String COMMAND_CONFIG = "config";

    private final String COMMAND_HELP = "help";

    private boolean consoleEnabled = false;

    private Extractor application = null;

    private java.io.Console javaConsole;

    private Thread thisThread;

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
        }
    }

    public void print(
        final String content) {

        System.out.printf("\n%s: " + content, new Date().toString());
        System.out.printf(this.CONSOLE_PROMPT);
    }

    private boolean doCommand(
        final String command) {

        if (command == null) {
            return false;
        }
        if (command.equalsIgnoreCase(this.COMMAND_QUIT)) {
            this.consoleEnabled = false;
            this.application.shutdown();
        } else if (command.equalsIgnoreCase(this.COMMAND_VERSION)) {
            this.javaConsole.printf("\n" + this.application.getVersion() + "\n");
        } else if (command.equalsIgnoreCase(this.COMMAND_CONFIG)) {
            this.javaConsole.printf("\n" + this.application.getConfiguration() + "\n");
        } else if (command.equalsIgnoreCase(this.COMMAND_HELP)) {
            this.javaConsole.printf("\n" + doHelp() + "\n");
        } else {
            if (!command.equals("")) {
                this.javaConsole.printf("Unknown command '%s'", command);
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
