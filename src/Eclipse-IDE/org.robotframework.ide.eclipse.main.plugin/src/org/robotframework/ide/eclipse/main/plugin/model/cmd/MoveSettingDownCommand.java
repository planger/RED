/*
 * Copyright 2015 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see licence.txt file for details.
 */
package org.robotframework.ide.eclipse.main.plugin.model.cmd;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import org.robotframework.ide.eclipse.main.plugin.model.RobotKeywordCall;
import org.robotframework.ide.eclipse.main.plugin.model.RobotModelEvents;
import org.robotframework.ide.eclipse.main.plugin.model.RobotSetting;
import org.robotframework.ide.eclipse.main.plugin.model.RobotSetting.SettingsGroup;
import org.robotframework.ide.eclipse.main.plugin.model.RobotSettingsSection;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.EditorCommand;

public class MoveSettingDownCommand extends EditorCommand {

    private final RobotSetting setting;

    public MoveSettingDownCommand(final RobotSetting setting) {
        this.setting = setting;
    }

    @Override
    public void execute() throws CommandExecutionException {
        final RobotSettingsSection section = setting.getParent();
        final int currentIndex = section.getChildren().indexOf(setting);
        final int downIndex = findNextIndexDown(currentIndex, setting);
        if (downIndex >= section.getChildren().size()) {
            return;
        }
        Collections.swap(section.getChildren(), currentIndex, downIndex);

        eventBroker.post(RobotModelEvents.ROBOT_SETTING_MOVED, section);
    }

    private int findNextIndexDown(final int currentIndex, final RobotSetting setting) {
        final List<RobotKeywordCall> children = setting.getParent().getChildren();
        for (int i = currentIndex + 1; i < children.size(); i++) {
            if (getSettingsGroupSet(((RobotSetting) children.get(i)).getGroup()).equals(
                    getSettingsGroupSet(setting.getGroup()))) {
                return i;
            }
        }
        return children.size();
    }

    private EnumSet<SettingsGroup> getSettingsGroupSet(final SettingsGroup group) {
        final EnumSet<SettingsGroup> imports = EnumSet.of(SettingsGroup.LIBRARIES, SettingsGroup.RESOURCES,
                SettingsGroup.VARIABLES);
        if (imports.contains(group)) {
            return imports;
        }
        return EnumSet.of(group);
    }
}
