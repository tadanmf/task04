<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- semantic-ui -->
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.13/semantic.min.css">
<base>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.13/semantic.min.js"></script>
<style type="text/css">
.wrap {
	width: 100%;
	height: 100%;
	display: flex;
	flex-direction: row;
	
	justify-content: center;
	align-items: center;
	border: red solid 1px;
}

.container {
	display: flex;
	flex-direction: column;
	border: red solid 1px;
}

.table_pie {
	display: flex;
	flex-direction: row;
	border: blue solid 1px;
}

.line_bar {
	display: flex;
	flex-direction: row;
	border: blue solid 1px;
}
</style>
<title>Chart</title>
</head>
<body>
	<div class="wrap">
		<div class="container">
			<div class="table_pie">
				<div class="table_chart"></div>
				<div class="pie_chart"></div>
			</div>
			<div class="line_bar">
				<div class="line_chart"></div>
				<div class="bar_chart"></div>
			</div>
		</div>
	</div>
</body>
</html>