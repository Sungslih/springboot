package com.xwbing.domain.mapper;

import java.util.List;
import java.util.Map;

/**
 * @author xiangwb
 * @version $Id: BaseMapper.java, v 0.1 2018年06月04日 11:05 wb-xwb402636 Exp $
 */
public interface BaseMapper<M> {
    int insert(M model);

    int insertBatch(List<M> models);

    int deleteById(String id);

    int deleteByIds(List<String> ids);

    int delete(Map<String, Object> conditions);

    int update(M model);

    int updateBatch(List<M> models);

    M findById(String id);

    List<M> findByIds(List<String> ids);

    List<M> find(Map<String, Object> conditions);

    List<M> findAll();
}

