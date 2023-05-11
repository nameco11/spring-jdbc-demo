package com.tms.springjdbc.domain.model;

import com.tms.springjdbc.infrastructure.annotation.Column;
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
public class DeptEmpEntity extends BaseEntity implements Serializable {
    @Id
    @Column(name = "emp_no")
    private Long empNo;
    @Column(name = "dept_no")
    private String deptNo;
    @Column(name = "from_date")
    private java.sql.Date fromDate;
    @Column(name = "to_date")
    private java.sql.Date toDate;
}
