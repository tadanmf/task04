<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!-- semantic-ui -->
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.13/semantic.min.css">
<base>
<script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.13/semantic.min.js"></script>
<!-- chart.js -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
<!-- datatables -->
<!-- <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css"> -->
<script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
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

.container > div:first-child {
	width: 90%;
	height: 3%;
	display: flex;
	flex-direction: row;
	justify-content: flex-end;
	align-items: center;
/*   	border: blue solid 1px; */
}

.container > div:nth-child(2), div:nth-child(3) {
	width: 90%;
	height: 50%;
	display: flex;
	flex-direction: row;
	justify-content: space-between;
	align-items: center;
/*  	border: blue solid 1px; */
}

.container > div:nth-child(2) > div:first-child {
	width: 66%;
	height: 90%;
	border: orange solid 1px;
	overflow-y: auto;
	overflow-x: hidden;
/* 	display: flex; */
/* 	align-items: center; */
}

.container > div:nth-child(2) > div:nth-child(2) {
	width: 30%;
	height: 90%;
	border: orange solid 1px;
	display: flex;
	justify-content: center;
	align-items: center;
}

.container > div:nth-child(3) > div {
	width: 48%;
	height: 90%;
	border: orange solid 1px;
	display: flex;
	align-items: center;
}

#pie_chart {
	width: 70% !important;
	height: auto !important;
}
</style>
<script>
$(document).ready(function() {
	init();
});

