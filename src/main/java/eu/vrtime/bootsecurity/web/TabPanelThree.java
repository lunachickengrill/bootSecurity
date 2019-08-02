package eu.vrtime.bootsecurity.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class TabPanelThree extends Panel {

	public TabPanelThree(final String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Label("label3", Model.of("TabPanelThree")));
	}

}
