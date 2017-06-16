define([], function() {
	return {
		'report/2' : {
			'rpId' : 2,
			'templeteId':2,
			'name':'CaaP/AI Event',
			'type':'moment',
			'content':[
				{
		    		'tabName':'Header',
		    		'controls':[
		    			{
							'type':'block',
							'style' : {
								'background-color' : '#00BCF2','height':'124px', 'width': '100%','left':0, 'top':'60px','z-index' : 1
							}
						},
						{
							'type':'textbox',
							'text':'CaaP/AI Event',
							'style' : {
								'font-size' : '27px',
								'font-family' : 'yahei',
								'left':'20px',
								'top':'80px',
								'z-index' : 2,
								'height':'30px'
							}
						},
						{
							'type':'textbox',
							'text':'EOD Report',
							'style' : {
								'left':'20px',
								'top':'125px',
								'z-index' : 2,
								'height':'60px'
							}
						},
						{
							'type':'textbox',
							'text':'December 13, 2016',
							'style' : {
								'left':'20px',
								'top':'195px',
								'z-index' : 2,
								'height':'60px'
							}
						},
						{
							'type':'image',
							'url':'https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo_top_ca79a146.png',
							'style' : {
								'right':'10px',
								'top':'90px',
								'z-index' : 2,
								'height':'60px'
							}
						}
		    		],
		    		'id': 11
		    	},
	        	{
	        		'tabName':'Executive Summary',
	        		'controls':[
	        			{
							'type':'block',
							'text' : '',
							'style' : {
								'left':'20px',
								'top':'20px',
								'z-index' : 1
							}
						},
						{
							'type':'textbox',
							'text':'Executive Summary',
							'style' : {
								'font-size' : '35px', 'font-family' : 'yahei',
								'left':'20px',
								'z-index' : 2,
								'top':'50px'
							}
						},
						{
							'type':'textarea',
							'text':'Microsoft’s event kicked-off Tuesday morning, resulting in a moderate '+
							       'news cycle with a total of 38 media placements* and 3 broadcast hits as'+
							       ' of 2PM PT. Media placements were mixed in sentiment, with 45% positive,'+
							       ' 50% neutral, and 5% negative. AT may provide additional summary here.',
							'editable' : true,
							'style' : {
								'font-size' : '18px', 'font-family' : 'yahei',
								'left':'20px',
								'z-index' : 2,
								'top':'140px',
								'resize':'none',
								'width':'1000px',
								'height':'250px'
							}
						}

	        		],
	        		'id': 22
	        	},
	        	{
	        		'tabName':'Summary by numbers',
	        		'controls':[
	        			{
							'type':'tablea',
							'cols' : 3,
							'text':'BY THE NUMBERS',
							'style' : {
								'left':'100px',
								'top':'80px',
								'width':'450px'
							},
							'strip' : true,
							'headerBgColor' : '#00BCF2',
							'dataset':{
								'id':1,
								'type':'by-the-numbers',
								'data':[
									['MEDIA PLACEMENTS', 'POSITIVE SENTIMENT', 'MESSAGE  RESONANCE'],
									[38, '45%', '50%'],
									['TWEETS', 'PAGEVIEWS', 'BROADCAST HITS'],
									['1,130', '*Will be available in Day Two reporting', '3']
								]
							}
						}
	        		],
	        		'id': 33
	        	},
	        	{
	        		'tabName':'Press Highlights',
	        		'controls':[
	        			{
							'type':'linklist',
							'style' : {
								'left':'40px',
								'top':'40px',
								'width':'800px',
								'line-height':1.8
							},
							'dataset':{
								'id':1,
								'type':'by-the-numbers',
								'data':[]
							}
						}
	        		],
	        		'id': 44
	        	}
			]
		}
	};
});
