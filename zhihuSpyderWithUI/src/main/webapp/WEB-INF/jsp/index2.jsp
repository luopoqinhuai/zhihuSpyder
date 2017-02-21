<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://echarts.baidu.com/dist/echarts.min.js"></script>

<script>
function ceshi(){
	
	
	
	var myChart = echarts.init(document.getElementById('main'));
	var option = {
				title:{ 
					text: '呵呵哒'
					},
				tooltip: {},
				legend: {
					data:['什么鬼']
					},
				xAxis: {data: [1,2,3]
					},				
				yAxis: {},
				series: [{ name: '吧里吧里',type: 'bar',barWidth:20, data: [11047,9432,10450] }]};

				
	myChart.setOption(option);
}
	
	
function displayDate2(){
	var myChart = echarts.init(document.getElementById('main'));
	var option = {
				title:{ 
					text: '呵呵哒'
					},
				tooltip: {},
				legend: {
					data:['什么鬼']
					},
				xAxis: {data: [1,2,3]
					},				
				yAxis: {},
				series: [{ name: '吧里吧里',type: 'bar',barWidth:20, data: [1,2,3] }]};

				
	myChart.setOption(option);
}	
</script>

<title>Insert title here</title>
</head>
<body>

<div id="main" style="width: 80%;height:400px; padding-top:20px;"></div>

HELLO RENO!  index2
<button type="button" onclick="ceshi()">显示日期</button>
<img src="/zhihuSpyderWithUI/images/ceshi.bmp">

	
<div id="main" style="width: 80%;height:400px; padding-top:20px;"></div>	

</body>
</html>