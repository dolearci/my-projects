package cz.muni.fi.pv168.cashflow.ui.renderers;

import cz.muni.fi.pv168.cashflow.business.model.Template;

import javax.swing.JLabel;

public final class TemplateRenderer extends AbstractRenderer<Template> {

    public TemplateRenderer() {
        super(Template.class);
    }

    @Override
    protected void updateLabel(JLabel label, Template template) {
        if (template != null) {
            label.setText(template.getTemplateName());
        } else {
            label.setText("Select template");
        }
    }
}
