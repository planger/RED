/*
 * Copyright 2015 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.ide.eclipse.main.plugin.tableeditor.settings;

import static org.eclipse.jface.viewers.Stylers.mixingStyler;
import static org.eclipse.jface.viewers.Stylers.withFontStyle;
import static org.eclipse.jface.viewers.Stylers.withForeground;

import java.util.Map.Entry;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.robotframework.ide.eclipse.main.plugin.RedImages;
import org.robotframework.ide.eclipse.main.plugin.RedTheme;
import org.robotframework.ide.eclipse.main.plugin.model.RobotSetting;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.HeaderFilterMatchesCollection;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.MatchesHighlightingLabelProvider;
import org.robotframework.red.graphics.ColorsManager;
import org.robotframework.red.graphics.ImagesManager;

import com.google.common.base.Supplier;

class GeneralSettingsCommentsLabelProvider extends MatchesHighlightingLabelProvider {

    GeneralSettingsCommentsLabelProvider(final Supplier<HeaderFilterMatchesCollection> matcherProvider) {
        super(matcherProvider);
    }

    @Override
    public Color getBackground(final Object element) {
        return getSetting(element) == null ? ColorsManager.getColor(250, 250, 250) : null;
    }

    @Override
    public final StyledString getStyledText(final Object element) {
        final String comment = getComment(element);
        if (!comment.isEmpty()) {
            final Styler commentStyler = mixingStyler(
                    withForeground(RedTheme.getCommentsColor()),
                    withFontStyle(SWT.ITALIC));
            final String prefix = "# ";
            return highlightMatches(new StyledString(prefix + comment, commentStyler), prefix.length(), comment);
        }
        return new StyledString();
    }

    @Override
    public String getToolTipText(final Object element) {
        return "# " + getComment(element);
    }

    @Override
    public Image getToolTipImage(final Object element) {
        return ImagesManager.getImage(RedImages.getTooltipImage());
    }

    private String getComment(final Object element) {
        final RobotSetting setting = getSetting(element);
        return setting != null ? setting.getComment() : "";
    }

    private RobotSetting getSetting(final Object element) {
        return (RobotSetting) ((Entry<?, ?>) element).getValue();
    }
}
