<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- semantic-ui -->
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.13/semantic.min.css">
<base>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.13/semantic.min.js"></script>
<!-- chart.js -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
<!-- datatables -->
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css">
<script
	src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
<!-- axios -->
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<!-- loglevel -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/loglevel/1.6.0/loglevel.min.js"></script>
<style type="text/css">
.wrap {
	width: 100%;
	height: 100%;
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: center;
	/* 	border: red solid 1px; */
}

.container {
	width: 90%;
	height: 95%;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	/* 	border: red solid 1px; */
}

.container>div:first-child {
	width: 90%;
	height: 3%;
	display: flex;
	flex-direction: row;
	justify-content: space-between;
	align-items: center;
	/*   	border: blue solid 1px; */
}

.container>div:nth-child(2), div:nth-child(3) {
	width: 90%;
	height: 50%;
	display: flex;
	flex-direction: row;
	justify-content: space-between;
	align-items: center;
	/*  	border: blue solid 1px; */
}

.container>div:nth-child(2)>div:first-child {
	width: 66%;
	height: 90%;
	border: orange solid 1px;
	overflow-y: auto;
	overflow-x: hidden;
	/* 	display: flex; */
	/* 	align-items: center; */
}

.container>div:nth-child(2)>div:nth-child(2) {
	width: 30%;
	height: 90%;
	border: orange solid 1px;
	display: flex;
	justify-content: center;
	align-items: center;
}

.container>div:nth-child(3)>div {
	width: 48%;
	height: 90%;
	border: orange solid 1px;
	display: flex;
	align-items: center;
}

.two_for_one {
	width: 50%;
}

.two_for_two {
	width: 50%;
	display: flex;
	flex-direction: row;
	align-items: center;
	justify-content: flex-end;
}
#pie_chart {
	width: 70% !important;
	height: auto !important;
}

.loading_bar {
	display: none;
	width: 100%;
	height: 100%;
	position: absolute;
	text-align: center;
	background: rgba(0, 0, 0, 0.7);
	z-index: 8000;
}

.spinner {
	margin: 350px auto;
	width: 40px;
	height: 40px;
	position: relative;
	text-align: center;
	-webkit-animation: sk-rotate 2.0s infinite linear;
	animation: sk-rotate 2.0s infinite linear;
}

.dot1, .dot2 {
	width: 60%;
	height: 60%;
	display: inline-block;
	position: absolute;
	top: 0;
	background-color: #fff;
	border-radius: 100%;
	-webkit-animation: sk-bounce 2.0s infinite ease-in-out;
	animation: sk-bounce 2.0s infinite ease-in-out;
}

.dot2 {
	top: auto;
	bottom: 0;
	-webkit-animation-delay: -1.0s;
	animation-delay: -1.0s;
}

