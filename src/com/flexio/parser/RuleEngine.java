package com.flexio.parser;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleEngine {

	public static Map<String,String> process (RulesSet pRulesSet) throws Exception {
		
		byte[] fileData = Files.readAllBytes(Paths.get(pRulesSet.getDataFile()));
		String fileContent = new String(fileData, "ISO-8859-1");
		
		Map<String,String> data = new HashMap<String,String>();
		data.put("receiptid", pRulesSet.getMetadata().getReceiptId());
		data.put("clientid", pRulesSet.getMetadata().getClientId());
		data.put("receiptdate", pRulesSet.getMetadata().getCreationDate().toString());
		for (Rule rule : pRulesSet.getRules()) {
			Pattern rulePattern = Pattern.compile(rule.getPattern(), Pattern.MULTILINE);
			Matcher m = rulePattern.matcher(fileContent);
			if (m.find()) {
				data.put(rule.getFieldName(), m.group());
			} else {
				data.put(rule.getFieldName(), "???");
			}
		}

		return data;
	}
	
}
