<style>

* { 
  font-family: Arial;
  font-size: 14px; 
}

.highlight { 
  font-size: 16px;
  font-weight: bold;
}

</style>

<#macro timeNode node indent>
		<#local spc>${""?right_pad(indent*6*4, "&nbsp;")}</#local>
		<#if node.isDataNode()>
		   	<tr>
		     	<td style='padding-right: 20px;'>${spc}${node.getText()}:</td>
	    	 	<td>${node.getPredictedTime()/1000}s</td>
	    	</tr>
	    	<#list node.getChildren() as child>
				<@timeNode node=child indent=indent+1 />
	  		</#list>
	    <#else>
		    <#list node.getChildren() as child>
				<@timeNode node=child indent=indent />
	  		</#list>
	    </#if>
</#macro>

<p>This method has been identified as critical.</p>
<p><span class="highlight">The Average Total Time is: ${avgTotal}</span></p>


<div><strong>Measured Operations:</strong></div>
<table border="0" cellspacing="0">
		<@timeNode node=procedureExecutions indent=0 />
</table>