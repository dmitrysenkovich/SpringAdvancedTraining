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
  		<th>NAME</th>
  		<th>NUMBER OF SEATS</th>
  	</tr>  	
  	<#list auditoriums as auditorium>
	<tr>
  		<td>${auditorium.id}</td> 
  		<td>${auditorium.name}</td>
  		<td>${auditorium.numberOfSeats}</td>
  	</tr>  
  	</#list>
  </table>
</div>  
</body>
</html>