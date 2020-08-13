package com.hc.capp.hcmore.service;

import com.hc.capp.hcmore.util.JenkinsUtils;

import java.io.*;
import java.util.stream.Stream;

public class K8sService implements CommonService{
    @Override
    public void execute() {
        System.out.println("K8sService::execute");
        deploy();
    }

    @Override
    public void shutdown() {
        System.out.println("K8sService::shutdown");
    }

    @Override
    public void deploy() {
        System.out.println("K8sService::deploy");
        JenkinsUtils.triggerJenkinsJob();
    }

    @Override
    public void startup() {
        System.out.println("K8sService::startup");
    }
}
