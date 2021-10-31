package io.github.ycg000344.async.excel.demo.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class User {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "name")
    private String name;
    @Excel(name = "age")
    private Integer age;
    @Excel(name = "email")
    private String email;
}
