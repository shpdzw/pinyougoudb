 //品牌控制层 
app.controller('baseController' ,function($scope){	
	
	
	//刷新列表
	$scope.reloadList=function(){
		
	//刷新页面,每次点击页面是所穿给后台数据以及显示在页面上的分页数据
		//不需要ng-init初始化,因为分页点击的时候就会初始化
		$scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
	}
    
  //分页控件配置  	currentPage:当前页  ,totalItems:总记录数   itemsPerPage:每页记录数 ,perPageOptions:分页选项
	//onChange:当页码变更后自动触发的方法
	$scope.paginationConf = {
		currentPage: 1,
		totalItems: 10,
		itemsPerPage: 10,
		perPageOptions: [10, 20, 30, 40, 50],
		onChange: function(){
			$scope.reloadList();
		}
	}; 
	
	$scope.selectIds=[];//选中的ID集合 

	//更新复选
	//用户勾选调用该方法
	$scope.updateSelection=function($event,id){
		
		if($event.target.checked){//event.target代表的是数据列表中的<input>标签复选框 中checked属性 //如果是被选中,则增加到数组
			$scope.selectIds.push(id);//push向集合添加元素
		}else{
			var index =$scope.selectIds.indexOf(id);//查找值的位置
			$scope.selectIds.splice(index,1);//参数1:移除的位置,参数2:移除的个数
			
		}
		
	}
	
	//将一个 json 字符串中某个属性的值提取出来，用逗号拼接成一个新的字符串
	//提取 json 字符串数据中某个属性，返回拼接字符串 逗号分隔
	$scope.jsonToString=function(jsonToString,key){
		
		var json= JSON.parse(jsonString);//将json字符串转换成json对象
		
		//定义一个变量 用于接收最终要返回的数据
		var value ="";
		
		for(var i=0;i<json.length;i++){//i代表每次循环的对象
			if (i > 0){
				value=",";
			}
			value = json[i][key]; //获取每一个对象的值
		}
		
		return value;
	}
	
});		