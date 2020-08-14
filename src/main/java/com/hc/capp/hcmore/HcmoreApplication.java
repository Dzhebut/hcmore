package com.hc.capp.hcmore;

import com.hc.capp.hcmore.service.CommonService;
import com.hc.capp.hcmore.service.K8sService;
import com.hc.capp.hcmore.service.VMService;
import com.hc.capp.hcmore.util.ConfigUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HcmoreApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(HcmoreApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("starting");
        ConfigUtils.readConfiguration();
        for (String className: ConfigUtils.classNames){
            Class<?>clazz = Class.forName(className);
            Method method = clazz.getMethod("execute");
            method.invoke(clazz.newInstance());
        }
    }
}
