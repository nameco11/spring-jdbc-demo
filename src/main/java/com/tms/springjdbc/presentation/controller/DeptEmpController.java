package com.tms.springjdbc.presentation.controller;

import com.tms.springjdbc.application.dto.CustomDeptEmpResponse;
import com.tms.springjdbc.application.services.DeptEmpService;
import com.tms.springjdbc.domain.model.DeptEmpEntity;
import com.tms.springjdbc.infrastructure.search.SearchResult;
import com.tms.springjdbc.presentation.web.dto.BaseResponse;
import com.tms.springjdbc.presentation.web.dto.PageRequest;
import com.tms.springjdbc.presentation.web.dto.SearchParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/deptEmp")
public class DeptEmpController extends BaseController<DeptEmpEntity, Long, DeptEmpService> {
    public DeptEmpController(DeptEmpService service) {
        super(service);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<SearchResult<DeptEmpEntity>>> search(@RequestParam Map<String, String> allRequestParams, PageRequest pageRequest) {
        List<SearchParam> searchParams = convertRequestParamsToSearchParams(allRequestParams);
        SearchResult<DeptEmpEntity> searchResult = service.search(searchParams, pageRequest);
        return success("Search completed successfully", searchResult);
    }

    @GetMapping("/select")
    public ResponseEntity<BaseResponse<SearchResult<CustomDeptEmpResponse>>> findCustomDeptEmp(PageRequest pageRequest) {
        SearchResult<CustomDeptEmpResponse> searchResult = service.findCustomDeptEmp(pageRequest);
        return success("Search completed successfully", searchResult);
    }

    @GetMapping("/join")
    public ResponseEntity<BaseResponse<SearchResult<CustomDeptEmpResponse>>> join2(PageRequest pageRequest) {
        SearchResult<CustomDeptEmpResponse> searchResult = service.join2(pageRequest);
        return success("Search completed successfully", searchResult);
    }
}
