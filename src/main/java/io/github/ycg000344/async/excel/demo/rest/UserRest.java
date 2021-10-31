package io.github.ycg000344.async.excel.demo.rest;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.ycg000344.async.excel.demo.bean.User;
import io.github.ycg000344.async.excel.demo.mapper.UserMapper;
import io.github.ycg000344.async.excel.demo.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserRest {

    @Autowired
    private UserService userService;

//    @GetMapping("/model")
//    public void model(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        ExportParams params = new ExportParams();
//        params.setType(ExcelType.XSSF);
//        Workbook workbook = ExcelExportUtil.exportExcel(params, User.class, new ArrayList<>());
//
//
//        String mimetype = "application/x-msdownload";
//        response.setContentType(mimetype);
//        String downFileName = "dataFile.xlsx";
//        String inlineType = "attachment"; // 是否内联附件
//        response.setHeader("Content-Disposition", inlineType
//                + ";filename=\"" + downFileName + "\"");
//
//        ServletOutputStream stream = response.getOutputStream();
//        workbook.write(stream);
//        workbook.close();
//        stream.flush();
//        stream.close();
//    }

    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        return userService.upload(file);
    }


    @GetMapping("/export")
    public String export() throws IOException {
        return userService.export();
    }

    @GetMapping("/export/s")
    public void exportS(HttpServletResponse response) throws IOException {
        ExportParams params = new ExportParams();
        params.setType(ExcelType.XSSF);

        Workbook workbook = ExcelExportUtil.exportBigExcel(params, User.class, (queryParams, page) -> {
            PageInfo<Object> objects = PageHelper.startPage(page, 100).doSelectPageInfo(() -> {
                userMapper.selectList(new QueryWrapper<>());
            });
            return objects.getList();
        }, new HashMap<>());


        String mimetype = "application/x-msdownload";
        response.setContentType(mimetype);
        String downFileName = String.format("%s.xlsx", DateUtil.now());
        String inlineType = "attachment"; // 是否内联附件
        response.setHeader("Content-Disposition", inlineType
                + ";filename=\"" + downFileName + "\"");

        ServletOutputStream stream = response.getOutputStream();
        workbook.write(stream);
        workbook.close();
        stream.flush();
        stream.close();
    }

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/list")
    public Object list() {
        return userMapper.selectList(new QueryWrapper<>());
    }

    @GetMapping("/page/mp")
    public Object pageMp() {
        IPage<User> page = new Page(1, 100);
        return userMapper.selectPage(page, new QueryWrapper<>());
    }

    @GetMapping("/page/ph")
    public Object pagePh() {
        return PageHelper.startPage(1, 100).doSelectPageInfo(() -> {
            userMapper.selectList(new QueryWrapper<>());
        });
    }
}
