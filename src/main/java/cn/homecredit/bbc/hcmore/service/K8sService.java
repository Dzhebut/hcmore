package cn.homecredit.bbc.hcmore.service;

import cn.homecredit.bbc.hcmore.entity.JenkinsConfig;
import cn.homecredit.bbc.hcmore.util.ConfigUtils;
import cn.homecredit.bbc.hcmore.util.JenkinsUtils;

public class K8sService implements CommonService{
    @Override
    public void execute() {
        System.out.println("K8sService::execute");
        preDeploy();
        deploy();
        postDeploy();
    }

    @Override
    public void preDeploy() {
        System.out.println("K8sService::pre-deploy");
    }

    @Override
    public void deploy() {
        System.out.println("K8sService::deploy");
//        JenkinsConfig config = ConfigUtils.readJenkinsConfig("K8s");
//        JenkinsUtils.triggerJenkinsJob(config);
    }

    @Override
    public void postDeploy() {
        System.out.println("K8sService::post-deploy");
    }
}