function init() {
	$('#table').DataTable({
		"lengthMenu": [5, 10], 
		"searching": false,
        "info":     false
	});
	
	$('#table_length select').addClass('ui search dropdown');
	
	var ctx_pie = $('#pie_chart');
	var pie_chart = new Chart(ctx_pie,{
		"type":"doughnut",
		"data":{
			"labels":["Red","Blue","Yellow"],
			"datasets":[{
				"label":"My First Dataset",
				"data":[300,50,100],
				"backgroundColor":[
					"rgb(255, 99, 132)",
					"rgb(54, 162, 235)",
					"rgb(255, 205, 86)"
					]
			}]
		}
	});
	
	var ctx_line = $('#line_chart');
	var line_chart = new Chart(ctx_line, {
	    // The type of chart we want to create
	    type: 'line',

	    // The data for our dataset
	    data: {
	        labels: ["January", "February", "March", "April", "May", "June", "July"],
	        datasets: [{
	            label: "My First dataset",
	            backgroundColor: 'rgba(255, 99, 132, 0)',
	            borderColor: 'rgb(255, 99, 132)',
	            data: [0, 10, 5, 2, 20, 30, 45],
	        }]
	    },

	    // Configuration options go here
	    options: {}
	});
	
	var ctx_bar = $('#bar_chart');
	var bar_chart = new Chart(ctx_bar, {
	    type: 'bar',
	    data: {
	        labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
	        datasets: [{
	            label: '# of Votes',
	            data: [12, 19, 3, 5, 2, 3],
	            backgroundColor: [
	                'rgba(255, 99, 132, 0.2)',
	                'rgba(54, 162, 235, 0.2)',
	                'rgba(255, 206, 86, 0.2)',
	                'rgba(75, 192, 192, 0.2)',
	                'rgba(153, 102, 255, 0.2)',
	                'rgba(255, 159, 64, 0.2)'
	            ],
	            borderColor: [
	                'rgba(255,99,132,1)',
	                'rgba(54, 162, 235, 1)',
	                'rgba(255, 206, 86, 1)',
	                'rgba(75, 192, 192, 1)',
	                'rgba(153, 102, 255, 1)',
	                'rgba(255, 159, 64, 1)'
	            ],
	            borderWidth: 1
	        }]
	    },
	    options: {
	        scales: {
	            yAxes: [{
	                ticks: {
	                    beginAtZero:true
	                }
	            }]
	        }
	    }
	});
}
</script>
<title>Chart</title>
</head>
<body>
	<div class="wrap">
		<div class="container">
			<div class="search">
				<form>
					<input type="date" />
				</form>
				<button class="ui button">search</button>
			</div>
			<div class="table_pie">
				<div class="table_div">
					<table id="table" class="ui celled table" cellspacing="0" width="100%">
				        <thead>
				            <tr>
				                <th>Name</th>
				                <th>Position</th>
				                <th>Office</th>
				                <th>Age</th>
				                <th>Start date</th>
				                <th>Salary</th>
				            </tr>
				        </thead>
				        <tbody>
				            <tr>
				                <td>Tiger Nixon</td>
				                <td>System Architect</td>
				                <td>Edinburgh</td>
				                <td>61</td>
				                <td>2011/04/25</td>
				                <td>$320,800</td>
				            </tr>
				            <tr>
				                <td>Garrett Winters</td>
				                <td>Accountant</td>
				                <td>Tokyo</td>
				                <td>63</td>
				                <td>2011/07/25</td>
				                <td>$170,750</td>
				            </tr>
				            <tr>
				                <td>Ashton Cox</td>
				                <td>Junior Technical Author</td>
				                <td>San Francisco</td>
				                <td>66</td>
				                <td>2009/01/12</td>
				                <td>$86,000</td>
				            </tr>
				            <tr>
				                <td>Cedric Kelly</td>
				                <td>Senior Javascript Developer</td>
				                <td>Edinburgh</td>
				                <td>22</td>
				                <td>2012/03/29</td>
				                <td>$433,060</td>
				            </tr>
				            <tr>
				                <td>Airi Satou</td>
				                <td>Accountant</td>
				                <td>Tokyo</td>
				                <td>33</td>
				                <td>2008/11/28</td>
				                <td>$162,700</td>
				            </tr>
				            <tr>
				                <td>Brielle Williamson</td>
				                <td>Integration Specialist</td>
				                <td>New York</td>
				                <td>61</td>
				                <td>2012/12/02</td>
				                <td>$372,000</td>
				            </tr>
				            <tr>
				                <td>Herrod Chandler</td>
				                <td>Sales Assistant</td>
				                <td>San Francisco</td>
				                <td>59</td>
				                <td>2012/08/06</td>
				                <td>$137,500</td>
				            </tr>
				            <tr>
				                <td>Rhona Davidson</td>
				                <td>Integration Specialist</td>
				                <td>Tokyo</td>
				                <td>55</td>
				                <td>2010/10/14</td>
				                <td>$327,900</td>
				            </tr>
				            <tr>
				                <td>Colleen Hurst</td>
				                <td>Javascript Developer</td>
				                <td>San Francisco</td>
				                <td>39</td>
				                <td>2009/09/15</td>
				                <td>$205,500</td>
				            </tr>
				            <tr>
				                <td>Sonya Frost</td>
				                <td>Software Engineer</td>
				                <td>Edinburgh</td>
				                <td>23</td>
				                <td>2008/12/13</td>
				                <td>$103,600</td>
				            </tr>
				            <tr>
				                <td>Jena Gaines</td>
				                <td>Office Manager</td>
				                <td>London</td>
				                <td>30</td>
				                <td>2008/12/19</td>
				                <td>$90,560</td>
				            </tr>
				            <tr>
				                <td>Quinn Flynn</td>
				                <td>Support Lead</td>
				                <td>Edinburgh</td>
				                <td>22</td>
				                <td>2013/03/03</td>
				                <td>$342,000</td>
				            </tr>
				            <tr>
				                <td>Charde Marshall</td>
				                <td>Regional Director</td>
				                <td>San Francisco</td>
				                <td>36</td>
				                <td>2008/10/16</td>
				                <td>$470,600</td>
				            </tr>
				            <tr>
				                <td>Haley Kennedy</td>
				                <td>Senior Marketing Designer</td>
				                <td>London</td>
				                <td>43</td>
				                <td>2012/12/18</td>
				                <td>$313,500</td>
				            </tr>
				            <tr>
				                <td>Tatyana Fitzpatrick</td>
				                <td>Regional Director</td>
				                <td>London</td>
				                <td>19</td>
				                <td>2010/03/17</td>
				                <td>$385,750</td>
				            </tr>
				            <tr>
				                <td>Michael Silva</td>
				                <td>Marketing Designer</td>
				                <td>London</td>
				                <td>66</td>
				                <td>2012/11/27</td>
				                <td>$198,500</td>
				            </tr>
				            <tr>
				                <td>Paul Byrd</td>
				                <td>Chief Financial Officer (CFO)</td>
				                <td>New York</td>
				                <td>64</td>
				                <td>2010/06/09</td>
				                <td>$725,000</td>
				            </tr>
				            <tr>
				                <td>Gloria Little</td>
				                <td>Systems Administrator</td>
				                <td>New York</td>
				                <td>59</td>
				                <td>2009/04/10</td>
				                <td>$237,500</td>
				            </tr>
				            <tr>
				                <td>Bradley Greer</td>
				                <td>Software Engineer</td>
				                <td>London</td>
				                <td>41</td>
				                <td>2012/10/13</td>
				                <td>$132,000</td>
				            </tr>
				            <tr>
				                <td>Dai Rios</td>
				                <td>Personnel Lead</td>
				                <td>Edinburgh</td>
				                <td>35</td>
				                <td>2012/09/26</td>
				                <td>$217,500</td>
				            </tr>
				            <tr>
				                <td>Jenette Caldwell</td>
				                <td>Development Lead</td>
				                <td>New York</td>
				                <td>30</td>
				                <td>2011/09/03</td>
				                <td>$345,000</td>
				            </tr>
				            <tr>
				                <td>Yuri Berry</td>
				                <td>Chief Marketing Officer (CMO)</td>
				                <td>New York</td>
				                <td>40</td>
				                <td>2009/06/25</td>
				                <td>$675,000</td>
				            </tr>
				            <tr>
				                <td>Caesar Vance</td>
				                <td>Pre-Sales Support</td>
				                <td>New York</td>
				                <td>21</td>
				                <td>2011/12/12</td>
				                <td>$106,450</td>
				            </tr>
				            <tr>
				                <td>Doris Wilder</td>
				                <td>Sales Assistant</td>
				                <td>Sidney</td>
				                <td>23</td>
				                <td>2010/09/20</td>
				                <td>$85,600</td>
				            </tr>
				            <tr>
				                <td>Angelica Ramos</td>
				                <td>Chief Executive Officer (CEO)</td>
				                <td>London</td>
				                <td>47</td>
				                <td>2009/10/09</td>
				                <td>$1,200,000</td>
				            </tr>
				            <tr>
				                <td>Gavin Joyce</td>
				                <td>Developer</td>
				                <td>Edinburgh</td>
				                <td>42</td>
				                <td>2010/12/22</td>
				                <td>$92,575</td>
				            </tr>
				            <tr>
				                <td>Jennifer Chang</td>
				                <td>Regional Director</td>
				                <td>Singapore</td>
				                <td>28</td>
				                <td>2010/11/14</td>
				                <td>$357,650</td>
				            </tr>
				    	</tbody>
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