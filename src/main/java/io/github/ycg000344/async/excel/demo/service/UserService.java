package io.github.ycg000344.async.excel.demo.service;

import io.github.ycg000344.async.excel.demo.handler.AsyncImportHandlerImpl;
import io.github.ycg000344.async.excel.demo.handler.StaticExportHandlerImpl;
import io.github.ycg000344.async.excel.demo.mapper.UserMapper;
import io.github.ycg000344.async.excel.bean.TaskInfo;
import io.github.ycg000344.async.excel.manager.AsyncExcelTaskManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AsyncExcelTaskManager manager;

    private ExecutorService executorService;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private TaskProcessCacheFuncImpl taskProcessCacheFunc;

    @PostConstruct
    public void init() {
        executorService = Executors.newSingleThreadExecutor();
    }

    public String upload(MultipartFile file) {
        try {
            TaskInfo taskInfo = manager.createImportTask(new FileTransferFuncImpl(file), new AsyncImportHandlerImpl(), executorService, sqlSessionFactory, taskProcessCacheFunc);
            return taskInfo.getTaskId();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


    public String export() {
        TaskInfo exportTask = manager.createExportTask(new StaticExportHandlerImpl(userMapper), executorService, taskProcessCacheFunc);
        return exportTask.getTaskId();
    }
}
