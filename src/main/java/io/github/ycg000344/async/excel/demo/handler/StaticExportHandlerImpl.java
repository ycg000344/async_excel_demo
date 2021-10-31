package io.github.ycg000344.async.excel.demo.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.ycg000344.async.excel.demo.bean.User;
import io.github.ycg000344.async.excel.demo.mapper.UserMapper;
import io.github.ycg000344.async.excel.handler.StaticExportHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class StaticExportHandlerImpl extends StaticExportHandler {


    private StaticExportHandlerImpl() {
    }


    private UserMapper userMapper;

    public StaticExportHandlerImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List doSelect(Map param) {
        return userMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Class pojoClass() {
        return User.class;
    }

    @Override
    public Map param() {
        return new HashMap();
    }
}
