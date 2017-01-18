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
		     	<#if !singleLineMode>	
	    	 		<#list node.getPredictedText() as text>
						<td>${text}</td>
		  			</#list>
		    	<#else>
		    		<td>${node.getPredictedText()[0]}</td>		    	
		    	</#if>
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

<p>This ${nodeName} has been identified as critical.</p>
<p><span class="highlight">The average total time is: ${avgTotal}</span></p>

<table border="0" cellspacing="0">
		<#if !singleLineMode>
			<tr>
				<td></td>
				<#list procedureExecutions.getHeader().getText() as headerText>
					<td><b>${headerText}</b></td>
		  		</#list>
			</tr>
	    </#if>	
		<@timeNode node=procedureExecutions indent=0 />
</table>