<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:Main title="Monitoring ${procedure.getName()}(${procedure.argumentsToString()})">

	<jsp:attribute name="scripts">
	
    <script src="/static/js/dygraph-combined.js"></script>
    
    <script type="text/javascript">
		      function formatDate(date, granularity) {
		        return Dygraph.zeropad(date.getDate()) + "."
		        + Dygraph.zeropad(date.getMonth()+1) + "."
		        + Dygraph.zeropad(date.getFullYear()) + " " 
		        + Dygraph.zeropad(date.getHours()) + ":"
		        + Dygraph.zeropad(date.getMinutes()) + ":"
		        + Dygraph.zeropad(date.getSeconds());
		      }
    
          plots = [];

					plots.push(new Dygraph(
					// containing div
					document.getElementById("graph-execution-time"),

					// data
					${dataExecutionTime},

					// options
					{
						title : "Execution Time [ms]",
						labels : [ 'date', 'A' ],
						showRangeSelector : true,
						highlightCircleSize : 3,
						strokeWidth : 1,
						connectSeparatedPoints : true,
						
						axes: {
							x: {
								axisLabelFormatter: function(date, granularity) {
						      return formatDate(date, granularity);
					      },
			        }
			      },

						highlightSeriesOpts : {
							strokeWidth : 3,
							strokeBorderWidth : 1,
							highlightCircleSize : 5,
						},

						drawCallback : function(plot, isInitial) {
							redraw(plot, isInitial)
						}
					}));

					plots.push(new Dygraph(

					// containing div
					document.getElementById("graph-cpu-usage"),

					// data
					${dataCpuUsage},

					// options
					{
						title : "CPU Usage [%]",
						labels : [ 'date', 'B', ],
						valueRange : [ 0, 100 ],
						showRangeSelector : true,
						highlightCircleSize : 3,
						strokeWidth : 1,
						connectSeparatedPoints : true,
						
						axes: {
				      x: {
				        axisLabelFormatter: function(date, granularity) {
				          return formatDate(date, granularity);
				        },
              }
	          },

						highlightSeriesOpts : {
							strokeWidth : 3,
							strokeBorderWidth : 1,
							highlightCircleSize : 5,
						},

						drawCallback : function(plot, isInitial) {
							redraw(plot, isInitial)
						}
					}));

					var doRedraw = true;

					function redraw(updatedPlot, isInitial) {
						if (!isInitial && doRedraw) {
							doRedraw = false;
							for (var i = 0; i < plots.length; i++) {
								if (plots[i] != updatedPlot) {
									plots[i].updateOptions({
										dateWindow : updatedPlot.xAxisRange(),
										valueRange : updatedPlot.yAxisRange()
									});
								}
							}
							doRedraw = true;
						}
					}
				</script>
    
  </jsp:attribute>

	<jsp:body>
    
    <div class="subtitle">${procedure}:</div>
    
    <div id="graph-execution-time" class="plot" style="height: 240px;"></div>
    <div id="graph-cpu-usage" class="plot padding-top" style="height: 160px;"></div>
    
  </jsp:body>

</t:Main>