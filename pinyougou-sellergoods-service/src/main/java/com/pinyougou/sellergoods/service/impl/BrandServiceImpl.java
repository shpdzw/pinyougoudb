package com.pinyougou.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.pojo.TbBrandExample.Criteria;
import com.pinyougou.sellergoods.service.BrandService;

import entity.PageResult;

@Service
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	private TbBrandMapper brandMapper;
	
	// 查询所有品牌信息
	@Override
	public List<TbBrand> findAll() {

		return brandMapper.selectByExample(null);
	}

	// 分页信息
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		// 封装分页参数
		PageHelper.startPage(pageNum, pageSize);
		// 执行查询
		Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(null);

		return new PageResult(page.getTotal(), page.getResult());
	}

	// 添加品牌信息
	@Override
	public void add(TbBrand brand) {

		brandMapper.insert(brand);
	}

	// 根据id查询品牌信息
	@Override
	public TbBrand findOne(Long id) {

		return brandMapper.selectByPrimaryKey(id);
	}

	// 修改品牌信息
	@Override
	public void update(TbBrand brand) {

		brandMapper.updateByPrimaryKey(brand);
	}

	// 删除品牌信息
	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			brandMapper.deleteByPrimaryKey(id);
		}
	}

	// 品牌分页信息
	@Override
	public PageResult findPage(TbBrand brand, int pageNum, int pageSize) {
		// 封装分页参数
		PageHelper.startPage(pageNum, pageSize);// 分页

		// 创建数据库映射类的Example对象，并进行分页数据的读取
		TbBrandExample example = new TbBrandExample();

		Criteria criteria = example.createCriteria();// 构建分页条件

		// 进行非空判断
		if (brand != null) {
			if (brand.getName() != null && brand.getName().length() > 0) {
				criteria.andNameLike("%" + brand.getName() + "%");// 根据name模糊查询
			}

			if (brand.getFirstChar() != null && brand.getFirstChar().length() > 0) {
				criteria.andFirstCharLike("%" + brand.getFirstChar() + "%");// 根据首字母模糊查询
			}
		}
		Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(example);

		return new PageResult(page.getTotal(), page.getResult());
	}

	/*
	 * 列表数据
	 */
	@Override
	public List<Map> selectOptionList() {
		// TODO Auto-generated method stub
		return brandMapper.selectOptionList();
	}

}
