package cn.homecredit.bbc.hcmore.service;

import cn.homecredit.bbc.hcmore.entity.JenkinsConfig;
import cn.homecredit.bbc.hcmore.util.ConfigUtils;
import cn.homecredit.bbc.hcmore.util.JenkinsUtils;

public class VMService implements CommonService{
    @Override
    public void execute() {
        System.out.println("VMService::execute");
        preDeploy();
        deploy();
        postDeploy();
    }

    @Override
    public void preDeploy() {
        System.out.println("VMService::pre-deploy");
    }

    @Override
    public void deploy() {
        System.out.println("VMService::deploy");
        JenkinsConfig config = ConfigUtils.readJenkinsConfig("VM");
        JenkinsUtils.triggerJenkinsJob(config);
    }

    @Override
    public void postDeploy() {
        System.out.println("VMService::post-deploy");
    }
}
