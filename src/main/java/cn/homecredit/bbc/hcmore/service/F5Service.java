package cn.homecredit.bbc.hcmore.service;

public class F5Service implements CommonService{
    @Override
    public void execute() {
        System.out.println("F5Service::execute");
    }

    @Override
    public void preDeploy() {
        System.out.println("F5Service::pre-deploy");
    }

    @Override
    public void deploy() {
        System.out.println("F5Service::deploy");
    }

    @Override
    public void postDeploy() {
        System.out.println("F5Service::post-deploy");
    }
}
