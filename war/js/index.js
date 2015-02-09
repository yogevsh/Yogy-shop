var app = {}; // create namespace for our app

var boughtItemsCounter = 0;
    
    //--------------
    // Models
    //--------------
    app.Item = Backbone.Model.extend({
      defaults: {
        name: '',
        price: 0
      }
    });

    //--------------
    // Collections
    //--------------
    app.ItemsList = Backbone.Collection.extend({
      model: app.Item,
      url: '/productslist'
      
    });

	
	//--------------
    // Views
    //--------------
    
    // renders individual item (li)
    app.ItemView = Backbone.View.extend({
      tagName: 'li',
      template: _.template($('#item-template').html()),
      render: function(){
        this.$el.html(this.template(this.model.toJSON()));
        return this; // enable chained calls
      },
	  
	   events: {
		'click .buy-item-btn': 'onBuyClicked',
		'click .remove-item-btn': 'onRemoveClicked'
		},
		
		onBuyClicked: function() {
		  this.$('.buy-item-btn').toggleClass('hidden',true);
		  this.$('.remove-item-btn').toggleClass('hidden',false);
		  //update the items counter
		  boughtItemsCounter++;
		  $(".items-counter").html(boughtItemsCounter);
		},
		
		onRemoveClicked: function() {
		  this.$('.buy-item-btn').toggleClass('hidden',false);
		  this.$('.remove-item-btn').toggleClass('hidden',true);
		   //update the items counter
		  boughtItemsCounter--;
		  $(".items-counter").html(boughtItemsCounter);
		 }
    });
	
	 
	// renders Items Page Header
    app.ItemsPageHeaderView = Backbone.View.extend({
      template: _.template($('#items-page-header-template').html()),
      render: function(){
        this.$el.html(this.template({counter : boughtItemsCounter}));
        return this; // enable chained calls
      }
	});
	
	
	app.ItemsPageView = Backbone.View.extend({
		el: $("#mainContent"),
	 
		initialize: function () {
			this.itemsList = new app.ItemsList();
			var self = this;
			
			//fetch Callback
			this.itemsList.fetch().done(function () { // queue up this callback to run when fetch() completes
			       self.render();
			  });
			
			//fetch the items from the server
			this.itemsList.fetch();	
		},
	 
		render: function () {
			var that = this;
			
			//add items page header
			var header = new app.ItemsPageHeaderView();
			this.$el.html(header.render().el);	
			
			_.each(this.itemsList.models, function (item) {
				that.renderItem(item);
			}, this);
		},
	 
		renderItem: function (item) {
			var itemView = new app.ItemView({
				model: item
			});
			this.$el.append(itemView.render().el);
		}
	});
	
	// renders Items Page Header
    app.CheckoutView = Backbone.View.extend({
      template: _.template($('#checkout-template').html()),
	  el: $("#mainContent"),
	  
	  initialize: function () { this.render(); },
	  
      render: function(){
        this.$el.html(this.template());
		
        return this; // enable chained calls
      }
	});
	
	//--------------
    // Router
    //--------------
	app.Router = Backbone.Router.extend({
		  routes: {
			'': 'productsList',
			'checkout': 'checkout'
			
		  },

		  checkout: function () {
			var view = new app.CheckoutView();

		  },

		  productsList: function () {
			boughtItemsCounter = 0;
			var view = new app.ItemsPageView();
		  }
	});
	
	//define our new instance of router 
	var appRouter = new app.Router(); 
	
	// without History API 
	Backbone.history.start(); 