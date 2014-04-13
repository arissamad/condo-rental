function Page(initialMetaId) {
	this.initialMetaId = initialMetaId;
	
	// First, check to see if the user is logged in
	if(Cookie.get("sirra-session-id") == null) {
		new LoginWidget();
	} else {
		this.load();
	}
}

Page.prototype.load = function() {
	Rest.get("/api/menus", [], $A(this, function(menus) {
		gv.menuWidget = new MenuWidget(menus);
		gv.menuWidget.select(this.initialMetaId);
	}));
	
	Rest.get("/api/users/pendinginfo", {}, $A(this, function(pendingInfo) {
		if(pendingInfo.hasPending == true) {
			var msg = new BigMessageWidget("Welcome! We need the following information from you: " + pendingInfo.message +
					".<br/>Click <span class='link'>here</span> to go to your profile page.");
			msg.widget.find(".link").click(function() {
				gv.menuWidget.select("profile");
				msg.remove();
			});
		}
	}));
}