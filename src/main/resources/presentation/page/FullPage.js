function FullPage(initialMetaId, title, resourcePath) {
	this.initialMetaId = initialMetaId;
	
	if(initialMetaId != null) {
		
		if(gv.menuWidget == null) {
			Rest.get("/api/menus", [], $A(this, function(menus) {
				gv.menuWidget = new MenuWidget(menus);
				gv.menuWidget.highlight(initialMetaId);
			}));
		} else {
			gv.menuWidget.highlight(initialMetaId);
		}
	}

	current = $(".app-content");
	
	PageStack.push();
	
	History.pushState({}, title, resourcePath);
	
	this.keyUpFunc = $IA(this, function(e) {
		if(e.keyCode == 27) { // ESC
			this.close();
		}
	});
	
	$(document).keyup(this.keyUpFunc);
}

FullPage.prototype.close = function() {

	$(document).unbind("keyup", this.keyUpFunc);
	
	var popped = PageStack.pop();
	
	if(!popped) {
		gv.menuWidget.select(this.initialMetaId);
	}
};