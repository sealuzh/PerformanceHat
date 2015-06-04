<style>
	<#include "style.css">
</style>

<#include "header.ftl">

<table class="theme2">
	<tr>
		<td><div class="arrow_box">overall requests</div></td>
		<td clas="spacer"></td>
		<td><div class="service_box">${serviceIdentifier?keep_after_last(".")?cap_first} Service</div></td>
		<td>
			<div class="arrow_box">
				<#if !overallAvg?has_content>no</#if> requests to this method
			</div>
		</td>
		<td clas="spacer"></td>
		<td><div class="service_box">${serviceMethod}</div></td>
	</tr>
	<tr>
		<td>
			<ul label="Overall requests per ${interval}">
				<li>Min:	${overallMin!0}</li>
				<li>Avg:	${overallAvg!0}</li>
				<li>Max:	${overallMax!0}</li>
			</ul>
		</td>
		
		<td></td>
		
		<td class="costs">
			<ul label="Cost Factors">
				<#if instances?has_content><li>${instances} instances</li></#if>
				<#if maxRequests?has_content><li>Max ${maxRequests} req/s per instance</li></#if>
				<#if pricePerInstance?has_content><li>USD ${pricePerInstance} per instance hour</li></#if>
			</ul>
		</td>
		
		<td>
			<#if overallAvg?has_content>
				<ul label="Requests per ${interval}:">
					<li>Min:	${incomingMin!0}</li>
					<li>Avg:	${incomingAvg!0}</li>
					<li>Max:	${incomingMax!0}</li>
				</ul>
			</#if>
		</td>
		
		<td></td>
		
		<td>
			<#if requests?has_content>
				<ul label="Callers (avg. requests)">
				<#list requests as request>
					 <li>${request_index + 1}. ${request.getCaller()?keep_after_last(".")?cap_first} (${request.getAvg()} req / ${interval})</li>
				</#list> 
				</ul>
			</#if>
		</td> 
	  </tr>
</table>