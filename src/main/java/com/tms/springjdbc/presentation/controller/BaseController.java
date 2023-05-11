package com.tms.springjdbc.presentation.controller;

import com.tms.springjdbc.application.services.BaseService;
import com.tms.springjdbc.infrastructure.search.Operation;
import com.tms.springjdbc.presentation.web.dto.BaseResponse;
import com.tms.springjdbc.presentation.web.dto.SearchParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

}
