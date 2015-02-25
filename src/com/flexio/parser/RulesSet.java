package com.flexio.parser;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class RulesSet {

    private String rulesFile = null;
    private List<Rule> rules = new ArrayList<Rule>();
    
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

    private List<Rule> getRules() {
    	return rules;
    }
}
