package com.guang.upms.rpc.api;


import com.guang.common.base.BaseServiceMock;
import com.guang.upms.dao.mapper.UpmsSystemMapper;
import com.guang.upms.dao.model.UpmsSystem;
import com.guang.upms.dao.model.UpmsSystemExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
* 降级实现UpmsSystemService接口
* Created by shuzheng on 2017/3/20.
*/
public class UpmsSystemServiceMock extends BaseServiceMock<UpmsSystemMapper,UpmsSystem,UpmsSystemExample> implements UpmsSystemService {
    private static Logger _log = LoggerFactory.getLogger(UpmsSystemServiceMock.class);

    @Override
    public void initMapper() {

    }

//    @Override
//    public List<UpmsSystem> selectByExample(UpmsSystemExample example) {
//        _log.info("UpmsSystemServiceMock => selectByExample");
//        return null;
//    }
//
//    @Override
//    public List<UpmsSystem> selectByExampleForOffsetPage(UpmsSystemExample example, Integer offset, Integer limit) {
//        _log.info("UpmsSystemServiceMock => selectByExampleForOffsetPage");
//        return null;
//    }
//
//    @Override
//    public int insertSelective(UpmsSystem upmsSystem) {
//        _log.info("UpmsSystemServiceMock => insertSelective");
//        return 0;
//    }
//
//    @Override
//    public UpmsSystem selectByPrimaryKey(Integer systemId) {
//        _log.info("UpmsSystemServiceMock => selectByPrimaryKey");
//        return null;
//    }
//
//    @Override
//    public int updateByPrimaryKeySelective(UpmsSystem record) {
//        _log.info("UpmsSystemServiceMock => updateByPrimaryKeySelective");
//        return 0;
//    }
//
//    @Override
//    public int deleteByPrimaryKey(Integer systemId) {
//        _log.info("UpmsSystemServiceMock => deleteByPrimaryKey");
//        return 0;
//    }

}
