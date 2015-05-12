package eu.cloudwave.wp5.monitoring.tracing;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;

import eu.cloudwave.wp5.monitoring.dto.RunningProcedureExecution;

/**
 * Stores all the monitored call traces per thread. It is implemented as a singleton and accessible via a static field.
 */
public class TraceStorage {

  private final ArrayListMultimap<Long, RunningProcedureExecution> storage;

  public static final TraceStorage INSTANCE = new TraceStorage();

  private TraceStorage() {
    this.storage = ArrayListMultimap.create();
  }

  /**
   * Adds a {@link RunningProcedureExecution} to the call trace of the current thread.
   * 
   * @param procedureExecution
   *          the {@link RunningProcedureExecution} to be added
   */
  public void add(final RunningProcedureExecution procedureExecution) {
    this.storage.put(currentThreadId(), procedureExecution);
  }

  /**
   * Checks whether the call trace of the current thread is finished.
   * 
   * @return <code>true</code> if the call trace is finished, <code>false</code> otherwise
   */
  public boolean isFinished() {
    return isFinished(Thread.currentThread().getId());
  }

  /**
   * Checks whether the call trace of the thread with the given id is finished.
   * 
   * @param threadId
   *          the id of the thread
   * @return <code>true</code> if the call trace is finished, <code>false</code> otherwise
   */
  public boolean isFinished(final long threadId) {
    for (final RunningProcedureExecution procedureExecution : storage.get(threadId)) {
      if (!procedureExecution.isFinished()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns the call trace of the current thread.
   * 
   * @return the call trace of the current thread
   */
  public List<RunningProcedureExecution> getCallTrace() {
    return getCallTrace(currentThreadId());
  }

  /**
   * Returns the call trace of the thread with the given id.
   * 
   * @param threadId
   *          the id of the thread
   * @return the call trace of the thread with the given id
   */
  public List<RunningProcedureExecution> getCallTrace(final long threadId) {
    return storage.get(threadId);
  }

  /**
   * Clears the call trace of the current thread
   */
  public void clear() {
    clear(currentThreadId());
  }

  /**
   * Clears the call trace of the thread with the given id.
   * 
   * @param threadId
   *          the id of the thread
   */
  public void clear(final long threadId) {
    storage.removeAll(threadId);
  }

  /**
   * Returns the last element in the call trace that has not been finished. This is used to detect the caller of
   * subsequent elements.
   * 
   * @return the alst element in the call trace that has not been finished.
   */
  public RunningProcedureExecution getLastNotFinished() {
    final List<RunningProcedureExecution> callTrace = getCallTrace();
    for (int i = callTrace.size() - 1; i >= 0; i--) {
      final RunningProcedureExecution last = callTrace.get(i);
      if (!last.isFinished()) {
        return last;
      }
    }
    return null;
  }

  private Long currentThreadId() {
    return Thread.currentThread().getId();
  }
}
