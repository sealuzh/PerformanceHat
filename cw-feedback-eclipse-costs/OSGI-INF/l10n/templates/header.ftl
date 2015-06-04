<div class="header">
	<h1>Calculations based on </h1> 
	<span>
	a time period from  
	<#if from?has_content>
		<#assign from_number = from?number>
		<#assign from_date = from_number?number_to_datetime>
		<i>${from_date?string["dd.MM.yyyy / HH:mm"]}</i>
	<#else>
		not specified
	</#if> 
	to  
	<#if to?has_content>
		<#assign to_number = to?number>
		<#assign to_date = to_number?number_to_datetime>
		<i>${to_date?string["dd.MM.yyyy / HH:mm"]}</i>
	<#else>
		not specified
	</#if>
	</span>  
	<span>
		and 
		<#if interval?has_content>
			an aggregation interval of <i>${interval}s</i>
		<#else>
			an unspecified aggregation interval
		</#if>
	</span>
</div>