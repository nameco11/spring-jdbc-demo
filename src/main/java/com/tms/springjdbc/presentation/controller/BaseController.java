package com.tms.springjdbc.presentation.controller;

import com.tms.springjdbc.application.services.BaseService;
import com.tms.springjdbc.infrastructure.search.Operation;
import com.tms.springjdbc.presentation.web.dto.BaseResponse;
import com.tms.springjdbc.presentation.web.dto.ErrorResponse;
import com.tms.springjdbc.presentation.web.dto.SearchParam;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.*;

public abstract class BaseController<T, ID extends Serializable, S extends BaseService<T, ID>> {

    protected final S service;

    public BaseController(S service) {
        this.service = service;
    }

    protected <T> ResponseEntity<BaseResponse<T>> success(String message, T data) {
        return ResponseEntity.ok(BaseResponse.success(message, data));
    }

    protected <T> ResponseEntity<BaseResponse<T>> error(String message, T data) {
        return ResponseEntity.badRequest().body(BaseResponse.error(message, data));
    }

    protected List<SearchParam> convertRequestParamsToSearchParams(Map<String, String> requestParams) {
        List<SearchParam> searchParams = new ArrayList<>();

        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            String paramName = entry.getKey();
            String paramValue = entry.getValue();
            SearchParam searchParam = new SearchParam(paramName, paramValue, Operation.EQUAL);
            searchParams.add(searchParam);
        }

        return searchParams;
    }

    @PostMapping
    public ResponseEntity<T> create(@RequestBody T entity) {
        T savedEntity = service.save(entity);
        return new ResponseEntity<>(savedEntity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable ID id) {
        Optional<T> entity = service.findById(id);
        return entity.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        List<T> entities = service.findAll();
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable ID id, @RequestBody T entity) {
        Optional<T> existingEntity = service.findById(id);
        if (existingEntity.isPresent()) {
            service.save(entity);
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable ID id) {
        Optional<T> existingEntity = service.findById(id);
        if (existingEntity.isPresent()) {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = ex.getMessage();
        String errorCode = "ERR_UNKNOWN";
        Object data = null;

        if (ex instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
            errorCode = "ERR_BAD_REQUEST";
        } else if (ex instanceof DataAccessException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorCode = "ERR_DATA_ACCESS";
        } else if (ex instanceof NoSuchElementException) {
            status = HttpStatus.NOT_FOUND;
            errorCode = "ERR_NOT_FOUND";
        }

        // ... other exceptions can be added here
        ErrorResponse errorResponse = new ErrorResponse(status.toString(), message, errorCode, data);
        return new ResponseEntity<>(errorResponse, status);
    }

//    @GetMapping("/search")
//    public ResponseEntity<SearchResult<T>> search(@RequestParam(defaultValue = "0") int page,
//                                                            @RequestParam(defaultValue = "10") int size,
//                                                            @RequestParam(defaultValue = "id") String sort,
//                                                            @RequestParam(defaultValue = "DESC") String direction
//    ) {
//        PageRequest pageRequest = new PageRequest(page, size, sort, direction);
//        SearchResult<T> searchResult = service.search(new ArrayList<>(), new ArrayList<>(), pageRequest);
//        return ResponseEntity.ok(searchResult);
//    }
//
//    @GetMapping("/search-join")
//    public ResponseEntity<SearchResult<T>> searchJoin(@RequestParam(defaultValue = "0") int page,
//                                                                @RequestParam(defaultValue = "10") int size,
//                                                                @RequestParam(defaultValue = "id") String sort,
//                                                                @RequestParam(defaultValue = "DESC") String direction) {
//        PageRequest pageRequest = new PageRequest(page, size, sort, direction);
//        SearchResult<T> searchResult = service.searchJoin(new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), pageRequest);
//        return ResponseEntity.ok(searchResult);
//    }
}
