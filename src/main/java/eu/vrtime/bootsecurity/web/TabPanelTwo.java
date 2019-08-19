package eu.vrtime.bootsecurity.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class TabPanelTwo extends Panel {

	public TabPanelTwo(final String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Label("label2", Model.of("TabPanelTwo")));
	}

}
