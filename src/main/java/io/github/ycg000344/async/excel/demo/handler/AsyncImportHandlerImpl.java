package io.github.ycg000344.async.excel.demo.handler;

import io.github.ycg000344.async.excel.demo.bean.User;
import io.github.ycg000344.async.excel.demo.mapper.UserMapper;
import io.github.ycg000344.async.excel.bean.ImportHandlerResult;
import io.github.ycg000344.async.excel.handler.AsyncImportHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.Row;

import java.util.Objects;

@Slf4j
public class AsyncImportHandlerImpl implements AsyncImportHandler {


    private UserMapper userMapper;


    @Override
    public void getMapper(SqlSession sqlSession) {
        this.userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @Override
    public ImportHandlerResult handle(Row row) {

        try {
            User user = tran(row);
            User byId = this.userMapper.selectById(user.getId());
            if (Objects.nonNull(byId)) {
                return ImportHandlerResult.builder().build().error("repeat");
            }
            log.info("{}", user);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return ImportHandlerResult.builder().build().error(e.getMessage());
        }
        return ImportHandlerResult.builder().ok(true).build();
    }

    private User tran(Row row) {
        User user = new User();
        String idStr = row.getCell(0).getStringCellValue();
        String name = row.getCell(1).getStringCellValue();
        String ageStr = row.getCell(2).getStringCellValue();
        String email = row.getCell(3).getStringCellValue();
        try {
            user.setId(Long.parseLong(idStr));
            user.setName(name);
            user.setAge(Integer.parseInt(ageStr));
            user.setEmail(email);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return user;
    }
}
