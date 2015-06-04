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
			<#if isNew>
				<ul label="Expected impact">
					<li>Additional instances: 5</li>
					<li>Additional costs per hour: 0.5</li>
					<li>Cost Trend: +50%</li>
				</ul>
			</#if>
			
			<ul label="Status">
				<#if instances?has_content><li>${instances} instances</li></#if>
				<#if maxRequests?has_content><li>Max ${maxRequests} req/s per instance</li></#if>
				<#if pricePerInstance?has_content><li>USD ${pricePerInstance} per instance hour</li></#if>
			</ul>
		</td> 
	  </tr>
</table>