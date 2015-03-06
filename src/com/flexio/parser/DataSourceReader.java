package com.flexio.parser;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class DataSourceReader
    implements Runnable {

	private static final int READER_PAUSE = 30000;
	
    private final String dataSourcePath;

    private final DataSourceListener listener;

    private boolean readerEnabled = false;

    private Thread thisThread;

    private WatchService watcher;

    private final Extractor application;

    public DataSourceReader(
        final Extractor pApplication,
        final DataSourceListener pListener,
        final String pDataSourcePath) throws Exception {

        this.application = pApplication;
        this.listener = pListener;
        this.dataSourcePath = pDataSourcePath;

        init();
    }

    private void init()
        throws Exception {

        final Path dataFolder = Paths.get(this.dataSourcePath);
        this.watcher = FileSystems.getDefault().newWatchService();
        dataFolder.register(this.watcher, StandardWatchEventKinds.ENTRY_CREATE);
    }

    public void start() {

        this.thisThread = new Thread(this);
        this.thisThread.start();
    }

    @Override
    public void run() {

        this.readerEnabled = true;

        try {
            while (this.readerEnabled) {
                final File f = new File(this.dataSourcePath);
                final File[] fileList = f.listFiles();
                
                if (fileList.length > 0) {
	                this.application.getConsole().print(String.format("%d unprocessed files found.", fileList.length));
	                for (final File file : fileList) {
	                	if (this.readerEnabled) {
	                		this.listener.onNewFile(file.getAbsolutePath());
	                	} else {
	                		shutdown();
	                	}
	                }
                }
                Thread.sleep(READER_PAUSE);
            }
        } catch (final Exception e) {
            shutdown();
        }
    }

    // @Override
    public void run2() {

        WatchKey watchKey;
        this.readerEnabled = true;

        while (this.readerEnabled) {

            try {
                watchKey = this.watcher.take();
            } catch (final InterruptedException e) {
                shutdown();
                e.printStackTrace();
                return;
            }
            for (final WatchEvent<?> event : watchKey.pollEvents()) {
                final WatchEvent.Kind<?> kind = event.kind();

                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }

                final WatchEvent<Path> ev = (WatchEvent<Path>) event;
                this.listener.onNewFile(ev.context().toString());
            }

            if (!watchKey.reset()) {
                break;
            }

        }
    }

    public void shutdown() {

        // this.application.getConsole().print("Shutting datasource listener...");
        this.readerEnabled = false;
        this.thisThread.interrupt();
    }

}
