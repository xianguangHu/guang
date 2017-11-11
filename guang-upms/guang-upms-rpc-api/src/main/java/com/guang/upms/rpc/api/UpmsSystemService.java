package com.guang.upms.rpc.api;

import com.guang.upms.dao.model.UpmsSystem;
import com.guang.upms.dao.model.UpmsSystemExample;

import java.util.List;

public interface UpmsSystemService {

    /**
     * 根据条件查询
     * @param example
     * @return
     */
    List<UpmsSystem> selectByExample(UpmsSystemExample example);



}
