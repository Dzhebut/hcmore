package com.hc.capp.hcmore.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);

    public static List<String> classNames = new ArrayList<>();
    public static String deployURL;
    public static String jenkinsUsername;
    public static String jenkinsPassword;
    public static String jenkinsJobName;
    public static String jenkinsJobParam;
    public static String jenkinsToken;

    public static void readConfiguration() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        for (String propertyName:properties.stringPropertyNames()){
            if (propertyName.matches(".*ClassName")){
                classNames.add(properties.getProperty(propertyName));
            }
        }

        deployURL = properties.getProperty("K8s.jenkins.url");
        jenkinsUsername = properties.getProperty("K8s.jenkins.username");
        jenkinsPassword = properties.getProperty("K8s.jenkins.password");
        jenkinsJobName = properties.getProperty("K8s.jenkins.job");
        jenkinsJobParam = properties.getProperty("K8s.jenkins.param");
        jenkinsToken = properties.getProperty("K8s.jenkins.token");
    }
}
