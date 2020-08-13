package com.hc.capp.hcmore.service;

public class F5Service implements CommonService{
    @Override
    public void execute() {
        System.out.println("F5Service::execute");
    }

    @Override
    public void shutdown() {
        System.out.println("F5Service::shutdown");
    }

    @Override
    public void deploy() {
        System.out.println("F5Service::deploy");
    }

    @Override
    public void startup() {
        System.out.println("F5Service::startup");
    }
}
