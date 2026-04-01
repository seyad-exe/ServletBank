package com.banking.api.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	private static final Properties properties = new Properties();
	static {
		try (InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("application.properties")){
			if(input==null) {
				System.out.println("unable to find application.properties");
			}
			else {
				properties.load(input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
