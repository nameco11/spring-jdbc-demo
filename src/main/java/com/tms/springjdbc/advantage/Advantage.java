package com.tms.springjdbc.advantage;

import com.tms.springjdbc.advantage.paging.Paging;
import com.tms.springjdbc.advantage.paging.PagingResult;

import java.util.List;

public interface Advantage {
    public boolean insert(Object object);


    public boolean updateById(Object object);


    public int execute(String sql, Object... args);

    public <T> List<T> list(String sql, Class<T> clazz, Object... args);

    public <T> PagingResult<T> list(StringBuffer sql, Class<T> clazz, Paging paging);

    public <T> T queryForObject(String sql, Class<T> clazz, Object... args);

    public <T> T queryById(Class<T> clazz, Object id);

    public <T> boolean deleteById(Class<T> clazz, Object id);

}
