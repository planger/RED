package org.robotframework.ide.core.testData.text.context.recognizer.settingTable;

import org.robotframework.ide.core.testData.text.lexer.RobotWordType;


public class TestTemplateDeclaration extends ASettingTableElementRecognizer {

    public TestTemplateDeclaration() {
        super(SettingTableRobotContextType.TABLE_SETTINGS_TEST_TEMPLATE,
                createWithAllAsMandatory(RobotWordType.TEST_WORD,
                        RobotWordType.TEMPLATE_WORD));
    }
}
