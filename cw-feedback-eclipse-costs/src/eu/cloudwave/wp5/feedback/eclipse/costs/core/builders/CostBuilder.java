package eu.cloudwave.wp5.feedback.eclipse.costs.core.builders;

import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.FeedbackBuilder;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.FeedbackCleaner;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.FeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.CostPluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.builders.participants.MicroserviceClientInvocationParticipant;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.builders.participants.MicroserviceMethodDeclarationParticipant;

public class CostBuilder extends FeedbackBuilder {

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<FeedbackBuilderParticipant> getParticipants() {
    System.out.println("CostBuilder: register participants");
    List<FeedbackBuilderParticipant> participants = Lists.newArrayList();

    participants.add(new MicroserviceClientInvocationParticipant());
    participants.add(new MicroserviceMethodDeclarationParticipant());

    return participants;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FeedbackJavaResourceFactory getFeedbackJavaResourceFactory() {
    return CostPluginActivator.instance(FeedbackJavaResourceFactory.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FeedbackCleaner getFeedbackCleaner() {
    return CostPluginActivator.instance(FeedbackCleaner.class);
  }
}