@
-webkit-keyframes sk-rotate { 100% {
	-webkit-transform: rotate(360deg)
}

}
@
keyframes sk-rotate { 100% {
	transform: rotate(360deg);
	-webkit-transform: rotate(360deg)
}

}
@
-webkit-keyframes sk-bounce { 0%, 100% {
	-webkit-transform: scale(0.0)
}

50%
{
-webkit-transform
:
 
scale
(1
.0
)
 
}
}
@
keyframes sk-bounce { 0%, 100% {
	transform: scale(0.0);
	-webkit-transform: scale(0.0);
}
50%
{
transform
:
 
scale
(1
.0
);

    
-webkit-transform
:
 
scale
(1
.0
);

  
}
}
</style>
<script>
	var color_lst = [ "#3370AD", "#F56400", "#FFC400", "#41BB38", "#3e95cd",
			"#8e5ea2", "#3cba9f", "#c45850", "#3F7B7E", "#3C4EAA", "#8370C9",
			"#53498F", "#A74E8B", "#824361", "#A27B51", "#C5AD79", "#745F53",
			"#848484", "#103134", "#0F1754", "#372A67", "#191440", "#52173D",
			"#361221", "#4E3118", "#655630", "#2D2019", "#383838", "#CBB500",
			"#e8c3b9" ];
	var start = new Date();
	var end;
	var pie_chart = null;
	var grid_table = null;

	$(document).ready(function() {
// 		console.log(log);
		log.setDefaultLevel("debug");

		init();
	});

	function init() {
		setInitDate();

		// 	axiosInterceptor();
		getData();

		$('#search').click(function() {
			// 		log.debug('click!');
			getData();
		});
		
		$('#report').click(function() {
			location.href = "${ pageContext.request.contextPath }/downloadReport";
		});
	}

	function setInitDate() {
		getInitDate();
		log.debug(start);
		log.debug(end);

		$('#start').val(start);
		$('#end').val(end);
	}

	function getInitDate() {
		end = new Date().toISOString().substr(0, 10).replace('T', ' ');

		// 일주일 전 날짜
		start.setDate(new Date().getDate() - 7);
		start = start.toISOString().substr(0, 10).replace('T', ' ');
	}

	function axiosInterceptor() {
		axios.interceptors.request.use(function(config) {
			$('.loading_bar').show();
			log.info('log test');
			return config;
		}, function(error) {
			return Promise.reject(error);
		});

		axios.interceptors.response.use(function(response) {
			_.delay(function() {
				$('.loading_bar').hide();
			}, 1000);
			return response;
		}, function(error) {
			return Promise.reject(error);
		});
	}

	function getData() {
		// 	log.debug('$("#start").val():', typeof (new Date($('#start').val()).getTime() / 1000) );
		var start = new Date($('#start').val()).getTime();
		var end = new Date($('#end').val()).getTime();

		axios.get('${ pageContext.request.contextPath }/getData', {
			params : {
				start : start,
				end : end
			}
		}).then(function(response) {
			log.debug('response:', response);

			//파이 차트
			createPieChart(response.data.pie_data);

			// 라인 차트
			createLineChart(response.data.line_data);
			
			// 테이블
			createDataTable(response.data.grid_data);
			
			// 바 차트
			createStackBarChart();
		});
	}
	
	function createDataTable(data) {
// 		log.debug('data:', data);
// 		log.debug('data.result:', data.result);
// 		log.debug('data.columns:', data.columns);
		
// 		if ( $.fn.dataTable.isDataTable( '#grid_table' ) ) {
// 			log.debug('asd');
// 			grid_table.destroy();
// // 		    table = $('#example').DataTable();
// 		}

// 		if (grid_table != null) {
// 			log.debug('before grid_table:', grid_table);
// 			grid_table.destroy();
// 		}
		
		grid_table = $('#grid_table').DataTable({
			 data: data.result,
		     columns: data.columns,
		     destroy: true,
		     searching: false,
		     lengthMenu: [ 5, 10, 25, 50, 75, 100 ]
		});
	}

	function createPieChart(data) {
		// 	log.debug('data:', data);

		// 차트가 이미 그려졌으면 지운다
		if (pie_chart != null) {
			// 		log.debug('before pie_chart:', pie_chart);
			pie_chart.destroy();
		}

		var ctx_pie = $('#pie_chart');
		var length = data.length;
		var _data = {};
		var labels = [];
		var datasets = [];
		var dataset = {};
		var data_list = [];
		var backgroundColor = [];

		// 	log.debug('ctx_pie:', ctx_pie);	
		// 	log.debug('length:', length);

		for (var i = 0; i < length; i++) {
			var vo = data[i];

			labels.push('type(' + vo.type + ')');
			data_list.push(vo.value);
			backgroundColor.push(color_lst[i]);
		}
		dataset = {
			data : data_list,
			backgroundColor : backgroundColor
		}
		datasets.push(dataset);
		_data = {
			labels : labels,
			datasets : datasets
		}
		// 	_data.push(labels);
		// 	_data.push(datasets);

		// 	log.debug('_data:', _data);
		// 	log.debug('value_data:', value_data);

		pie_chart = new Chart(ctx_pie, {
			"type" : "doughnut",
			"data" : _data
		})

		// 	var pie_chart = new Chart(ctx_pie,{
		// 		"type":"doughnut",
		// 		"data":{
		// 			"labels":["Red","Blue","Yellow"],
		// 			"datasets":[{
		// 				"label":"My First Dataset",
		// 				"data":[300,50,100],
		// 				"backgroundColor":[
		// 					"rgb(255, 99, 132)",
		// 					"rgb(54, 162, 235)",
		// 					"rgb(255, 205, 86)"
		// 				]
		// 			}]
		// 		}
		// 	});
	}

	function createLineChart(data) {
// 		log.debug('data:', data.length);

		//var ctx_line = $('#line_chart');
		var ctx_line = document.getElementById("line_chart").getContext("2d");

		var length = data.length;
		var _data = {};
		var labels = [];
		var datasets = [];
		var dataset = {};

		var vo = [];
		for (var i = 0; i < length; i++) {
			vo = data[i];
			dataset = {
				label : vo.type,
				borderColor : color_lst[i],
				backgroundColor : color_lst[i],
				data : vo.value,
				fill : false
			}
			datasets.push(dataset);
		}

		labels.push(data[0].date);
		_data = {
			labels : labels[0],
			datasets : datasets
		}

// 		log.debug('_data:', _data);

 		var line_chart = new Chart(ctx_line, {
			type : 'line',
			data : _data,
			options : {
				responsive: true,
				stacked : false,
				scales : {
				}
			}
		});

			/* var line_chart = new Chart(ctx_line, {
			    // The type of chart we want to create
			    type: 'line',

			    // The data for our dataset
			    data: {
			        labels: ["2017-12-19", "2017-12-20", "2017-12-21", "2017-12-22", "2017-12-23", "2017-12-24", "2017-12-25", "2017-12-26"],
			        datasets: [{
			            label: "2",
			            backgroundColor: 'rgba(255, 99, 132, 0)',
			            borderColor: '#3370AD',
			            data: [44, 24, 3, 0, 0, 0, 0, 0, 0, 0, 0],
			        },
			        {
			            label: "3",
			            backgroundColor: 'rgba(255, 99, 100, 0)',
			            borderColor: 'red',
			            data: [0, 24, 3, 0, 0, 0, 0, 0, 0, 0, 0],
			        }			        
			        ]
			    }
			}); */
	}

	function createStackBarChart() {
		var ctx_bar = $('#bar_chart');
		var bar_chart = new Chart(ctx_bar,
				{
					type : 'bar',
					data : {
						labels : [ "Red", "Blue", "Yellow", "Green", "Purple",
								"Orange" ],
						datasets : [ {
							label : '# of Votes',
							data : [ 12, 19, 3, 5, 2, 3 ],
							backgroundColor : [ 'rgba(255, 99, 132, 0.2)',
									'rgba(54, 162, 235, 0.2)',
									'rgba(255, 206, 86, 0.2)',
									'rgba(75, 192, 192, 0.2)',
									'rgba(153, 102, 255, 0.2)',
									'rgba(255, 159, 64, 0.2)' ],
							borderColor : [ 'rgba(255,99,132,1)',
									'rgba(54, 162, 235, 1)',
									'rgba(255, 206, 86, 1)',
									'rgba(75, 192, 192, 1)',
									'rgba(153, 102, 255, 1)',
									'rgba(255, 159, 64, 1)' ],
							borderWidth : 1
						} ]
					},
					options : {
						scales : {
							yAxes : [ {
								ticks : {
									beginAtZero : true
								}
							} ]
						}
					}
				});
	}
</script>
<title>Chart</title>
</head>
<body>
	<div class="wrap">
		<!-- loading bar -->
		<div class="loading_bar">
			<div class="spinner">
				<div class="dot1"></div>
				<div class="dot2"></div>
			</div>
		</div>
		<div class="container">
			<div class="search">
				<div class="two_for_one">
					<button class="ui button" id="report">report</button>
				</div>
				<div class="two_for_two">
					<form>
						<input type="date" name="start" id="start" /> <input type="date" name="end" id="end" />
					</form>
					<button class="ui button" id="search">search</button>
				</div>
			</div>
			<div class="table_pie">
				<div class="table_div">
					<table id="grid_table" class="ui celled table" cellspacing="0" width="100%">
					</table>
				</div>
				<div class="pie_chart_div">
					<canvas id="pie_chart"></canvas>
				</div>
			</div>
			<div class="line_bar">
				<div class="line_chart_div">
					<canvas id="line_chart"></canvas>
				</div>
				<div class="bar_chart_div">
					<canvas id="bar_chart"></canvas>
				</div>
			</div>
		</div>
	</div>
</body>
</html>