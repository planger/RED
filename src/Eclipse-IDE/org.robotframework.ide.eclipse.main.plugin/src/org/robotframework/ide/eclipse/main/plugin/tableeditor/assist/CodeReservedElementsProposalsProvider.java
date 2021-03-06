/*
 * Copyright 2016 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.ide.eclipse.main.plugin.tableeditor.assist;

import java.util.List;
import java.util.Optional;

import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.rf.ide.core.executor.RobotRuntimeEnvironment;
import org.rf.ide.core.testdata.text.read.recognizer.RobotToken;
import org.robotframework.ide.eclipse.main.plugin.assist.AssistProposal;
import org.robotframework.ide.eclipse.main.plugin.assist.AssistProposalPredicate;
import org.robotframework.ide.eclipse.main.plugin.assist.AssistProposalPredicates;
import org.robotframework.ide.eclipse.main.plugin.assist.RedCodeReservedWordProposals;
import org.robotframework.ide.eclipse.main.plugin.model.RobotKeywordCall;
import org.robotframework.red.jface.assist.AssistantContext;
import org.robotframework.red.jface.assist.RedContentProposal;
import org.robotframework.red.jface.assist.RedContentProposalProvider;
import org.robotframework.red.nattable.edit.AssistanceSupport.NatTableAssistantContext;

public class CodeReservedElementsProposalsProvider implements RedContentProposalProvider {

    private final RobotRuntimeEnvironment environment;

    private final IRowDataProvider<?> dataProvider;

    public CodeReservedElementsProposalsProvider(final RobotRuntimeEnvironment environment,
            final IRowDataProvider<?> dataProvider) {
        this.environment = environment;
        this.dataProvider = dataProvider;
    }

    @Override
    public RedContentProposal[] getProposals(final String contents, final int position,
            final AssistantContext context) {
        final String prefix = contents.substring(0, position);

        final AssistProposalPredicate<String> predicateWordHasToSatisfy = createWordPredicate(
                (NatTableAssistantContext) context);
        final List<? extends AssistProposal> reservedWordProposals = new RedCodeReservedWordProposals(
                predicateWordHasToSatisfy).getReservedWordProposals(prefix);

        return reservedWordProposals.stream()
                .map(proposal -> RedCodeReservedWordProposals.GHERKIN_ELEMENTS.contains(proposal.getLabel())
                        ? new AssistProposalAdapter(environment, proposal, " ")
                        : new AssistProposalAdapter(environment, proposal, p -> true))
                .toArray(RedContentProposal[]::new);
    }

    private AssistProposalPredicate<String> createWordPredicate(final NatTableAssistantContext context) {
        final int cellIndex = context.getColumn();
        final Object tableElement = dataProvider.getRowObject(context.getRow());
        if (tableElement instanceof RobotKeywordCall) {
            final List<RobotToken> tokens = ((RobotKeywordCall) tableElement).getLinkedElement().getElementTokens();

            final Optional<RobotToken> firstTokenInLine = tokens.isEmpty() ? Optional.empty()
                    : Optional.of(tokens.get(0));
            return AssistProposalPredicates.codeReservedWordsPredicate(cellIndex + 1, firstTokenInLine);
        } else {
            return AssistProposalPredicates.alwaysFalse();
        }
    }
}
