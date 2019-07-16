app.controller('brandController',function($scope,$controller,brandService){
//伪继承,可以做出继承那样的效果,相当于把这里面的方法都拿过来使用,实际上这种继承是通过scope来实现的
//(相当于把baseController中的scope拿到了brandController中使用,让当前brandController中的scope等于baseController中的scope,共用scope那么其中的方法都可以共用)

	$controller('baseController',{$scope:$scope});
//查询品牌列表
	$scope.findAll=function(){
		brandService.findAll().success(
				function(response){
					$scope.list=response;
				}
			);
		}

	
	
	$scope.findPage=function(page,size){
		brandService.findPage(page,size).success(
				function(response){
					
				//后台返回前端的两个数据就可以显示一个分页的页面
					//每页记录数,总页数
					$scope.list =response.rows;//显示当前页数据
				//上面分页插件中的总页数是固定的,我们数据是在变化的,所以我们根据返回的值跟上面赋值
					$scope.paginationConf.totalItems=response.total;//更新总记录数
					
				}
		);
	}
	
	//新增
	$scope.save=function(){
		//定义一个对象
		var object=null;
		
		if($scope.entity.id != null){//实体类对象不为空
			object=brandService.update($scope.entity);
		
		}else{
			object=brandService.add($scope.entity);
		}
		
		object.success(
				function(response){
					if(response.success){//添加成功
						$scope.reloadList();//重新加载
					}else{
						alert(response.message);//失败
					}
				}
		);
	}
	
	//查询实体
	$scope.findOne=function(id){
		brandService.findOne(id).success(
				function(response){	//此处的response返回的是一个实体类对象
					$scope.entity=response;
				}
		);
	}
	
	
	//删除
	$scope.dele=function(){
		if(confirm('确定要删除吗？')){
			brandService.dele($scope.selectIds).success(
					function(response){
						if(response.success){
							$scope.reloadList();//刷新
						}else{
							alert(response.message);
						}						
					}
			);
		}		
	}
	
	//搜索
	$scope.searchEntity={};//初始化没有属性值的对象,
	//条件查询
	$scope.search=function(page,size){
		brandService.search(page,size,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;//显示当前页数据
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}	
		);
	}

});