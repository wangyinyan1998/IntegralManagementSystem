package userTask;

public class Task {
    TaskLifeCycleStrategy taskLifeCycleStrategy;
    TaskPointCalcStrategy taskPointCalcStrategy;
    TaskDef taskDef;
    public TaskDef getTaskDef() {
        return taskDef;
    }

    public void setTaskDef(TaskDef taskDef) {
        this.taskDef = taskDef;
    }
    public TaskLifeCycleStrategy getTaskLifeCycleStrategy() {
        return taskLifeCycleStrategy;
    }

    public void setTaskLifeCycleStrategy(TaskLifeCycleStrategy taskLifeCycleStrategy) {
        this.taskLifeCycleStrategy = taskLifeCycleStrategy;
    }

    public TaskPointCalcStrategy getTaskPointCalcStrategy() {
        return taskPointCalcStrategy;
    }

    public void setTaskPointCalcStrategy(TaskPointCalcStrategy taskPointCalcStrategy) {
        this.taskPointCalcStrategy = taskPointCalcStrategy;
    }

    @Override
    public boolean equals(Object obj) {
        Task task = (Task)obj;
        return task.getTaskDef().equals(this.taskDef);
    }
}
