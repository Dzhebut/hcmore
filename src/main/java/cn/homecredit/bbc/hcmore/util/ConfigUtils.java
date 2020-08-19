package cn.homecredit.bbc.hcmore.util;

import cn.homecredit.bbc.hcmore.entity.JenkinsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);

    public static List<String> classNames = new ArrayList<>();

    public static void readClasses(){
        Properties properties = new Properties();
        try {
            InputStream in = ConfigUtils.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        for (String propertyName : properties.stringPropertyNames()) {
            if (propertyName.matches(".*ClassName")) {
                classNames.add(properties.getProperty(propertyName));
            }
        }
    }
    public static JenkinsConfig readJenkinsConfig(String service) {
        Properties properties = new Properties();
        try {
            InputStream in = ConfigUtils.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }

        JenkinsConfig config = new JenkinsConfig();
        config.setUrl(properties.getProperty(service + ".jenkins.url"));
        config.setUsername(properties.getProperty(service + ".jenkins.user.username"));
        config.setPassword(properties.getProperty(service + ".jenkins.user.password"));
        config.setUserToken(properties.getProperty(service + ".jenkins.user.token"));

        config.setJobName(properties.getProperty(service + ".jenkins.job.name"));
        config.setJobToken(properties.getProperty(service + ".jenkins.job.token"));
        config.setJobParam(properties.getProperty(service + ".jenkins.job.param"));
        return config;
    }
}
