package cn.homecredit.bbc.hcmore.util;

import cn.homecredit.bbc.hcmore.entity.JenkinsConfig;
import com.alibaba.fastjson.JSON;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class JenkinsUtils {

    private static final Logger logger = LoggerFactory.getLogger(JenkinsUtils.class);


    public static boolean isBuilding(int number, JenkinsConfig jc, JenkinsHttpClient jenkinsHttpClient){
        try {
            String buildResult = jenkinsHttpClient.get(String.format("/job/%s/%d/api/json?token=%s", jc.getJobName(), number, jc.getJobToken()));
            Map<String, Object> param = JSON.parseObject(buildResult, Map.class);
            if ("false".equals(param.get("building"))){
                return false;
            }else {
                return true;
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return false;
    }

    /**
     * 触发 Jenkins Job 构建
     */
    public static void triggerJenkinsJob(JenkinsConfig jc) {
        try {
            JenkinsHttpClient jenkinsHttpClient = new JenkinsHttpClient(new URI(jc.getUrl()),jc.getUsername(),jc.getUserToken());

            String lastBuildInfo = jenkinsHttpClient.get(String.format("/job/%s/lastBuild/api/json?token=%s", jc.getJobName(), jc.getJobToken()));
            Map<String, Object> buildInfo = JSON.parseObject(lastBuildInfo, Map.class);
            int nextNum = Integer.parseInt((String) buildInfo.get("id")) + 1;
            Map<String, String> jenkinsParam = JSON.parseObject(jc.getJobParam(), Map.class);
            if (jenkinsParam.isEmpty()){
                jenkinsHttpClient.get(String.format("/job/%s/build?token=%s", jc.getJobName(), jc.getJobToken()));
            }else {
                StringBuilder sb = new StringBuilder();
                for (String key:jenkinsParam.keySet()){
                    sb.append("&");
                    sb.append(key);
                    sb.append("=");
                    sb.append(jenkinsParam.get(key));
                }
                String buildUrl = String.format("job/%s/buildWithParameters?token=%s%s", jc.getJobName(), jc.getJobToken(), sb);
                jenkinsHttpClient.get(buildUrl);
            }
            System.out.println("building this job...");

            Thread.sleep(10000);
            String logPath = String.format("/job/%s/%d/logText/progressiveText",jc.getJobName(),nextNum);
            int beginIndex = 0;
            while (isBuilding(nextNum, jc, jenkinsHttpClient)){
                Thread.sleep(3000);
                try {
                    String consoleOutput = jenkinsHttpClient.get(logPath);
                    System.out.print(consoleOutput.substring(beginIndex));
                    beginIndex = consoleOutput.length();
                }catch (IOException  e) {
                    e.printStackTrace();
                    Thread.sleep(10000);
                    continue;
                }
            }
            String remainLog = jenkinsHttpClient.get(logPath);
            System.out.print(remainLog.substring(beginIndex));
        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            logger.info("triggerJenkinsJob::Exception");
        }
    }
}
