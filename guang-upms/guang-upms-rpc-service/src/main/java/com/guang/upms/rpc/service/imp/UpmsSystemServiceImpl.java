package com.guang.upms.rpc.service.imp;


import com.guang.common.annotation.BaseService;
import com.guang.common.base.BaseServiceImpl;
import com.guang.upms.dao.mapper.UpmsSystemMapper;
import com.guang.upms.dao.model.UpmsSystem;
import com.guang.upms.dao.model.UpmsSystemExample;
import com.guang.upms.rpc.api.UpmsSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author huxianguang
 * @create 2017-11-10-下午8:55
 **/
@Service
@Transactional
@BaseService
public class UpmsSystemServiceImpl extends BaseServiceImpl<UpmsSystemMapper,UpmsSystem,UpmsSystemExample> implements UpmsSystemService {

    @Autowired
    private UpmsSystemMapper upmsSystemMapper;

//    @Override
//    public List<UpmsSystem> selectByExample(UpmsSystemExample example) {
//        return upmsSystemMapper.selectByExample(example);
//    }
//
//    @Override
//    public List<UpmsSystem> selectByExampleForOffsetPage(UpmsSystemExample example, Integer offset, Integer limit) {
//        PageHelper.offsetPage(offset,limit,false);
//        List<UpmsSystem> upmsSystems = upmsSystemMapper.selectByExample(example);
//        return upmsSystems;
//    }
//
//    @Override
//    public int insertSelective(UpmsSystem upmsSystem) {
//        int count = upmsSystemMapper.insertSelective(upmsSystem);
//        return count;
//    }
//
//    /**
//     * 根据系统id来获取system
//     * @param systemId
//     * @return
//     */
//    @Override
//    public UpmsSystem selectByPrimaryKey(Integer systemId) {
//        UpmsSystem upmsSystem = upmsSystemMapper.selectByPrimaryKey(systemId);
//        return upmsSystem;
//    }
//
//    @Override
//    public int updateByPrimaryKeySelective(UpmsSystem record) {
//        int count = upmsSystemMapper.updateByPrimaryKeySelective(record);
//        return count;
//    }
//
//    @Override
//    public int deleteByPrimaryKey(Integer systemId) {
//        int count = upmsSystemMapper.deleteByPrimaryKey(systemId);
//        return count;
//    }

}
