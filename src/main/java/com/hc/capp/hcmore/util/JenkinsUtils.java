package com.hc.capp.hcmore.util;

import com.alibaba.fastjson.JSON;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

import static com.hc.capp.hcmore.util.ConfigUtils.jenkinsJobName;

public class JenkinsUtils {

    private static final Logger logger = LoggerFactory.getLogger(JenkinsUtils.class);

    /**
     * 查看job指定版本号的build是否完成
     *
     * @param number 构建版本号
     * @param jobName job名称
     * @param jenkinsServer
     * @return
     */
    public static boolean isFinished(int number, String jobName, JenkinsServer jenkinsServer) {
        boolean isBuilding = false;
        if (number <= 0) {
            throw new IllegalArgumentException("jenkins build number must greater than 0!");
        }
        try {
            JobWithDetails job = jenkinsServer.getJob(jobName);
            // build 如果为空则证明正在构建，走else
            Build buildByNumber = job.getBuildByNumber(number);
            if (null != buildByNumber) {
                BuildWithDetails details = buildByNumber.details();
                if (null != details) {
                    isBuilding = details.isBuilding();
                } else {
                    isBuilding = true;
                }
            } else {
                isBuilding = true;
            }
            return !isBuilding;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("isFinished::Exception");
        }
        return false;
    }


   static class HtmlContent {
        URL url;
        public HtmlContent(URL url) throws UnsupportedEncodingException, IOException {
            // TODO Auto-generated constructor stub
            this.url = url;
            get_content();
        }
        private void get_content() throws IOException{
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
            String s = null;
            while((s = br.readLine())!=null){
                System.out.println(s);
            }
        }
    }

    /**
     * 触发 Jenkins Job 构建
     *
     * @return result of latest build
     */
    public static void triggerJenkinsJob() {
        try {
            JenkinsServer jenkinsServer =
                    new JenkinsServer(
                            new URI(ConfigUtils.deployURL),
                            ConfigUtils.jenkinsUsername,
                            ConfigUtils.jenkinsToken);
            JobWithDetails jobWithDetails = jenkinsServer.getJob(jenkinsJobName);
            JenkinsHttpClient jenkinsHttpClient = new JenkinsHttpClient(new URI(ConfigUtils.deployURL),ConfigUtils.jenkinsUsername,ConfigUtils.jenkinsToken);


            int nextNum = jobWithDetails.getNextBuildNumber();
            Map<String, String> param = JSON.parseObject(ConfigUtils.jenkinsJobParam, Map.class);
            if (param.isEmpty()) {
                jobWithDetails.build();
            } else {
                jobWithDetails.build(param);
            }
            System.out.println("building this job...");
            Thread.sleep(10000);
            String logPath = String.format("/job/%s/%d/logText/progressiveText",ConfigUtils.jenkinsJobName,nextNum);
           // String logPath = String.format("C:\\Program Files (x86)\\Jenkins\\jobs\\%s\\builds\\%d\\log", ConfigUtils.jenkinsJobName, nextNum);
            int beginIndex = 0;
            System.out.println("nextNum is: " + nextNum);
            while (!isFinished(nextNum, jenkinsJobName, jenkinsServer)){
                Thread.sleep(3000);
                String s = jenkinsHttpClient.get(logPath);
                System.out.print(s.substring(beginIndex));
                beginIndex = s.length();
                /*
                Scanner scanner = new Scanner(new File(logPath));
                while (scanner.hasNextLine()){
                    System.out.println(scanner.nextLine());
                }

                 */
            }
        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            logger.info("BuildWithDetails::Exception");
        }
    }
}
