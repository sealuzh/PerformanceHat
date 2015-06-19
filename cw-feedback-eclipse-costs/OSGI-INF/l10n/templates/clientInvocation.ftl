<style>
	<#include "style.css">

	p.invocation {
		margin-top: 10px;	
	}
</style>

<#include "header.ftl">

<table class="theme2">
	<tr>
		<td><div class="arrow_box">requests</div></td>
		<td clas="spacer"></td>
		<td><div class="service_box">${serviceIdentifier?keep_after_last(".")?cap_first} Service</div></td>
		<td><div class="arrow_box">requests</div></td>
		<td clas="spacer"></td>
		<td><div class="service_box">${invokedClassname?keep_after_last(".")} </div></td>
	</tr>
	<tr>
		<td>
			<ul label="Frequency">
				<li>Min:	${overallMin} requests per ${interval}</li>
				<li>Avg:	${overallAvg} requests per ${interval}</li>
				<li>Max:	${overallMax} requests per ${interval}</li>
			</ul>
		</td>
		<td></td>
		<td>
			<ul label="<#if isNew>New </#if>Invocation">
				<li>${invokedMethodName}</li>
			</ul>
		</td>
		<td></td>
		<td></td>
		<td class="costs">
			
			<#if instances?has_content || maxRequests?has_content || pricePerInstance?has_content>
				<#if predictions?has_content>
					<ul label="Expected impact">
					<#list predictions as prediction>
						<li>${prediction.getType().getName()}: ${prediction.getValue()} ${prediction.getType().getUnit()}</li>
					</#list> 	
					</ul>
				</#if>
			
				<ul label="Status">
					<#if instances?has_content><li>${instances} instances</li></#if>
					<#if maxRequests?has_content><li>Max ${maxRequests} req/s per instance</li></#if>
					<#if pricePerInstance?has_content><li>USD ${pricePerInstance} per instance hour</li></#if>
				</ul>
			</#if>
			
		</td> 
	  </tr>
</table>