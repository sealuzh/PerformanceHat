'use strict';

/**
 * Created by Emanuel Stoeckli on 02.06.15.
 */

angular.module('FeedbackApp')
  .controller('EnvironmentCtrl', function($scope) {
 
 	  var showGraph = function(){
			  // http://blog.thinkst.com/2011/06/simple-graphs-with-arborjs.html      
		
		      var sys = arbor.ParticleSystem(800, 600, 0.5);
				  sys.parameters({gravity:true});
		          sys.renderer = Renderer("#viewport") ;
		
			  var data = {
				nodes:{
					animals:{'color':'#543266','shape':'dot','label':'Shop Service'},
					dog:{'color':'green','shape':'dot','label':'Assortment Service'},
					cat:{'color':'blue','shape':'dot','label':'Currency Service'}
				},
				edges:{
					animals:{ dog:{}, cat:{} }
				}
			};
			
			sys.graft(data);
	  }; 
	  
	  showGraph();
});
