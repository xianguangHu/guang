package com.guang.upms.rpc.service.imp;

import com.guang.upms.dao.mapper.UpmsSystemMapper;
import com.guang.upms.dao.model.UpmsSystem;
import com.guang.upms.dao.model.UpmsSystemExample;
import com.guang.upms.rpc.api.UpmsSystemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author huxianguang
 * @create 2017-11-10-下午8:55
 **/
public class UpmsSystemServiceImpl implements UpmsSystemService{

    @Autowired
    private UpmsSystemMapper upmsSystemMapper;

    @Override
    public List<UpmsSystem> selectByExample(UpmsSystemExample example) {
        return upmsSystemMapper.selectByExample(example);
    }
}
