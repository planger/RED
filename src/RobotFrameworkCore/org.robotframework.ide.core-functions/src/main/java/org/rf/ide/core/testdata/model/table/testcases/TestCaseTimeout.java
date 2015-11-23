/*
 * Copyright 2015 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.rf.ide.core.testdata.model.table.testcases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.rf.ide.core.testdata.model.AModelElement;
import org.rf.ide.core.testdata.model.FilePosition;
import org.rf.ide.core.testdata.model.ModelType;
import org.rf.ide.core.testdata.text.read.recognizer.RobotToken;


public class TestCaseTimeout extends AModelElement<TestCase> {

    private final RobotToken declaration;
    private RobotToken timeout;
    private final List<RobotToken> message = new ArrayList<>();
    private final List<RobotToken> comment = new ArrayList<>();


    public TestCaseTimeout(final RobotToken declaration) {
        this.declaration = declaration;
    }


    @Override
    public boolean isPresent() {
        return (declaration != null);
    }


    public RobotToken getDeclaration() {
        return declaration;
    }


    public RobotToken getTimeout() {
        return timeout;
    }


    public void setTimeout(final RobotToken timeout) {
        this.timeout = timeout;
    }


    public List<RobotToken> getMessage() {
        return Collections.unmodifiableList(message);
    }


    public void addMessagePart(final RobotToken messagePart) {
        this.message.add(messagePart);
    }


    public List<RobotToken> getComment() {
        return Collections.unmodifiableList(comment);
    }


    public void addCommentPart(final RobotToken rt) {
        this.comment.add(rt);
    }


    @Override
    public ModelType getModelType() {
        return ModelType.TEST_CASE_TIMEOUT;
    }


    @Override
    public FilePosition getBeginPosition() {
        return getDeclaration().getFilePosition();
    }


    @Override
    public List<RobotToken> getElementTokens() {
        final List<RobotToken> tokens = new ArrayList<>();
        if (isPresent()) {
            tokens.add(getDeclaration());
            if (getTimeout() != null) {
                tokens.add(getTimeout());
            }
            tokens.addAll(getMessage());
            tokens.addAll(getComment());
        }

        return tokens;
    }
}
