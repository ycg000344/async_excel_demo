package io.github.ycg000344.async.excel.demo.service;

import io.github.ycg000344.async.excel.handler.FileTransferFunc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
public class FileTransferFuncImpl implements FileTransferFunc {

    private FileTransferFuncImpl() {
    }

    private MultipartFile file;

    public FileTransferFuncImpl(MultipartFile file) {
        this.file = file;
    }

    @Override
    public void transferTo(String absolutePath) throws Exception {
        File file = new File(absolutePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        this.file.transferTo(file);

    }
}
