package com.tms.springjdbc.application.dto;

import com.tms.springjdbc.domain.model.DeptEmpEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomDeptEmpResponse extends DeptEmpEntity {
    private String deptName;
//    private String deptNo;
//    private String empNo;
}
