package com.hc.capp.hcmore.service;

public class VMService implements CommonService{
    @Override
    public void execute() {
        System.out.println("VMService::execute");
    }

    @Override
    public void shutdown() {
        System.out.println("VMService::shutdown");
    }

    @Override
    public void deploy() {
        System.out.println("VMService::deploy");
    }

    @Override
    public void startup() {
        System.out.println("VMService::startup");
    }
}
