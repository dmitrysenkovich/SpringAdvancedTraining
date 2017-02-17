<html>
<head><title>${entity}</title>
<body>
<div id="header">
<H2> ${entity} </H2>
</div>

<div id="content">
  <h3>Message:</h3>
  <table class="datatable" border="1">
  	<tr>
  		<th>ID</th>  
  		<th>FIRST NAME</th>
  		<th>LAST NAMR</th>
  		<th>EMAIL</th>
  	</tr>  	
  	<#list users as user>
	<tr>
  		<td>${user.id}</td> 
  		<td>${user.firstName}</td>
  		<td>${user.lastName}</td>
  		<td>${user.email}</td>
  	</tr>  
  	</#list>
  </table>
</div>  
</body>
</html>