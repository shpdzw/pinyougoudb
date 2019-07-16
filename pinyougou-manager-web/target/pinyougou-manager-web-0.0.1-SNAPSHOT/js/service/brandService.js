//品牌服务层	  先有服务在有控制层
//JS 和 html 都放在一起，并不利于我们后期的维护。我们可以在前端代码中也运用 MVC 的设计模式，将代码进行分离，提高程序的可维护性。
app.service('brandService',function($http){
	
	//读取列表数据绑定到表单
	this.findAll=function(){
		return $http.get('../brand/findAll.do');
	}
	
	//根据id查询
	this.findOne=function(id){
		return $http.get('../brand/findOne.do?id='+id);
	}
	
	//分页
	this.findPage=function(page,size){
		return $http.get('../brand/findPage.do?page='+page+'&size='+size);
	}
	
	//添加
	this.add=function(entity){
		return $http.post('../brand/add.do',entity);
	}
	
	//修改
	this.update=function(entity){
		return $http.post('../brand/update.do',entity);
	}
	
	//删除
	this.dele=function(ids){
		return $http.get('../brand/delete.do?ids='+ids);
	}
	
	//搜索
	this.search=function(page,size,searchEntity){
		return $http.post('../brand/search.do?page='+page+"&size="+size, searchEntity);
	}
	
	//下拉列表数据
	this.selectOptionList=function(){
		return $http.get('../brand/selectOptionList.do');
	}
});