package com.tms.springjdbc.domain.model;

import com.tms.springjdbc.infrastructure.annotation.Id;
import com.tms.springjdbc.infrastructure.annotation.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Table("dept_emp")
@Setter
@Getter
@NoArgsConstructor
public class DeptEmpEntity implements Serializable {
    @Id
    private Long empNo;
    private String deptNo;
    private java.sql.Date fromDate;
    private java.sql.Date toDate;
}
