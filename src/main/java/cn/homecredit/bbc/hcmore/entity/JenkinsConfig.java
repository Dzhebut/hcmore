package cn.homecredit.bbc.hcmore.entity;

import java.io.Serializable;

public class JenkinsConfig implements Serializable {

    private String url;
    private String username;
    private String password;
    private String userToken;
    private String jobName;
    private String jobToken;
    private String jobParam;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobToken() {
        return jobToken;
    }

    public void setJobToken(String jobToken) {
        this.jobToken = jobToken;
    }

    public String getJobParam() {
        return jobParam;
    }

    public void setJobParam(String jobParam) {
        this.jobParam = jobParam;
    }

    public JenkinsConfig(String url, String username, String password, String userToken, String jobName, String jobToken, String jobParam) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.userToken = userToken;
        this.jobName = jobName;
        this.jobToken = jobToken;
        this.jobParam = jobParam;
    }

    public JenkinsConfig(){

    }

    @Override
    public String toString() {
        return "JenkinsConfig{" +
                "url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userToken='" + userToken + '\'' +
                ", jobName='" + jobName + '\'' +
                ", jobToken='" + jobToken + '\'' +
                ", jobParam='" + jobParam + '\'' +
                '}';
    }
}
