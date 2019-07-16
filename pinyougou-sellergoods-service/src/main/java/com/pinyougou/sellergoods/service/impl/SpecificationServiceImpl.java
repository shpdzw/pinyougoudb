package com.pinyougou.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationExample.Criteria;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojogroup.Specification;
import com.pinyougou.sellergoods.service.SpecificationService;

import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSpecification> page=   (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Specification specification) {

		// 获取规格的实体
		TbSpecification tbSpecification = specification.getSpecification();

		specificationMapper.insert(tbSpecification);

		// 获取规格选项集合对象
		List<TbSpecificationOption> specificationOptions = specification.getSpecificationOptionList();

		// 遍历循环集合
		for (TbSpecificationOption option : specificationOptions) {

			// 设置规格id
			option.setSpecId(tbSpecification.getId());

			// 循环添加每一个option 新增规格
			specificationOptionMapper.insert(option);

		}
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification) {

		// 获取规格实体
		TbSpecification tbSpecification = specification.getSpecification();

		// 保存修改的规格
		specificationMapper.updateByPrimaryKey(tbSpecification);

		// 删除原有的规格选项
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();

		com.pinyougou.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		// 精确获取规格选项id 添加xxx字段等于value条件
		criteria.andSpecIdEqualTo(tbSpecification.getId());
		// 删除原有的规格选项
		specificationOptionMapper.deleteByExample(example);

		// 获取规格选项集合
		List<TbSpecificationOption> specificationOptions = specification.getSpecificationOptionList();

		// 遍历循环集合
		for (TbSpecificationOption option : specificationOptions) {

			// 设置规格id
			option.setSpecId(tbSpecification.getId());

			// 循环添加每一个option 新增规格
			specificationOptionMapper.insert(option);// 新增规格
		}
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id) {

		// 新建组合实体类
		Specification specification = new Specification();

		// 获取规格实体
		TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);

		specification.setSpecification(tbSpecification);

		// 获取规格选项列表 从dao层拿取mybatis封装的对象
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();

		com.pinyougou.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();

		// 精确查询
		criteria.andSpecIdEqualTo(id);

		List<TbSpecificationOption> specificationOptionList = specificationOptionMapper.selectByExample(example);

		specification.setSpecificationOptionList(specificationOptionList);

		return specification;
		// return specificationMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			// 删除规格表数据
			specificationMapper.deleteByPrimaryKey(id);

			// 删除原有的规格选项
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();

			com.pinyougou.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();

			// 指定规格id为条件
			criteria.andSpecIdEqualTo(id);

			// 删除
			specificationOptionMapper.deleteByExample(example);

		}		
	}
	
	
		@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
	
		}
		
		Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public List<Map> selectOptionList() {
		// TODO Auto-generated method stub
		return specificationMapper.selectOptionList();
	}
	
}
