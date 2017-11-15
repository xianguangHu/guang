package com.guang.common.base;


import java.util.List;

/**
 * 降级实现BaseService抽象类
 * Created by ZhangShuzheng on 2017/02/14.
 */
public class BaseServiceMock<Mapper, Record, Example> implements BaseService<Record, Example> {


	@Override
	public int countByExample(Example example) {
		return 0;
	}

	@Override
	public int deleteByExample(Example example) {
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Integer permissionId) {
		return 0;
	}

	@Override
	public int insert(Record record) {
		return 0;
	}

	@Override
	public int insertSelective(Record record) {
		return 0;
	}

	@Override
	public List<Record> selectByExample(Example example) {
		return null;
	}

	@Override
	public Record selectByPrimaryKey(Integer permissionId) {
		return null;
	}

	@Override
	public int updateByExampleSelective(Record record, Example example) {
		return 0;
	}

	@Override
	public int updateByExample(Record record, Example example) {
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(Record record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Record record) {
		return 0;
	}

	@Override
	public List<Record> selectByExampleForOffsetPage(Example example, Integer offset, Integer limit) {
		return null;
	}

	@Override
	public void initMapper() {

	}
}