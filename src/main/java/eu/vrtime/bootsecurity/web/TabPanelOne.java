package eu.vrtime.bootsecurity.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class TabPanelOne extends Panel {

	public TabPanelOne(final String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Label("label1", Model.of("TabPanelOne")));
	}

}
