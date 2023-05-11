package com.tms.springjdbc.domain.model;


import com.tms.springjdbc.infrastructure.annotation.Id;
import com.tms.springjdbc.infrastructure.annotation.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Table("employees")
@Setter
@Getter
@NoArgsConstructor
public class EmployeesEntity extends BaseEntity implements Serializable {
    @Id
    private Long empNo;
    private LocalDate birthDate;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate hireDate;
}
