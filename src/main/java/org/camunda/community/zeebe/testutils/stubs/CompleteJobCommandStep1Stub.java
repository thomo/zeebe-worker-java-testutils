package org.camunda.community.zeebe.testutils.stubs;

import io.camunda.zeebe.client.api.ZeebeFuture;
import io.camunda.zeebe.client.api.command.CompleteJobCommandStep1;
import io.camunda.zeebe.client.api.command.FinalCommandStep;
import io.camunda.zeebe.client.api.response.CompleteJobResponse;
import io.camunda.zeebe.client.impl.ZeebeObjectMapper;
import io.camunda.zeebe.client.impl.command.CommandWithVariables;
import java.time.Duration;

class CompleteJobCommandStep1Stub extends CommandWithVariables<CompleteJobCommandStep1>
    implements CompleteJobCommandStep1 {

  private final ZeebeObjectMapper mapper;

  private final ActivatedJobStub job;

  CompleteJobCommandStep1Stub(final ActivatedJobStub job, final ZeebeObjectMapper mapper) {
    super(mapper);
    this.mapper = mapper;
    this.job = job;
  }

  @Override
  protected CompleteJobCommandStep1 setVariablesInternal(final String variables) {
    if (job != null) {
      job.setOutputVariables(mapper.fromJsonAsMap(variables));
    }
    return this;
  }

  @Override
  public FinalCommandStep<CompleteJobResponse> requestTimeout(final Duration requestTimeout) {
    // no effect on stub
    return this;
  }

  @Override
  public ZeebeFuture<CompleteJobResponse> send() {
    final ZeebeFutureStub<CompleteJobResponse> result = new ZeebeFutureStub<>();

    if (job != null) {
      job.setCompleted();
      result.complete(new CompleteJobResponse() {});
    } else {
      result.completeExceptionally(ExceptionBehavior.buildNotFoundException());
    }

    return result;
  }
}
