package com.flexio.parser;

import java.io.FileInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import com.flexio.parser.console.Console;

public class Extractor
    implements Runnable {

	final static private String VERSION = "0.1"; 
    final static private String CONFIGURATION_FILE = "extractor.ini";

    private Configuration configuration = null;
    private boolean serverEnabled = false;
    
    private Console serverConsole;
    private Thread serverConsoleThread;
    
    public Extractor(final Configuration pConfiguration) {

        this.configuration = pConfiguration;
    }

    public static void main(
        final String[] args)
        throws Exception {

        final Extractor application = new Extractor(ConfigurationLoader.load(Extractor.CONFIGURATION_FILE));
        
        // Use only one thread for now. Will use a threadpool in the future.
        Thread serverThread = new Thread(application);
        serverThread.start();

    }

    @Override
    public void run() {

        try {
            final Console console = new Console(this);
            serverConsole = console;
            serverEnabled = true;
            serverConsole.print("Server started with configuration: " + getConfiguration());
            serverConsole.print("Server started");

            serverConsoleThread = new Thread(console);
            serverConsoleThread.start();

            FileInputStream dataFileInputStream = new FileInputStream(configuration.getDataSourceDirectory());
            Path dataFolder = Paths.get(configuration.getDataSourceDirectory());
            WatchService watcher = FileSystems.getDefault().newWatchService();
            dataFolder.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
            WatchKey watckKey = watcher.take();
            List<WatchEvent<?>> events = watckKey.pollEvents();
            
            while (serverEnabled) {
            	Thread.sleep(1000);
            	
            }
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			serverConsole.print("Server stopped");
			serverConsoleThread.interrupt();
        }
    }
    
    public String getConfiguration () {
    	return this.configuration.toString();
    }

    public void shutdown () {
    	serverConsole.print("Shutting down server...");
    	serverEnabled = false;
    }
    
    public String getVersion() {
    	return VERSION;
    }
}
