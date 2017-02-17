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
  		<th>SEAT</th>
  		<th>TICKET PRICE</th>
  	</tr>  	
  	<#list tickets as ticket>
	<tr>
  		<td>${ticket.id}</td> 
  		<td>${ticket.seat}</td>
  		<td>${ticket.ticketPrice}</td>
  	</tr>  
  	</#list>
  </table>
</div>  
</body>
</html>