package cn.homecredit.bbc.hcmore.workflow;

import cn.homecredit.bbc.hcmore.entity.JenkinsConfig;
import cn.homecredit.bbc.hcmore.util.ConfigUtils;
import cn.homecredit.bbc.hcmore.util.JenkinsUtils;
import com.uber.cadence.activity.ActivityMethod;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.workflow.Workflow;
import com.uber.cadence.workflow.WorkflowMethod;

import java.util.concurrent.CompletableFuture;

public class VMServiceWorkflow {

    public interface Task{
        @WorkflowMethod(executionStartToCloseTimeoutSeconds = 200, taskList = "vm_service")
        void execute();
    }

    public static class TaskImpl implements Task{

        private final VMActivities activities =
                Workflow.newActivityStub(VMActivities.class);
        @Override
        public void execute() {
            System.out.println("workflow begin...");
            activities.executeActivity();
        }
    }

    public interface VMActivities {
        @ActivityMethod(scheduleToCloseTimeoutSeconds = 150, taskList = "vm_service")
        void executeActivity();
    }

    static class VMActivitiesImpl implements VMActivities {
        @Override
        public void executeActivity() {
            JenkinsConfig config = ConfigUtils.readJenkinsConfig("VM");
            JenkinsUtils.triggerJenkinsJob(config);
        }
    }

    public static void main(String[] args) {
        Worker.Factory factory = new Worker.Factory("test-domain");
        Worker worker = factory.newWorker("vm_service");
        worker.registerWorkflowImplementationTypes(TaskImpl.class);
        worker.registerActivitiesImplementations(new VMActivitiesImpl());
        factory.start();
        System.out.println("worker for " + worker.getTaskList() + " is ready.");
    }
    //执行具体实例，供VMService::deploy调用
    public static void executeService(){
        WorkflowClient client = WorkflowClient.newInstance("test-domain");
        Task task = client.newWorkflowStub(Task.class);
        task.execute();
        CompletableFuture<Void> future = WorkflowClient.execute(task::execute);

    }
}

