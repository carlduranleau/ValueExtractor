package com.flexio.parser;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NameParser {
	private String clientId;
	private Date creationDate;
	private String id;
	
	public static NameParser parse (String pName) {
		NameParser parser = new NameParser();
		
		//38-SANJKTTEST08-20150202 011645
		
		String[] parts = pName.split("-");
		
		parser.setId(parts[0]);
		parser.setClientId(parts[1]);
		
		int year = Integer.parseInt(parts[2].substring(0, 4));
		int month = Integer.parseInt(parts[2].substring(4, 6));
		int day = Integer.parseInt(parts[2].substring(6, 8));
		int hours = Integer.parseInt(parts[2].substring(9, 11));
		int minutes = Integer.parseInt(parts[2].substring(11, 13));
		int seconds = Integer.parseInt(parts[2].substring(13, 15));
		Calendar c = new GregorianCalendar(year,month-1,day,hours,minutes,seconds);
		parser.setCreationDate(c.getTime());
		
		return parser;
	}
	
	public String getClientId() {
		return clientId;
	}
	private void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	private void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getId() {
		return id;
	}
	private void setId(String id) {
		this.id = id;
	}
	
	
	
}
