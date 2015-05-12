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

<p>This loop has been identified as critical.</p>
<p><span class="highlight">The Average Total Time is: ${avgTotal}</span></br>
Average Iterations: ${avgInterations}; Average Time per Iteration: ${avgTimePerIteration}</p>

<div><strong>Executed methods:</strong></div>
<table border="0" cellspacing="0">
  <#list procedureExecutions as procExec>
    <tr>
      <td style="padding-right: 20px;">${procExec.getClassName()}.${procExec.getName()?html}:</td>
      <td>${procExec.getExecutionTime()}</td>
    </tr>
  </#list>
</table>