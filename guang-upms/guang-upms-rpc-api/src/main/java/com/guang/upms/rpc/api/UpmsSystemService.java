package com.guang.upms.rpc.api;

import com.guang.upms.dao.model.UpmsSystem;
import com.guang.upms.dao.model.UpmsSystemExample;

import java.util.List;

public interface UpmsSystemService {

    List<UpmsSystem> selectByExample(UpmsSystemExample example);
}
