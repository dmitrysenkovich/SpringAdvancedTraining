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
  		<th>BASE PRICE</th>
  	</tr>  	
  	<#list events as event>
	<tr>
  		<td>${event.id}</td> 
  		<td>${event.name}</td>
  		<td>${event.basePrice}</td>
  	</tr>  
  	</#list>
  </table>
</div>  
</body>
</html>