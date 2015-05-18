package org.robotframework.ide.eclipse.main.plugin.navigator.actions;

import org.eclipse.jface.dialogs.InputLoadingFormComposite;
import org.eclipse.jface.dialogs.RobotPopupDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.robotframework.ide.eclipse.main.plugin.project.library.KeywordSpecification;

class KeywordDocumentationPopup extends RobotPopupDialog {

    private InputLoadingFormComposite<Composite> composite;
    private final KeywordSpecification specification;

    KeywordDocumentationPopup(final Shell parent, final KeywordSpecification spec) {
        super(parent);
        this.specification = spec;
    }

    @Override
    protected Control createDialogControls(final Composite parent) {
        composite = new KeywordDocumentationComposite(parent, specification);
        return composite;
    }

    @Override
    protected Control getFocusControl() {
        return composite.getFocusControl();
    }
}
