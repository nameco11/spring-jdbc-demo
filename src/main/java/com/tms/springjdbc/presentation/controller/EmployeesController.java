package com.tms.springjdbc.presentation.controller;

import com.tms.springjdbc.application.services.EmployeesService;
import com.tms.springjdbc.domain.model.EmployeesEntity;
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
@RequestMapping("/employees")
public class EmployeesController extends BaseController<EmployeesEntity, Long, EmployeesService> {
    public EmployeesController(EmployeesService service) {
        super(service);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<SearchResult<EmployeesEntity>>> search(@RequestParam Map<String, String> allRequestParams, PageRequest pageRequest) {
        List<SearchParam> searchParams = convertRequestParamsToSearchParams(allRequestParams);
        SearchResult<EmployeesEntity> searchResult = service.search(searchParams, pageRequest);
        return success("Search completed successfully", searchResult);
    }
}
