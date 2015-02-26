package com.flexio.parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import com.flexio.parser.console.Console;

public class Extractor
    implements Runnable, DataSourceListener {

    final static private String VERSION = "Release 0.1\nCopyright Flexio inc. 2015";

    final static private String CONFIGURATION_FILE = "extractor.ini";

    private Configuration configuration = null;

    private boolean serverEnabled = false;

    private Console serverConsole;

    private DataSourceReader serverDataSourceReader;

    public Extractor(final Configuration pConfiguration) {

        this.configuration = pConfiguration;
    }

    public static void main(
        final String[] args)
        throws Exception {

        final Extractor application = new Extractor(ConfigurationLoader.load(Extractor.CONFIGURATION_FILE));

        final Thread serverThread = new Thread(application);
        serverThread.start();

    }

    @Override
    public void run() {

        try {
            this.serverConsole = new Console(this);
            this.serverEnabled = true;
            this.serverConsole.print("Server started with configuration: " + getConfiguration());

            // Start console thread
            this.serverConsole.start();

            // Start data source reader thread
            this.serverDataSourceReader = new DataSourceReader(this, this, this.configuration.getDataSourceDirectory());
            this.serverDataSourceReader.start();

            this.serverConsole.print("Server started");

            while (this.serverEnabled) {
                Thread.sleep(1000);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            shutdown();
        }
    }

    public Console getConsole() {

        return this.serverConsole;
    }

    public String getConfiguration() {

        return this.configuration.toString();
    }

    public void shutdown() {

        this.serverConsole.print("Shutting down server...");
        this.serverEnabled = false;
        this.serverConsole.shutdown();
        this.serverDataSourceReader.shutdown();
    }

    public String getVersion() {

        return Extractor.VERSION;
    }

    @Override
    public void onNewFile(
        final String filePath) {

        if (filePath == null) {
            return;
        }
        this.serverConsole.print("Parsing: " + filePath);
        try {
        	Map<String,String> data = RuleEngine.process(RulesSet.create(this.configuration.getRulesSourceDirectory(), filePath));
        	
        	for (String key : data.keySet()) {
        		this.serverConsole.print(key+"="+data.get(key));
        	}
        	
        	//TODO: This should be in the RuleEngine
        	Path filename = Paths.get(filePath).getFileName();
    		Files.move(Paths.get(filePath), Paths.get(this.configuration.getProcessedDirectory() + filename.toString()), StandardCopyOption.REPLACE_EXISTING);

    		//TODO: Add database connection
        	
        } catch (Exception e) {
        	e.printStackTrace();
        	this.serverConsole.print(e.getMessage());
        	this.serverConsole.print("Unable to process: " + filePath);
        }
    }
}
