package cz.muni.fi.pv168.cashflow.ui.renderers;

import cz.muni.fi.pv168.cashflow.utils.Either;

import javax.swing.JLabel;

public class EitherRenderer<L, R> extends AbstractRenderer<Either<L, R>> {

    private final AbstractRenderer<L> leftRenderer;
    private final AbstractRenderer<R> rightRenderer;

    private EitherRenderer(Class<?> clazz, AbstractRenderer<L> leftRenderer, AbstractRenderer<R> rightRenderer) {
        super((Class<Either<L, R>>) clazz);
        this.leftRenderer = leftRenderer;
        this.rightRenderer = rightRenderer;
    }

    public static <L, R> EitherRenderer<L, R> create(AbstractRenderer<L> leftRenderer,
                                                     AbstractRenderer<R> rightRenderer) {
        return new EitherRenderer<>(Either.class, leftRenderer, rightRenderer);
    }

    @Override
    protected void updateLabel(JLabel label, Either<L, R> value) {
        if (value != null) {
            value.apply(
                    l -> leftRenderer.updateLabel(label, l),
                    r -> rightRenderer.updateLabel(label, r)
            );
        }
    }
}
