package cn.homecredit.bbc.hcmore;

import cn.homecredit.bbc.hcmore.entity.JenkinsConfig;
import cn.homecredit.bbc.hcmore.util.ConfigUtils;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class HcmoreApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(HcmoreApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("starting");
        ConfigUtils.readClasses();
        for (String className: ConfigUtils.classNames){
            Class<?>clazz = Class.forName(className);
            Method method = clazz.getMethod("execute");
            method.invoke(clazz.newInstance());
        }
    }
}
