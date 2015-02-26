package com.flexio.parser;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class RulesSet {

	private final static String RULES_FILE_EXTENSION = ".tpl";
    private String rulesFile = null;
    private List<Rule> rules = new ArrayList<Rule>();
    private String dataFile;
    
    public static RulesSet create (final String pRulesFolder, final String pDataFile) throws Exception {
    	
    	NameParser name = NameParser.parse(pDataFile);
    	
    	RulesSet rulesSet = new RulesSet(pRulesFolder + name.getClientId() + RULES_FILE_EXTENSION);
    	rulesSet.setDataFile(pDataFile);
    	return rulesSet;
    }
    
    public RulesSet(final String pRulesFile) throws Exception {

        this.rulesFile = pRulesFile;
        load();
    }

    private void load() throws Exception {
        final Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(this.rulesFile);

        prop.load(fis);

        Enumeration<?> e = prop.propertyNames();
		while (e.hasMoreElements()) {
			String field = (String) e.nextElement();
			String pattern = prop.getProperty(field);
			rules.add(new Rule(field,pattern));
		}
    }

    public void setDataFile(String pFilename) {
    	this.dataFile = pFilename;
    }
    
    public String getDataFile() {
    	return this.dataFile;
    }
    
    public List<Rule> getRules() {
    	return rules;
    }
}
