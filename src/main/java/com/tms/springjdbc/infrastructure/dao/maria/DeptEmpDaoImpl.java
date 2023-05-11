package com.tms.springjdbc.infrastructure.dao.maria;

import com.tms.springjdbc.application.dto.CustomDeptEmpResponse;
import com.tms.springjdbc.domain.model.DeptEmpEntity;
import com.tms.springjdbc.domain.repository.DeptEmpDao;
import com.tms.springjdbc.infrastructure.dao.port.BaseDaoImpl;
import com.tms.springjdbc.infrastructure.search.Operation;
import com.tms.springjdbc.infrastructure.search.SearchResult;
import com.tms.springjdbc.infrastructure.search.SelectColumn;
import com.tms.springjdbc.presentation.web.dto.JoinParam;
import com.tms.springjdbc.presentation.web.dto.PageRequest;
import com.tms.springjdbc.presentation.web.dto.SearchParam;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class DeptEmpDaoImpl extends BaseDaoImpl<DeptEmpEntity, Long> implements DeptEmpDao {
    public DeptEmpDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(namedParameterJdbcTemplate, DeptEmpEntity.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public SearchResult<CustomDeptEmpResponse> findCustomDeptEmp(PageRequest pageRequest) {
        List<SelectColumn> selectColumn = List.of(
                new SelectColumn("dept_emp.emp_no", "empNo")
//                new SelectColumn("employees.first_name", "firstName"),
//                new SelectColumn("employees.last_name", "lastName"),
//                new SelectColumn("dept_emp.dept_no", "deptNo"),
//                new SelectColumn("departments.dept_name", "deptName"),
//                new SelectColumn("dept_emp.from_date", "fromDate"),
//                new SelectColumn("dept_emp.to_date", "toDate")
        );
        return (SearchResult<CustomDeptEmpResponse>) this.search(selectColumn, Collections.EMPTY_LIST, pageRequest);
    }

    @Override
    @SuppressWarnings("unchecked")
    public SearchResult<CustomDeptEmpResponse> join2(PageRequest pageRequest) {
        List<SelectColumn> selectColumn = List.of(
                new SelectColumn("dept_emp.dept_no", "dept_no"),
                new SelectColumn("dept_manager.emp_no", "empNo")
        );
        List<JoinParam> joinParams = new ArrayList<>();
        JoinParam primaryJoinParam = new JoinParam("dept_emp");
        primaryJoinParam.setAlias("dept_emp");
        primaryJoinParam.setColumn("emp_no");
        primaryJoinParam.setJoinType(JoinParam.JoinType.INNER_JOIN);
        joinParams.add(primaryJoinParam);

        JoinParam joinParam1 = new JoinParam("dept_manager");
        joinParam1.setAlias("dept_manager");
        joinParam1.setColumn("emp_no");
        joinParam1.setJoinType(JoinParam.JoinType.INNER_JOIN);
        joinParams.add(joinParam1);

        List<SearchParam> searchParams = new ArrayList<>();
        SearchParam searchParam1 = new SearchParam();
        searchParam1.setName("dept_emp.dept_no");
        searchParam1.setOperation(Operation.EQUAL);
        searchParam1.setValue("d002");
        searchParams.add(searchParam1);

        return searchJoin(selectColumn, searchParams, joinParams, pageRequest);
    }
}
