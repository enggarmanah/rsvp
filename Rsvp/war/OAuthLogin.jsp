<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Info Klinik</title>
	<link rel="stylesheet" href="css/style.css"/>
	<link rel="stylesheet" href="css/gwt.css"/>
	<link rel="stylesheet" href="css/ie.css"/>
	<script type="text/javascript">
		
		function authenticate() {
			location.replace("<%= session.getAttribute("SESSION_AUTHORIZATION_URL") %>");
		}
		
		function defer() {
			setTimeout(function(){ 
		        authenticate();
		    }, 2500);  
		}
	
	</script>
</head>
<body onload="defer()">
	<div id="body-wrapper">
		<div id="content-authenticate-wrapper">
			<div id="content-authenticate">
			
			<table id="authenticate-table">
				<tr>
					<td width="48px"><image src="images/loading.gif" class="loading-img"></image></td>
					<td class="progress-text">Melaksanakan autentifikasi dengan Facebook ...</td>
				</tr>
			</table>
			
			</div>
		</div>
	</div>
</body>
</html>