package cz.muni.fi.pv168.cashflow.ui.renderers;

import cz.muni.fi.pv168.cashflow.business.model.Category;

import javax.swing.JLabel;

public final class CategoryRenderer extends AbstractRenderer<Category> {

    public CategoryRenderer() {
        super(Category.class);
    }

    @Override
    protected void updateLabel(JLabel label, Category category) {
        if (category != null) {
            label.setText(category.getName());
        }
    }
}
