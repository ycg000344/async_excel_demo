package io.github.ycg000344.async.excel.demo.service;

import io.github.ycg000344.async.excel.bean.TaskProgress;
import io.github.ycg000344.async.excel.handler.TaskProcessCacheFunc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@Slf4j
public class TaskProcessCacheFuncImpl implements TaskProcessCacheFunc {


    private final Map<String, TaskProgress> taskProgressMap = new ConcurrentHashMap<>();

    @Override
    public void updateTaskProcess(String taskId, TaskProgress taskProgress) {
        taskProgressMap.put(taskId, taskProgress);
    }

    @Override
    public TaskProgress getTaskProcess(String taskId) {
        return taskProgressMap.get(taskId);
    }

}
