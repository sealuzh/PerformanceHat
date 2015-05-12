package eu.cloudwave.wp5.feedback.eclipse.base.resources.markers;

/**
 * Represents the location of a marker within a file.
 */
public class MarkerPosition {

  private int line;
  private int start;
  private int end;

  public MarkerPosition(final int line, final int start, final int end) {
    super();
    this.line = line;
    this.start = start;
    this.end = end;
  }

  public int getLine() {
    return line;
  }

  public int getStart() {
    return start;
  }

  public int getEnd() {
    return end;
  }

}
